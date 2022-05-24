<template>
<!--    主体框架-->
    <Navi></Navi>
    <div style="display: flex">
        <Aside style="width: 200px;min-height:calc(100vh - 50px)"></Aside>
        <router-view style="flex:1;" v-if="isShow"/>
    </div>
</template>

<script>
    import Navi from "@/components/Navi";
    import Aside from "@/components/Aside";

    export default {
        name: "layout",
        components: {
            Navi,//头部
            Aside//侧边栏
        },
        //刷新页面
        provide () {
            return {
                reload: this.reload
            }
        },
        data () {
            return {
                isShow: true
            }
        },
        methods: {
            reload () {
                this.isShow= false
                this.$nextTick(function () {
                    this.isShow= true
                })
            }
        }
    }
</script>

<style scoped>

</style>