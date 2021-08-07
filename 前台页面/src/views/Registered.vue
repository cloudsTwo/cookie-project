<template>
  <div id="registered">
    <top-bar currentModel="login" activeStyle="topBar" class="topbar"/>
    <reg-page class="login-page" />
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import RegPage from '@/components/comment/login/RegPage'

export default {
  name:'Registered',
  components:{
    TopBar,
    RegPage
  }
}
</script>

<style scoped>
#registered{
  position: relative;
}

.topbar{
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 10;
}
</style>