<template>
    <div class="container">
        <div class="container_title">商家登录</div>
        <div class="container_min_title">为您量身定做的餐饮管理左右手</div>
        <div class="container_main">
            <div class="header">账号密码登录</div>
            <el-form  ref="form" :model="form" :label-position="left">
                <el-form-item>
                    <el-input class="input" size="large" v-model="form.username" prefix-icon="Avatar" placeholder="请输入账号"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input class="input" size="large" v-model="form.password" prefix-icon="lock" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item style="align-content: center ">
                    <el-button class="btn_login" style="width: 100%;height: 40px" type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
            <div class="footer">
                <div><span @click="register">没账号?去开店</span></div>
<!--                <div><span>忘记密码?</span></div>-->
            </div>
        </div>
    </div>
</template>

<script>
    import request from "@/utils/requst";

    export default {
        name: "login",
        data(){
            return{
                form:{
                }
            }
        },
        methods:{
            login(){
                if(this.form.username == null || this.form.password == null) {
                    alert("请输入完整信息");
                }else {
                    request.post("/user/login",this.form).then(res=>{
                        if(res.code == 0){
                            this.$router.push("/dishManage");
                            sessionStorage.setItem('token','111333');
                            sessionStorage.setItem('data',JSON.stringify(res.data));
                        }else{
                            alert("账号或密码错误");
                        }
                    })
                }
            },
            register(){
                this.$router.push("/register");
            }
        }
    }
</script>
<style scoped>
    @import "../assets/css/login.css";
</style>