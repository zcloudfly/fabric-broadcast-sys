<template>
  <el-container>
    <el-header style=' height:150px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" ref="formInline"  class="demo-form-inline">
            <el-form-item label="申请单号">
              <el-input v-model="formInline.id" placeholder="申请单号"></el-input>
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
            label="单号"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="标题"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="申请人"
            width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.senduser}}</span>
          </template>
        </el-table-column>

        <el-table-column
            label="开始时间"
            width="185">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.starttime }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="结束时间"
            width="185">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.endtime }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="发布范围"
            width="180">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>机构号: {{ scope.row.distorgid }}</p>
              <p>机构名称: {{ scope.row.distorg }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.distorg}}</el-tag>
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
        id: '',
       // orgid: '',
        checkuserid:'',
        sts:'0',
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
        url:'/appform/findFormByWhere',
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