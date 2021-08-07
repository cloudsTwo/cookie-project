<template>
  <div id="madeSteps" v-if="$store.state.user.uid && menuInfo.menu">
    <div class="head">
      <div class="title">
        <span>食谱</span>
        <span>.{{menu.mname}}</span>
        <span v-if="isOwn" class="edit" @click="editMenu">编辑</span>
      </div>

      <div class="op">
        <span @click="onChangeLike" v-if="!isLike" class="el-icon-ccweibiaoti1">{{menu.likeCount}}</span>
        <span @click="onChangeLike" v-else class="el-icon-ccweibiaoti1active">{{menu.likeCount}}</span>
        <span class="el-icon-ccmimakejian">{{menu.views}}</span>
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
        <span v-if="!isCollect && menu.isChecked === 1" @click="onChangeCollect" style="color:grey" >
          <i style="color:grey" class="el-icon-collection-tag"></i>收藏</span>
        <span v-else-if="menu.isChecked === 1" @click="onChangeCollect"><i class="el-icon-collection-tag"></i>已收藏</span>
      </div>
    </div>

    <el-divider />

    <div class="description">
      <div class="user">
        <el-avatar class="avatar" fit="contain" size="large" :src="avatarUrl"></el-avatar>
        <h2>{{putUser.name}}</h2>
        <span class="focu" @click="onChangeFocu" v-if="!isFocu  && !isOwn">+关注</span>
        <span class="focued" @click="onChangeFocu" v-else-if="!isOwn">已关注</span>
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

    <div class="comment">
      <h3>评论</h3>
      <el-input
        v-model="comment.ccontent"
        type="textarea"
        :rows="10">
      </el-input>

      <div class="comment-btn" @click="addComment"><button>添加评论</button></div>
    </div>

    <div class="user-comments" v-for="(item,index) in comments" :key="index">
      <div class="user-info">
        <img :src="item.user.avatar">
        <div>
          <p>{{item.user.name}}</p>
          <p>
            <span>{{item.comment.ctime}}</span>
            <span>回复</span>
          </p>
        </div>

        <el-button type="danger" style="margin-left:50%;" @click="deleteComment(item.comment.cid)" 
        v-show="$store.state.user.uid?$store.state.user.uid===item.user.uid:false">删除</el-button>
      </div>

      <div class="comment-content">
        {{item.comment.ccontent}}
      </div>

      <el-divider />
    </div>

    <div v-if="isSale" class="order-op">
       <el-tooltip class="item" effect="dark" content="将商品加入购物车" placement="top-start">
         <div  class="shop-cart" @click="addShopcart">
          <span class="el-icon-ccgouwuchekong"></span>
        </div>
      </el-tooltip>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

import {loadIsLike, changeLike,findMenuComment,
  deleteCommentById,loadIsCollect, changeCollect} from '@/network/menu.js'
import {loadIsFocued, changeFocu} from '@/network/user.js'
import {findGoodInfoByMenu,findShopcart} from '@/network/order.js'
import '@/assets/icon/iconfont.css'

export default {
  name:'MadeSteps',
  data(){
    return{
      mid:'',
      menu:{},
      putUser:{},
      ingredients:[],
      steps:[],
      comments:[],

      comment:{
        cid:0,
        uid:0,
        mid:0,
        ccontent:'',
        ctime:''
      }, // 新评论

      goodInfo:{},

      isCollect:false,  // 是否收藏
      isLike:false, // 是否点赞
      isFocu:false, // 是否关注
      isSale: false,  // 是否销售
    }
  },
  props:{
    menuInfo:{
      type:Object,
      default(){
        return {}
      }
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
    // 是否本人发布
    isOwn(){
      return this.$store.state.user.uid === this.menu.uid
    },
    avatarUrl(){
      return this.putUser.avatar.indexOf('http') === -1 ? 
        require("../../../assets/img/user/" + this.putUser.avatar) : this.putUser.avatar
    },
  },
  mounted(){
    this.loadPageData()
  },
  watch:{
    menuInfo(newV,oldV){
      this.loadPageData(newV)
    }
  },
  methods:{
    loadPageData(menuInfo1){
      let menuInfo = JSON.parse(JSON.stringify(menuInfo1))

      if(!menuInfo){
        return 
      }

      // 菜谱基本信息
      this.menu = menuInfo.menu;
      this.mid = this.menu.mid;

      // 查看信息
      if(this.$store.state.isLogin){
        // 防止用户信息获取延迟
        let uid = this.$store.state.user.uid

        loadIsCollect(uid,this.mid).then(res=> {
          this.isCollect = res.data;
        }).catch(err => {
          console.log('err:' + err)
        })

        loadIsLike(uid,this.mid).then(res=> {
          this.isLike = res.data;
        }).catch(err => {
          console.log('err:' + err)
        });

        findGoodInfoByMenu(this.mid).then(res => {
          if(res.data && res.data.reserve > 0){
            this.goodInfo = res.data
            this.isSale = true
          }
        }).catch(err => {
          console.log(err)
        })
      }

      this.menu.desImg =  this.menu.desImg.indexOf('http') === -1 ? require('../../../assets/img/menu/' + this.menu.desImg) : this.menu.desImg;
      this.putUser = menuInfo.user
      // 食材信息
      this.ingredients = menuInfo.materials
      // 菜谱步骤
      this.steps = menuInfo.steps
      this.steps = this.steps.sort(function(a,b){
        return a.stepCount - b.stepCount
      })
      
      this.$store.state.isLogin ? loadIsFocued(this.$store.state.user.uid,this.menu.uid).then(res=> {
        this.isFocu = res.data;
      }).catch(err => {
        console.log('err:' + err)
      }) : '';

      this.steps.map((item, index)=> {
        item.imgUrl =  item.imgUrl.indexOf('http') === -1 ? require('../../../assets/img/menu/' + item.imgUrl) : item.imgUrl;
      })

      // 菜谱评论
      findMenuComment(this.mid).then(res => {
        this.comments = res.data
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
      
    },

    addComment(){
      // 是否登录
      if(!this.$store.state.isLogin){
        this.$message = {
          type:'warning',
          message:'请先登录账号！'
        }
        return
      }


      // 是否为空内容
      let reg = /\s+/
      if(reg.test(this.comment.ccontent) || this.comment.ccontent.length == 0){

        this.$message = {
          type:'warning',
          message:'请输入评论信息！'
        }
        return
      }

      this.comment.uid = this.$store.state.user.uid
      this.comment.mid = this.menu.mid
      let time = new Date()
      this.comment.ctime = this.formateDate(time)

      axios({
        method:'post',
        url:'http://localhost:8888/day07/comment/addComment',
        data:this.comment,
        headers:{
          Authorization:localStorage.eleToken,
          timeout:5000,
          'Content-Type': 'application/json',
        }
      }).then(res=>{
        this.$message({
          type:'success',
          message:'评论成功!'
        })
        let item = {
          comment:this.comment,
          user:this.$store.state.user
        }
        
        this.comment = {  // 重新赋值
          cid:0,
          uid:0,
          mid:0,
          ccontent:'',
          ctime:''
        }, // 新评论
        this.comments.unshift(item);
      })
      .catch(err => {
       this.$message = {
          type:'error',
          message:'评论失败！'
        }
        console.log(err);
      })
    },

    // 删除评论
    deleteComment(cid){
      deleteCommentById(cid).then(res => {
        this.comments.forEach((item,index) => {
          if(item.comment.cid === cid){
            this.comments.splice(index,1)
          }
        })

        this.$message = {
          type:'error',
          message:'评论删除成功！'
        }
      }).catch(err => {
        console.log(err)
      })
    },

    // 改变收藏状态
    onChangeCollect(){
      if(!this.$store.state.isLogin){
        this.$message = {
          type:'warning',
          message:'请先登录账号！'
        }
        return
      }

      changeCollect(this.$store.state.user.uid, this.mid).then(res=>{
        this.isCollect = res.data;
      })
      .catch(err => {
        console.log(err);
      })

    },

    // 改变状态
    onChangeLike(){
      if(!this.$store.state.isLogin){
        this.$message = {
          type:'warning',
          message:'请先登录账号！'
        }
        return
      }

      changeLike(this.$store.state.user.uid, this.mid).then(res=>{
        this.isLike = res.data

        this.isLike ? this.menu.likeCount += 1 : this.menu.likeCount -= 1;
        if(this.isOwn){
          this.isLike ? this.$store.commit('like', 1) : this.$store.commit('like', -1)
        }
      })
      .catch(err => {
        console.log(err)
      })

    },

    // 改变状态
    onChangeFocu(){
      if(!this.$store.state.isLogin){
        this.$message = {
          type:'warning',
          message:'请先登录账号！'
        }
        return
      }

      changeFocu(this.$store.state.user.uid, this.menu.uid).then(res=>{
        this.isFocu = res.data
        
        this.isFocu ? this.$store.commit('focu', 1) : this.$store.commit('focu', -1)
        
      })
      .catch(err => {
        console.log(err)
      })

    },

    // 编辑食谱
    editMenu(){
      this.$emit('editMenu')
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

    // 加入购物车
    async addShopcart(){

      let uid = this.$store.state.user.uid
      let mid = this.menu.mid

      try{
        let res = await findShopcart(uid, this.mid)
        console.log(res)

        // 已经在购物车中
        if(res.data){
          if(res.data.count > this.goodInfo.reserve - 1){
            this.$message({
              type:'warning',
              message:'商品库存不足'
            })
            return
          }else{
            let sc  = res.data
            sc.count += 1
            await axios({
              method:'post',
              url:'http://localhost:8888/day07/shopcart/changeShopcart',
              data:sc,
              headers:{
                Authorization:localStorage.eleToken,
                timeout:5000,
                'Content-Type': 'application/json',
              }
            })
          }

          this.$message({
            type:'success',
            message:'加入购物车成功'
          })
          return
        }else{
          let shopcart = {
            uid,mid,
            count: 1,
            status: 1,
            createTime:'',
            updateTime:'',
          }

          // 加入购物车
          await axios({
            method:'post',
            url:'http://localhost:8888/day07/shopcart/addShopcart',
            data:shopcart,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          })

          this.$message({
            type:'success',
            message:'加入购物车成功'
          })
        }
      }catch(e){
        this.$message({
          type:'error',
          message:'加入购物车失败'
        })

        return
      }
    }
  }
}
</script>

<style scoped>
#madeSteps{
  width:70%;
  margin:0 auto;
  font-size: 18px;
  padding-top:120px;
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

.op :first-child{
  cursor: pointer;
}

.title :nth-child(1){
  font-weight: 800;
  color: rgb(184, 32, 120);
}

.title .edit{
  display: inline-block;
  margin-left:20px;
  padding:0 10px;
  font-size: 15px;
  color: rgb(9, 88, 207);
  /* text-decoration: underline; */
  border-bottom: 1px solid rgb(9, 88, 207);
  cursor: pointer;
}

.attrs{
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
  width:100%;
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

.user .focu{
  margin-left:20px;
  padding:5px 10px;
  border:1px solid rgb(0, 119, 255);
  border-radius: 15px;
  font-size: 14px;
  color:rgb(0, 119, 255);
  cursor: pointer;
}

.user .focued{
  margin-left:20px;
  padding:5px 10px;
  border:1px solid rgb(158, 158, 158);
  border-radius: 15px;
  font-size: 14px;
  color:rgb(158, 158, 158);
  cursor: pointer;
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

.comment-btn{
  padding:10px;
  text-align: right;
}

.comment-btn button{
  border-radius:5px;
  color:white;
  width:100px;
  height:40px;
  background: rgb(182, 78, 113);
  border:0;
}

.user-comments .user-info{
  display: flex;
  align-items: center;
}

.user-comments .user-info img{
  width:70px;
  height:70px;
  border-radius:50%;
}

.user-info div{
  padding:20px;
}

.user-info div :nth-last-child(1){
  font-size: 15px;
}

.user-info div :nth-last-child(1) span{
  padding-right:10px;
}

.user-info div :nth-last-child(1) :nth-last-child(1){
  color:rgb(163, 65, 98);
}

.comment-content{
  padding-left:90px;
}

.order-op{
  position: fixed;
  bottom:30px;
  right:180px;
  color:white;
}

.order-op .shop-cart{
  border-radius: 100px;
  background-color: orangered;
  width:90px;
  height:90px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
</style>