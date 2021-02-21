<template>
  <div id="app">
    
    <router-view/>
  </div>
</template>

<script>
import jwt from 'jwt-decode';
import {findOneUserById} from './network/user'

export default {
  name:'App',
  created(){   /*在根组件进行判断，否则刷新就没了*/
    this.initData()
  },
  methods: {
    // 有token时初始化数据
    initData(){
      if (localStorage.eleToken){
        const decoded = jwt(localStorage.eleToken);
        let _this = this;

        
        findOneUserById(decoded.aud).then(res => {
          console.log(res)
          // token已过期
          if(res.data.code == 401){
            /*存储至vuex*/
            localStorage.removeItem('eleToken')
            _this.$store.dispatch("setLogin",false)
            
          }else{
            _this.$store.dispatch("setUser",res.data)
            /*存储至vuex*/
            _this.$store.dispatch("setLogin",!this.isEmpty(decoded))
          }
        }).catch(err=>{
          console.log(err)
        })
      }
    },

    isEmpty(value){
        return(
            value ===undefined || value ===null ||
            (typeof  value === "object" && Object.keys(value).length ===0) ||
            (typeof value ==="string" && value.trim().length ===0)
        );
    }
  }
}
</script>

<style scoped>

</style>
