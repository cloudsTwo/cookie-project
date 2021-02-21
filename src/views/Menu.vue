<template>
  <div id="menu">
    <top-bar activeStyle="active" currentModel="menu" class="topbar"/>
    <menu-sort class="menu-sort" />

  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import MenuSort from '@/components/comment/menu/MenuSort'
import MenuSortItem from '@/components/comment/menu/MenuSortItem'

export default {
  name:'Menu',
  components:{
    TopBar,
    MenuSort,
    MenuSortItem
  },
  data(){
    return{
    }
  }
}
</script>

<style scoped>
#menu{
  position: relative;
}

.topbar{
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 10;
  background: white;
  opacity: 1;
}

.menu-sort{
  position: absolute;
  top:130px;
}
</style>