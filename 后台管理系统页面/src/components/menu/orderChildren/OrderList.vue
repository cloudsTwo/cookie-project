<template>
  <div id="orderList">
    <list-display  :datas="newContent" @jump="jump">
      <span slot="title">
        <span v-if="currentMode != '全部订单'" class="return" @click="$emit('getAll')">返回</span>
        当前：{{newMode}} ({{newContent.length}})
      </span>
      <span class="classify" slot="btn">
        <el-tag @click="changeType(1)">全部</el-tag>
        <el-tag @click="changeType(2)" type="warning">未发货</el-tag>
        <el-tag @click="changeType(3)" type="success">已发货</el-tag>
        <el-tag @click="changeType(4)" type="info">已收货</el-tag>
      </span>

      <table slot="table" v-if="newContent.length">
        <th></th>
        <th>订单号</th>
        <th>下单时间</th>
        <th>是否支付</th>
        <th>是否发货</th>
        <th>订单状态</th>
        <th>管理</th>

        <tr v-for="(item,index) in currentContent"  :key="index">
          <td class="check"><input type="checkbox" /></td>
          <td>{{item.order.orderNum}}</td>
          <td>{{item.order.createTime}}</td>
          <td>{{item.order.isPay ? '是' : '否'}}</td>
          <td>
            <span v-if="item.order.isShip == 1" >已发货</span>
            <span v-else-if="item.order.isShip == 0">未发货</span>
            <span v-else-if="item.order.isShip == 2">已收货</span>
          </td>
          <td>
            <span v-if="item.order.status == 1" >正常</span>
            <span v-else-if="item.order.status == 0">禁用</span>
            <span v-else-if="item.order.status == -1">已删除</span>
          </td>
          <td>
            <el-button type="warning" size="mini" @click="showMore(item,index)">详情</el-button>
            <el-button v-if="currentType == 2" type="success" size="mini" @click="ship(item.order.oid,index)">发货</el-button>
          </td>
        </tr>
      </table>
      <div slot="table" class="no-content" v-else>
        当前无({{newMode}})
      </div>
    </list-display>

    <div class="good-pad" v-if="showPad">
      <span @click="showPad = false" class="el-icon-cccancel cancel">X</span>
      <div class="title">订单信息</div>
      <table>
        <tr>
          <td colspan="2">商品信息</td>
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
            <input class="username" v-else v-model="changeO.changeUsername" />
          </td>
          <td>收货电话</td>
          <td>
            <span v-if="!isChange">{{orderl.orderGood.phoneNumber}}</span>
            <input  class="phone" v-else v-model="changeO.changePhone" />
          </td>
        </tr>
        <tr>
          <td>收货地址</td>
          <td colspan="3">
            <span v-if="!isChange">{{orderl.orderGood.address}}</span>
            <input class="address" v-else v-model="changeO.changeAddress" />
          </td>
        </tr>
        <tr>
          <td>订单号</td><td >{{orderl.order.orderNum}}</td>
          <td>下单时间</td><td >{{orderl.order.createTime}}</td>
        </tr>
        <tr class="tip">
          <td>总共支付:</td><td> <span class="allpay">￥{{orderl.order.allPay}}</span></td>
          <td>发货状态</td>
          <td v-if="!isChange">
            <div class="isShip" v-if="changeO.changeShip == 1">已发货</div>
            <div class="noShip" v-else-if="changeO.changeShip == 0">未发货</div>
            <div class="hasShip" v-else>已收货</div>
          </td> 
          <td v-else>
            <el-select v-model="changeO.changeShip" placeholder="请选择">
              <el-option
                label="未发货"
                value="0"
                >
              </el-option>
              <el-option
                label="已发货"
                value="1"
                >
              </el-option>
              <el-option
                label="已收货"
                value="2">
              </el-option>
            </el-select>
          </td>
        </tr>
        <tr>
          <td>订单状态</td>
          <td v-if="!isChange">
            <span v-if="changeO.changeStatus == 1" >正常</span>
            <span v-else-if="changeO.changeStatus == 0">禁用</span>
            <span v-else-if="changeO.changeStatus == -1">已删除</span>
          </td>
          <td v-else>
            <el-select v-model="changeO.changeStatus" placeholder="请选择">
              <el-option
                label="正常"
                value="1">
              </el-option>
              <el-option
                label="禁用"
                value="0">
              </el-option>
              <el-option
                label="已删除"
                value="-1">
              </el-option>
            </el-select>
          </td>
        </tr>
      </table>
      
      <div class="pay" v-if="!isChange" @click="isChange = true">修改订单信息</div>
      <div class="pay" v-else @click="changeOrderInfo">确认修改</div>
      <div v-if="isChange" @click="isChange = false" style="cursor:pointer" class="back">取消</div>
    </div>
  </div>
  
</template>

<script>
import ListDisplay from '@/components/children/ListDisplay'
import axios from 'axios'

import {changeOrderShip} from '@/network/order'

export default {
  name:'OrderList',
  components:{
    ListDisplay
  },
  data(){
    return {
      selectIndex:0,  // 选择的订单
      orderl:{},  // 选中查看订单
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:4, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面
      currentType:1,
      newContent:[],
      newMode:this.currentMode,

      showPad: false,
      isChange:false,
      changeO:{
        changeStatus:'1',
        changeShip:'0',
        changeUsername:'',
        changePhone:'',
        changeStatus:''
      }
    }
  },
  computed:{
  },
  props:{
    content:{
      type:Array,
      default(){
        return []
      }
    }, // 所有文章
    currentMode:{
      type:String,
      default:''
    } // 当前模块
  },
  watch:{
    content(newV,oldV){
      this.init(newV)
    },
    currentMode(newM){
      this.newMode = newM
    }
  },
  mounted(){
    this.init(this.content)
  },
  methods:{
    changeType(t){
      this.currentType = t;
      let newV = {};
      +t;
      switch(t){
        case 1:
          newV = this.content
          this.newMode = '全部订单'
          break;
        case 2:
          newV = this.content.filter(item => {
            return item.order.isShip == 0
          })
          this.newMode = '未发货订单'
          break;
        case 3:
          newV = this.content.filter(item => {
            return item.order.isShip == 1
          })
          this.newMode = '已发货订单'
          break;
        case 4:
          newV = this.content.filter(item => {
            return item.order.isShip == 2
          })
          this.newMode = '已收货订单'
          break;
      }

      this.init(newV);
    },
    init(content){
      // 传给分页组件
      this.newContent = content

      // 分页
      this.totalNum = content.length
      this.totalPage = parseInt(this.totalNum%this.onePageNum === 0? this.totalNum / this.onePageNum : this.totalNum / this.onePageNum + 1)
      this.speratePage(content)
    },

    // 分页
    speratePage(content){

      // 该页内容开始位置
      let startNum = (this.currentPage - 1) * this.onePageNum
      let endNum = 0
      
      // 是否为最后一页
      if(this.currentPage === this.totalPage){
        endNum = this.totalNum
      }else{
        endNum = startNum + this.onePageNum
      }

      this.currentContent = content.slice(startNum,endNum)
    },

    jump(content){
      this.currentContent = content
    },

    toEditUser(index){
      let selectIndex =  this.onePageNum * (this.currentPage - 1) + index
      this.$router.push({path:'/home/userEdit', query:{selectIndex}})
    },

    getShip(isShip){
      +isShip;
      switch(isShip){
        case 0: 
          return '未发货';
        case 1:
          return '已发货';
        case 2:
          return '已收货';
      }
    },

    getStatus(s){
      +s;
      switch(s){
        case 0: 
          return '禁用';
        case 1:
          return '正常';
        case -1:
          return '删除';
      }
    },

    showMore(item,index){
      this.selectIndex = index
      this.orderl = item

      this.changeO = {
        changeStatus : item.order.status,
        changeShip : item.order.isShip,
        changeUsername : item.orderGood.userName,
        changePhone : item.orderGood.phoneNumber,
        changeAddress : item.orderGood.address
      }
      this.showPad = !this.showPad
    },

    async changeOrderInfo(){
      try{
        let orderGood = this.orderl.orderGood
        // 订单发生改变
        if(this.changeO.changeStatus != this.orderl.order.status || this.changeO.changeShip != this.orderl.order.isShip){
          this.orderl.order.status = this.changeO.changeStatus
          this.orderl.order.isShip = this.changeO.changeShip

          await axios({
            method:'post',
            url:'http://localhost:8888/day07/order/changeOrder',
            data:this.orderl.order,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          })

          this.currentContent[this.selectIndex].order = this.orderl.order
        }

        // 订单商品信息改变
        if(orderGood.userName && orderGood.phoneNumber && orderGood.address){
          if(orderGood.status != this.changeO.changeStatus || orderGood.userName != this.changeO.changeUsername || orderGood.phoneNumber != this.changeO.changePhone ||
          orderGood.address != this.changeO.changeAddress){
            
            orderGood.status = this.changeO.changeStatus
            orderGood.userName = this.changeO.changeUsername 
            orderGood.phoneNumber = this.changeO.changePhone
            orderGood.address = this.changeO.changeAddress

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

            this.currentContent[this.selectIndex].orderGood = orderGood

            this.isChange = false

            this.$message({
              type:'success',
              message:'订单信息修改成功!'
            })
          }else{
            this.isChange = false
            return 
          }
        }else{
          this.$message({
            type:'warning',
            message:'请填写正确的订单信息！'
          })
        }
      }catch(e){
        console.log(e)
      }
    },

    async ship(oid,index){
      let isShip = 1
      try{
        await changeOrderShip(oid, isShip)
        this.content.forEach((item,i) => {
          if(item.order.oid == oid){
            this.content[i].order.isShip = 1
          }
        })
        this.currentContent.splice(index,1)
      }catch(e){
        console.log(e)
      }
    }
  }
}
</script>

<style scoped>

  table{
    width:90%;
    border-radius: 3px;
  }

  table,th,tr,td{
    border:1px solid rgb(206, 205, 205);
    border-collapse: collapse;
    border-radius:3px;
  }

  tr,th{
    height:3rem;
    line-height: 3rem;
    text-align: center;
  }

  #orderList{
    border-radius: 3px;
    margin-top:10px;
    border:1px solid rgb(206, 205, 205);
  }

  .return{
    cursor: pointer;
    color:blue;
    padding:10px;
    text-decoration: underline;
  }

  .list-top{
    border-bottom: 1px solid rgb(206, 205, 205);
    padding:10px 10% 10px 2%;
    display: flex;
    justify-content: space-between;
  }

  .list-content{
    margin:20px;
    display: flex;
    justify-content: center;
  }

  .list-content .check{
    width:3rem;
  }

  .page-jump{
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .page-jump button{
    width:60px;
    height:30px;
    border-radius:3px;
    color:white;
    background-color: rgb(69, 115, 201);
    margin:10px;
  }

  .page-jump input{
    border:1px solid #aabbaa;
    height:30px!important;
  }

  .page-jump span{
    color: rgb(141, 140, 140);
    padding:0 10px 0;
  }

  .good-pad{
    width:600px;
    z-index: 9;
    text-align: center;
    position: fixed;
    left:50%;
    top: 50%;
    transform: translate(-50%, -50%);

    background-color: rgb(213, 235, 248);
    color:rgb(77, 76, 76);
    padding: 20px 30px;
    border: 2px solid rgb(34, 1, 180);
  }

  .good-pad table, .good-pad td, .good-pad tr{
    border: 0;
  }

  .good-pad .cancel{
    position: absolute;
    top:8px;
    right: 10px;
    cursor: pointer;
  }

  .good-pad .title{
    text-align: center;
    color:rgb(92, 150, 205);
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 15px;
  }

  .good-pad tr{
    text-align: left;
  }

  .good-pad input{
    outline: none;
    height:30px;
  }

  td input.username{
    width: 150px;
  }

  td input.phone{
    width:150px;
  }

  td input.address{
    width:390px;
  }

  .good-pad tr td:nth-of-type(2n+1){
    width: 70px;
    color:black;
  }

  .good-pad tr td:nth-of-type(2n){
    width: 150px;
    color:rgb(110, 110, 110);
  }

  .tip .allpay{
    font-size: 19px;
    color:rgb(228, 74, 3);
    font-weight: 500;
  }

  .pay{
    cursor: pointer;
    margin:10px auto;
    width:130px;
    padding: 12px 0;
    background-color: rgb(8, 107, 221);
    color:white;
    border-radius: 10px;
  }

  .material{
    width:200px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    flex-wrap: wrap;
    background-color: white;
    border: 2px solid rgb(2, 22, 204);
    border-radius: 10px;
  }

  .material .material-item{
    width:100%;
    color:grey;
    display: flex;
    align-items: flex-start;
    justify-content: space-around;
  }

  .good-pad input{
    border:1px solid rgb(53, 53, 53);
    background-color: white;
  }

  .status{
    color:rgb(87, 87, 87);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .noShip, .isShip, .hasShip{
    height:30px;
    padding:0;
    margin:0 auto;
    width:60px;
    border-radius: 15px;
    color:white;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .noShip{
    background-color: tomato;
  }

  .isShip{
    background-color:rgb(7, 127, 207);
  }

  .hasShip{
    background-color:rgb(112, 110, 110);
  }

  .classify{
    cursor: pointer;
  }

  .no-content{
    display: flex;
    justify-content: center;
    align-items: center;
    height:300px;
    font-size: 22px;
  }

</style>