<template>
<div>
    <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
    </el-input>

    <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        default-expand-all
        :filter-node-method="filterNode"
        @node-click="handleNodeClick"
        ref="tree">
    </el-tree>

</div>
</template>

<script>
export default {
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },

  methods: {
    handleNodeClick:function (data){
     // console.log(data);
      this.$emit('orgnamelisten',data);
    //  this.$emit('orgnamelisten',data.label+'('+data.id+')')
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getTreeData(){
      let userorgid=sessionStorage.getItem('userorg');
     // console.log(userorgid);
      this.$axios({
        method:'get',
        url:'/org/getOrgTree?orgId='+userorgid,
      }).then(res=>{
        let {code,data} = res.data;
        if(code=='0'){
          this.data=data;
        }
      })

    }
  },

  data() {
    return {
      filterText: '',
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    };
  },
  mounted() {
    this.getTreeData();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
