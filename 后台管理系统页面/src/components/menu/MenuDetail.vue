<template>
  <div id="madeSteps" >
    <div class="head">
      <div class="title">
        <span>食谱</span>
        <span>.{{menu.mname}}</span>
      </div>
    </div>

    <el-image class="topImg"  :src="menu.desImg" fit="contain"></el-image>
    <h2>{{menu.name}}</h2>
    <div class="attrs">
      <div>
        <span><i class="el-icon-dish"></i>{{menu.hard === 1?'容易':menu.hard === 2?'一般':'困难'}}</span>
        <span v-if="menu.cookieTime"><i class="el-icon-timer"></i>{{cookieTime}}</span>
        <span><i class="el-icon-fork-spoon"></i>{{menu.flavour}}</span>
      </div>

      <div>
        <span><i class="el-icon-mobile-phone"></i>APP</span>
        <span><i class="el-icon-share"></i>分享</span>
      </div>
    </div>

    <el-divider />

    <div class="description">
      <div class="user">
        <el-avatar class="avatar" fit="contain" size="large" :src="avatarUrl"></el-avatar>
        <h2>{{putUser.name}}</h2>
      </div>

      <div class="des-word">
        {{menu.description}}
      </div>
    </div>

    <el-divider />

    <div class="ingredients">
      <h2>食材清单</h2>
      <table>
        <tr v-for="(item,index) in ingredients" :key="index">
          <td>{{item.materialName}}</td>
          <td>{{item.mamount}}</td>
        </tr>
      </table>
    </div>

    <div class="steps">
      <h2>制作步骤</h2>
      <table>
        <tr v-for="(item,index) in steps" :key="index">
          <td>
            <p>步骤{{item.stepCount}}</p>
            <p>{{item.description}}</p>
          </td>
          <td>
            <el-image class="step-img"  :src="item.imgUrl" fit="contain"></el-image>
          </td>
        </tr>
      </table>
    </div>

    <el-divider />

    <div class="goodBtn" @click="showAddPad = true" v-if="!menuInfo.goodInfo">
      创建商品
    </div>
    <el-dialog class="pad" title="添加商品信息"  :visible.sync="showAddPad">
      <el-form :model="newGood">
        <el-form-item label="商品ID" label-width="150">
         <el-input :disabled="true" v-model="newGood.mid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" label-width="150">
          <el-input v-model="newGood.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="库存" label-width="150">
          <el-input type="number"  v-model="newGood.reserve" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" label-width="150">
            <el-select v-model="newGood.status" placeholder="请选择">
              <el-option
                label="正常"
                :value="1">
              </el-option>
              <el-option
                label="禁用"
                :value="0">
              </el-option>
              <el-option
                label="删除"
                :value="-1">
              </el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAddPad = false">取 消</el-button>
        <el-button type="primary" @click="addGoodInfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {findMenuById} from '@/network/menu.js'
import axios from 'axios'
import '@/assets/icon/iconfont.css'

export default {
  name:'menuDetail',
  data(){
    return{
      mid:'',
      menu:{},
      putUser:{},
      ingredients:[],
      steps:[],
      mid:0,
      menuInfo:{},
      showAddPad:false,
      newGood:{
        status:-3
      },
    }
  },
  computed:{
    // 烹饪时间
    cookieTime(){
      switch (this.menu.cookieTime ){
        case '1': return '15分钟以下';
        case '2': return '15分钟~1小时';
        case '3': return '1~3小时';
        case '4': return '>3小时';
        case '5': return '>一天';
        default: return '不限时';
      }
    },
    avatarUrl(){
      // return this.putUser.avatar.indexOf('http') === -1 ? 
      //   require("../../../assets/img/user/" + this.putUser.avatar) : this.putUser.avatar
    },
  },
  mounted(){
    this.mid = this.$route.query.mid
    this.newGood.mid = this.mid
    this.loadPageData()
  },
  watch:{
  },
  methods:{
    async loadPageData(){
      try{
        let menuInfo = (await findMenuById(this.mid)).data

        this.menuInfo = menuInfo

        // 菜谱基本信息
        this.menu = menuInfo.menu

        this.goodInfo = menuInfo.goodInfo

        // this.menu.desImg =  this.menu.desImg.indexOf('http') === -1 ? require('../../../assets/img/menu/' + this.menu.desImg) : this.menu.desImg;
        this.putUser = menuInfo.user
        // 食材信息
        this.ingredients = menuInfo.materials
        // 菜谱步骤
        this.steps = menuInfo.steps
        this.steps = this.steps.sort(function(a,b){
          return a.stepCount - b.stepCount
        })

        this.steps.map((item, index)=> {
          // item.imgUrl =  item.imgUrl.indexOf('http') === -1 ? require('../../../assets/img/menu/' + item.imgUrl) : item.imgUrl;
        })
      }catch(e){
        console.log(e)
      }
      
    },

    // 格式化的时间
    formateDate(time){
      return time.getFullYear() + '-' + 
      this.formatNum((time.getMonth() + 1)) + '-' + 
      this.formatNum(time.getDate()) + ' ' + 
      this.formatNum(time.getHours()) + ':' + 
      this.formatNum(time.getMinutes()) + ':' + 
      this.formatNum(time.getSeconds())
    },

    formatNum(num){
      if(num < 10){
        return '0' + num
      }else{
        return num
      }
    },

    addGoodInfo(){
      this.newGood.updateTime = ''
      if(!this.newGood.reserve || !this.newGood.price || this.newGood.status === -3){
        this.$message({
          type:'warning',
          message:'请输入正确信息！'
        })  
        return    
      }

      if(this.newGood.reserve < 0 || this.newGood.price < 0 || this.newGood.price > 100000){
        this.$message({
          type:'warning',
          message:'请输入合法数值！'
        })  
        return    
      }else{
        axios({
          method:'post',
          url:'http://localhost:8888/day07/goodInfo/addGoodInfo',
          data:this.newGood,
          headers:{
            Authorization:localStorage.eleToken,
            timeout:5000,
            'Content-Type': 'application/json',
          }
        }).then(res => {
          this.showAddPad= false
          this.$message({
            message: '创建商品成功！',
            type: 'success'
          });
        }).catch( err => {
          this.$message({
            message: '创建商品失败！',
            type: 'error'
          });
        })
      }
    },
  }
}
</script>

<style scoped>
#madeSteps{
  width:70%;
  font-size: 18px;
  padding-top:20px;
  position: relative;
}

.head{
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title{
  padding:10px 0 30px;
}

.title :nth-child(1){
  font-weight: 800;
  color: rgb(184, 32, 120);
}

.attrs{
  padding-top:10px;
  display: flex;
  flex: 1;
  justify-content: space-between;
  align-items: center;
}

.attrs span{
  padding:10px;
}

.attrs i{
  color:rgb(243, 133, 106);
  padding:3px;
}

.topImg{
  height:400px;

  display: flex;
  justify-content: center;
  padding:15px 0;
}

.user{
  display: flex;
  align-items: center;
  padding:10px;
}

.user .avatar{
  display: flex;
  justify-content: center;

  margin-right:20px;
  width:90px;
  height:90px;
  border:2px solid rgb(255, 153, 0);
}


.description .des-word{
  font-size: 18px;
  line-height: 30px;
  padding-top:20px;
  padding-left:10px;
}

.ingredients{
  padding:20px 0;
}

.ingredients table{
  width:100%;
  text-align: left;
}

.ingredients table tr{
  height:40px;
}


.steps table{
  width:100%;
  text-align:left;
}

.steps table td{
  width:10%;
}

.steps .step-img{
  background: rgb(218, 252, 168);
  width:300px;
  height:200px;
}

.goodBtn{
  position: fixed;
  bottom: 50px;
  right: 50px;
  background-color: rgb(180, 39, 4);
  width: 80px;
  height:80px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 16px;
  border-radius: 100px;
  cursor: pointer;
}
</style>