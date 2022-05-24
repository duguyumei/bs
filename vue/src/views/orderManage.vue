<template>
    <div class="dishManage_table" style="width: 100%;">
        <el-table :data="tableData" border :row-class-name="rowClassName">
<!--            <el-table-column prop="id" label="订单ID" sortable width="100"/>-->
            <el-table-column prop="stime" label="时间" sortable/>
            <el-table-column prop="dish" label="菜品">
                <template #default="scope">
                    <div>
                        <ul>
                            <li v-for="item in scope.row.dish">
                                {{item}}
                            </li>
                        </ul>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="message" label="备注"/>
            <el-table-column prop="people" label="收货人" />
            <el-table-column prop="address" label="收货地址" sortable />
            <el-table-column label="骑手" prop="ride">
                <template #default="scope">
                    <el-select v-if="scope.row.state == '已接单,等待配送'" v-model="scope.row.ride" prop="ride" >
                        <el-option
                                v-for="item in options"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                    <el-select v-else v-model="scope.row.ride" prop="ride" disabled>
                        <el-option
                                v-for="item in options"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column prop="state" label="状态" sortable />
            <el-table-column fixed="right" label="操作" :width="100" >
                <template #default="scope">
                    <el-button v-if="scope.row.state == '等待接单'" type="text" size="small" @click="acceptOrder(scope.row)">接单</el-button>
                    <el-button v-if="scope.row.state == '已接单,等待配送'" type="text" size="small" @click="postOrder(scope.row)">派送</el-button>
                    <el-button v-if="scope.row.state == '正在配送'" type="text" size="small" style="color: #cccccc">正在派送</el-button>
                    <el-button v-if="scope.row.state == '完成'" type="text" size="small" style="color: #cccccc">已完成</el-button>
                    <el-button v-if="scope.row.state == '用户已评论' || scope.row.state == '已回复'" type="text" size="small" @click="showEvaluate(scope.row)">查看评论</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <div style="margin-top: 10px">
            <el-pagination
                    v-model:currentPage="currentPage"
                    :page-size="pageSize"
                    :page-sizes="[5, 10, 20]"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total=total
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
            >
            </el-pagination>
        </div>
    </div>
    <div class="evaluateWindow" id="evaluateWindow">
        <el-dialog v-model="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px">
                <el-form-item label="时间" style="font-weight: bolder">
                    {{evaluate.stime}}
                </el-form-item>
                <el-form-item label="评分" style="font-weight: bolder">
                    <el-rate
                            v-model="evaluate.source"
                            disabled
                            size="large"
                            style="margin-top: 3px"
                    />
                </el-form-item>
                <el-form-item label="图片" v-if="evaluate.url != ''" style="font-weight: bolder">
                    <template #default="scope">
                        <div v-for="(item,i) in evaluate.url">
                            <el-image
                                    style="width: 100px;height: 100px;"
                                    :src="item"
                                    :preview-src-list="[item]">
                            </el-image>
                        </div>
                    </template>
                </el-form-item>
                <el-form-item label="评论" v-if="evaluate.message != ''" style="font-weight: bolder">
                    {{evaluate.message}}
                </el-form-item>
                <el-form-item label="回复" v-if="evaluate.marmessage != '' && evaluate.marmessage != null">
                    {{evaluate.marmessage}}
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" v-if="evaluate.marmessage != '' && evaluate.marmessage != null" @click="dialogVisible = false">确定</el-button>
                    <el-button type="primary" v-if="evaluate.marmessage == null" @click="returnEvaluate">回复</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
    import request from "@/utils/requst";
    import { ElMessage, ElMessageBox } from 'element-plus';

    export default {
        name: "orderManage",
        components: {

        },
        data(){
            return{
                // isMax : false,//动态绑定表单中上传框
                // search:'', //搜索关键词
                returnData:[],
                tableData:[], //表格数据
                total:0, //数据量
                currentPage:1, //页码
                pageSize:10, //页容量
                dialogVisible:false, //评论窗口
                // form:{} //新增表单
                options: [],
                evaluate:{}
            }
        },
        created() {
            //页面加载时的方法
            this.getData();
            this.rideList();
        },
        methods: {
            //渲染tabledata
            getData(){
                var that = this;
                request.get("/order",{
                    params:{
                        pageNum:this.currentPage,
                        pageSize:this.pageSize,
                        id: eval("(" + sessionStorage.getItem("data") + ")").id
                    }
                }).then(res=>{
                    this.returnData = JSON.parse(JSON.stringify(res.data.records))//深拷贝,独立对象
                    this.tableData = res.data.records;
                    this.total = res.data.total;

                    //处理时间
                    for (var i = 0; i < this.tableData.length; i++){
                        // console.log(this.tableData[i].stime);
                        this.tableData[i].stime = this.getMyDate(this.tableData[i].stime);
                        // var newDate = new Date();
                        // newDate.setTime(this.tableData[i].stime);
                        // // console.log(newDate.toLocaleString());
                        // this.tableData[i].stime = newDate.toLocaleString();
                    }


                    //处理菜品
                    for (var i = 0; i < this.tableData.length; i++){
                        let dishlist=[];
                        let dishes = this.tableData[i].dish.split(" ");
                        let num = 0;
                        for(var j = 0; j < dishes.length; j+=2){
                            let index = i;
                            num = dishes[j+1];
                            // console.log(dishes[j]," ",dishes[j+1]);
                            request.get("/dishes/getDataById",{
                                params:{
                                    id: dishes[j]
                                }
                            }).then(res=>{
                                var  dishstr = res.data.name + " ---*" +  num;
                                dishlist.push(dishstr)
                                // console.log(index,dishlist);
                                that.tableData[index].dish = dishlist;
                                // console.log(res.data.name)
                                // if(n == dishes.length){
                                    // that.tableData[i].dish = dishlist;
                                    // console.log("index",i)
                                    // console.log(this.tableData[0]);
                                // }
                            })
                        }
                    }

                    //处理状态
                    for (var i = 0; i < this.tableData.length; i++){
                        if(this.tableData[i].state == "1"){
                            this.tableData[i].state = "等待接单";
                        }
                        if(this.tableData[i].state == "2"){
                            this.tableData[i].state = "已接单,等待配送";
                        }
                        if(this.tableData[i].state == "3"){
                            this.tableData[i].state = "正在配送";
                        }
                        if(this.tableData[i].state == "4" && this.tableData[i].evaluate == "0"){
                            this.tableData[i].state = "完成";
                        }
                        if(this.tableData[i].state == "4" && this.tableData[i].evaluate == "1"){
                            this.tableData[i].state = "用户已评论";
                        }
                        if(this.tableData[i].state == "4" && this.tableData[i].evaluate == "2"){
                            this.tableData[i].state = "已回复";
                        }
                    }

                    //处理骑手
                    for (var i = 0; i < this.tableData.length; i++){
                        // console.log(1,this.tableData[i].ride)
                        if(this.tableData[i].ride != "请选择"){
                            let index = i;
                            request.get("/ride/getDataById",{
                                params:{
                                    sid: this.tableData[i].ride
                                }
                            }).then(res=>{
                                this.tableData[index].ride = res.data.name;
                            })
                        }
                    }
                })
            },
            //把每一行的索引放进row
            rowClassName({row, rowIndex}) {
                row.index = rowIndex;
            },
            //分页操作
            handleSizeChange(pageSize){
                this.pageSize = pageSize;
                this.getData();
            },
            handleCurrentChange(currentPage){
                this.currentPage = currentPage;
                this.getData();
            },
            //接单
            acceptOrder(e){
                e.state = "已接单,等待配送"
                let order = this.returnData[e.index];
                order.state="2";
                console.log(order);
                request.put("/order/updateOrder",order).then(res=>{
                    console.log(res)
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"接单成功"
                        });
                            // this.getData();
                    }else {
                        this.$message({
                            type:"error",
                            message:res.message
                        })
                    }
                })
            },
            //派送
            postOrder(e){
                if(e.ride == "请选择"){
                    alert("请选择骑手");
                }else {
                    e.state = "正在配送";
                    let order = this.returnData[e.index];
                    order.state = "3";
                    order.ride = e.ride;
                    request.put("/order/updateOrder",order).then(res=>{
                        console.log(res)
                        if(res.code === '0'){
                            this.$message({
                                type:"success",
                                message:"派送成功"
                            });
                            // this.getData();
                        }else {
                            this.$message({
                                type:"error",
                                message:res.message
                            })
                        }
                    })
                }
            },
            //获取骑手列表
            rideList(){
                var that = this;
                request.get("/ride/getRideList",{
                    params:{
                        id: eval("(" + sessionStorage.getItem("data") + ")").id
                    }
                }).then(
                    res=>{
                        var list = eval(res.msg);
                        list.forEach(ride => {
                            that.options.push({name:ride.name,code:ride.id});
                        })
                    }
                )
            },
            //展示评论
            showEvaluate(e) {
                var that =this;
                request.get("/evaluate/getDataByOrder", {
                    params: {
                        id: e.id
                    }
                }).then(res => {
                    var date = new Date(parseInt(res.data.stime));
                    var y = date.getFullYear(), m = date.getMonth() + 1, d = date.getDate();
                    res.data.stime  =  y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + date.toTimeString().substr(0, 8);
                    // res.data.isReturn = e.evaluate;
                    res.data.url = res.data.url.split(",");
                    that.evaluate = res.data;
                })
                this.dialogVisible = true;
            },
            //回复评论
            returnEvaluate(){
                var that =this;
                ElMessageBox.prompt('请输入回复信息', '回复', {
                    confirmButtonText: '提交',
                    cancelButtonText: '取消',
                    inputPattern:/^.{1,200}$/,

                }).then(res => {
                    // console.log(res)
                    request.get("/evaluate/returnEvaluate", {
                        params: {
                            id: that.evaluate.orderid,
                            message:res.value
                        }
                    }).then(rrr=>{
                        that.dialogVisible = false
                        ElMessage({
                            type: 'success',
                            message: `回复成功`,
                        })
                    })
                }).catch(() => {
                })
            },
            //格式化时间
            getMyDate(str){
                var date = new Date();
                date.setTime(str);
                var MyYear = date.getFullYear(), //年
                    MyMonth = date.getMonth()+1, //月
                    MyDay = date.getDate(),  //日
                    h = date.getHours(),//小时
                    m = date.getMinutes(),//分钟
                    s = date.getSeconds();//秒数
                // 以自己需要的方式拼接
                var MyTime = MyYear +'-'+ this.getZero(MyMonth) +'-' + this.getZero(MyDay)+' ' + this.getZero(h) + ':' +  this.getZero(m)+ ':' +  this.getZero(s);//最后拼接时间
                return MyTime;
            },
            getZero(num){
                // 单数前面加0
                if(parseInt(num) < 10){
                    num = '0'+num;
                }
                return num;
            }
        },
    }
</script>

<style scoped>

</style>