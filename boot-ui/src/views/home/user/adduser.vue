<template>
  <div>
  <el-form ref="sizeForm" :model="sizeForm" label-width="80px" size="mini">
    <el-form-item label="用户名称">
      <el-input v-model="sizeForm.name"></el-input>
    </el-form-item>

        <el-form-item label="所属机构"  >
          <el-input v-model="sizeForm.orgname"
                    placeholder="请选择机构"
                    disabled
                    style="width: 90%"
          ></el-input>
          <el-button  @click="dialogTreeVisible = true" style="width: 10%">...</el-button>
          <el-dialog title="机构选择" :visible.sync="dialogTreeVisible" >
            <org-tree v-on:orgnamelisten="getOrg"></org-tree>
          </el-dialog>
        </el-form-item>

    <el-form-item label="账号">
      <el-input v-model="sizeForm.acct" ></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="sizeForm.email" v-bind:disabled="isable!=sizeForm.acct"></el-input>
    </el-form-item>
    <el-form-item label="电话">
      <el-input v-model="sizeForm.tel" v-bind:disabled="isable!=sizeForm.acct"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="sizeForm.pwd" v-bind:disabled="isable!=sizeForm.acct"></el-input>
    </el-form-item>
    <el-form-item size="large" align="center">
      <el-button type="primary" @click="onSubmit" size="mini">保存</el-button>
      <el-button @click="cancel" size="mini">取消</el-button>
    </el-form-item>
    <el-input type="hidden" v-model="sizeForm.id"></el-input>
  </el-form>
  </div>
</template>

<script>
import OrgTree from "../../../components/OrgTree";
export default {
  components:{
    OrgTree,
  },
  name: "adduser",
  props:{
    row:{
      default: []
    },
    option:{
      default: ''
    }
  },
  data() {
    return {
      isable:'',
      listenorg:'',
      dialogTreeVisible:false,
      sizeForm: {
        acct:'',
        name: '',
        orgid: '',
        orgname:'',
        email:'',
        tel:'',
        pwd:''
      }
    };
  },
  watch:{
    listenorg() {
       this.dialogTreeVisible=false;
    },
    row(){
      if(this.row.length==1) {
        this.sizeForm = this.row[0]
        this.sizeForm.orgname = this.row[0].orgName
        if (this.row[0].acct == sessionStorage.getItem('acct')) {
          //console.log(this.row[0])
          this.isable = this.row[0].acct
        }
      }
    }
  },
  mounted() {
    if(this.row.length==1) {
      this.sizeForm = this.row[0]
      this.sizeForm.orgname = this.row[0].orgName
      if (this.row[0].acct == sessionStorage.getItem('acct')) {
        console.log(this.row[0])
        this.isable = this.row[0].acct
      }
    }
  },
  methods: {
    onSubmit() {
      let url=''
     // console.log(this.option)
      if(this.option=='add'){
        url='/user/addUser'
      }else{
        url='/user/editUser'
      }
      this.$axios({
        method:'post',
        url:url,
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
    getOrg(data){
      this.listenorg=data.id;
      this.sizeForm.orgid=data.id;
      this.sizeForm.orgname=data.label;
    },
    cancel(){
      this.$emit('lisenDialogAdd','');
    }
},


}
</script>

<style scoped>

</style>