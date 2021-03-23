<template>
  <div id="module">
    <!-- 左侧导航 -->
    <el-container>
      <Nav />
     <!-- 左侧导航 end -->
     <el-container>
        <!-- 头部 -->
        <el-header>
          <el-button class="exit" type="primary" @click="goBack()" size="mini">退出</el-button>
          <!-- 面包屑 -->
          <Breadcumb /> 
        </el-header>
        <!-- 主体 -->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Nav from './Nav'; //导入
import Breadcumb from './Breadcumb'; //面包屑

export default {
  name: 'App',
  data(){
    return {

    }
  },
  components:{
    Nav,
    Breadcumb
  },

  methods: {
    goBack(){
      this.$router.push('/login');
      sessionStorage.removeItem('user');
    }
  },
  created() {

  }

}
</script>

<style>

</style>

