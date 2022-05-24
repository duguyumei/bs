<template>
    <div class="navi">
        <div class="navi_left">
            商家管理
        </div>
        <div class="navi_empty"></div>
        <div class="navi_right">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <text style="overflow: hidden; white-space:nowrap; display: inline-block; width: 120px">{{marchant.name}}</text>
                    <el-icon class="el-icon--right"><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="quit">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import request from "@/utils/requst";

    export default {
        name: "Navi",
        data(){
            return{
                url:"http://localhost:8090/files/bd388f993b064a1b8db1b3d65805e841",
                marchant:{},
            }
        },
        methods:{

            quit(){
                this.$router.push('/login');
                sessionStorage.clear();
            },
            getData(){
                let strJson = eval("(" + sessionStorage.getItem("data") + ")");
                this.marchant.username = strJson.username;
                request.post("/user/getDataByUsername",this.marchant).then(res=> {
                    this.marchant = res.data;
                })
            }
        },
        mounted:function(){
            this.getData();
        }
    }
</script>

<style scoped>
    @import "../assets/css/navi.css";
</style>