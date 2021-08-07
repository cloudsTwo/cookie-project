   <template>
  <div class="good-item">

    <input type="checkbox" v-model="isSelected" class="selected">
    <el-image @click="toMenuPage"
      style="width: 130px; height: 130px"
      :src="imgUrl"
      fit="cover"></el-image>

    <div class="menu-info" v-if="goodInfo.price">
      <div class="name" >{{good.menu.mname}}</div>
      <div class="des">{{good.menu.description}}</div>
      <div class="price">￥{{goodInfo.price}}</div>
    </div>

    <div class="sel-count">
      <div class="good-count">
        <div class="add" :style="{'background': abgColor}" @click="changeCount(1)">+</div>
        <div class="num">X{{good.shopcart.count}}</div>
        <div class="sub" :style="{'background': sbgColor}" @click="changeCount(-1)"> - </div> 
      </div>
      <div class="reserve">库存量: {{goodInfo.reserve}}</div>
    </div>

    <div class="material">
      商品包含：
      <div class="material-item" v-for="item in good.material" :key="item.mtId">
        <div class="material-name">{{item.materialName}}</div>
        <div class="material-count">{{item.mamount}}</div>
      </div>
    </div>

    <div class="good-op">
      
      <div class="buy" @click="buyGood">立即购买</div>
      <div class="del" @click="deleteGood">删除食材</div>
    </div>

    <div class="good-pad" v-if="showPad">
      <span @click="showPad = false" class="el-icon-cccancel cancel"></span>
      <div class="title">订单信息</div>
      <table>
        <tr>
          <td colspan="2">食材信息</td>
          <td colspan="2">
            <div class="material">
              <div class="material-item" v-for="item in good.material" :key="item.mtId">
                <div class="material-name">{{item.materialName}}</div>
                <div class="material-count">{{item.mamount}}</div>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>价格</td><td>￥{{goodInfo.price}}</td><td>数量</td><td>X{{good.shopcart.count}}</td>
        </tr>
        <tr>
          <td>收货人</td><td>{{user.name}}</td><td>收货电话</td><td>{{user.phoneNumber}}</td>
        </tr>
        <tr>
          <td>收货地址</td><td colspan="3">{{user.address}}</td>
        </tr>
      </table>
      <div class="tip">确认订单信息后支付, 总计 <span class="allpay">￥{{good.shopcart.count * goodInfo.price}}</span></div>
      <div class="pay" @click="onPay">立即支付</div>
    </div>
  </div>
</template>

<script>
import {findGoodInfoByMenu} from '@/network/order'
import axios from 'axios'

export default {
  name:'GoodItem',
  data(){
    return {
      isSelected:false,
      goodInfo:{},
      user:{},
      showPad:false,
    }
  },
  props:{
    good:{
      type:Object,
      default(){
        return {}
      }
    }
  },
  computed:{
    imgUrl(){
      return this.good.menu.desImg.indexOf('http') === -1 ? 
        require("../../../assets/img/user/" + this.good.menu.desImg) : this.good.menu.desImg
    },
    abgColor(){
      return this.good.shopcart.count < this.goodInfo.reserve ? 'rgb(78, 148, 212)' : 'rgb(150, 188, 224)'
    },
    sbgColor(){
      return this.good.shopcart.count > 1 ? 'rgb(78, 148, 212)' : 'rgb(150, 188, 224)'
    }
  },
  mounted(){
    this.user = this.$store.state.user
    console.log(this.good)
    this.initData()
  },
  methods:{
    initData(){
      findGoodInfoByMenu(this.good.menu.mid).then(res => {
        console.log(res.data)
        this.goodInfo = res.data
      })
    },

    // 改变商品数量
    changeCount(num){
      if(num==1 && this.good.shopcart.count > this.goodInfo.reserve - 1){
        return
      }
      if(num==-1 && this.good.shopcart.count < 2){
        return
      }
      this.good.shopcart.count += num
    },

    toMenuPage(){
      this.$router.push({
        path:'/foodMade',
        query:{
          mid:this.good.menu.mid
        }
      })
    },

    buyGood(){
      this.showPad = true
    },

    deleteGood(){
      this.$emit('deleteGood', this.good.shopcart.sid)
    },

    async onPay(){
      let date = new Date()
      let orderNum = 'o' + date.getTime()
      let uid = this.user.uid
      let mid = this.good.menu.mid
      let count = this.good.shopcart.count
      let singlePrice = this.goodInfo.price
      let address = this.user.address
      let allPay = this.good.shopcart.count * this.goodInfo.price
      let phoneNumber = this.user.phoneNumber
      let userName = this.user.name

      let order = {
        orderNum,
        uid,
        isPay:1,
        allPay,
        payTime:'',
        isShip:0,
        shipTime:'',
        createTime:'',
        updateTime:'',
        status:1,
      }

      let orderGood = {
        mid,
        count,
        singlePrice,
        address,
        status:1,
        createTime:'',
        updateTime:'',
        phoneNumber,
        userName,
      }

      try{
        let res = await  axios({
          method:'post',
          url:'http://localhost:8888/day07/order/addOrder',
          data:order,
          headers:{
            Authorization:localStorage.eleToken,
            timeout:5000,
            'Content-Type': 'application/json',
          }
        })

        let oid = res.data
        orderGood.oid = oid

        await axios({
          method:'post',
          url:'http://localhost:8888/day07/orderGoods/addOrderGoods',
          data:orderGood,
          headers:{
            Authorization:localStorage.eleToken,
            timeout:5000,
            'Content-Type': 'application/json',
          }
        })

        this.showPad = false
        this.$message({
          message:'支付成功',
          type:'success'
        })

        this.goodInfo.reserve -= count
        await axios({
          method:'post',
          url:'http://localhost:8888/day07/goodInfo/changeGoodInfo',
          data:this.goodInfo,
          headers:{
            Authorization:localStorage.eleToken,
            timeout:5000,
            'Content-Type': 'application/json',
          }
        })

        this.deleteGood()
      }catch(e){
        console.log(e)
      }
    }
  }
}
</script>

<style scoped>
  .good-item{
    margin-bottom: 20px;
    padding:20px;
    background-color: rgb(234, 238, 248);
    border:1px solid rgb(226, 223, 223);
    border-radius: 10px;
    
    height:130px;

    display: flex;
    align-items: center;
  }

  .good-item .selected{
    width:18px;
    height:18px;
    margin-right: 20px;
  }

  .good-item .menu-info{
    padding: 0 20px;
    height:100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: flex-start;
  }

  .menu-info .name{
    color:rgb(49, 49, 49);
    font-weight: 600;
    font-size: 20px;
  }

  .menu-info .des{
    color:rgb(105, 104, 104);
    max-width:100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .menu-info .price{
    font-size: 22px;
    color:rgb(202, 61, 9);
  }

  .sel-count{
    padding: 0 15px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .sel-count .reserve{
    color:grey;
    font-size: 14px;
    margin-top:20px;
  }

  .good-count{
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .good-count .add, .good-count .sub{
    cursor: pointer;
    color:white;
    font-size: 17px;
  }

  .good-count .add{
    padding:2px 7px;
  }

  .good-count .sub{
    padding:2px 9px;
  }

  .good-count .num{
    color:rgb(116, 116, 116);
    padding: 0 15px;
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
    border: 2px solid rgb(107, 106, 172);
  }

  .material .material-item{
    color:grey;
    padding:5px 15px;
    width:100px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .good-op{
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .good-op div{
    border-radius: 5px;
    margin:10px;
    text-align: center;
    width: 75px;
    padding: 10px;
    font-size: 15px;
    color:white;
    cursor: pointer;
  }

  .good-op .buy{
    background-color: tomato;
  }

  .good-op .del{
    background-color: grey;
  }

  .good-pad{
    text-align: center;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    background-color: rgb(222, 235, 247);
    color:rgb(77, 76, 76);
    padding: 20px 30px;
    border: 2px solid grey;
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
    margin-bottom: 20px;
  }

  .good-pad tr{
    text-align: left;
  }

  .good-pad td{
    width:80px;
    padding:20px 0;
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