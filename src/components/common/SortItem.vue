<template>
  <div id="sortItem">
    <h2><slot name="title"></slot></h2>
    <p><slot name="des"></slot></p>
    <div>
      <slot  name="content"></slot>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#sortItem{
  width:80%;
  margin:0 auto;
}

  .content img{
    width:230px;
    height:230px;
  }

  .content .title{
    font-size: 20px;
    font-weight: 800;
  }
</style>