<template>
    <div id='check'>
        <check-top-find @findMenu="findMenu" />
        <check-list @getAll="getAllMenu" v-if="menus.length" :currentMode="currentListMode" :menus="menus" />
        <div class="no-content" v-else>
          当前无待审核食谱
        </div>
    </div>
</template>

<script>
import CheckTopFind from './checkChildren/CheckTopFind'
import CheckList from './checkChildren/CheckList'
import {findCheckedMenu,findCheckedMenuByName} from '@/network/menu'

export default {
    name:'Check',
    data(){
        return {
            currentListMode:'待审核食谱',
            menus:[]
        }
    },
    created(){
        // 获取所有文章
        this.getAllMenu()
    },
    components:{
        CheckTopFind,
        CheckList,
    },
    methods:{
        async getAllMenu(){
            this.currentListMode = '待审核食谱'
            let res = await findCheckedMenu(0);
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
              this.currentListMode = '查询食谱：' + title
              let res = await findCheckedMenuByName(0,title)
              this.menus = res.data
            }

        },
    }
}
</script>

<style  scoped>
    #check {
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