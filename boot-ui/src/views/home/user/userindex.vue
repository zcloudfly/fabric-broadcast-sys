<template>
  <el-container>
    <el-header style=' height:70px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" ref="formInline"  class="demo-form-inline">
            <el-form-item label="账号">
              <el-input v-model="formInline.acct" placeholder="请输入账号" size="mini"></el-input>
            </el-form-item>
              <el-form-item label="姓名">
                <el-input v-model="formInline.name" placeholder="请输入姓名" size="mini"></el-input>
              </el-form-item>
                <el-form-item label="机构ID">
                  <el-input v-model="formInline.orgid" placeholder="请输入机构" size="mini"></el-input>
                </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit" size="mini">查询</el-button>
              <el-button type="primary" @click="resetForm('formInline')" size="mini">重置</el-button>
            </el-form-item>

          </el-form>

        </el-tab-pane>

      </el-tabs>
    </el-header>
    <el-main>
      <el-divider></el-divider>
      <el-button type="primary"
                 plain icon="el-icon-circle-plus-outline" size="mini"
                 @click="dialogAddVisible = true"
                 >新增</el-button>
      <el-button type="primary" plain icon="el-icon-edit" size="mini" @click="toggleSelection('edit')">编辑</el-button>
      <el-button type="primary" plain icon="el-icon-user" size="mini" @click="register">注册</el-button>
      <el-button type="primary" plain icon="el-icon-document" size="mini" @click="enroll">登记</el-button>
      <el-button type="primary" plain icon="el-icon-bangzhu" size="mini" @click="toggleSelection('ur')">分配角色</el-button>
     <br><br>
      <el-dialog title="操作用户" :visible.sync="dialogAddVisible" >
        <user-add v-on:lisenDialogAdd="dialogAdd" :row="multipleSelection" :option="option"></user-add>
      </el-dialog>
      <el-dialog title="分配角色" :visible.sync="dialogAddRoleVisible"
                 v-if="dialogAddRoleVisible">
        <user-BindUserRole v-on:lisenDialogAdd="dialogAdd" :userRole="userRole" :uid="multipleSelection[0].id"></user-BindUserRole>
      </el-dialog>
      <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="45">
        </el-table-column>
        <el-table-column
            label="账号"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.acct }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="姓名"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column
            label="机构"
            width="180">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>机构号: {{ scope.row.orgid }}</p>
              <p>机构名称: {{ scope.row.orgName }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.orgid}}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
            label="状态"
            width="180">
          <template slot-scope="scope">
            <span v-if="scope.row.sts==1" style="margin-left: 10px">已添加</span>
            <span v-if="scope.row.sts==2" style="margin-left: 10px">已注册</span>
            <span v-if="scope.row.sts==3" style="margin-left: 10px">已登记</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="todetail(scope.row)">详情</el-button>
            <el-button
                size="mini"
                type="warning">删除</el-button>
          </template>

        </el-table-column>
      </el-table>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="formInline.currentPage"
          :page-sizes="[5, 10, 20, 40]"
          :page-size="formInline.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </el-main>
  </el-container>
</template>

<script>
import UserAdd from "./adduser";
import UserBindUserRole from "./bindUserRole";

export default {
  components:{
    UserAdd,
    UserBindUserRole
  },
  data() {
    return {
      formInline: {
        acct: '',
        orgid: '',
        currentPage:1, //初始页
        pagesize:5,    // 每页的数据
      },
      total:0,
      activeName:'first',
      tableData: [],
      multipleSelection: [],
      dialogAddVisible:false,
      dialogAddRoleVisible:false,
      option:'edit',
      userRole:[]
    }
  },
  created(){
    this.onSubmit();
  },
  methods: {
    onSubmit(){
      this.$axios({
        method:'post',
        url:'/user/getUserByWhere',
        data: this.formInline
      }).then(res=>{
        let {code,msg,data} = res.data;
        if(code=='0'){
          this.tableData=data;
          this.total=parseInt(msg)
        }
      })
    },
    resetForm() {
      this.formInline={}
    },
    // 初始页currentPage、初始每页数据数pagesize和数据data
    handleSizeChange: function (size) {
      this.formInline.pagesize = size;
      //console.log(this.pagesize)  //每页下拉显示数据
      this.onSubmit()
    },
    handleCurrentChange: function(currentPage){
      this.formInline.currentPage = currentPage;
      this.onSubmit() //点击第几页
    },
    todetail( row) {
      this.$router.push({
        path:'/info/detail',
        query:{
          appformid:row.id
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    dialogAdd(){
      this.option='add'
      this.dialogAddVisible=false
      this.dialogAddRoleVisible=false

    },
    register(){
      if(this.multipleSelection.length==0){
        this.$message.error("请选择一行")
      }else  if(this.multipleSelection.length>1){
        this.$message.error("编辑只能选择一行")
      }else {
        this.$axios({
          method: 'post',
          url: '/user/editUser',
          data: {id:this.multipleSelection[0].id,sts:'2'}
        }).then(res => {
          let {code} = res.data;
          if (code == '0') {
            location.reload()
            this.$message.success("设置成功")
          } else {
            this.$message.error("设置失败")
          }
        })
      }
    },
    enroll(){
      if(this.multipleSelection.length==0){
        this.$message.error("请选择一行")
      }else  if(this.multipleSelection.length>1){
        this.$message.error("编辑只能选择一行")
      }else {
        this.$axios({
          method: 'post',
          url: '/user/editUser',
          data: {id:this.multipleSelection[0].id,sts:'3'}
        }).then(res => {
          let {code} = res.data;
          if (code == '0') {
            location.reload()
            this.$message.success("设置成功")
          } else {
            this.$message.error("设置失败")
          }
        })
      }
    },

    toggleSelection(val){
      if(this.multipleSelection.length==0){
        this.$message.error("请选择一行")
      }else  if(this.multipleSelection.length>1){
        this.$message.error("编辑只能选择一行")
      }else{
        if(val=='edit') {
          this.dialogAddVisible = true
        }else if(val=='ur'){
          this.$axios({
            method:'get',
            url:'/role/getUserRole?uid='+this.multipleSelection[0].id,
          }).then(res=>{

            let {code,data} = res.data;
            if(code=='0'){
              this.userRole=data;

              this.dialogAddRoleVisible = true
            }
          })


        }
      }
    }
  }
}
</script>