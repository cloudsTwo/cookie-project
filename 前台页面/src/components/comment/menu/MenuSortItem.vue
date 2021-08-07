<template>
  <div id="menuSortItem" v-if="menus">
    <food-item v-for="(item,index) in menus" :mid="item.mid" :key="index">
      <el-image class="img" slot="img" :src="item.desImg" fit="contain"></el-image>
      <h3 slot="name">{{item.mname}}</h3>
      <p slot="description">{{item.description}}</p>
    </food-item>

  </div>
</template>

<script>
import FoodItem from '../../common/FoodItem'

export default {
  name:'MenuSortItem',
  components:{
    FoodItem
  },
  props:{
    newMenus:{
      type:Array,
      default(){
        return []
      }
    }
  },
  data(){
    return {
      menus:[]
    }
  },
  watch:{
    newMenus(newVal){
      this.menus = JSON.parse(JSON.stringify(newVal))
      
      this.menus?.map((item,index) => {
        let imgUrl = this.menus[index].desImg;
        // 网络图片还是本地图片
        this.menus[index].desImg = imgUrl.indexOf('http') === -1 ? require('../../../assets/img/menu/' + imgUrl) : imgUrl
      })
    }
  },
  created(){
  },
  mounted(){
    
  },
}
</script>

<style scoped>
#menuSortItem{
  margin:0 auto;
  display: flex;
  flex: 1;
  align-content: center;
  flex-wrap: wrap;
}

.img{
  width:220px;
  height:160px;
}

p{
  width:13rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>