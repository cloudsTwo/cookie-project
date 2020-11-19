<template>
  <div id="addMenu">
    <top-bar currentModel="add-menu" activeStyle="active" class="topbar"/>
    <add-menu-children />
    
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'

import AddMenuChildren from '@/components/comment/menuOperation/AddMenuChildren'

export default {
  name:'addMenu',
  components:{
    TopBar,
    AddMenuChildren
  }
}
</script>

<style scoped>
#addMenu{
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