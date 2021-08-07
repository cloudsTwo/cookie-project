<template>
  <div id="foodItem"  @click="toMadePage">
    <div class="img-box">
      <div class="img">
        <slot name="img"></slot>
      </div>
      <slot name="cover"></slot>
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

.img-box{
  position: relative;
  overflow: hidden;
}

.cover{
  position: absolute;
  top:0;
  left:0;
  right:0;
  bottom: 0;
  color:white;
  font-size: 20px;
  letter-spacing: 3px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.img-box .img:hover{
  transition: all 1s;
  transform: scale(1.1);
}
</style>