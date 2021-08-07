<template>
  <div>
    <div :id="activeStyle">
      <div class="logo">
        小食间
      </div>

      <div class="search" v-if="activeStyle==='active'">
        <input
          :style="{'visibility':activeStyle===true?'hidden':'visible'}"
          type="text"
          placeholder="搜索食谱/食材"
          v-model="searchText">
          <i class="el-icon-search" @click="search"></i>
      </div>
      <div v-else></div>

      <div class="bar">
        <span @click="toHome" :class="currentModel=='home'?'active-bar':''">首页</span>
        <span @click="toMenu" :class="currentModel=='menu'?'active-bar':''">全部食谱</span>
        <span @click="toAdd" :class="currentModel=='add-menu'?'active-bar':''">发布食谱</span>
        <span @click="toShopcart" :class="currentModel=='shopcart'?'active-bar':''">购物车</span>
        <span @click="toOrder" :class="currentModel=='order'?'active-bar':''">订单列表</span>
        <span v-if="isLogin" @click="toPrivate" :class="currentModel==='own'?'active-bar':''">个人主页</span>
        <span v-else @click="toLogin" :class="currentModel==='login'?'active-bar':''">登录/注册</span>
      </div>

    </div>

    <el-divider class="divider" v-if="activeStyle === 'active'" />
  </div>
</template>

<script>
export default {
  name:'TopBar',
  props:{
    activeStyle:{
      type:String,
      default:'topBar'
    },
    currentModel:{
      type:String,
      default:'home'
    }
  },
  data(){
    return{
      searchText:'',
      isLogin:this.$store.state.isLogin,
    }
  },
  watch:{
    activeStyle(val){
      this.activeStyle = val
    },
    currentModel(val){
      this.currentModel = val
    },
    isLogin(val){
      this.isLogin = val
    }
  },
  methods:{
    toMenu(){
      this.$router.push('/menu')
    },

    toHome(){
      this.$router.push('/home')
    },

    toLogin(){
      this.$router.push('/login')
    },

    toAdd(){
      this.$router.push('/addMenu')
    },

    toPrivate(){
      this.$router.push('/own')
    },

    toShopcart(){
      this.$router.push('/shopcart')
    },

    toOrder(){
      this.$router.push('/order')
    },

    search(){
      this.$store.commit('changeSearchName',this.searchText)


      if(this.$route.path.indexOf('menu')!=1){
        this.$router.push('/menu')
      }else{
        this.$bus.$emit('search',this.searchText)
      }
    }
  }
}
</script>

<style scoped>

.el-divider--horizontal{
  margin:0;
}

#topBar,#activea,#active{
  margin:0 auto;
  width:80%;
  min-width: 1024px;

  display: flex;
  justify-content: space-around;
  align-items: center;

  font-size: 15px;
  padding:20px 10%;
  z-index: 999;
  opacity:1;
}

#active,#activea{
  background-color: white;
  opacity: 1;
  color:black;
}

#topBar{
  color:white;
  box-shadow: 0 0 10px grey;
  background-color: rgba(204, 204, 207, 0.5);
}

.logo{
  padding:8px;
  font-size: 16px;
  border-radius:10px;
  opacity: 0.7;
  box-shadow: 0 0 2px 2px  rgb(228, 225, 225);
}

#topBar .logo{
  border:2px solid white;
}

#active .logo, #activea .logo{
  border:2px solid rgb(197, 193, 193);
}

.search input{
  visibility: hidden;
  border-radius:20px;
  border:1px solid white;
  box-shadow: 0 0 10px 3px #e9e7e7;
  height:2.5rem;
  width:300px;
  font-size:14px;
}

.search input:focus{
  outline:none;
}

.el-icon-search::before{
  content: "\e778";
  color:blue;
  padding-left:10px;
  display: inline-block;
}


.bar span{
  padding:15px;
  cursor: pointer;
}

.bar .active-bar{
  font-weight: 800;
  border-bottom: 2px solid;
}
</style>