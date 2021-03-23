<template>
  <div class="block">

    <el-tree
        :data="trData"
        ref="trData"
        show-checkbox
        node-key="id"
        default-expand-all
        :expand-on-click-node="false">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button  type="text" size="mini" @click="() => append(data)"><strong>&nbsp;&nbsp;+</strong></el-button>
          <el-button  type="text"  size="mini" @click="() => remove(node, data)"><strong>&nbsp;&nbsp;-</strong></el-button>
        </span>
      </span>
    </el-tree>
    <div size="large" align="center">
      <el-button type="primary"  :style="{ display: dialogType }" size="mini" @click="bindRolePermission">保存</el-button>
      <el-button @click="cancel" size="mini">关闭</el-button>
    </div>

  </div>
</template>

<script>
//let id=1000;
export default {

  props:['treeData','dialogType','role'],
  data(){

    //const data = []
    return{
     trData:this.treeData,
      recode:'0',
      rid:this.role?this.role[0].id:[]
    }
  },
  watch:{
    rid(){
      this.$refs.trData.setCheckedKeys([]);
      this.$axios({
        method:'post',
        url:'/permission/getRoleAndPermission',
        data: {rid:this.role[0].id}
      }).then(res=>{
        let {code,data}= res.data;
        if(code=='0') {
          this.$refs.trData.setCheckedKeys(data);
        }
      })
    }
  },
  created() {
   if(this.treeData.length==0){
     this.$axios({
       method:'get',
       url:'/permission/getTree?id=00',
     }).then(res=>{
       let {code,data} = res.data;
       if(code=='0'){
         this.trData=data;
       }
     })
   }
  },
  mounted(){

    if(this.role!=null&&this.role.length>0){
      this.$axios({
        method:'post',
        url:'/permission/getRoleAndPermission',
        data: {rid:this.role[0].id}
      }).then(res=>{
        let {code,data}= res.data;
        if(code=='0') {
          this.$refs.trData.setCheckedKeys(data);
        }
      })


    }
  },
  methods: {

    append(data) {
      let nodeName='newNode'
      let url=''
      this.$prompt('请输入节点名称和路径用#号隔开', '添加', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        if(value.split('#').length!=2){
          this.$message({
            type: 'info',
            message: '输入有误'
          });
          return;
        }else{
          nodeName=value.split('#')[0]
          url=value.split('#')[1]
        }
       // nodeName=value
        if (!data.children) {
          this.$set(data, 'children', [])
        }
        let cid=data.id+''+(data.children.length+1)
        const newChild = {id: cid, label: nodeName, children: [],url:url}
        this.save( data.id,newChild)
        if(this.recode==0) {
          data.children.push(newChild)
        }else{
          this.$message({
            type: 'info',
            message: '添加失败'
          });
        }

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });

    },

    remove(node, data) {

      this.$axios({
        method:'get',
        url:'/permission/delete?id='+data.id,

      }).then(res=>{
        let {code,msg}=res.data
        if(code=='0'){
          const parent = node.parent;
          const children = parent.data.children || parent.data;
          const index = children.findIndex(d => d.id === data.id);
          children.splice(index, 1);
        }else{
          this.$message({
            type: 'alert',
            message: msg
          });
        }

      })

    },
    save( pid,child){
      let node={id:child.id,name:child.label,pid:pid,url:child.url}
      this.$axios({
        method:'post',
        url:'/permission/insert',
        data: node
      }).then(res=>{
        this.recode= res.data.code;
      })
    },
    bindRolePermission() {
      let treedata=this.$refs.trData.getCheckedNodes();
      this.$axios({
        method:'post',
        url:'/permission/bindRoleAndPermission',
        data: {id:this.role[0].id,ptree:treedata}
      }).then(res=>{
        this.recode= res.data.code;
        if(this.recode=='0'){
          this.$message({
            type: 'alert',
            message: '操作成功！'
          });
          //this.$refs.trData.setCheckedKeys([]);
          this.$emit('lisenDialog','');

        }
      })
    },
    cancel(){
     // this.$refs.trData.setCheckedKeys([]);
      this.$emit('lisenDialog','');
    }
  }

}
</script>

<style scoped>

</style>