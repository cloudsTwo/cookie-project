<template>
    <div id="order">
        <order-top-find @findOrder="findOrder"></order-top-find>
        <order-list @getAll="init" :currentMode="currentListMode" v-if="orderList.length" :content="orderList"></order-list>
        <div class="no-content" v-else>当前未查找到订单</div>
    </div>
</template>

<script>
import OrderTopFind from './orderChildren/OrderTopFind'
import OrderList from './orderChildren/OrderList'

import {findOrderByOrderNum,findAllOrder} from '@/network/order'

export default {
    name:'Order',
    components:{
        OrderTopFind,
        OrderList
    },
    data(){
        return{
          currentListMode:'全部订单',
          orderList:[]
        }
    },
    mounted(){
        this.init()
    },
    methods:{
        async init(){
            this.currentListMode = '全部订单'
            this.orderList = (await findAllOrder()).data
        },
        async findOrder(orderNum){
            let reg = /\s+/
            if(reg.test(orderNum)){
                this.$router.go(0)
            }else{
                findOrderByOrderNum(orderNum).then(res => {
                    this.orderList = []
                    this.orderList[0] = res.data
                    this.currentListMode = '查找订单：' + orderNum
                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style  scoped>
#order{
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