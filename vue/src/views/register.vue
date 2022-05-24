<template>
    <div class="register">
<!--        <div class="img"><img src="../assets/register.png" alt="picture"></div>-->
        <div style="margin-bottom: 50px"></div>
        <div class="register_main">
        <el-form  ref="form" :model="form" :label-position="left">
            <el-form-item>
                <el-input class="input" size="large" v-model="form.username" prefix-icon="Avatar" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="form.password" prefix-icon="lock" placeholder="请输入密码" show-password></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="repassword" prefix-icon="lock" placeholder="请确认密码" show-password></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="form.phone" prefix-icon="Iphone" placeholder="请填写手机号码"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="form.name" prefix-icon="house" placeholder="请输入店名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="form.address" prefix-icon="location" placeholder="请填写实体店地址"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="input" size="large" v-model="form.message" prefix-icon="document" placeholder="请填写描述"></el-input>
            </el-form-item>
<!--            上传证件照片-->
            <el-upload action = "http://localhost:8090/files/upload/"
            list-type = "picture-card"
            :class = "{disabled:isMax}"
            :limit = 5
            :on-success = "filesUploadSuccess"
            :on-exceed = "exceed"
            :before-upload = "beforeAvatarUpload">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">上传证件照片</div>
            </el-upload>
            <el-form-item style="align-content: center ">
                <el-button class="btn_login" style="width: 100%;height: 40px" type="primary" @click="isOnly">注册</el-button>
            </el-form-item>
        </el-form>
        </div>
    </div>
</template>

<script>
    import request from "@/utils/requst";
    import $ from "@/assets/js/jquery-3.6.0.min"

    export default {
        name: "register",
        data(){
            return{
                form:{
                },
                repassword:""
            }
        },
        methods: {
            //记录图片路径
            filesUploadSuccess(res, file, fileList) {
                console.log(res);
                if (this.form.documents == null || this.form.documents == "") {
                    this.form.documents = "";
                    this.form.documents = res.data
                } else {
                    this.form.documents = this.form.documents + "---" + res.data;
                }
                console.log(this.form.documents);
            },
            //申请注册
            register() {
                this.form.status = -1;
                request.post("/user", this.form).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "注册成功"
                        });
                        this.$router.push('/login');
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg()
                        })
                    }
                });
            },
            beforeAvatarUpload: function (file) {
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isPG = (isJPG || isPNG)                                       //限制图片格式为jpg / png
                const isLt2M = file.size / 1024 / 1024 < 2;                         //限制图片大小
                const isSize = new Promise(function (resolve, reject) {
                    let width = 0
                    let height = 0
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
                    this.$message.error('上传图片像素要大于600*400!');
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
            exceed: function () {
                this.$message.error('最多只能上传3张图片')
            },
            isOnly:function(){
                var that = this;
                if(this.form.username == null || this.form.password == null || this.form.phone == null || this.form.name == null || this.form.address == null || this.form.documents == null){
                    alert("请输入完整信息");
                }else if(this.form.password != this.repassword){
                    alert("两次密码不一致");
                } else{
                    var url = "user/isOnly?name=" + this.form.name + "&username=" + this.form.username;
                    request.get(url).then(res =>{
                        if(res.code == -1){
                            alert(res.msg);
                        }else{
                            that.addrHand();
                        }
                    })
                }
            },
            addrHand () {
                let that = this
                $.ajax({
                    type: "get",
                    dataType: 'jsonp',
                    data: {
                        key: "UUNBZ-LV6WP-QCQDR-LE267-R3R76-D6BM6", // 填申请到的腾讯key
                        address: that.form.address, //具体的地址
                        output: 'jsonp' //返回格式：支持JSON/JSONP，默认JSON
                    },
                    jsonp: "callback",
                    jsonpCallback: "QQmap",
                    url: "https://apis.map.qq.com/ws/geocoder/v1/?",
                    success: function (json) {
                        console.log(json)
                        if (json.status == 0) {
                            that.form.latitude = json.result.location.lat
                            that.form.longitude = json.result.location.lng
                            // that.$message({
                            //     message: '成功获取位置的经纬度',
                            //     type: 'success'
                            // })
                            that.register();
                        } else {
                            that.$message.error('获取该位置经纬度失败')
                        }
                    },
                    error: function (err) {
                        that.$message.error('异常错误，请刷新浏览器后重试')
                    }
                })
            },
        }
    }
</script>

<style scoped>
    @import "../assets/css/register.css";
</style>