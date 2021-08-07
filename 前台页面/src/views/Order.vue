<template>
  <div class="order">
    <top-bar currentModel="order" activeStyle="active" class="topbar"/>
    <div class="od-list" v-if="orderList.length > 0 && $store.state.isLogin">

      <el-tabs v-model="currentMode" type="card">
        <el-tab-pane label="全部订单" name="first">
          <div class="all">
            总计: <span class="num">({{orderList.length}})</span>个订单
          </div>
          <div class="list">
            <order-item @shiped="shiped" :orderl="item" v-for="(item,index) in orderList" :key="index"></order-item>
          </div>
        </el-tab-pane>

        <el-tab-pane label="未发货" name="second">
          <div class="all">
            未发货: <span class="num">({{noShip.length}})</span>个订单
          </div>
          <div class="list">
            <order-item :orderl="item" v-for="(item,index) in noShip" :key="index"></order-item>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已发货" name="third">
          <div class="all">
            已发货: <span class="num">({{isShip.length}})</span>个订单
          </div>
          <div class="list">
            <order-item @shiped="shiped" :orderl="item" v-for="(item,index) in isShip" :key="index"></order-item>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已收货" name="fourth">
          <div class="all">
            已收货: <span class="num">({{hasShip.length}})</span>个订单
          </div>
          <div class="list">
            <order-item :orderl="item" v-for="(item,index) in hasShip" :key="index"></order-item>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="tip" v-else-if="!$store.state.isLogin">
      请登录查看订单信息!
    </div>
    <div class="tip" v-else>
      您还没有订单信息，快去购买商品吧~~
    </div>
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import OrderItem from '@/components/comment/order/OrderItem'

import {findOrderByUser,changeOrderShip} from '@/network/order'

export default {
  name:'Order',
  components:{
    TopBar,
    OrderItem,
  },
  data(){
    return {
      orderList:[],
      currentMode:'first',
    }
  },
  mounted(){
    this.initData()
  },
  computed:{
    isShip(){
      return this.orderList.filter(item => {
        return item.order.isShip === 1
      })
    },

    noShip(){
      return this.orderList.filter(item => {
        return item.order.isShip === 0
      })
    },

    hasShip(){
      return this.orderList.filter(item => {
        return item.order.isShip === 2
      })
    },

    
  },
  methods:{
    initData(){
      findOrderByUser(this.$store.state.user.uid).then(res => {
        this.orderList = res.data
      })
    },

    shiped(oid){
      this.$confirm('请确认已经收到商品?', '确认收货', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          changeOrderShip(oid,2).then(res => {
            this.orderList.forEach((item,index) =>{
              if(item.order.oid == oid){
                this.orderList[index].order.isShip = 2
              }
            })
          })
        }).catch(() => {
                  
        });
    }
  }
}
</script>

<style scoped>
  .od-list{
    width:60%;
    padding-top:20px;
    margin:0 25%;
  }

  .order .all{
    font-size: 18px;
    padding: 10px;
  }

  .all .num{
    color:rgb(196, 3, 83);
  }

  .tip{
    background-color: rgb(245, 234, 219);
    height:500px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 25px;
    color:rgb(177, 10, 88);
  }

  .el-tabs{
    width:700px;
    height: calc(100%);
  }


  .el-tabs >>> .el-tabs__content{
    padding:0 20px 20px;
    height: 400px;
    overflow-y: scroll;
  }
</style>