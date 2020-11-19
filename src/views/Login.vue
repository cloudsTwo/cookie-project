<template>
  <div id="login">
    <top-bar currentModel="login" activeStyle="topBar" class="topbar"/>
    <login-page class="login-page" />
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import LoginPage from '@/components/comment/login/LoginPage'

export default {
  name:'Login',
  components:{
    TopBar,
    LoginPage
  }
}
</script>

<style scoped>
#login{
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