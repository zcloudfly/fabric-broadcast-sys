<template>
  <div>
    <el-tabs v-model="activeName" >
      <el-tab-pane label="IPFS节点信息" name="first">
    <el-button type="primary" plain icon="el-icon-user" size="mini" >增加</el-button>
    <el-button type="danger" plain icon="el-icon-document" size="mini" >删除</el-button>
    <br>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="orgId"
          label="机构"
          width="180">
      </el-table-column>
      <el-table-column
          prop="peerID"
          label="peerID"
          width="225">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
    </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "ipfs",

  data() {
    return {
      activeName:'first',
      tableData: [{
        peerID: 'QmPwTpNf4W95SZqHKtgYX8ToJpQsxFe5C8bEW5Ai96i8px',
        orgId: '***银行总行（000000）',
        address: '/ip4/192.168.91.138/tcp/4001/ipfs/'
      }, {
        peerID: 'QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5',
        orgId: '***北京分行（010000）',
        address: '/ip4/192.168.91.140/tcp/4001/ipfs/'
      }, {
        peerID: 'TRPwTpNf4W95rgthhjgYX8ToJpQsxFe5C8bEW5Ai96trjy',
        orgId: '***上海分行（030000）',
        address: '/ip4/192.168.91.141/tcp/4001/ipfs/'
      }, {
        peerID: 'Qmfrgooh4W95SZqHKtgYX8ToJpQsxFe5C8bdfrigtb56bu',
        orgId: '***河北分行（040000）',
        address: '/ip4/192.168.91.142/tcp/4001/ipfs/'
      }]
    }
  }
}
</script>

<style scoped>

</style>