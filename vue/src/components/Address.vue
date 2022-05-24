<template>
    <div>
        <my-in-content>
            <!-- 高德地图 -->
            <div id="container"></div>
            <div id="details">
                <Card style="width:340px;height:280px">
                    <div style="text-align:lef;">
                        <p>经纬度：{{this.lnglat}}</p><br/>
                        <p>地址：{{this.address}}</p><br/>
                        <p>最近的路口：{{this.nearestJunction}}</p><br/>
                        <p>最近的路：{{this.nearestRoad}}</p><br/>
                        <p>最近的POI：{{this.nearestPOI}}</p><br/>
                    </div>
                </Card>
            </div>
            <!-- 搜索 -->
            <div id="search">
                <Input v-model="searchValue" placeholder="请输入要搜索的位置" style="width: 300px" />
                <Button type="primary" @click="seachAddress">搜索</Button>
                <Button style="margin-left:5px;" type="primary" @click="reporAddress">上报位置</Button>
            </div>
        </my-in-content>
    </div>
</template>
<script>
    export default {
        name:'gdmap',
        data() {
            return {
                searchValue:'',
                gdmap: null,
                /* 当前位置 */
                thisPosition: {
                    location: '',
                    lng: '',
                    lat: ''
                },
                /* 选取的位置 */
                chosePosition: {
                    location: '',
                    lng: '',
                    lat: ''
                },
                /* 范围圆的数据 */
                myCircle: {},
                /* 签到圆对象 */
                circle: {},
                /* 编辑器对象 */
                circleEditor: null,
                /* 拖拽对象 */
                positionPickerObj: {},
                //经纬度
                lnglat:'',
                //地址
                address:'',
                //最近的路口
                nearestJunction:'',
                //最近的路
                nearestRoad:'',
                //最近的POI
                nearestPOI:'',
                /* 当前城市编码 */
                citycode: '020',
                //标记
                marker:[],
            }
        },
        created() {

        },
        mounted() {
            //地图初始化
            this.gdmap = new AMap.Map('container', {
                resizeEnable: true,//是否监控地图容器尺寸变化
                zoom: 15,//地图显示的缩放级别
                zooms: [3, 18],//地图显示的缩放级别范围在PC上，默认为[3,18]，取值范围[3-18]；
                viewMode: '2D',//默认为‘2D’，可选’3D’，选择‘3D’会显示 3D 地图效果

            })
            //加载工具条
            this.addTool();
            //获取当前位置
            this.thisLocation();

        },
        methods:{
            //工具条
            addTool () {
                AMap.plugin(['AMap.ToolBar'], () => {
                    let toolbar = new AMap.ToolBar()
                    this.gdmap.addControl(toolbar)
                })
            },
            //定位
            thisLocation () {
                this.gdmap.plugin('AMap.Geolocation', () => {
                    let geolocation = new AMap.Geolocation({
                        enableHighAccuracy: true,//是否使用高精度定位，默认:true
                        timeout: 100,            //超过10秒后停止定位，默认：无穷大
                        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                        showButton: true,        //显示定位按钮，默认：true
                        buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                        buttonOffset:new AMap.Pixel(10,20),
                        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                        zoomToAccuracy:true,     //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                    })
                    this.gdmap.addControl(geolocation);
                    geolocation.getCurrentPosition();
                    AMap.event.addListener(geolocation, 'complete', (data) => {
                        console.log(data);
                        //当前城市编码
                        this.citycode = data.addressComponent.cityCode;
                        //经纬度
                        this.thisPosition = data.position;
                        //地址
                        this.formattedAddress=data.formattedAddress;
                        this.chosePosition = this.thisPosition;
                        /* 画圆 */
                        this.cancelLocation()
                        this.addCircle()
                        /* 拖拽选址 */
                        this.positionPicker();

                    })
                    AMap.event.addListener(geolocation, 'error', function (data) {
                        alert('定位失败');
                    })
                })
            },
            /* 拖拽选址 */
            positionPicker () {
                AMapUI.loadUI(['misc/PositionPicker'], (PositionPicker) => {
                    this.positionPickerObj = new PositionPicker({
                        mode: 'dragMarker', // 设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                        map: this.gdmap // 依赖地图对象
                    })
                    this.positionPickerObj.on('success', (positionResult) => {
                        console.log(positionResult,"positionResult");
                        this.chosePosition = positionResult.position;
                        //经纬度
                        console.log("经纬度:"+positionResult.position);
                        this.lnglat=positionResult.position;
                        //地址
                        console.log("地址:"+positionResult.address);
                        this.address=positionResult.address;
                        //最近的路口
                        console.log("最近的路口:"+positionResult.nearestJunction);
                        this.nearestJunction=positionResult.nearestJunction;
                        //最近的路
                        console.log("最近的路:"+positionResult.nearestRoad);
                        this.nearestRoad=positionResult.nearestRoad;
                        //最近的POI
                        console.log("最近的POI:"+positionResult.nearestPOI);
                        this.nearestPOI=positionResult.nearestPOI;

                        /* 画圆 */
                        this.cancelLocation();
                        this.addCircle();
                    })
                    this.positionPickerObj.start([this.chosePosition.lng, this.chosePosition.lat])
                })
            },
            /* 取消圆 */
            cancelLocation() {
                this.gdmap.remove(this.circle)
                if (this.circleEditor) {
                    this.circleEditor.close()
                }
            },
            /* 画图 */
            addCircle() {
                this.myCircle = {
                    center: [this.chosePosition.lng, this.chosePosition.lat], // 圆心位置
                    radius: 50, // 半径
                    strokeColor: '#FFFF00', // 线颜色
                    strokeOpacity: 0.2, // 线透明度
                    strokeWeight: 1, // 线粗细度
                    fillColor: '#222222', // 填充颜色
                    fillOpacity: 0.2 // 填充透明度
                }
                this.circle = new AMap.Circle(this.myCircle)
                this.circle.setMap(this.gdmap)
                // 引入多边形编辑器插件
                this.gdmap.plugin(['AMap.CircleEditor'], () => {
                    // 实例化多边形编辑器，传入地图实例和要进行编辑的多边形实例
                    this.circleEditor = new AMap.CircleEditor(this.gdmap, this.circle)
                    // 开启编辑模式
                    this.circleEditor.open()
                    //this.myCircle.radius = this.circle.Mg.radius
                    this.circleEditor.on('adjust', (data) => {
                        this.myCircle.radius = data.radius
                    })
                    this.circleEditor.on('move', (data) => {
                        console.log('移动')
                        this.chosePosition.lng = data.lnglat.lng
                        this.chosePosition.lat = data.lnglat.lat
                    })
                })
            },
            //搜索
            seachAddress(){
                if(this.searchValue!=''){
                    //清楚地图上的覆盖物
                    this.gdmap.clearMap();
                    console.log("搜索");
                    this.gdmap.plugin('AMap.PlaceSearch', () => {
                        let placeSearch = new AMap.PlaceSearch({
                            // city 指定搜索所在城市，支持传入格式有：城市名、citycode和adcode
                            city: '0797',
                            map: this.gdmap
                        });
                        let that=this;
                        placeSearch.search(this.searchValue, function (status, result) {
                            // 查询成功时，result即对应匹配的POI信息
                            console.log(result)
                            var pois = result.poiList.pois;
                            for(var i = 0; i < pois.length; i++){
                                var poi = pois[i];
                                var marker = [];
                                marker[i] = new AMap.Marker({
                                    position: poi.location,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
                                    title: poi.name
                                });
                                // 将创建的点标记添加到已有的地图实例：
                                that.gdmap.add(marker[i]);
                            }
                            that.gdmap.setFitView();
                            AMap.event.addListener(placeSearch,'markerClick',function(data){
                                console.log(data);
                                let result=data;
                                //经纬度
                                let lng=result.event.lnglat.lng;
                                let lat=result.event.lnglat.lat;
                                that.lnglat=lng+","+lat;
                                //地址
                                that.address=result.data.address;
                                //最近路口
                                that.nearestJunction='';
                                //最近的路
                                that.nearestRoad='';
                                //最近的POI
                                that.nearestPOI='';

                            })
                        });
                    })
                }
            },
            //位置上报
            reporAddress(){

            },
        }
    }
</script>
<style>
    #container {
        width:100%;
        height: 600px;
    }
    #search{
        z-index:999;
        position:absolute;
        left:100px;
        top:30px;
        opacity:0.8;
    }
    #details{
        z-index:999;
        position:absolute;
        right:0px;
        top:0px;
        opacity:0.8;
    }
</style>