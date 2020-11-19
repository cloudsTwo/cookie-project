<template>
  <div id="foodMade">
    <top-bar currentModel="menu" activeStyle="active" class="topbar"/>
    <made-steps :mid="mid" />
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import MadeSteps from '@/components/comment/steps/MadeSteps'

export default {
  name:'FoodMade',
  components:{
    TopBar,
    MadeSteps,
  },
  data(){
    return {
      mid:parseInt(this.$route.query.mid)
    }
  },
  mounted(){

  }
}
</script>

<style scoped>
.topbar{
  background: white;
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 1;
}
</style>