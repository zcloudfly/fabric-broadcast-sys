<template>
  <el-container>
    <el-header style=' height:150px'>
      <el-tabs v-model="activeName">
        <el-tab-pane label="查询区" name="first">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="审批人">
              <el-input v-model="formInline.user" placeholder="审批人"></el-input>
            </el-form-item>
            <el-form-item label="活动区域">
              <el-select v-model="formInline.region" placeholder="活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
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
        width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.id }}</span>
      </template>
    </el-table-column>

    <el-table-column
        label="机构名称"
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
    <el-table-column
        label="注册日期"
        width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.createdate }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
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
      </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        user: '',
        region: ''
      },
      activeName:'first',
      tableData: [{
        createdate: '2016-05-02',
        orgname: '***银行',
        orgid: '000000',
        id:'12344477'
      }, {
        createdate: '2016-05-02',
        orgname: '***银行',
        orgid: '000000',
        id:'12344477'
      }, {
        createdate: '2016-05-02',
        orgname: '***银行',
        orgid: '000000',
        id:'12344477'
      }]
    }
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    }
  }
}
</script>