<template>


    <el-main>

      <el-button type="primary"
                 plain icon="el-icon-circle-plus-outline" size="mini"
                 @click="dialogAddVisible = true" v-if="!dialogAddVisible"
      >编辑</el-button>
      <el-dialog title="编辑权限" :visible.sync="dialogAddVisible" >
        <add-permission v-on:lisenDialog="dialogAdd" :treeData="tableData" :dialogType="'none'"></add-permission>
      </el-dialog>


      <br><br>


        <el-table
            :data="tableData"
            style="width: 100%;margin-bottom: 20px;"
            row-key="id"
            border
            lazy
            :load="load"
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            tooltip-effect="dark"
            @selection-change="handleSelectionChange"
        >
          <el-table-column
              type="selection"
              width="50">
          </el-table-column>
          <el-table-column
              prop="id"
              label="ID"
              sortable
              width="120">
          </el-table-column>
          <el-table-column
              prop="label"
              label="菜单名"
              sortable
              width="160">
          </el-table-column>
          <el-table-column
              prop="url"
              label="路径"
              width="160">
          </el-table-column>
          <el-table-column
              prop="createtime"
              label="创建时间">
          </el-table-column>
        </el-table>
    </el-main>
</template>

<script>
import AddPermission from "./addPermission.vue";
export default {
  components:{
    AddPermission,
  },
  data() {
    return {
      dialogAddVisible:false,
      multipleSelection: [],
      total:0,
      id:'00',
      tableData: []
    }
  },
  created(){
    this.onSubmit();
  },

  methods: {


    load(tree, treeNode, resolve) {
        //console.log(treeNode);
       // this.id=treeNode.id;
        setTimeout(() => {

          resolve(treeNode.children)
        }, 1000)
      },

    dialogAdd(){
      this.dialogAddVisible=false
    },
  handleSelectionChange(val) {
      console.log(val)
    this.multipleSelection = val;
  },
  resetForm() {
    //  console.log(formName);
    this.formInline={}
    //this.$refs[formName].resetFields();
  },
  onSubmit(){
    this.$axios({
      method:'get',
      url:'/permission/getTree?id='+this.id,
    }).then(res=>{
      let {code,data} = res.data;
      if(code=='0'){
        this.tableData=data;
        return data;
      }
    })
  }
    },
}
</script>