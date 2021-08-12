<template>
  <el-form
    ref="user"
    v-model="user"
    class="regForm"
    label-width="80px"
  >

    <div style="padding-top:2px;height:600px;padding-left:10px;padding-right:20px;">

      <div
        class="regDiv"
        style="border-radius:10px;text-align:center;padding:10px;width:500px;height:400px;background-color:white; margin-top:80px"
      >
        <h2>注册用户</h2>
        <div style="margin-left:15%">
          <el-form-item label="用户名">
            <el-col
              :span="14"
              style="margin-bottom:5px"
            >
              <el-input
                v-model="user.name"
                placeholder="请输入用户名"
                style="width:250px"
                clearable
              ></el-input>
            </el-col>
          </el-form-item>

          <el-form-item label="手机号">
            <el-col
              :span="14"
              style="margin-bottom:5px"
            >
              <el-input
                v-model="user.phoneNumber"
                placeholder="请输入手机号"
                style="width:250px"
                clearable
              ></el-input>
            </el-col>
          </el-form-item>

          <el-form-item label="密码">
            <el-col :span="14">
              <el-input
                v-model="user.password"
                type="password"
                placeholder="请输入密码"
                style="width:250px"
                clearable
              ></el-input>
            </el-col>
          </el-form-item>

          <el-form-item label="验证码">
            <el-col
              :span="8"
              style="margin-bottom:5px"
            >
              <el-input
                v-model="qrCode"
                placeholder="请输入验证码"
                style="width:150px"
                clearable
              ></el-input>
            </el-col>

            <el-col :span="15">
              <el-button
                type="info"
                style="width:90px;"
                size="small"
              >获取验证码</el-button>
            </el-col>
          </el-form-item>
        </div>

        <el-button
          type="primary"
          @click="addUser()"
        >注册</el-button>

        <div style="text-align:right; margin-right:25px; ">
          <span>已有账号?</span>
          <el-link
            @click="jumpToLogin()"
            :underline="false"
            style="margin-bottom:5px;"
          >去登录</el-link>
        </div>

        <div class="logoDiv">
          <img src="../../../assets/img/logo2.gif">
        </div>
      </div>

    </div>
  </el-form>
</template>


<script>
import { Registered, findOneUserByName } from '@/network/user.js'
import axios from 'axios'

export default {

  data () {
    return {
      user: {},
      qrCode: '',  // 验证码
    }
  },
  methods: {
    addUser () {
      let reg = /\s+/
      // 验证用户
      for (let n in this.user) {
        if (this.user[n] === '' || reg.test(this.user[n])) {
          this.$message({
            type: 'warning',
            message: '请输入' + n,
          })
          return
        }
      }
      this.user.admin = 0;
      // Registered(this.user)
      findOneUserByName(this.user.name).then(res => {
        if (res.data != '') {
          this.$message({
            type: 'warning',
            message: '用户名已存在',
          })
          return
        } else {
          axios.post('http://localhost:8888/day07/user/registered', this.user)
            .then(res => {
              this.$message({
                type: 'success',
                message: '注册成功',
              })
              this.user = {}
              this.$router.replace('/login')
            })
            .catch(err => {
              this.$message({
                type: 'error',
                message: '注册失败',
              })
              console.log(err);
            })
        }
      }).catch(err => {
        console.log(err)
      })

    },

    jumpToLogin () {
      this.$router.push('/login')
    }
  },

}
</script>


<style scoped>
.regForm {
  filter: alpha(Opacity=85);
  -moz-opacity: 0.85;
  opacity: 0.85;
  background: rgba(0, 0, 0, 0) url("../../../assets/img/logo1.jpeg") no-repeat
    scroll 100% 100%;
  background-size: auto;
  background-size: cover;
  width: 100%;
  height: 100vh;
}

.add-box {
  padding-left: 20px;
  padding-top: 20px;
  display: inline-block;
}

.regDiv {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 600px;
}

.logoDiv {
  text-align: center;
}

.logoDiv img {
  width: 300px;
  border-radius: 50%;
  height: 150px;
}
</style>