<template>
  <div class="order-item">
    <el-tooltip content="查看订单详细信息" placement="top" effect="light">
      <span v-if="showPad" @click="showPad = !showPad"  class="el-icon-ccmimakejian detail"></span>
      <span v-else @click="showPad = !showPad"  class="el-icon-ccmimakejian1 detail"></span>
    </el-tooltip>

    <el-image @click="toMenuPage"
      style="width: 100px; height: 100px;border-radius:15px"
      :src="imgUrl"
      fit="cover"></el-image>

    <div class="order-info" >
      <div class="name" >{{orderl.menu.mname}}  <span class="num">X{{orderl.orderGood.count}}</span></div>
      <div class="des">{{orderl.menu.description}}</div>
    </div>

    <div class="material">
      商品信息：
      <div class="material-item" v-for="item in orderl.material" :key="item.mtId">
        <div class="material-name">{{item.materialName}}</div>
        <div class="material-count">{{item.mamount}}</div>
      </div>
    </div>

    <div class="status">
      <div>实际支付：<span class="price">￥{{orderl.order.allPay}}</span></div>
      <div v-if="orderl.order.isShip === 1">
        <span class="isShip">已发货</span>
        <el-button style="margin-left:20px" @click="shiped" type="warning" size="mini">收货</el-button>
      </div>
      <div class="noShip" v-else-if="orderl.order.isShip === 0">未发货</div>
      <div class="hasShip" v-else>已收货</div>
    </div>

    <div class="good-pad" v-if="showPad">
      <span @click="showPad = false" class="el-icon-cccancel cancel"></span>
      <div class="title">订单信息</div>
      <table>
        <tr>
          <td colspan="2">食材信息</td>
          <td colspan="2">
            <div class="material">
              <div class="material-item" v-for="item in orderl.material" :key="item.mtId">
                <div class="material-name">{{item.materialName}}</div>
                <div class="material-count">{{item.mamount}}</div>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>价格</td><td>￥{{orderl.orderGood.singlePrice}}</td><td>数量</td><td>X{{orderl.orderGood.count}}</td>
        </tr>
        <tr>
          <td>收货人</td>
          <td>
            <span v-if="!isChange">{{orderl.orderGood.userName}}</span>
            <input class="username" v-else v-model="orderl.orderGood.userName" />
          </td>
          <td>收货电话</td>
          <td>
            <span v-if="!isChange">{{orderl.orderGood.phoneNumber}}</span>
            <input  class="phone" v-else v-model="orderl.orderGood.phoneNumber" />
          </td>
        </tr>
        <tr>
          <td>收货地址</td>
          <td colspan="3">
            <span v-if="!isChange">{{orderl.orderGood.address}}</span>
            <input class="address" v-else v-model="orderl.orderGood.address" />
          </td>
        </tr>
        <tr>
          <td>订单号</td><td colspan="3">{{orderl.order.orderNum}}</td>
        </tr>
        <tr>
          <td>下单时间</td><td colspan="3">{{orderl.order.createTime}}</td>
        </tr>
        <tr class="tip">
          <td>总共支付:</td><td> <span class="allpay">￥{{orderl.order.allPay}}</span></td>
          <td colspan="2">
            <div class="isShip" v-if="orderl.order.isShip === 1">已发货</div>
            <div class="noShip" v-else-if="orderl.order.isShip === 0">未发货</div>
            <div class="hasShip" v-else>已收货</div>
          </td>  
        </tr>
      </table>
      
      <div class="pay" v-if="!isChange && !orderl.order.isShip" @click="isChange = true">修改订单信息</div>
      <div class="pay" v-else-if="!orderl.order.isShip" @click="changeOrderInfo">确认修改</div>
      <div v-if="isChange && !orderl.order.isShip" @click="isChange = false" style="cursor:pointer" class="back">取消</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name:'GoodItem',
  data(){
    return {
      goodInfo:{},
      user:{},
      showPad:false,
      isChange: false,
    }
  },
  props:{
    orderl:{
      type:Object,
      default(){
        return {}
      }
    }
  },
  computed:{
    imgUrl(){
      return this.orderl.menu.desImg.indexOf('http') === -1 ? 
        require("../../../assets/img/user/" + this.orderl.menu.desImg) : this.orderl.menu.desImg
    },
  },
  mounted(){
    this.user = this.$store.state.user
  },
  methods:{

    toMenuPage(){
      this.$router.push({
        path:'/foodMade',
        query:{
          mid:this.orderl.menu.mid
        }
      })
    },

    async changeOrderInfo(){
      
      let orderGood = this.orderl.orderGood
      if(orderGood.userName && orderGood.phoneNumber && orderGood.address){
        try{
          await axios({
            method:'post',
            url:'http://localhost:8888/day07/orderGoods/changeOrderGoods',
            data:orderGood,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          })

          this.isChange = false

          this.$message({
            type:'success',
            message:'订单信息修改成功!'
          })
        }catch(e){
          console.log(e)
        }
      }else{
        this.$message({
          type:'warning',
          message:'请填写正确的订单信息！'
        })
      }
    },

    shiped(){
      this.$emit('shiped', this.orderl.order.oid)
    }
  }
}
</script>

<style scoped>
  .order-item{
    position:relative;
    margin-bottom: 20px;
    padding:20px;
    background-color: rgb(253, 241, 252);
    border:1px solid rgb(226, 223, 223);
    border-radius: 10px;
    
    height:100px;

    display: flex;
    align-items: center;
  }

  .detail{
    cursor: pointer;
    position: absolute;
    top:5px;
    right:10px;
  }

  .order-item .order-info{
    padding: 0 20px;
    height:100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: flex-start;
  }

  .name{
    color:rgb(124, 2, 2);
    font-weight: 500;
    font-size: 20px;
  }

  .order-info .des{
    font-size: 15px;
    color:rgb(105, 104, 104);
    max-width:100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .status .price{
    font-size: 20px;
    color:rgb(219, 38, 6);
  }

 .order-info .num{
   padding:0 5px;
    font-size: 14px;
    font-weight: 500;
    color:rgb(116, 116, 116);
  }

  .material{
    margin:0 20px;
    padding:10px 5px;
    height: 90%;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    flex-wrap: wrap;
    background-color: white;
    border: 2px solid rgb(204, 2, 29);
    border-radius: 10px;
  }

  .material .material-item{
    color:grey;
    padding:3px 15px;
    width:100px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .status{
    color:rgb(87, 87, 87);
    padding:10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .noShip, .isShip, .hasShip{
    text-align: center;
    margin: 0 auto;
    padding:5px;
    width:60px;
    border-radius: 15px;
    color:white;
  }

  .order-item .noShip{
    background-color: tomato;
  }

  .order-item .isShip{
    background-color:rgb(7, 127, 207);
  }

  .order-item .hasShip{
    background-color:rgb(112, 110, 110);
  }

  .status div{
    margin: 10px 0;
  }

  .good-pad{
    width:400px;
    z-index: 9;
    text-align: center;
    position: fixed;
    left:30%;
    top: 50%;
    transform: translate(-50%, -50%);

    background-color: rgb(248, 248, 213);
    color:rgb(77, 76, 76);
    padding: 20px 30px;
    border: 2px solid rgb(180, 82, 1);
  }

  .good-pad .cancel{
    position: absolute;
    top:8px;
    right: 10px;
  }

  .good-pad .title{
    text-align: center;
    color:indianred;
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 15px;
  }

  .good-pad tr{
    text-align: left;
  }

  .good-pad td{
    width:100px;
    padding:10px 0;
  }

  .good-pad td input{
    outline: none;
    height:20px;
  }

  td input.username{
    width: 70px;
  }

  td input.phone{
    width:90px;
  }

  td input.address{
    width:290px;
  }

  .good-pad tr td:nth-of-type(2n+1){
    color:black;
  }

  .good-pad tr td:nth-of-type(2n){
    color:rgb(110, 110, 110);
  }

  .good-pad .tip{
    padding:10px;
    font-size: 14px;
    color:blue;
  }

  .tip .allpay{
    font-size: 19px;
    color:orangered;
    font-weight: 500;
  }

  .pay{
    cursor: pointer;
    margin:10px auto;
    width:130px;
    padding: 12px 0;
    background-color: rgb(223, 64, 6);
    color:white;
    border-radius: 10px;
  }
</style>