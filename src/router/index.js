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
    path:'/own',
    component:Own
  }
]

const router = new VueRouter({
  routes,
  mode:'history'
})

export default router
