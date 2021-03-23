<template>
  <el-container>
    <el-header style=' height:70px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" ref="formInline"  class="demo-form-inline">
            <el-form-item label="角色名称">
              <el-input v-model="formInline.rolename" placeholder="请输入角色" size="mini"></el-input>
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


      <el-button type="primary"
                 plain icon="el-icon-bangzhu" size="mini"
                 @click="bindroleperm"
      >添加权限</el-button>
      <br><br>
      <el-dialog title="操作角色" :visible.sync="dialogAddVisible" >
        <add-role v-on:lisenDialogAdd="dialogAdd" ></add-role>
      </el-dialog>
      <el-dialog title="绑定权限" :visible.sync="dialogAddPermissionVisible" size="mini">
        <add-permission v-on:lisenDialog="dialogAdd" :treeData="[]" :dialogType="''" :role="multipleSelection"></add-permission>
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
            label="ID"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="名称"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.rolename }}</span>
          </template>
        </el-table-column>


        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                type="warning"
                size="mini"
                @click="deleteRole(scope.row)"
                >删除</el-button>
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
import AddRole from "./addrole.vue";
import AddPermission from "./addPermission.vue";
export default {
  components:{
    AddRole,
    AddPermission
  },
  data() {
    return {
      formInline: {
        rolename: '',
        currentPage:1, //初始页
        pagesize:5,    // 每页的数据
      },
      total:0,
      activeName:'first',
      tableData: [],
      multipleSelection: [],
      dialogAddVisible:false,
      dialogAddPermissionVisible:false

    }
  },
  created(){
    this.onSubmit();
  },
  methods: {
    onSubmit(){
      this.$axios({
        method:'post',
        url:'/role/getRoleByWhere',
        data: this.formInline
      }).then(res=>{
        let {code,msg,data} = res.data;
        if(code=='0'){
          this.tableData=data;
          this.total=parseInt(msg)
          // console.log(msg,data)
        }
      })
    },
    resetForm() {
      //  console.log(formName);
      this.formInline={}
      //this.$refs[formName].resetFields();
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
    deleteRole(row) {
      this.$axios({
        method:'get',
        url:'/role/deletRole?id='+row.id,
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', { style: 'color: teal'}, data)
          });
          this.timer = setTimeout(()=>{   //设置延迟执行
            this.$router.go(0)
          },500);
        }
      })
    },
    handleSelectionChange(val) {
      //console.log(val)
      this.multipleSelection = val;
    },
    dialogAdd(){
      this.dialogAddVisible=false
      this.dialogAddPermissionVisible=false
     // this.multipleSelection=[]
    },
    bindroleperm(){
      if(this.multipleSelection.length==0){
        this.$message.error("请选择一行")
      }else  if(this.multipleSelection.length>1){
        this.$message.error("编辑只能选择一行")
      }else{

        this.dialogAddPermissionVisible=true
      }
    }


  }
}
</script>