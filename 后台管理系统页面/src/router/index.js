import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Check from '@/components/menu/Check'
import Comment from '@/components/menu/Comment'
import Art from '@/components/menu/Art'
import Order from '@/components/menu/Order'
import User from '@/components/menu/User'
import EditMenu from '@/components/menu/EditMenu'
import MenuDetail from '@/components/menu/MenuDetail'
import Good from '@/components/menu/Good'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path:'/home',
      name:'Home',
      component:Home,
      children:[
        {
          path: 'all',
          name: 'Art',
          component: Art,
        },
        {
          path: 'editMenu',
          name: 'EditMenu',
          component: EditMenu,
        },
        {
          path: 'check',
          name: 'Check',
          component: Check,
        },
        {
          path: 'user',
          name: 'User',
          component: User,
        },
        {
          path: 'comment',
          name: 'Comment',
          component: Comment,
        },
        {
          path: 'order',
          name: 'Order',
          component: Order,
        },
        {
          path: 'good',
          name: 'Good',
          component: Good,
        },
        {
          path: 'menuDetail',
          name: 'MenuDetail',
          component: MenuDetail,
        },
      ]
    },
  ],
})

/*路由守卫   根据登录获得token*/
router.beforeEach((to,from,next) =>{
  const isLogin = localStorage.eleToken ? true :false ;
  if(to.path ==="/login"){
    next();
  }else{
    isLogin ? next() :next("/login")   /*真跳转  假注册*/
  }
})

export default router;

