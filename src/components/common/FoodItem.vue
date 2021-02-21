<template>
  <div id="foodItem">
    <div class="img" @click="toMadePage">
      <slot name="img"></slot>
    </div>

    <div class="title"><slot name="name"></slot></div>
    <div class="des"><slot name="description"></slot></div>
  </div>
</template>

<script>
export default {
  name:'FoodItem',
  props:{
    mid:Number
  },
  data(){
    return{
      
    }
  },
  methods:{
    toMadePage(){

      this.$router.push({
        path:'/foodMade',
        query:{
          mid:this.mid
        }
      })
    }
  }
}
</script>

<style scoped>
#foodItem{
  margin:8px;
}

h1,h2,h3,h4,h5{
  margin-block-start: 10px;
  margin-block-end: 0;
}

p{
  margin-block-start: 0;
  margin-block-end: 2rem;
}

.des{
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.img:hover{
  transform: scale(1.2);
}
</style>