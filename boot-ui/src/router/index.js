import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect:'/home'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/Login.vue')
  },
  {
    path: '/home',
    name: 'home',
    redirect:'/index',
    meta:{title:'首页'},
    component: () => import( '../views/home/index.vue'),
    children:[
      {
        path: '/index',
        name: 'index',
        meta:{title:'首页'},
        component: () => import( '../views/home/index/index.vue')
      },
      {
        path: '/stats',
        name: 'stats',
        meta:{title:'数据统计'},
        component: () => import( '../views/home/stats/index.vue')
      },
      {
        path: '/info',
        name: 'info',
        meta:{title:'信息管理'},
        component: () => import( '../views/home/info/infomanage.vue'),
        children:[
          {
            path: '/info/send',
            name: 'info/send',
            meta:{title:'信息发布'},
            component: () => import( '../views/home/info/infosend.vue')
          },{
            path: '/info/select',
            name: 'info/select',
            meta:{title:'信息查询'},
            component: () => import( '../views/home/info/infoselect.vue')
          },{
            path: '/info/todo',
            name: '/info/todo',
            meta:{title:'待办事项'},
            component: () => import( '../views/home/info/infotodo.vue')
          },
        ]
      },
    ]
  }
]

const router = new VueRouter({
  routes
})

//路由拦截
router.beforeEach(function(to,from,next){
  if(!sessionStorage.getItem('user')){
    if(to.path !== '/login'){
      next('/login')
    }
  }
  next();
})
export default router
