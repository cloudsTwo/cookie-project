<template>
    <div id="user">
        <user-top-find @findUser="findUser"></user-top-find>
        <user-list @getAll="init" :currentMode="currentListMode" v-if="user.length" :content="user"></user-list>
        <div class="no-content" v-else>当前未查找到用户数据</div>
    </div>
</template>

<script>
import UserTopFind from './userChildren/UserTopFind'
import UserList from './userChildren/UserList'

import {findAllUser, findOneUserByName} from '@/network/user'

export default {
    name:'User',
    components:{
        UserTopFind,
        UserList
    },
    data(){
        return{
            currentListMode:'全部用户',
            user:[]
        }
    },
    mounted(){
        this.init()
    },
    methods:{
        async init(){
            this.currentListMode = '全部用户'
            this.user = (await findAllUser()).data
        },
        async findUser(name){
            let reg = /\s+/
            if(reg.test(name)){
                this.$router.go(0)
            }else{
                findOneUserByName(name).then(res => {
                    this.user = []
                    this.user[0] = res.data
                    this.currentListMode = '查找用户：' + res.data.name
                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style  scoped>
#user{
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