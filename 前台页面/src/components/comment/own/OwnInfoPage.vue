<template>
  <div id="ownInfoPage">
    <div class="common-info">
      <div class="base">
        <el-avatar shape="circle" class="avatar" fit="cover" size="large" :src="avatarUrl" ></el-avatar>
      </div>

      <el-popover
        placement="right"
        width="200"
        trigger="click">
        <div class="setting-select" @click="showAvatarTab = true">更换头像</div>
        <div class="setting-select" @click="showSettingTab = true">设置个人信息</div>
        <div slot="reference" class="setting">设置</div>
      </el-popover>
      
      <el-dialog title="修改用户" :visible.sync="showSettingTab">
        <el-form :model="changeUser">
          <el-form-item label="用户名" label-width="150">
            <el-input v-model="changeUser.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户密码" label-width="150">
            <el-input type="password" v-model="changeUser.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" label-width="150">
            <el-input type="phoneNumber" v-model="changeUser.phoneNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="收货地址" label-width="150">
            <el-input type="address" v-model="changeUser.address" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showSettingTab = false">取 消</el-button>
          <el-button type="primary" @click="postChange" >确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog width="300px" custom-class="change-avatar" title="更换头像" :visible.sync="showAvatarTab">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8888/day07/user/fileUpload"
          :show-file-list="false"
          :limit="1"
          :headers = "fileHeaders"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">

          <img v-if="avatarName" :src="newAvatar" class="new-avatar">
          <i v-else class="el-icon-plus el-upload avatar-uploader-icon"></i>
        </el-upload>

        <div slot="footer" class="dialog-footer">
          <el-button @click="showAvatarTab = false">取 消</el-button>
          <el-button type="primary" @click="postAvatar" >确 定</el-button>
        </div>
      </el-dialog>

      <div class="count-info">
        <p>{{user.name}}</p>
        <span>粉丝 {{user.fans}}</span>
        <span>关注 {{user.focus}}</span>
        <span>获赞 {{user.getLike}}</span>
      </div>
    </div>

    <el-divider></el-divider>

    <div>

      <el-tabs type="border-card" class="pane" @tab-click="loadCollectMenus">
        <el-tab-pane>
          <span slot="label"><i class="el-icon-date"></i> 我的发布</span>
          <own-sort-item :menus="putMenus" />
        </el-tab-pane>
        <el-tab-pane label="我的收藏">
          <span slot="label"><i class="el-icon-collection-tag"></i> 我的收藏</span>
          <menu-sort-item :newMenus="collectMenus" />
        </el-tab-pane>
      </el-tabs>
      
    </div>
  </div>
</template>

<script>
import {findMenuByUser,findCollectByUser} from '@/network/menu.js'

import OwnSortItem from '@/components/comment/own/OwnSortItem'

import MenuSortItem from '@/components/comment/menu/MenuSortItem'

import axios from 'axios'
import { changeAvatar } from '../../../network/user'


export default {
  name:'OwnInfoPage',
  components:{
    OwnSortItem,
    MenuSortItem
  },
  data(){
    return {
      user:{},
      putMenus:[],
      collectMenus:[],
      showSettingTab: false,  // 展示设置面板
      changeUser: {}, // 复制的用户
      showAvatarTab: false, // 头像设置
      avatarName: '', // 头像名
      fileHeaders:{ // 文件头
        Authorization:localStorage.eleToken
      }
    }
  },
  computed:{
    avatarUrl(){
      return this.user.avatar.indexOf('http') === -1 ? 
        require("../../../assets/img/user/" + this.user.avatar) : this.user.avatar
    },
    newAvatar(){
      return require("../../../assets/img/user/" + this.avatarName)
    }
  },
  created(){
    this.user = this.$store.state.user
    this.changeUser = JSON.parse(JSON.stringify(this.user)) // 复制品，用于修改时使用

    this.loadPostMenus();
    this.loadCollectMenus();
  },
  methods:{
    // 步骤图
    handleAvatarSuccess(res, file) {
      // 
      this.avatarName = file.name
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    loadPostMenus(){
      // 发布的食谱
      findMenuByUser(this.user.uid).then(res => {
        this.putMenus = res.data
      }).catch(err => {
        console.log(err)
      })
    },

    loadCollectMenus(){
      // 收藏的食谱
      findCollectByUser(this.user.uid).then(res => {
        
        // 如果有数据
        if(res.data?.length){
          this.collectMenus = res.data;
        }
      }).catch(err => {
        console.log(err)
      })
    },

    postChange(){
      axios({
        method:'post',
        url:'http://localhost:8888/day07/user/changeUser',
        data:this.changeUser,
        headers:{
          Authorization:localStorage.eleToken,
          timeout:5000,
          'Content-Type': 'application/json',
        }
      }).then(res => {
        this.showSettingTab = false
         this.$message({
          message: '修改成功！',
          type: 'success'
        });
        this.user = this.changeUser
        this.$store.commit('SET_USER', this.changeUser)
      }).catch(err => {
         this.$message({
          message: '修改失败！',
          type: 'warn'
        });
        console.log(err)
        return
      })
    },
    postAvatar(){
      changeAvatar(this.user.uid, this.avatarName).then(res => {
        this.user.avatar = this.avatarName
        this.showAvatarTab = false
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
  #ownInfoPage{
    padding:120px;
  }

  .change-avatar , .change-avatar .avatar-uploader{
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #b85178;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  
  .avatar-uploader .el-upload:hover {
    border-color: #f32e14;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #eeab74;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .new-avatar {
    width: 150px;
    height: 150px;
    display: block;
  }

  .common-info{
    position: relative;
    padding:10px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    background: url('https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604331144720&di=2a77a3a580ba97ce06e4cb83acffb7cd&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3Db8b60a13d0a20cf44690fed746084b0c%2F803edb39b6003af3dbb351f4352ac65c1138b603.jpg');
  }

  .setting{
    position: absolute;
    text-align: center;
    padding: 0 5px !important;
    font-size: 16px;
    color: rgb(0, 89, 255);
    border-bottom: 1px solid rgb(0, 89, 255);
    top: 130px;
    left: 140px;
    cursor: pointer;
  }

  .setting-select{
    padding: 10px 5px;
    cursor: pointer;
  }

  .setting-select:hover{
    background-color: rgb(227, 247, 252);
  }

  .common-info div{
    padding-right:20px;
  }

  .base .avatar{
    display: flex;
    justify-content: center;
    padding:0;
    border:5px solid rgb(255, 153, 0);
    height:150px;
    width:150px;
  }

  .common-info .count-info{
    display: flex;
    align-items: center;
  }

  .common-info p{
    padding:0 10px;
    font-size: 25px;
    font-weight: 800;
    color:rgb(218, 90, 16);
  }

  .common-info span{
    font-size: 15px;
    padding:0 10px;
  }

  .pane span{
    color:purple;
  }
</style>