<template>
  <div id="orderTopFind">
    <top-find>
      <div slot="find-input">
        订单号:<input type="text" v-model="findOrder" placeholder="请输入订单号" />
      </div>

      <el-button type="primary" size="mini" :disabled="!findOrder" slot="btn" @click="find" >查询</el-button>
    </top-find>
  </div>
</template>

<script>
import TopFind from '@/components/children/TopFind'

export default {
  name:'OrderTopFind',
  data(){
      return {
          findOrder:''
      }
  },
  components:{
    TopFind
  },
  methods:{
      find(){
        this.$emit('findOrder', this.findOrder)
      }
  }

}
</script>

<style scoped>
  input{
    padding: 0 5px;
  }
</style>