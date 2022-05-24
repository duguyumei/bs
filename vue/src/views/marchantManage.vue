<template>
    <div class="marchant_img">
        <div class="title">商家头像</div>
        <el-image
                style="width: 250px;height: 250px;"
                :src="form.url">
        </el-image>
        <el-button class="btn_login" type="primary" @click="editImg">修改头像</el-button>
        <el-dialog v-model="dialogVisible" width="300px">
            <el-form>
                    <el-upload action = "http://localhost:8090/files/upload/"
                       list-type = "picture-card"
                       :class = "{disabled:this.isMax}"
                       :limit = 1
                       :on-success = "filesUploadSuccess"
                       :on-exceed = "exceed"
                       :before-upload = "beforeAvatarUpload"
                       :on-change = "change"
                       :on-remove = "remove"
                       :on-error = "error"
                        style="margin-left: 50px">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">上传照片</div>
            </el-upload>
            <div style="width: 200px;margin-left: 50px;margin-top: 20px;">
                <el-button type="primary" @click="submitForm" style="width: 70px">确认</el-button>
                <el-button @click="dialogVisible = false" style="width: 70px">取消</el-button>
            </div>
        </el-form>
        </el-dialog>
    </div>
    <div class="marchant_main">
        <div class="title">简要信息</div>
        <el-form  ref="form" :model="form" label-position="left" label-width="100px" >
            <el-form-item label="店名">
                <el-input class="input" size="large" v-model="form.name" prefix-icon="house" disabled></el-input>
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input class="input" size="large" v-model="form.phone" prefix-icon="Iphone" placeholder="请填写手机号码"></el-input>
            </el-form-item>
            <el-form-item  label="地址">
                <el-input class="input" size="large" v-model="form.address" prefix-icon="location" placeholder="请填写实体店地址" disabled></el-input>
            </el-form-item>
            <el-form-item  label="描述">
                <el-input class="input" style="width: 300px" type="textarea" size="large" v-model="form.message" prefix-icon="document" placeholder="请填写描述"></el-input>
            </el-form-item>
            <el-button class="btn_login" type="primary" @click="editMain">确认修改</el-button>
        </el-form>
    </div>
    <div class="marchant_right">
        <div class="title">
            重要选项
        </div>
        <div class="right_btn">
            <el-button type="primary" @click="showPassEdit">修改密码</el-button>
            <el-dialog v-model="dialogVisiblePassword" width="300px">
                <div style="margin-bottom: 10px">修改密码</div>
                <el-form :model="form">
                    <el-form-item>
                        <el-input class="input" size="large" v-model="repassword" placeholder="请输入旧密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input class="input" size="large" v-model="form.password" placeholder="请输入新密码"></el-input>
                    </el-form-item>
                    <div style="width: 300px;margin-top: 20px;margin-left: -18px">
                        <el-button type="primary" @click="editMain" style="width: 120px">确认</el-button>
                        <el-button @click="dialogVisiblePassword = false" style="width: 120px">取消</el-button>
                    </div>
                </el-form>
            </el-dialog>
<!--            <el-button type="primary" @click="editMain">修改证件</el-button>-->
        </div>
        <div style="margin-bottom: 50px">
            <el-button type="primary" @click="dialogVisibleArea = true">修改配送范围</el-button>
            <el-dialog v-model="dialogVisibleArea" width="300px">
                <div style="margin-bottom: 10px">输入新的配送范围(公里)</div>
                <el-form :model="form">
                    <el-form-item>
                        <el-input class="input" size="large" v-model="form.area"></el-input>
                    </el-form-item>
                    <div style="width: 300px;margin-top: 20px;margin-left: -18px">
                        <el-button type="primary" @click="editMain" style="width: 120px">确认</el-button>
                        <el-button @click="dialogVisibleArea = false" style="width: 120px">取消</el-button>
                    </div>
                </el-form>
            </el-dialog>
        </div>
        <el-switch
                v-model="value"
                size="large"
                width="150"
                active-text="开店"
                inactive-text="关店"
                @change="editstate"
        />
    </div>
</template>

<script>
    import request from "@/utils/requst";

    export default {
        name: "marchantManage",
        data(){
            return{
                form_new:{},
                form:{},
                value:false,
                dialogVisible:false,
                isMax:false,//动态绑定表单中上传框
                fileList:[],
                url:"",
                dialogVisibleArea:false,
                dialogVisiblePassword:false,
                repassword: ""
            }
        },
        mounted: function () {
            let strJson = eval("(" + sessionStorage.getItem("data") + ")");
            this.form = strJson;
            if(strJson.status == 1){
                this.value = true;
            }
            this.form_new = strJson;
        },
        methods:{
            //修改头像
            editImg:function () {
                this.dialogVisible = true;
            },
            //记录图片路径
            filesUploadSuccess(res, file, fileList) {
                this.url = res.data
            },
            //上传前检查格式
            beforeAvatarUpload: function (file) {
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isPG = (isJPG || isPNG)                                       //限制图片格式为jpg / png
                const isLt2M = file.size / 1024 / 1024 < 2;                         //限制图片大小
                const isSize = new Promise(function (resolve, reject) {
                    let width = 100
                    let height = 100
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
                    this.$message.error('上传图片像素要大于100*100!');
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
            //提交图片
            submitForm:function(){
                this.form_new.url = this.url;
                request.put("/user/updateMarchant",this.form_new).then(
                    res=>{
                        if(res.code === '0'){
                            this.$message({
                                type:"success",
                                message:"更新成功"
                            });
                            this.dialogVisible = false;
                            // 重新存取
                            sessionStorage.setItem('data',JSON.stringify(this.form_new));
                            this.form = eval("(" + sessionStorage.getItem("data") + ")");
                        }else{
                            this.$message({
                                type:"error",
                                message:res.message
                            });
                        }
                    }
               )
            },
            //提交基础信息
            editMain:function(){
                if(this.dialogVisiblePassword){
                    if(this.repassword != eval("(" + sessionStorage.getItem("data") + ")").password){
                        alert("旧密码输入错误");
                        return;
                    }
                }
                request.put("/user/updateMarchant",this.form).then(
                    res=>{
                        if(res.code == '0'){
                            this.$message({
                                type:"success",
                                message:"更新成功"
                            });
                            this.dialogVisible = false;
                            if(this.dialogVisibleArea){
                                this.dialogVisibleArea = false;
                            }
                            if(this.dialogVisiblePassword){
                                this.dialogVisiblePassword = false;
                            }
                            // 存
                            sessionStorage.setItem('data',JSON.stringify(this.form));
                        }else{
                            this.$message({
                                type:"error",
                                message:res.message
                            });
                        }
                    }
                )
            },
            //开关店
            editstate:function(res){
                if(res){
                    this.form.status = 1;
                }else {
                    this.form.status = 0;
                }
                console.log(this.form)
                request.put("/user/updateMarchant",this.form).then(
                    res=>{
                        console.log(res);
                        if(res.code == '0'){
                            sessionStorage.setItem('data',JSON.stringify(this.form));
                        }
                    }
                )
            },
            //修改密码
            showPassEdit:function (res) {
                this.dialogVisiblePassword = true;
                this.form.password = '';
            }
        }
    }
</script>

<style scoped>
    @import "../assets/css/marchantManage.css";
</style>