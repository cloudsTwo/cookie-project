<template>
  <div id="goodTopFind">
    <top-find>
      <div slot="find-input">
        商品ID号:<input type="text" v-model="goodId" placeholder="请输入订单ID号" />
      </div>

      <el-button type="primary" size="mini" :disabled="!goodId" slot="btn" @click="find" >查询</el-button>
    </top-find>
  </div>
</template>

<script>
import TopFind from '@/components/children/TopFind'

export default {
  name:'GoodTopFind',
  data(){
      return {
          goodId:1
      }
  },
  components:{
    TopFind
  },
  methods:{
      find(){
        this.$emit('findGood', this.goodId)
      }
  }

}
</script>

<style scoped>
  input{
    padding: 0 5px;
  }
</style>