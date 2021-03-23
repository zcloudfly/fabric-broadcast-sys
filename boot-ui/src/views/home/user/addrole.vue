<template>
  <div>
  <el-form ref="sizeForm" :model="sizeForm" label-width="80px" size="mini">
    <el-form-item label="角色名称">
      <el-input v-model="sizeForm.rolename"></el-input>
    </el-form-item>


    <el-input type="hidden" v-model="sizeForm.id"></el-input>
    <el-form-item size="large" align="center">
      <el-button type="primary" @click="onSubmit">保存</el-button>
      <el-button @click="cancel">取消</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script>

export default {

  name: "adduser",

  data() {
    return {
      isable:'',
      listenorg:'',
      dialogTreeVisible:false,
      sizeForm: {
        id:'',
        rolename: '',
      }
    };
  },

  methods: {
    onSubmit() {

      this.$axios({
        method:'post',
        url:'/role/addRole',
        data:this.sizeForm,
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', { style: 'color: teal'}, '创建成功')
          });
          this.$emit('lisenDialogAdd','');
          this.timer = setTimeout(()=>{   //设置延迟执行
            this.$router.go(0)
          },500);

        }else{
          alert(data)
        }
      })

    },

    cancel(){
      this.$emit('lisenDialogAdd','');
    }
},


}
</script>

<style scoped>

</style>