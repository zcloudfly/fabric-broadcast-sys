<template>
  <div class="login">
     <p class="login-title">信息发布系统</p>
      <div class="login-content">
        <div class="login-main">
        <div class="login-container">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="pwd">
        <el-input v-model="ruleForm.pwd"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm()" >登录</el-button >
      </el-form-item>

    </el-form>
  </div>
  </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      ruleForm:{
        username:'zhangsan',
        pwd:'123456'
      },
      rules:{
        username:[
          {require:true,message:'请输入用户名',trigger:'blur'},
          { min: 3, max: 18, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        pwd:[
          {require:true,message:'请输入密码',trigger:'blur'},
           { min: 3, max: 18, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        
      }
    }
  },
   methods: {
      submitForm() {
        let {username,pwd}=this.ruleForm;
        this.$axios({
          method:'post',
          url:'/user/login',
          data:{name:username,pwd:pwd}
        }).then(res=>{
         // console.log(res);
          let {code,msg,data} = res.data;
          if(code == '0'){
            sessionStorage.setItem('user',data.name);
            sessionStorage.setItem('userorg',data.orgid);
            sessionStorage.setItem('userid',data.id);
            this.$router.push('/index')
          }else {
            alert(msg);
          }
        })
      }
   }
}
</script>
<style>
</style>
