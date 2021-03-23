<template>
  <div>

  <el-checkbox-group v-model="checkedRoles" @change="handleCheckedCitiesChange">
    <el-checkbox v-for="role in roles" :label="role.id" :key="role.id">{{role.rolename}}</el-checkbox>
  </el-checkbox-group>
    <br>
    <div  align="center">
      <el-button type="primary" @click="onSubmit" size="mini">保存</el-button>
      <el-button @click="cancel" size="mini">取消</el-button>
    </div>
  </div>
</template>

<script>
//const cityOptions = ['上海', '北京', '广州', '深圳'];
export default {
  name: "bindUserRole",
  props:['userRole','uid'],
  data() {
    return {
      checkAll: false,
      checkedRoles: [],
      roles: [],
      isIndeterminate: true
    };
  },
  mounted() {

    this.$axios({
      method:'post',
      url:'/role/getRoleByWhere',
      data: {currentPage:1,pagesize:20}
    }).then(res=>{

      let {code,data} = res.data;
      if(code=='0'){
        this.roles=data;
        this.checkedRoles=this.userRole
       // console.log(this.userRole)
      }
    })
  },
  methods: {

    handleCheckedCitiesChange(value) {
     // console.log(value)
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.roles.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length;
    },
    cancel(){
      this.$emit('lisenDialogAdd','');
    },
    onSubmit(){
      this.$axios({
        method:'get',
        url:'/role/insertUserRole?uid='+this.uid+'&rids='+this.checkedRoles,
       // data: {uid:this.uid,rids:this.checkedRoles}
      }).then(res=>{
        let {code} = res.data;
        if(code=='0'){
          this.$message.success("保存成功")
          this.cancel()
        }else{
          this.$message.error("保存失败")
        }
      })
    }
  },


}
</script>

<style scoped>

</style>