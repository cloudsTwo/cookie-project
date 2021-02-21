<template>
  <div id="ownInfoPage">
    <div class="common-info">
      <div class="base">
        <el-avatar class="avatar" fit="contain" size="large" :src="user.avatar" ></el-avatar>
      </div>

      <div>
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
import {findMenuByUser,findCollectByUser,findMenuById} from '@/network/menu.js'

import OwnSortItem from '@/components/comment/own/OwnSortItem'

import MenuSortItem from '@/components/comment/menu/MenuSortItem'


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
    }
  },
  created(){
    this.user = this.$store.state.user

    this.loadPostMenus();
    this.loadCollectMenus();
  },
  methods:{

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
    }
  }
}
</script>

<style scoped>
  #ownInfoPage{
    padding:120px;
  }

  .common-info{
    padding:10px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    background: url('https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604331144720&di=2a77a3a580ba97ce06e4cb83acffb7cd&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3Db8b60a13d0a20cf44690fed746084b0c%2F803edb39b6003af3dbb351f4352ac65c1138b603.jpg');
  }

  .common-info div{
    padding-right:30px;
  }

  .base .avatar{
    display: flex;
    justify-content: center;
    padding:0;
    border:5px solid rgb(255, 153, 0);
    height:150px;
    width:150px;
  }

  .common-info p{
    padding:10px;
    font-size: 25px;
    font-weight: 800;
    color:rgb(218, 90, 16);
  }

  .common-info span{
    color:white;
    font-size: 19px;
    padding:10px;
  }

  .pane span{
    color:purple;
  }
</style>