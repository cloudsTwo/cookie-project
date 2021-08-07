<template>
  <div id="foodMade">
    <top-bar currentModel="menu" activeStyle="active" class="topbar"/>
    <made-steps @editMenu="editMenu" :menuInfo="menuInfo" v-if="$store.state.user.uid && !isEdit" />
    <edit-menu :menuInfo="menuInfo" v-else-if="$store.state.user.uid"></edit-menu>
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import MadeSteps from '@/components/comment/steps/MadeSteps'
import EditMenu from '@/components/comment/menuOperation/EditMenuChildren'

import {findMenuById} from '@/network/menu.js'

export default {
  name:'FoodMade',
  components:{
    TopBar,
    MadeSteps,
    EditMenu
  },
  data(){
    return {
      mid:parseInt(this.$route.query.mid),
      user:this.$store.state.user,
      menuInfo:{},
      isEdit:false, // 显示与编辑的页面切换
    }
  },
  mounted(){
    this.loadPageData()
  },
  methods:{
    async loadPageData(){
      this.menuInfo = (await findMenuById(this.mid)).data
      console.log('...',this.menuInfo)
    },

    editMenu(){
      this.isEdit = true
    }
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