<template>
      <el-aside class="aside" width="200px">
        <el-menu
            :default-active="$route.path" exact
          class="el-menu-vertical-demo"
          router>
          <NavItem v-for="v in items" :key="v.url" 
                    :item="v" :basePath = "v.url"/>
        </el-menu>
      </el-aside> 
</template>

<script>
import NavItem from './NavItem'
export default {
  data(){
     return {
      id:0,  
      items:[
        {
          name:'首页',
          url:'/index'
        },
        {
          name:'系统管理',
          url:'/stats'
        },
        {
          name:'客户端管理',
          url:'/client'
        },
        {
          name:'信息管理',
          url:'/info',
          child:[
            {
              name:'信息发布',
              url:'/info/send'
            },
            {
              name:'信息查询',
              url:'/info/select'
            },
            {
              name:'代办事项',
              url:'/info/todo'
            },
          ]
        },
        {
          name:'用户管理',
          url:'/wms1',
          child:[
            {
              name:'学员统计',
              url:'/wms1/list'
            },
            {
              name:'角色统计',
              url:'/wms1/list2'
            },
          ]
        }
      ] 
    }
  },
  components:{NavItem}

}
</script>

<style>

</style>
