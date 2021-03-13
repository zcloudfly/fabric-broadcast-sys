<template>
  <el-container>
    <el-header style=' height:150px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" ref="formInline"  class="demo-form-inline">
            <el-form-item label="账号">
              <el-input v-model="formInline.acct" placeholder="请输入账号"></el-input>
            </el-form-item>
              <el-form-item label="姓名">
                <el-input v-model="formInline.name" placeholder="请输入姓名"></el-input>
              </el-form-item>
                <el-form-item label="机构">
                  <el-input v-model="formInline.orgid" placeholder="请输入机构"></el-input>
                </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            <el-button type="primary" @click="resetForm('formInline')">重置</el-button>
          </el-form>

        </el-tab-pane>

      </el-tabs>
    </el-header>
    <el-main>
      <el-table
          :data="tableData"
          style="width: 100%">
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
              <p>机构名称: {{ scope.row.orgname }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.orgid}}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="todetail(scope.row)">详情</el-button>

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
export default {
  data() {
    return {
      formInline: {
        acct: '',
        orgid: '',
        //checkuserid:'',
       // sts:'0',
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
      //this.sendForm.senduser=sessionStorage.getItem('user');
      this.formInline.checkuserid=sessionStorage.getItem('userid');
      this.$axios({
        method:'post',
        url:'/user/getUserByWhere',
        data: this.formInline
      }).then(res=>{
        let {code,msg,data} = res.data;
        if(code=='0'){
          this.tableData=data;
          this.total=parseInt(msg)
          console.log(msg,data)
        }
      })
    },
    resetForm() {
      //  console.log(formName);
      this.formInline=[]
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
    todetail( row) {
      // console.log(index, row.id);
      this.$router.push({
        path:'/info/detail',
        query:{
          appformid:row.id
        }
      })
    }
  }
}
</script>