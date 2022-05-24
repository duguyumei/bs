import { createRouter, createWebHistory } from 'vue-router'
import layout from "@/layout/layout";

const routes = [
  {
    path: '/',
    name: 'layout',
    component: layout,
    redirect:"/login",
    children:[ //二次路由
      {
        path: 'dishManage',
        name: "dishManage",
        component: ()=>import("@/views/dishManage")
      },
      {
        path: 'orderManage',
        name: 'orderManage',
        component: ()=>import("@/views/orderManage")
      },
      {
        path: 'rideManage',
        name: 'rideManage',
        component: ()=>import("@/views/rideManage")
      },
      {
        path: 'marchantManage',
        name: 'marchantManage',
        component: ()=>import("@/views/marchantManage")
      }
    ]
  },
  {
    path: "/login",
    name:"login",
    component: ()=>import("@/views/login")
  },
  {
    path: "/register",
    name: "register",
    component: ()=>import("@/views/register")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

//路由拦截
router.beforeEach((to,from,next)=>{
  //验证token
  let token = sessionStorage.getItem("token");
  if((token != null && token == "111333" ) || to.path === "/login"|| to.path ==="/register"){
    next();
  }else{
    next("/login");
  }
})
export default router
