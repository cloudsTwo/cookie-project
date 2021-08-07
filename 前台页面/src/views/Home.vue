<template>
  <div id="home">
    <top-bar currentModel="home" :activeStyle="topStatus" class="topbar"/>
    <top-swiper />

    <day-menu-recom />

    <div class="bottom"></div>
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
      topStatus:'topBar',
    }
  },
  mounted(){
    window.onscroll = () => {
      if(window.pageYOffset > 400){
        this.topStatus = 'active'
      }else{
        this.topStatus = 'topBar'
      }
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
  z-index: 999;
}


.bottom{
  width:100%;
  height:100px;
  background: black;
}
</style>