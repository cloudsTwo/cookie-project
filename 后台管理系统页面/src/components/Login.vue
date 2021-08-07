<template>
  <el-container class="cont" style="height:700px">
    <el-main class="main" style="color:red" >
      <el-row type="flex" justify="center" align="end" class="row" v-if="showLogin">
        <el-col :span="6">
          <el-form ref="ruleForm" :model="ruleForm" label-width="80px" class="form" :rules="rules" >
            <el-form-item label="用户名" prop="name">
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pwd">
              <el-input v-model="ruleForm.pwd" type="password"></el-input>
            </el-form-item>
            <el-form-item style="padding:10px">
              <el-button type="primary" @click="login">登录</el-button>
              <el-button type="" @click="signUp">注册</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <SignUp :showSignUp="this.showSignUp" @changeLogin="changeLogin"></SignUp>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  import SignUp from '../components/children/SignUp'
  import jwt from 'jwt-decode';
  import {Login, findOneUserByName} from '../network/user.js'

  export default {
    name: 'Login',
    components:{
      SignUp
    },
    data: () => ({
      ruleForm: {
        name: '',
        pwd: ''
      },
      rules:{
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          pwd: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ],
      },
      showSignUp:false,
      showLogin:true,
    }),
    mounted() {
      
    },
    methods:{
      async login(){
        // 判断是否为管理员权限
        let res1 = await findOneUserByName(this.ruleForm.name)
        if(res1.data.admin != 1){
          alert('该用户不是管理员，请切换管理员用户登录！')
          return
        }

        // 登录
        Login(this.ruleForm.name,this.ruleForm.pwd).then(res => {
          // 请求失败
          if(!res.data){
            this.$message({
              type:'warning',
              message:'用户名或密码错误！'
            })
            return
          }

          const token = res.data.token;
          const user = res.data.user;

          /*存储到ls*/
          localStorage.setItem('eleToken',token);
          /*解析token中的信息*/
          const decoded = jwt(token);
          /*存储至vuex*/
          this.$store.dispatch("setLogin",!this.isEmpty(decoded))  //decoded空，函数返回真，取反假
          this.$store.dispatch("setUser",user)

          // 保存store中数据防止刷新丢失
          sessionStorage.setItem('store', JSON.stringify(this.$store.state))
          
          /*跳转*/
          this.$router.push('/home/all');
        })
        .catch(err => {
          console.log(err)
        });
      },

      isEmpty(value){
        return(
            value ===undefined || value ===null ||
            (typeof  value === "object" && Object.keys(value).length ===0) ||
            (typeof value ==="string" && value.trim().length ===0)
        );
      },

      signUp(){
        this.showSignUp=true,
        this.showLogin=false
      },

      changeLogin(change){
        this.showLogin = true
        this.showSignUp =false  
      }
    }
  }
</script>

<style lang="stylus" scoped>
  .main
    padding-top 100px
    height 100%
    width 100%
    background-color white
    .form 
      background-color white
      padding-top 50px
      padding-right 50px
      padding-bottom 10px 
      color: white;
      font-weight: bold;
      opacity: 0.9;
      transition: opacity 0.5s;
      -webkit-transition: opacity 0.5s;
      opacity 0.9
</style>
