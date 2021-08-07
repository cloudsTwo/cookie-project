<template>
  <div id="editMenu">
    <top-bar currentModel="menu" activeStyle="active" class="topbar"/>
    <edit-menu-children />
    
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import EditMenuChildren from '@/components/comment/menuOperation/EditMenuChildren'

export default {
  name:'editMenu',
  components:{
    TopBar,
    EditMenuChildren
  }
}
</script>

<style scoped>
#editMenu{
  position: relative;
}

.topbar{
  position: fixed;
  background: white;
  top:0;
  left:0;
  right:0;
  z-index: 1;
}
</style>