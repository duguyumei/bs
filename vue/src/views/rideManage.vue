<template>
    <div class="rideManage">
        <div class="rideManage_title">
            <!--                搜索区域-->
<!--            <div class="dishManage_search">-->
<!--                <el-input v-model="search" placeholder="请输入骑手" style="width: 30%;" clearable/>-->
<!--                <el-button type="primary" @click="getData()">搜索</el-button>-->
<!--            </div>-->
            <!--        新增-->
            <div class="rideManage_addbtn">
                <div>
                    <el-button type="primary" @click="openForm">新增</el-button>
                </div>
                <el-dialog v-model="dialogVisible" width="30%">
                    <el-form :model="form" label-width="120px">
                        <el-form-item label="账号">
                            <el-input v-model="form.username" style="width: 80%"></el-input>
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="form.password" style="width: 80%"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名">
                            <el-input v-model="form.name" style="width: 80%"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号">
                            <el-input v-model="form.phone" style="width: 80%"></el-input>
                        </el-form-item>
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
        <div class="rideManage_table">
            <el-table :data="tableData" border>
                <el-table-column prop="username" label="账号" sortable />
                <el-table-column prop="password" label="密码"/>
                <el-table-column prop="name" label="骑手姓名" sortable />
                <el-table-column prop="phone" label="联系方式" />
                <el-table-column fixed="right" label="操作" :width="180" >
                    <template #default="scope">
                        <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-popconfirm title="不可复原,是否确定?" @confirm="handleDelete(scope.row.id)">
                            <template #reference><el-button type="text" size="small">删除</el-button></template>
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
    export default {
        name: "rideManage",
        data(){
            return{
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
            //判读表格是修改还是新增
            submitForm:function(){
                if(this.form.id){
                    this.editRide();
                }else {
                    this.addRide();
                }
            },
            //新增骑手
            addRide(){
                let strJson = eval("(" + sessionStorage.getItem("data") + ")");
                this.form.marchant = strJson.id;
                // console.log(this.form);
                request.post("/ride/addRide",this.form).then(res=>{
                    // console.log(res)
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
            //渲染tabledata
            getData(){
                request.get("/ride",{
                    params:{
                        pageNum:this.currentPage,
                        pageSize:this.pageSize,
                        search:this.search,
                        id: eval("(" + sessionStorage.getItem("data") + ")").id
                    }
                }).then(res=>{
                    // console.log(res)
                    this.tableData = res.data.records;
                    this.total = res.data.total;
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
            //修改信息
            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row))//深拷贝,独立对象
                this.dialogVisible = true;
            },
            //向后台修改信息
            editRide(){
                request.put("/ride/editRide",this.form).then(res=>{
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
                request.delete("/ride/" + id).then(res=>{
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
            //验证是否唯一
            isOnly(){
                if(this.form.name == null || this.form.username == null || this.form.password == null || this.form.phone == null){
                    alert("请表单填写完整");
                }else {
                    var url = "/ride/isOnly?username=" + this.form.username;
                    request.get(url).then(res=>{
                        if(res.code == -1){
                            alert("账号重复");
                        }else{
                            this.submitForm();
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>