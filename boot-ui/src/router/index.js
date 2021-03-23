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
      },{
        path: '/sys',
        name: 'sys',
        meta:{title:'系统管理'},
        component: () => import( '../views/home/sys/index.vue'),
        children:[
          {
            path: '/sys/fabric',
            name: 'sys/fabric',
            meta:{title:'Fabric网络'},
            component: () => import( '../views/home/sys/fabric.vue')
          },{
            path: '/sys/ipfs',
            name: 'sys/ipfs',
            meta:{title:'IPFS网络'},
            component: () => import( '../views/home/sys/ipfs.vue')
          }
        ]
      },
      {
        path: '/client',
        name: 'client',
        meta:{title:'客户端管理'},
        component: () => import( '../views/home/client/index.vue')

      },
      {
        path: '/info',
        name: 'info',
        meta:{title:'信息管理'},
        component: () => import( '../views/home/info/infomanage.vue'),
        children:[
          {
            path: '/info/detail',
            name: 'info/detail',
            meta:{title:'信息详情'},
            component: () => import( '../views/home/info/infosend.vue')
          },
          {
            path: '/info/send',
            name: 'info/send',
            meta:{title:'信息申请'},
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
      {
        path: '/user',
        name: 'user',
        meta:{title:'用户管理'},
        component: () => import( '../views/home/user/mainindex.vue'),
        children:[
          {
            path: '/user/userindex',
            name: '/user/userindex',
            meta:{title:'用户信息'},
            component: () => import( '../views/home/user/userindex.vue')
          },{
            path: '/user/roleindex',
            name: '/user/roleindex',
            meta:{title:'角色信息'},
            component: () => import( '../views/home/user/roleindex.vue')
          },{
            path: '/user/permission',
            name: '/user/permission',
            meta:{title:'权限信息'},
            component: () => import( '../views/home/user/permission.vue')
          }
            ]
      }
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
