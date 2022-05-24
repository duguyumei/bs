<template>
    <div class="dishManage">
        <div class="dishManage_title">
        <!--                搜索区域-->
        <div class="dishManage_search">
            <el-input v-model="search" placeholder="Please input" style="width: 30%;" clearable/>
            <el-button type="primary" @click="getData()">搜索</el-button>
        </div>
            <!--        新增-->
        <div class="dishManage_addbtn">
            <div>
                <el-button type="primary" @click="openForm">新增</el-button>
            </div>
            <el-dialog v-model="dialogVisible" width="30%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="菜品名">
                        <el-input v-model="form.name" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="照片">
                        <el-upload action = "http://localhost:8090/files/upload/"
                                   list-type = "picture-card"
                                   :class = "{disabled:isMax}"
                                   :limit = 1
                                   :on-success = "filesUploadSuccess"
                                   :on-exceed = "exceed"
                                   :before-upload = "beforeAvatarUpload"
                                   :on-change = "change"
                                   :on-remove = "remove"
                                   :on-error = "error">
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">上传照片</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input type="textarea" v-model="form.message" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="售价">
                        <el-input v-model="form.money" style="width: 80%"></el-input>
                    </el-form-item>
<!--                    <el-form-item label="折扣">-->
<!--                        <el-input v-model="form.discount" style="width: 80%"></el-input>-->
<!--                    </el-form-item>-->
                </el-form>
                <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="isOnly">提交</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </span>
                </template>
            </el-dialog>
        </div>
        </div>
        <!--                表格区域-->
        <div class="dishManage_table">
            <el-table :data="tableData" border>
                <el-table-column prop="name" label="菜品名" sortable />
                <el-table-column label="图片" :width="125">
                    <template #default="scope">
                        <el-image
                                style="width: 100px;height: 100px;"
                                :src="scope.row.url"
                                :preview-src-list="[scope.row.url]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="message" label="详情" />
<!--                <el-table-column prop="gather" label="分类" sortable />-->
                <el-table-column prop="money" label="价格" sortable />
<!--                <el-table-column prop="discount" label="折扣" sortable :width="80"/>-->
<!--                <el-table-column prop="monthsales" label="月销量" sortable :width="80"/>-->
                <el-table-column prop="sales" label="月销量" sortable />
<!--                <el-table-column prop="praise" label="赞" sortable :width="80"/>-->
                <el-table-column prop="state" label="状态" sortable >
                    <template #default="scope">
                        <el-switch
                                v-model="scope.row.state"
                                size="large"
                                width="50"
                                active-text="显示"
                                inactive-text="隐藏"
                                @change="editstate(scope.row.state,scope.$index)"
                        />
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" :width="180" >
                    <template #default="scope">
                        <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-popconfirm title="不可复原,是否确定?" @confirm="handleDelete(scope.row.id)">
                            <template #reference><el-button type="text" size="small">下架</el-button></template>
                        </el-popconfirm>
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
    </div>
</template>

<script>
    import request from "@/utils/requst";
    import { ElLoading } from 'element-plus'

    export default {
        name: "dishManage",
        components: {

        },
        data(){
            return{
                isMax : false,//动态绑定表单中上传框
                search:'', //搜索关键词
                tableData:[], //表格数据
                total:0, //数据量
                currentPage:1, //页码
                pageSize:10, //页容量
                dialogVisible:false, //新增窗口
                form:{} //新增表单
            }
        },
        created() {
            //页面加载时的方法
            this.getData();
        },
        methods:{
            //渲染tabledata
            getData(){
                var that = this;
                request.get("/dishes",{
                    params:{
                        pageNum:this.currentPage,
                        pageSize:this.pageSize,
                        search:this.search,
                        id: eval("(" + sessionStorage.getItem("data") + ")").id
                    }
                }).then(res=>{
                    let strJson = eval("(" + sessionStorage.getItem("data") + ")");
                    var id = strJson.id;
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                    for(let i = 0; i < res.data.records.length; i++){
                        if(res.data.records[i].state == 1){
                            res.data.records[i].state = true;
                        }else{
                            res.data.records[i].state = false;
                        }
                        res.data.records[i].sales = 0;
                        request.get('http://localhost:8090/order/getSales', {
                            params:{
                                method: "dish",
                                id: res.data.records[i].id,
                                date: "month",
                                pid: id
                            }
                        }).then(e=>{
                            that.tableData[i].sales = e.data;
                        })
                    }
                })
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
            //控制表单弹窗
            openForm(){
                this.dialogVisible = true;
                this.form = {};
            },
            //向后台新增菜品
            addDish(){
                let strJson = eval("(" + sessionStorage.getItem("data") + ")");
                this.form.marchant = strJson.id;
                this.form.state = 1;
                request.post("/dishes/addDish",this.form).then(res=>{
                    console.log(res)
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"新增成功"
                        });
                        this.dialogVisible = false;
                        this.getData();
                    }else {
                        this.$message({
                            type:"error",
                            message:res.msg()
                        })
                    }
                });
            },
            //记录图片路径
            filesUploadSuccess(res, file, fileList) {
                console.log(res);
                this.form.url = res.data;
            },
            //上传前检查格式
            beforeAvatarUpload: function (file) {
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isPG = (isJPG || isPNG)                                       //限制图片格式为jpg / png
                const isLt2M = file.size / 1024 / 1024 < 2;                         //限制图片大小
                const isSize = new Promise(function (resolve, reject) {
                    let width = 30
                    let height = 20
                    let _URL = window.URL || window.webkitURL
                    let img = new Image()
                    img.onload = function () {
                        let valid = img.width >= width && img.height >= height
                        valid ? resolve() : reject();
                    }
                    img.src = _URL.createObjectURL(file)
                }).then(() => {
                    return file;
                }, () => {
                    this.$message.error('上传图片像素要大于300*200!');
                    return promise.reject();
                })
                if (!isPG) {
                    this.$message.error('上传图片只能是 JPG 或 PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传图片大小不能超过 2MB!');
                }
                return isPG && isLt2M && isSize
            },
            //超出提醒
            exceed: function () {
                this.$message.error('最多只能上传1张图片')
            },
            //判断isMax
            change:function(file,fileList){
                if(fileList.length >= 1){
                    this.isMax = true
                }
            },
            remove:function(file,fileList){
                if(fileList.length < 1){
                    this.isMax = false
                }
            },
            //上传失败的时候会出现Bug，故在上传失败的时候也进行一下判断
            error:function(err, file, fileList){
                this.$message.error('上传失败')
                if(fileList.length >= 1){
                    this.isMax = true
                }else{
                    this.isMax = false
                }
            },
            //判读表格是修改还是新增
            submitForm:function(){
                if(this.form.id){
                    this.editDish();
                }else {
                    this.addDish();
                }
            },
            //修改菜品
            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row))//深拷贝,独立对象
                this.dialogVisible = true;
            },
            //向后台修改信息
            editDish:function () {
                if(this.form.state){
                    this.form.state = 1;
                }else {
                    this.form.state = 0;
                }
                request.put("/dishes/editDish",this.form).then(res=>{
                    console.log(res)
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"更新成功"
                        });
                        this.dialogVisible = false;
                        this.getData();
                    }else {
                        this.$message({
                            type:"error",
                            message:res.message
                        })
                    }
                });
            },
            //删除
            handleDelete(id) {
                request.delete("/dishes/" + id).then(res=>{
                    console.log(res)
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"删除成功"
                        })
                    }else {
                        this.$message({
                            type:"error",
                            message:res.msg()
                        })
                    }
                    this.getData();
                })
            },
            //判断是否唯一
            isOnly(){
                if(this.form.name == null || this.form.url == null || this.form.money == null){
                    alert("请表单填写完整");
                }else {
                    var url = "/dishes/isOnly?name=" + this.form.name;
                    request.get(url).then(res=>{
                        if(res.code == -1){
                            alert("菜名重复");
                        }else{
                            this.submitForm();
                        }
                        // console.log(res)
                    })
                }
            },
            //设置状态
            editstate (res,e){
                var dish = JSON.parse(JSON.stringify(this.tableData[e]))//深拷贝,独立对象
                if(res){
                    dish.state = 1;
                }else {
                    dish.state = 0;
                }
                request.put("/dishes/editDish",dish).then(res=>{
                    console.log(res)
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"更新成功"
                        });
                        this.getData();
                    }else {
                        this.$message({
                            type:"error",
                            message:res.message
                        })
                    }
                });
            }
        }
    }

</script>

<style scoped>
    @import "../assets/css/dishManage.css";
</style>