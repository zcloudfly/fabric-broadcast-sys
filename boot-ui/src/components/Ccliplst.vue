<template>
<div>

  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        prop="name"
        label="素材名称"
        width="180">
    </el-table-column>
    <el-table-column
        prop="startdate"
        label="开始时间"
        width="180">
    </el-table-column>
    <el-table-column
        prop="staopdate"
        label="结束时间">
    </el-table-column>
    <el-table-column
        prop="type"
        label="类型">
    </el-table-column>
    <el-table-column
        prop="hashid"
        label="地址哈希码">
      <template slot-scope="scope">
        <el-link type="primary" @click="download(scope.row.hashid,scope.row.name)">{{scope.row.hashid}}</el-link>
<!--        <a @click="download(scope.row.hashid,scope.row.name)">{{scope.row.hashid}}</a>-->
      </template>
    </el-table-column>
  </el-table>


</div>
</template>

<script>


export default {
  name: "cliplst",
  props:['clientid'],
  data(){
    return{
      tableData:[]
    }
  },
  methods:{
    getCliId(){
      this.$axios({
        method:'get',
        url:'/clientterm/getCliPlst?clientid='+this.clientid,
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          console.log(data)
         this.tableData =JSON.parse(data);
        }
      })
    },
    download(hash,filename){
     window.location.href = 'http://localhost:8080/ipfs/getfile?hash='+hash+'&filename='+filename;
    }
  },
  mounted() {
    this.getCliId();
  }
}
</script>

<style scoped>

</style>