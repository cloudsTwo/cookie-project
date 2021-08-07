<template>
  <div id="artTopFind">
    <top-find>
      <div slot="find-input">
        食谱名:<input type="text" v-model="findTitle" placeholder="请输入食谱名" />
      </div>

      <el-button slot="btn" type="primary" size="mini" :disabled="!findTitle" @click="find" >查询</el-button>
    </top-find>
  </div>
</template>

<script>
import TopFind from '@/components/children/TopFind'

export default {
  name:'ArtTopFind',
  data(){
      return {
          findTitle:''
      }
  },
  components:{
    TopFind
  },
  methods:{
      find(){
        this.$emit('findMenu', this.findTitle)
      }
  }

}
</script>

<style scoped>
  input{
    padding: 0 10px;
  }
</style>