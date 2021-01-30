<template>
      <div>
          <!-- 无子级 -->
          <el-menu-item :index="basePath" v-if="!item.child">
            <i class="el-icon-menu"></i>
            <span slot="title">{{item.name}}</span>
          </el-menu-item>

          <!-- 有子级 -->
          <el-submenu v-else :index="basePath">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{item.name}}</span>
              </template>
              <NavItem v-for="child in item.child" :key="child.url" 
                    :item="child" :basePath = "child.url"/>
          </el-submenu>
          
      </div>
</template>

<script>

export default {
  name:'NavItem',
  data(){
     return {
    }
  },
  props:['item','basePath']

}
</script>

<style>

</style>
