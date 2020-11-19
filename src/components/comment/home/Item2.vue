<template>
  <div id="item2">
    <sort-item>
      <span slot="title">探寻.烘焙之旅</span>
      <p slot="des">美味且经过试做保障的烘焙食谱</p>
      <div class="content" slot="content">
        <div>1</div>
        <div>2</div>
      </div>
    </sort-item>
  </div>
</template>

<script>
import SortItem from '../../common/SortItem'

export default {
  name:'Item2',
  components:{
    SortItem
  },
}
</script>

<style>

</style>