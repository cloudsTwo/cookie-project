<template>
  <div id="home">
    <top-bar currentModel="home" activeStyle="topBar" class="topbar"/>
    <top-swiper/>

    <day-menu-recom />
    <item2 />
    <item2 />

    <img class="btimg" src="~@/assets/img/bottom.png">
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import Swiper from '@/components/comment/home/Swiper'
import TopSwiper from '@/components/comment/home/TopSwiper'
import DayMenuRecom from '@/components/comment/home/DayMenuRecom'
import Item2 from '@/components/comment/home/Item2'

export default {
  name:'Home',
  components:{
    TopBar,
    Swiper,
    TopSwiper,
    DayMenuRecom,
    Item2,
  },
  data(){
    return{
    }
  },
  methods:{
  }
}
</script>

<style scoped>
#home{
  position: relative;
}

.topbar{
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 1;
}

.btimg{
  padding:10px;
  width:99%;
}
</style>