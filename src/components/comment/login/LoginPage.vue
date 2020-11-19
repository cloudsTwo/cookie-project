<template>

  <el-form ref="users"  class="loginForm" label-width="80px" >
    
   <div class="loginDiv" style="text-align:center;background-color:white; height:430px">
     <!-- <div class="logoDiv" >
       <img src="../../../assets/img/logo2.gif">
     </div> -->

    <el-tabs  v-model="activeName" class="tab" type="border-card" tab-click="font-size:40px" style="padding-top:20px;height:400px;padding-left:20px;padding-right:20px;">
      
      <el-tab-pane label="账号登录" v-model="users" name="first" >

        <el-form-item label="用户名" style="padding-top:40px;padding-left:13%;">
          <el-col :span="15">
            <el-input v-model="users.name" class="input" style="width:260px;" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="密码" style="padding-left:13%;">
           <el-col :span="15">
            <el-input type="password" class="input" v-model="users.password" style="width:260px;" clearable></el-input> 
           </el-col>  
        </el-form-item>

        <el-row style="text-align:center; padding-left:18%; padding-bottom:20px;padding-top:10px">
          <el-col :span="10">
            <el-checkbox v-model="ifLoginNext">下次自动登录</el-checkbox>
         </el-col>

          <el-col :span="10">
            <el-link  href="">忘记密码?</el-link>
          </el-col>
        </el-row>

        <el-form-item class="login-btn">
          <el-button type="primary" @click="login()" style="margin-right:14%">登录</el-button>
        </el-form-item>

        <el-col style="text-align:right">
          还没有账号?
        <el-link :underline="false" class="registered" style="margin-bottom:5px" @click="toReg()">去注册</el-link>
      </el-col>
      </el-tab-pane>

      <el-tab-pane label="手机短信登录" name="second">
        <el-form-item label="手机号" style="padding-top:40px;padding-left:13%;">
          <el-col :span="15">
            <el-input v-model="users.phone" class="input" style="width:260px;" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="验证码" style="padding-left:13%;">
           <el-col :span="12">
            <el-input  class="input" style="width:150px;" clearable></el-input> 
           </el-col>  
           <el-col :span="9">
             <el-button type="info" style="width:90px;" size="small">获取验证码</el-button>
           </el-col>
        </el-form-item>

        <el-row style="padding-left:18%; padding-bottom:20px;padding-top:10px">
          <el-col :span="10">
            <el-checkbox v-model="ifLoginNext">下次自动登录</el-checkbox>
         </el-col>

          <el-col :span="10">
            <el-link :underline="false">忘记密码?</el-link>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="login" style="margin-right:14%">登录</el-button>
        </el-form-item>

        <el-col style="text-align:right">
          还没有账号?
        <el-link :underline="false" class="registered" style="margin-bottom:5px" href="http://localhost:8081/#/registered_page">去注册</el-link>
      </el-col>
      </el-tab-pane>

      <el-tab-pane label="二维码登录" name="third" >
        <img
          style="width: 200px; height: 200px; margin-top:10px"
          src="../../../assets/img/logo3.gif" />

          <p style="margin-top:20px">请使用微信扫描二维码登录</p>
          <p>"小食间"</p>
      </el-tab-pane>

      <div class="logoDiv" >
       <img src="../../../assets/img/logo2.gif">
     </div>
    </el-tabs>
    
   </div>
  </el-form>
</template>


<script>
  import qs from 'qs'
  import axios from 'axios'

  import {Login} from '@/network/user.js'


  export default {

    data() {

      return {
       activeName: 'first',
       type: '',
       users: {},
       ifLoginNext:false, //自动登录
     }

   },  
   
   
  methods: {


    login() { 

      Login(this.users.name,this.users.password).then(res => {
          if(res === ""){
            alert('用户名或密码错误')
          }else{
            alert('登录成功')
            this.$store.commit('login',res) // 改变为登录状态
            console.log(this.$store.state.user)
            this.$router.replace('/home')
          }
        })
        .catch(err => {
          console.log(err)
        });
    },


     jumpToList() {
      this.$router.push('/home')
    },

    toReg() {
      this.$router.push('/registered')
    },


  },

}
</script>


<style scoped>
.loginForm{
  filter:alpha(Opacity=85);
  -moz-opacity:0.85;
  opacity: 0.85;
  background: rgba(0, 0, 0, 0) url("../../../assets/img/logo1.jpeg") no-repeat scroll 100% 100%;
  background-size: cover;
  width:100%;
  height:600px;        
  overflow:hidden;
  z-index: 1;
}

.loginDiv{
  margin-top:80px;
  width:500px;
  height:600px;
  margin-left:27%;
}

.loginDiv .logoDiv{
  text-align:center;
}

.loginDiv .logoDiv img{
  width:300px;
  border-radius:50%;
  height:150px;
}

.add-box{
  padding-left:20px;
  padding-top:20px;
  display: inline;
}

.link{
  text-align: left;
  padding-left: 30px;
  padding-top: 40px;
}

.account_link{
  font-size: 30px;
  padding:20px;
}

.phone_link{
  font-size: 20px;
  padding: 20px;
}

.ma_link{
  font-size: 20px;
  padding: 20px;
}

.registered{
  color: rgb(84, 179, 235);
}


</style>