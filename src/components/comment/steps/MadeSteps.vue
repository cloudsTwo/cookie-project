<template>
  <div id="madeSteps">
    <div class="title">
      <span>食谱</span>
      <span>.{{menu.mname}}</span>
    </div>

    <img class="topImg" :src="menu.desImg" />

    <h2>{{menu.name}}</h2>
    <div class="attrs">
      <div>
        <span><i class="el-icon-dish"></i>{{menu.hard === 1?'容易':menu.hard === 2?'一般':'困难'}}</span>
        <span><i class="el-icon-timer"></i>{{menu.cookieTime}}分钟</span>
        <span><i class="el-icon-fork-spoon"></i>{{menu.flavour}}</span>
      </div>

      <div>
        <span><i class="el-icon-mobile-phone"></i>APP</span>
        <span><i class="el-icon-share"></i>分享</span>
        <span v-if="!isCollect" @click="addCollect" style="color:grey" ><i style="color:grey" class="el-icon-collection-tag"></i>收藏</span>
        <span v-else @click="cancelCollect"><i class="el-icon-collection-tag"></i>已收藏</span>
      </div>
    </div>

    <el-divider />

    <div class="description">
      <div class="user">
        <img :src="putUser.avatar" />
        <h2>{{putUser.name}}</h2>
      </div>

      <div>
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
            <img :src="item.imgUrl" />
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
        <img :src="commentUsers[index]?commentUsers[index]['avatar']:''">
        <div>
          <p>{{commentUsers[index]?commentUsers[index]['name']:''}}</p>
          <p>
            <span>{{item.ctime}}</span>
            <span>回复</span>
          </p>
        </div>

        <el-button type="danger" style="margin-left:50%;" @click="deleteComment(item.cid)" 
        v-show="$store.state.user.uid?$store.state.user.uid===commentUsers[index].uid:false">删除</el-button>
      </div>

      <div class="comment-content">
        {{item.ccontent}}
      </div>

      <el-divider />
    </div>
  </div>
</template>

<script>
import axios from 'axios'

import {findMenuById,findMmaterial,findSteps,findMenuComment,
  addMenuComment,deleteCommentById,changeMenuView,changeMenuCollect,
  findCollect,findCollectByUser,cancelCollect} from '@/network/menu.js'
import {findOneUserById} from '@/network/user.js'


export default {
  name:'MadeSteps',
  data(){
    return{
      menu:{},
      putUser:{},
      ingredients:[],
      steps:[],
      comments:[],
      commentUsers:[],

      comment:{
        cid:0,
        uid:0,
        mid:0,
        ccontent:'',
        ctime:''
      }, // 新评论

      isCollect:false,  // 是否收藏
    }
  },
  props:{
    mid:Number
  },
  created(){
    // 查看收藏信息
    if(this.$store.state.isLogin){
     let uid = this.$store.state.user.uid

     findCollect(uid,this.mid).then(res=> {
       if(res === ''){
         this.isCollect = false
       }else{
         this.isCollect = true
       }
     }).then(err => {
       console.log('err' + err)
     })
    }

    // 菜谱基本信息
    findMenuById(this.mid).then(res => {
      this.menu = res

      changeMenuView(this.menu.views + 1,this.menu.mid)
        .then(res => {
          
        }).catch(err => {
          console.log(err)
        })

      findOneUserById(this.menu.uid)
        .then(res => {
          this.putUser = res
        }).catch(err => {
          console.log(err)
        })

    }).catch(err => {
      console.log(err)
    })

    // 食材信息
    findMmaterial(this.mid).then(res => {
      this.ingredients = res
    }).catch(err => {
      console.log(err)
    })

    // 菜谱步骤
    findSteps(this.mid).then(res => {
      this.steps = res
      this.steps = this.steps.sort(function(a,b){
        return a.stepCount - b.stepCount
      })
    }).catch(err => {
      console.log(err)
    })

    // 菜谱评论
    findMenuComment(this.mid).then(res => {
      this.comments = res
      this.getCommentUsers()
    }).catch(err => {
      console.log(err)
    })
  },

  methods:{
    getCommentUsers(){
       // 评论用户信息
      for(let i = 0; i < this.comments.length; i++){

        findOneUserById(this.comments[i].uid)
        .then(res => {
          this.commentUsers.push(res)
        }).catch(err => {
          console.log(err)
        })
      }
    },

    addComment(){
      // 是否登录
      if(!this.$store.state.isLogin){
        alert('请先登录账号')
        return
      }


      // 是否为空内容
      let reg = /\s+/
      if(reg.test(this.comment.ccontent)){
        this.comment.ccontent = this.comment.ccontent.replace(reg,'')

        if(this.comment.ccontent === ''){
          alert('请输入评论内容')
          return
        }
      }

      this.comment.uid = this.$store.state.user.uid
      this.comment.mid = this.menu.mid
      let time = new Date()
      this.comment.ctime = time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDay() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds()

      //  console.log(this.comment)
      // addMenuComment(this.comment)
      // .then(res => {
      //   alert('评论添加成功')
      // }).catch(err => {
      //   console.log(err)
      // })

      axios.post('http://localhost:8888/day07/comment/addComment',this.comment)
        .then(res=>{
          alert('评论成功')
          // 菜谱评论
          // findMenuComment(this.mid).then(res => {
          //   this.comments = res
          //   this.getCommentUsers()
          // }).catch(err => {
          //   console.log(err)
          // })

          this.comments.unshift(this.comment)
          this.commentUsers.unshift(this.$store.state.user)
        })
        .catch(err => {
          alert("评论失败");
          console.log(err);
        })
    },

    deleteComment(cid){
      deleteCommentById(cid)
      .then(res => {
        this.comments.forEach((item,index) => {
          if(item.cid === cid){
            this.comments.splice(index,1)
            this.commentUsers.splice(index,1)
          }
        })

        alert('删除成功')
      }).catch(err => {
        console.log(err)
      })
    },

    addCollect(){
      if(!this.$store.state.isLogin){
        alert('请先登录账号')
        return
      }

      let collect = {
        uid:this.$store.state.user.uid,
        mid:this.mid,
        time:''
      }

      axios.post('http://localhost:8888/day07/collect/addCollect',collect)
        .then(res=>{
          this.isCollect=true
        })
        .catch(err => {
          console.log(err);
        })

    },

    cancelCollect(){
      let uid = this.$store.state.user.uid

      cancelCollect(uid,this.mid).then(res => {
        this.isCollect = false
      }).catch(err => {
        console.log(err)
      })
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
}

.title{
  padding:10px 0 30px;
}

.title :nth-child(1){
  font-weight: 800;
  color: rgb(184, 32, 120);
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
  width:90%;
  padding:15px 0;
}

.user{
  display: flex;
  padding:10px;
}

.user img{
  margin-right:30px;
  width:80px;
  height:80px;
  border-radius:50%;
}

.description :nth-last-child(1){
  font-size: 18px;
  line-height: 30px;
}

.ingredients{
  padding:20px 0;
}

.ingredients table{
  width:50%;
  text-align: left;
}


.steps table{
  width:100%;
  text-align:left;
}

.steps table td{
  width:10%;
}

.steps img{
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
</style>