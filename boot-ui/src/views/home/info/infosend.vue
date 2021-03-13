<template>
  <div class="about">

    <el-form :model="sendForm" :rules="rules" ref="sendForm" v-bind:disabled="isformShow" label-width="100px" class="demo-sendForm">
      <el-row>
        <el-col :span="22">
      <el-form-item label="信息标题" prop="title">
        <el-input v-model="sendForm.title"  placeholder="请输入标题"></el-input>
      </el-form-item>
        </el-col>
      </el-row>



      <el-row>
        <el-col :span=11>
          <el-form-item label="开始时间" required>
            <el-form-item prop="starttime">
              <el-date-picker
                  v-model="sendForm.starttime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间"
                  style="width: 100%;">
              </el-date-picker>            </el-form-item>
          </el-form-item>
        </el-col>
        <el-col :span=11>
          <el-form-item label="结束时间" required>

            <el-form-item prop="endtime">
              <el-date-picker
                  v-model="sendForm.endtime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间"
                  style="width: 100%;">
              </el-date-picker>
            </el-form-item>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span=11>
          <el-form-item label="信息类型" prop="infotype">
            <el-select v-model="sendForm.infotype" style="width: 100%" placeholder="请选择信息类型">
              <el-option label="文本" value="txt"></el-option>
              <el-option label="图片" value="pic"></el-option>
              <el-option label="视频" value="mp4"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="审批人" prop="checkuser">
<!--            <el-input v-model="sendForm.checkuser"  placeholder="审批人"></el-input>-->
            <el-select  v-model="sendForm.checkuserid" @change="chkuserchange" placeholder="请选择" style="width: 100%">
              <el-option
                  v-for="item in chkusers"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="21" >
          <el-form-item label="发布范围" prop="distorg" >
            <el-input v-model="sendForm.distorg"  disabled  placeholder="请选择发布范围"></el-input>

          </el-form-item>
        </el-col>
        <el-col :span="1">
          <el-button  @click="dialogTreeVisible = true" style="width: 100%">...</el-button>
        </el-col>
      </el-row>
      <el-dialog title="机构选择" :visible.sync="dialogTreeVisible" >
         <org-tree v-on:orgnamelisten="getOrg"></org-tree>
      </el-dialog>

      <el-row>
        <el-col :span="22">
          <el-form-item label="信息描述" prop="descr">
            <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                v-model="sendForm.descr">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="素材选择" prop="checkuser">
      <el-upload
          class="upload-demo"
          action="http://localhost:8080/file/upload"
          :on-success="uploadSuccess"
          :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>&nbsp;
        <span style="font-size:12px">只能上传jpg/png/txt/mp4文件，且不超过500kb</span>
      </el-upload>

      </el-form-item>
<!--      <el-input type="hidden" v-model="sendForm.distorgid"  ></el-input>-->

      <el-form-item style="text-align: center;">

        <el-button type="primary" round @click="submitForm('sendForm')" v-if="sendForm.id==''">立即创建</el-button>
        <el-button round @click="resetForm('sendForm')" v-if="sendForm.id==''">重置</el-button>
        <el-button type="primary" round @click="approveForm('sendForm')" v-if="sendForm.id!=''&&sendForm.sts=='0'">审批</el-button>


      </el-form-item>
    </el-form>

  </div>
</template>
<script>
import OrgTree from "../../../components/OrgTree";
export default {
  name:'infosend',
  components:{
    OrgTree,
  },
  data() {
    return {
      chkuer:{},
      orgName:'',
      dialogTreeVisible: false,
      fileList: [],
      chkusers:[],
      isShow:false,
      isformShow:false,
      sendForm: {
        id:'',
        title: '',
        distorg:'',
        distorgid:'',
        infotype: '',
        starttime: '',
        endtime: '',
        checkuser:'',
        checkuserid:'',
        files:[],
        descr:'',
        senduser:'',
        senduserid:''
      },
      rules: {
        title: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        distorg: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        infotype: [
          { required: true, message: '请选择信息类型', trigger: 'change' }
        ],
        starttime: [
          {  required: true, message: '请选择日期', trigger: 'change' }
        ],
        endtime: [
          { required: true, message: '请选择时间', trigger: 'change' }
        ],
        checkuser: [
          { required: true, message: '请选择审批人', trigger: 'change' }
        ],
        descr: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
    };
  },
  watch:{
    orgName(){
      this.dialogTreeVisible=false;
}
  },
  methods: {

    getOrg(data){
      this.orgName=data;
      this.sendForm.distorgid=data.id;
      this.sendForm.distorg=data.label+'('+data.id+')';
    },
    submitForm() {
     // let {username,pwd}=this.ruleForm;

      this.sendForm.senduser=sessionStorage.getItem('user');
      this.sendForm.senduserid=sessionStorage.getItem('userid');
      this.$axios({
        method:'post',
        url:'/appform/insert',
        data:this.sendForm
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', { style: 'color: teal'}, '创建成功')
          });
          this.$router.push({
            path:'/info/select'
          })
        }else{
          alert(data);
        }
      })

    },
    approveForm(){
      this.$axios({
        method:'post',
        url:'/appform/pushPlstToBlock',
        data:this.sendForm
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', { style: 'color: teal'}, '审批成功')
          });
          this.$router.push({
            path:'/info/todo'
          })
        }else{
          alert(data);
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    uploadSuccess(file){
      this.sendForm.files.push(file.data[0]);
      //console.log(this.files);
    },
    getchkusers(){
      let orgid=sessionStorage.getItem('userorg');
      this.$axios({
        method:'post',
        url:'/user/getUserByWhere',
        data: {orgid:orgid}
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          this.chkusers=data;
        }else{
          alert(data);
        }
      })
    },
    chkuserchange(event){
      let chkusername='';
      this.chkusers.forEach(function(user){
        if(user.id==event){
          chkusername=user.name
        }
      })
      this.sendForm.checkuser=chkusername
    }

  },
  mounted() {
    this.sendForm.id=this.$route.query.appformid
    //console.log(this.sendForm.id)
    if(this.sendForm.id){
      this.isShow=true;
      this.$axios({
        method:'get',
        url:'/appform/selectOne?id='+this.sendForm.id,
      }).then(res=>{
        let {code,data}=res.data
        console.log(data)
        if(code=='0'){
          this.sendForm=data;
        }
      })
    }else {
      this.sendForm.id=''
      this.getchkusers();
    }
  }
}
</script>