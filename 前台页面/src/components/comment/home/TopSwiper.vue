<template>
  <div id="swiper">
    <div class="title">
      <p>小食间<br>食幸福，享生活</p>
    </div>

    <div id="topSwiper">
    
      <!-- 排列四个不同背景主题的div,通过动画进行轮播显示 -->
      <div class="swipers swiper-slide1">
        <span class="tips">了解“南瓜腰豆糕”做法</span>
      </div>

      <div class="swipers swiper-slide2">
        <span class="tips">了解“马蹄银耳羹”做法</span>
      </div>

      <div class="swipers swiper-slide3">
        <span class="tips">了解“梭子蟹白菜汤”做法</span>
      </div>

      <div class="swipers swiper-slide4">
        <span class="tips">了解“提拉米苏”做法</span>
      </div>
    </div>

    <div class="progress">
      <el-progress :percentage="(currentIndex+1)*20" :stroke-width=2 color="blue" :show-text="false"></el-progress>
    </div>

    <div class="front" @click="toFront">

    </div>

    <div class="next" @click="toNext">

    </div>

    <div class="search">
      <img src="~@/assets/img/search.svg" alt="search">
      <input type="text" v-model="searchText" placeholder="搜索食谱/食材" autofocus>
      <button :disabled="!searchText" @click="search">搜索</button>
    </div>
  </div>
</template>

<script>
export default {
  name:'TopSwiper',
  data(){
    return {
      searchText:'',
      currentIndex:0, // 当前轮播的图片位置
      timer:null, // 定时器
    }
  },
  mounted(){
    this.startSwiper()
  },
  methods:{
    startSwiper(){
      // 开始轮播
      let sdiv = document.querySelector('#topSwiper')

      this.timer = setInterval(() => {

        if(this.currentIndex > 3){
          this.currentIndex = 0;
          sdiv.style.transform = 'translateX(0)'
        }else{
          // transform: translateX(0);
          if(this.currentIndex === 0){
            sdiv.style.transform = 'translateX(0)'
          }else{
            sdiv.style.transform = 'translateX(-'+this.currentIndex + '00%)'
          }

          this.currentIndex ++;
        }
      },2000)
    },

    // 前一张轮播图
    toFront(){
      clearInterval(this.timer)
      let sdiv = document.querySelector('#topSwiper')

      if(this.currentIndex > 0){
        // transform: translateX(0);
        this.currentIndex --;
        sdiv.style.transform = 'translateX(-'+ this.currentIndex + '00%)'
      }
      this.startSwiper()
      // else{
      //   this.currentIndex = 3;
      //   sdiv.style.transform = 'translateX(-'+ this.currentIndex + '00%)'
      // }
    },

    // 下一个轮播图
    toNext(){
      let sdiv = document.querySelector('#topSwiper')
      clearInterval(this.timer)

      if(this.currentIndex < 3){
        // transform: translateX(0);
        this.currentIndex ++;
        sdiv.style.transform = 'translateX(-'+ this.currentIndex + '00%)'
      }

      this.startSwiper()
      // else{
      //   this.currentIndex = 0;
      //   sdiv.style.transform = 'translateX(0)'
      // }
    },

    // 搜索
    search(){
      this.$router.push('/menu')
      
      setTimeout(()=>{
        this.$bus.$emit('search',this.searchText)
      },300)
    }
  }
}
</script>

<style scoped>
  #swiper{
    width:100%;
    min-width:1024px;
    overflow: hidden;
    position: relative;
  }

  #swiper .title{
    position: absolute;
    left: 150px;
    top:120px;
    opacity: 1;

    color:white;
    z-index: 1;
    font-size: 65px;
  }

  #topSwiper{
    z-index: -10;
    width:100%;
    display: flex;
    flex-wrap: nowrap;
    /* animation: move 16s linear infinite;
    animation-timing-function: steps(1, end); */
    /* transition:all 4s; */
  }

  #topSwiper .swipers{
    /* animation:tran 4s linear infinite; */
    width:100%;
    height:740px;
    background-repeat: no-repeat;
    background-size: cover;
    flex-shrink: 0;
    opacity: 1;

    position: relative;
    overflow: hidden;
  }

   #topSwiper .swipers:hover{
     animation-play-state: paused;
   }

  #topSwiper .swipers .tips{
    color:rgb(245, 230, 230);
    font-size: 30px;
    
    position: absolute;
    left: 150px;
    top:380px;
    opacity: 1;

  }

  #topSwiper .swiper-slide1{
    animation-delay: 0s;
    background-image: url(https://app-file.beitaichufang.com/img/H5/web/banner/banner20.jpg);
  }

  #topSwiper .swiper-slide2{
    background-image: url(https://app-file.beitaichufang.com/img/H5/web/banner/banner21.jpg);
  }

  #topSwiper .swiper-slide3{
    background-image: url(https://app-file.beitaichufang.com/img/H5/web/banner/banner22.jpg);
  }

  #topSwiper .swiper-slide4{
    background-image: url(https://app-file.beitaichufang.com/img/H5/web/banner/banner23.jpg);
  }

  #swiper .progress{
    position: absolute;
    bottom:40px;
    left:50%;
    transform: translateX(-50%);
    width:400px;
  }

  .front,.next{
    position: absolute;
    bottom:50%;

    opacity: 0.6;
    width:50px;
    height:50px;
    transform: rotate(45deg)
  }

  #swiper .front{
    left:30px;
    border-bottom: 4px solid rgb(92, 94, 223);
    border-left: 4px solid rgb(92, 94, 223);
  }

  #swiper .next{
    right:30px;
    border-right: 4px solid rgb(228, 161, 228);
    border-top: 4px solid rgb(228, 161, 228);
  }

  .next:hover,.front:hover{
    opacity: 1;
  }

  #swiper .search{
    
    position: absolute;
    bottom:150px;
    left:150px;

    width:70%;
    height:60px;
    border-radius: 10px;

    display: flex;
    justify-content: space-around;
    align-items: center;

    background: white;
  }

  #swiper .search img{
    width:40px;
    height:40px;
  }

  #swiper .search input{
    z-index: 9;

    border:1px solid white;
    width:80%;
    height:40px;
    background: white;
    display: inline-block;
    box-sizing: border-box;
  }

  #swiper .search input:focus{
    outline:none;
  }

  #swiper .search button{
    border:none;
    height:65%;
    width:75px;
    color:white;
    border-radius:5px;
    background: rgb(21, 91, 241);
  }

  

  @keyframes move{
    0%{
      transform: translateX(0);
    }

    25%{
      transform: translateX(-100%);
    }

    50%{
      transform: translateX(-200%);
    }

    75%{
      transform: translateX(-300%);
    }

  }

  @keyframes tran{
    0% {
      opacity: 0.5;
    }

    50% {
      opacity: 1;
    }

    100%{
      opacity: 0.8;
    }

  }

  @keyframes fontc{
    from{
      opacity: 1;
    }

    to{
      opacity: 1;
    }
  }

</style>