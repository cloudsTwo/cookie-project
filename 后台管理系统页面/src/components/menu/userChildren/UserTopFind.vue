<template>
  <div id="userTopFind">
    <top-find>
      <div slot="find-input">
        用户名:<input type="text" v-model="findName" placeholder="请输入用户名" />
      </div>

      <el-button size="mini" type="primary" slot="btn" :disabled="!findName" @click="find" >查询</el-button>
    </top-find>
  </div>
</template>

<script>
import TopFind from '@/components/children/TopFind'

export default {
  name:'UserTopFind',
  data(){
      return {
          findName:''
      }
  },
  components:{
    TopFind
  },
  methods:{
      find(){
  
        this.$emit('findUser', this.findName)
      }
  }

}
</script>

<style scoped>
  input{
    padding: 0 5px;
  }
</style>