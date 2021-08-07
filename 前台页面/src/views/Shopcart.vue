<template>
  <div class="shopcart" >
    <top-bar currentModel="shopcart" activeStyle="active" class="topbar"/>
    <div v-if="scList.length > 0 && $store.state.isLogin">
      <div class="all">
        总计: <span class="num">({{scList.length}})</span>件食材
      </div>
      <div class="list">
        <good-item @deleteGood="deleteGood" :good="item" v-for="(item,index) in scList" :key="index"></good-item>
      </div>
    </div>
    <div class="tip" v-else-if="!$store.state.isLogin">
      请登录查看购物车信息!
    </div>
    <div class="tip" v-else>
      您的购物车空空如也，快去添加食材吧~~
    </div>
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import GoodItem from '@/components/comment/shopcart/GoodItem'

import {findShopcartByUser, deleteShopcartById} from '@/network/order'

export default {
  name:'Shopcart',
  components:{
    TopBar,
    GoodItem
  },
  data(){
    return {
      user:{},
      scList:[]
    }
  },
  mounted(){
    this.user = this.$store.state.user
    this.initData()
  },
  methods:{
    initData(){
      findShopcartByUser(this.user.uid).then(res => {
        console.log(res.data)
        this.scList = res.data
      })
    },
    deleteGood(sid){
      deleteShopcartById(sid).then(res => {
        this.scList.forEach((item,index) => {
          if(item.shopcart.sid === sid){
            this.scList.splice(index,1)
          }
        })
      })
    }
  }
}
</script>

<style scoped>
  .shopcart .list, .all, .tip{
    width:70%;
    padding-top:20px;
    margin:0 15%;
  }

  .shopcart .all{
    font-size: 18px;
  }

  .all .num{
    color:blue;
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
</style>