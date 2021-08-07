<template>
  <div id="topFind">
    <div class="find">
        <div class="find-title">查询条件</div>
            <div class="find-content">
                <span>
                  <slot name="find-input"></slot>
                </span>

                <span><slot name="btn"></slot></span>
            </div>
        </div>
  </div>
</template>

<script>
export default {
  name:'TopFind',
  data(){
      return {
        findTitle:''
      }
  },
  methods:{
      find(){
        this.$emit('findArt', this.findTitle)
      }
  }

}
</script>

<style scoped>
  input{
        border:1px solid rgb(170, 169, 169);
        margin-left:20px;
        height:2rem;
        width:15rem;
        border-radius: 3px;
    }

    button{
        background-color: rgb(73, 156, 233);
        color:white;
        border-radius: 3px;
        height:30px;
        width:65px;
    }

    .find{
        border-radius:3px;
        border:1px solid  rgb(201, 199, 199);
    }

    .find .find-title{
        height:44px;
        line-height: 44px;
        padding-left:10px;
        border-radius: 3px;
        color:white;
        background-color: rgb(73, 156, 233);
    }

    .find .find-content{
        height:55px;
        width: 100%;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        padding:0 20px;
    }

    .find-content span{
        padding-right: 30px;
    }
</style>