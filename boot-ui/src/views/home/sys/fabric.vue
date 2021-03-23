<template>
<div>
  <el-tabs v-model="activeName" >
    <el-tab-pane label="区块链数据统计" name="first">
  <el-row :gutter="12">
    <el-col :span="5">
      <el-card class="box-card" >
        <div slot="header" class="clearfix" >
          <span >（Peer）网络节点数量 20个</span>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-cherry">{{'全节点数量:10个' }}</el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-cherry">{{'轻节点数量:4个' }}</el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-cherry">{{'客户端数量:6个' }}</el-button>
        </div>
      </el-card>
    </el-col>


    <el-col :span="5">
      <el-card class="box-card" >
        <div slot="header" class="clearfix" >
          <span >（Channel）网络通道数量 3个</span>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-guide">publicChannel:1个 </el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-guide">org1Channel:1个 </el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-guide">org2Channel:1个 </el-button>
        </div>

      </el-card>

    </el-col>
    <el-col :span="5">
      <el-card class="box-card" >
        <div slot="header" class="clearfix" >
          <span >（Chaincode)智能合约数量 3个</span>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-document">{{'publicData:1个' }}</el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-document">{{'clientData:1个' }}</el-button>
        </div>
        <div  class="text item"  align="center">
          <el-button icon="el-icon-document">{{'playData:1个' }}</el-button>
        </div>
      </el-card>
    </el-col>
  </el-row>
    </el-tab-pane>
  </el-tabs>
  <el-tabs v-model="activeName" >
    <el-tab-pane label="组织信息" name="first">
      <el-table
          :data="orgData"
          stripe
          style="width: 100%">
        <el-table-column
            prop="oname"
            label="组织名称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="omspid"
            label="组织mspid"
            width="150">
        </el-table-column>
        <el-table-column
            width="220"
            prop="peer"
            label="节点名称">
        </el-table-column>
        <el-table-column
            prop="address"
            label="节点地址">
        </el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
  <el-tabs v-model="activeName" >
    <el-tab-pane label="交易信息" name="first">
      <el-table
          :data="trandata"
          stripe
          style="width: 100%">
        <el-table-column
            prop="txid"
            width="300"
            label="Tx_id">
        </el-table-column>
        <el-table-column
            prop="Timestamp"
            label="Timestamp"
            width="150">
        </el-table-column>
        <el-table-column
            width="150"
            prop="channel_id"
            label="channel_id">
        </el-table-column>
        <el-table-column
            prop="chaincode_id"
            label="chaincode_id">
        </el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
export default {
  name: "fabric",
  data(){
    return{
      activeName:'first',
    orgData: [{
      oname: 'Org1',
      omspid: 'Org1MSP',
      peer: 'peer0.org1.example.com',
      address:'grpcs://192.168.91.142:7051'
    }, {
      oname: 'Org1',
      omspid: 'Org1MSP',
      peer: 'peer1.org1.example.com',
      address:'grpcs://192.168.91.142:8051'
    }, {
      oname: 'Org2',
      omspid: 'Org2MSP',
      peer: 'peer0.org2.example.com',
      address:'grpcs://192.168.91.143:7051'
    }, {
      oname: 'Org2',
      omspid: 'Org2MSP',
      peer: 'peer1.org2.example.com',
      address:'grpcs://192.168.91.143:8051'
    }],
      trandata: [{
        txid: 'QmRghyuHbUw10yxqUPZg2YaqLfuTM3Mddftybgyjjyjre',
        Timestamp: '1616392499383',
        channel_id: 'publicChannel',
        chaincode_id:'publicData'
      }, {
        txid: 'QmVc2GBsnHbUw99yxqUPZg2YaqLfuTM3May16HSnyXC1up',
        Timestamp: '1616392495039',
        channel_id: 'org1Channel',
        chaincode_id:'clientData'
      }, {
        txid: 'Toodnv2GBsnHbUw90yxqUPZg2YaqLfuderoetrmbfmblskdi',
        Timestamp: '1659486806860',
        channel_id: 'org2Channel',
        chaincode_id:'playData'
      }]
    }
  }
}
</script>

<style >
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}


</style>
