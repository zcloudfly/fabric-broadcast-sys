<template>
  <el-container>
    <el-header style=' height:100px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" ref="formInline"  class="demo-form-inline">
            <el-form-item label="设备编号">
              <el-input v-model="formInline.clientid" placeholder="设备编号" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="机构ID">
              <el-input v-model="formInline.orgid" placeholder="机构ID" size="mini"></el-input>
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
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        label="设备编号"
        width="130">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.clientid }}</span>
      </template>
    </el-table-column>

    <el-table-column
        label="机构ID"
        width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>机构号: {{ scope.row.orgid }}</p>
          <p>机构名称: {{ scope.row.orgname }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.orgid}}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column
        label="注册日期"
        width="185">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.createtime }}</span>
      </template>
    </el-table-column>
    <el-table-column
        label="区块编码"
        width="440">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.code }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
            size="mini"
            @click="showPlst(scope.row)">播放列表</el-button>
        <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
      <el-dialog title="播放列表" :visible.sync="dialogVisible" v-if="dialogVisible">
        <Ccliplst :clientid="searchClientid"></Ccliplst>
      </el-dialog>
      </el-main>
  </el-container>
</template>

<script>
import Ccliplst from "../../../components/Ccliplst"

export default {
  components:{
    Ccliplst,
  },
  data() {
    return {
      searchClientid:'',
      dialogVisible: false,
      formInline: {
        clientid: '',
        orgid: '',
        currentPage:1, //初始页
        pagesize:5,    // 每页的数据
      },
      total:0,
      activeName:'first',
      tableData: []
    }
  },
  created(){
    this.onSubmit();
  },
  methods: {
    onSubmit(){
      this.$axios({
        method:'post',
        url:'/clientterm/selectbyquery',
        data:this.formInline
      }).then(res=>{
        let {code,msg,data} = res.data;
        if(code=='0'){
          this.total=parseInt(msg);
          this.tableData=data;
        }else{
          alert(data);
        }
      })
    },
    resetForm(formName) {
      console.log(formName);
      this.$refs[formName].resetFields();
    },
    // 初始页currentPage、初始每页数据数pagesize和数据data
    handleSizeChange: function (size) {
      this.pagesize = size;
      console.log(this.pagesize)  //每页下拉显示数据
    },
    handleCurrentChange: function(currentPage){
      this.currentPage = currentPage;
      console.log(this.currentPage)  //点击第几页
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    showPlst(row){
      this.dialogVisible=true;
      this.searchClientid=row.clientid;
     // alert(row);
    }
  }
}
</script>