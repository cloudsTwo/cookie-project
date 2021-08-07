<template>
    <div id='art'>
        <art-top-find @findMenu="findMenu" />
        <art-list @getAll="getAllMenu" v-if="menus.length" :currentMode="currentListMode" :menus="menus" />
        <div class="no-content" v-else>
          当前未查找到食谱
        </div>
    </div>
</template>

<script>
import ArtTopFind from './artChildren/ArtTopFind'
import ArtList from './artChildren/ArtList'
import {findAllMenu,findMenuByName} from '@/network/menu'

export default {
    name:'AllMenu',
    data(){
        return {
            currentListMode:'全部食谱',
            menus:[]
        }
    },
    created(){
        // 获取所有食谱
        this.getAllMenu()
    },
    components:{
        ArtTopFind,
        ArtList,
    },
    methods:{
        async getAllMenu(){
            this.currentListMode = '全部食谱'
            let res = await findAllMenu();
            this.menus = res.data
            // 登录已过期
            if(!Array.isArray(this.menus)){
                this.$router.replace('/login')
            }
        },

        async findMenu(title){
            let reg = /\s+/
            if(reg.test(title)){
                this.$router.go(0)
            }else{
                this.currentListMode = '查找食谱：' + title
                let res = await findMenuByName(title)
                this.menus = res.data
            }

        },
    }
}
</script>

<style  scoped>
    #art{
        width:80%;
        color:grey;
    }

    .no-content{
      width:100%;
      height:300px;
      font-size: 25px;
      color:grey;
      display: flex;
      align-items: center;
      justify-content: center;
    }
</style>