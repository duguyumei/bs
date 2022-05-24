<template>
    <el-menu
            :default-active="path"
            class="el-menu-vertical-demo"
            router
    >
        <el-menu-item index="dishManage"><el-icon><location /></el-icon>菜品管理</el-menu-item>
        <el-menu-item index="orderManage"><el-icon><location /></el-icon>订单管理</el-menu-item>
        <el-menu-item index="rideManage"><el-icon><location /></el-icon>骑手管理</el-menu-item>
        <el-menu-item index="marchantManage"><el-icon><location /></el-icon>信息修改</el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        name: "Aside",
        data(){
            return{
                path:this.$route.path.slice(1)
            }
        }
    }
</script>

<style scoped>

</style>