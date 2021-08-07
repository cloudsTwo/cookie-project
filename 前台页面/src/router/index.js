import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Home = () => import('../views/Home')
const Menu = () => import('../views/Menu')
const Login = () => import('../views/Login')
const Registered = () => import('../views/Registered')
const FoodMade = () => import('../views/FoodMade')
const AddMenu = () => import('../views/AddMenu')
const Own = () => import('../views/Own')
const EditMenu = () => import('../views/EditMenu')
const Shopcart = () => import('../views/Shopcart')
const Order = () => import('../views/Order')


const routes = [
  {
    path:'/',
    redirect:'/home'
  },
  {
    path:'/home',
    component:Home
  },
  {
    path:'/menu',
    component:Menu
  },
  {
    path:'/login',
    component:Login
  },
  {
    path:'/registered',
    component:Registered
  },
  {
    path:'/foodMade',
    component:FoodMade
  },
  {
    path:'/addMenu',
    component:AddMenu
  },
  {
    path:'/editMenu',
    component:EditMenu
  },
  {
    path:'/own',
    component:Own
  },
  {
    path:'/shopcart',
    component:Shopcart
  },
  {
    path:'/order',
    component:Order
  }
]

const router = new VueRouter({
  routes,
  mode:'history'
})


/*路由守卫   根据登录获得token*/
router.beforeEach((to,from,next) =>{
  const isLogin = localStorage.eleToken ? true :false ;
  if(to.path ==="/login" || to.path ==="/registered"){
    return next();
  }else{
    return isLogin ? next() :next("/login")   /*真跳转  假注册*/
  }
})

export default router
