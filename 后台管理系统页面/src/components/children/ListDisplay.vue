<template>
  <div id="listDisplay">
    
    <div class="list-top">
      <slot name="title"></slot>
      <slot name="btn"></slot>
    </div>

    <div class="list-content">
      <slot name="table"></slot>
    </div>

    <div class="page-jump">
      <button  @click="toFirstPage" :disabled="firstPage" :style="{'background':firstPage?' rgb(172, 189, 221)':'rgb(73, 156, 233)'}">首页</button>
      <button  @click="toForwardPage" :disabled="firstPage" :style="{'background':firstPage?' rgb(172, 189, 221)':'rgb(73, 156, 233)'}">前一页</button>
      <button  @click="toNextPage" :disabled="finalPage" :style="{'background':finalPage?' rgb(172, 189, 221)':'rgb(73, 156, 233)'}">后一页</button>
      <button  @click="toLastPage" :disabled="finalPage" :style="{'background':finalPage?' rgb(172, 189, 221)':'rgb(73, 156, 233)'}">末页</button>

      <input type="text" v-model="jumpPage" placeholder="跳转" />
      <button @click="jumpTo">跳转</button>

      <span>{{currentPage}}/{{totalPage}}页</span>
      <span>共{{totalNum}}条</span>
    </div>
  </div>
</template>

<script>
export default {
  name:'ListDisplay',
 data(){
    return {
      
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:4, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面

      finalPage:false, // 尾页
      firstPage:true, // 首页

      content: this.datas,  // 数据
    }
  },
  watch:{
    datas:function(newV, oldV){
      this.content = newV
      this.init()
    },
    deep: true,
  },
  props:{
    datas:{
      type:Array,
      default(){
        return []
      }
    }, // 所有文章
  },
  created(){ 
    this.init()
  },
  methods:{
    init(){
      this.totalNum = this.content.length
      this.totalPage = parseInt(this.totalNum%this.onePageNum === 0? this.totalNum / this.onePageNum : this.totalNum / this.onePageNum + 1)

      if(this.totalNum === 0){
        this.firstPage = this.finalPage = true
        return
      }else if (this.totalPage === 1){
        this.firstPage = this.finalPage = true
        this.currentContent = this.content 
        return
      }

      this.speratePage()
    },

    // 第一页
    isFirstPage(){
      this.firstPage = true
      this.finalPage = false
    },

    isLastPage(){
      this.firstPage = false
      this.finalPage = true
    },

    // 跳到首页
    toFirstPage(){
      this.currentPage = 1
      this.speratePage()
      
      this.$emit('jump',this.currentContent)
    },

    // 前一页
    toForwardPage(){
      if(this.currentPage > 1){
        this.currentPage --
        this.speratePage()
        
        this.$emit('jump',this.currentContent)
      }
      
    },

    // 后一页
    toNextPage(){
      if(this.currentPage < this.totalPage){
        this.currentPage ++
        this.speratePage()
        
        this.$emit('jump',this.currentContent)
      }
      
    },

    // 最后一页
    toLastPage(){
      this.currentPage = this.totalPage
      this.speratePage()
      
      this.$emit('jump',this.currentContent)
    },

    // 分页
    speratePage(){
      
      // 该页内容开始位置
      let startNum = (this.currentPage - 1) * this.onePageNum
      let endNum = 0

      if(this.currentPage == 1){
        this.isFirstPage()
      }
      // 是否为最后一页
      if(this.currentPage == this.totalPage){
        endNum = this.totalNum
        this.isLastPage()
      }else{
        endNum = startNum + this.onePageNum
      }

      this.currentContent = this.content.slice(startNum,endNum)
    },

    jumpTo(){
      if(this.jumpPage > 0 && this.jumpPage < this.totalPage + 1){
        this.currentPage = this.jumpPage
        this.speratePage()
      }

      this.$emit('jump',this.currentContent)
    },

    toDetailArt(index){
      let selectIndex =  this.onePageNum * (this.currentPage - 1) + index
      this.$router.push({path:'/home/artDetail', query:{selectIndex}})
    }
  }
  
}
</script>

<style scoped>

  table{
    border-radius: 3px;
  }

  table,th,tr,td{
    border:1px solid rgb(206, 205, 205);
    border-collapse: collapse;
    border-radius:3px;
  }

  tr,th{
    height:3rem;
    line-height: 3rem;
    text-align: center;
  }

  #artList{
    border-radius: 3px;
    margin-top:10px;
    border:1px solid rgb(206, 205, 205);
  }

  .list-top{
    border-bottom: 1px solid rgb(206, 205, 205);
    padding:10px 10% 10px 2%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .list-content{
    margin:20px;
    display: flex;
    justify-content: center;
  }

  .list-content .check{
    width:3rem;
  }

  .list-content td{
    padding:10px;
    max-width: 200px;
    min-width: 60px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .page-jump{
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .page-jump button{
    width:60px;
    height:30px;
    border-radius:3px;
    color:white;
    background-color: rgb(69, 115, 201);
    margin:10px;
  }

  .page-jump input{
    border:1px solid #aabbaa;
    padding: 0 10px;
    height:30px;
  }

  .page-jump span{
    color: rgb(141, 140, 140);
    padding:0 10px 0;
  }

</style>