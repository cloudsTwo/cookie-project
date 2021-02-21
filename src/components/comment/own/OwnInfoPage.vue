<template>
  <div id="ownInfoPage">
    <div class="common-info">
      <div class="base">
        <img :src="user.avatar" alt="头像">
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

      <el-tabs type="border-card" class="pane">
        <el-tab-pane>
          <span slot="label"><i class="el-icon-date"></i> 我的发布</span>
          <own-sort-item :menus="putMenus" />
        </el-tab-pane>
        <el-tab-pane label="我的收藏">
          <span slot="label"><i class="el-icon-collection-tag"></i> 我的收藏</span>
          <menu-sort-item :menus="collectMenus" />
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

    findMenuByUser(this.user.uid).then(res => {
      this.putMenus = res
    }).catch(err => {
      console.log(err)
    })


    findCollectByUser(this.user.uid).then(res => {
      res.forEach((item,index) => {
        findMenuById(item.mid).then(res=> {
          this.collectMenus.push(res)
        }).catch(err => {
          console.log(err)
        })
      })
    }).catch(err => {
      console.log(err)
    })
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

  .base img{
    height:150px;
    width:150px;
    border-radius: 50%;
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