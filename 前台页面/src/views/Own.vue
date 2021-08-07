<template>
  <div id="own" v-if="$store.state.isLogin">
    <top-bar currentModel="own" activeStyle="active" class="topbar"/>
    <own-info-page  v-if="$store.state.user.uid"/>
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'

import OwnInfoPage from '@/components/comment/own/OwnInfoPage'

export default {
  name:'Own',
  components:{
    TopBar,

    OwnInfoPage,
  },
  beforeCreate(){
    if(!this.$store.state.isLogin){
      this.$router.replace('/login')
    }
  },
  created(){
  }
}
</script>

<style scoped>
.topbar{
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 1;
  background: white;
}
</style>