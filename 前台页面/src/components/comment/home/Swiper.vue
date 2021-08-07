<template>
  <div id="swiper">
    <img :src="imgs[currentImg]" alt="这是图片">
  </div>
</template>

<script>
export default {
  name:'Swiper',
  data(){
    return {
      currentImg:0,
      imgs:[
        'https://app-file.beitaichufang.com/img/H5/web/banner/banner20.jpg',
        'https://app-file.beitaichufang.com/img/H5/web/banner/banner21.jpg',
        'https://app-file.beitaichufang.com/img/H5/web/banner/banner22.jpg',
        'https://app-file.beitaichufang.com/img/H5/web/banner/banner23.jpg'
      ]
    }
  },
  mounted(){

    // 轮播图
    setInterval(() => {
      if(this.currentImg < this.imgs.length -1){
        this.currentImg ++
      }else{
        this.currentImg = 0;
      }
    },1000)
  }
}
</script>

<style scoped>
#swiper{
  width:100%;
}

img{
  width:100%;
}
</style>