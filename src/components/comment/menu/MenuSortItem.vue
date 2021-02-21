<template>
  <div id="menuSortItem">
    <food-item v-for="(item,index) in menus" :mid="item.mid" :key="index">
      <img slot="img" :src="item.desImg" alt="picture">
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
    menus:{
      type:Array,
      default(){
        return []
      }
    }
  },
  watch:{
    menus(newVal){
      this.menus = newVal
    }
  },
  mounted(){
    
  },
  data(){
    return {

    }
  }
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

img{
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