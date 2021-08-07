<template>
    <div id="good">
        <good-top-find @findGood="findGood"></good-top-find>
        <good-list @getAll="init" :currentMode="currentListMode" v-if="goodList.length" :content="goodList"></good-list>
        <div class="no-content" v-else>当前未查找到商品</div>
    </div>
</template>

<script>
import GoodTopFind from './goodChildren/GoodTopFind'
import GoodList from './goodChildren/GoodList'

import {findGoodInfoById,findAllGoodInfo} from '@/network/order'

export default {
    name:'Order',
    components:{
        GoodTopFind,
        GoodList
    },
    data(){
        return{
          currentListMode:'全部商品',
          goodList:[]
        }
    },
    mounted(){
        this.init()
    },
    methods:{
        async init(){
            this.currentListMode = '全部商品'
            this.goodList = (await findAllGoodInfo()).data
            console.log(this.goodList)
        },
        async findGood(goodId){
            let reg = /\s+/
            if(reg.test(goodId)){
                this.$router.go(0)
            }else{
                findGoodInfoById(goodId).then(res => {
                    this.goodList = []
                    this.goodList[0] = res.data
                    this.currentListMode = '查找商品ID号:' + goodId
                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style  scoped>
#good{
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