<template>
  <div id="ownSortItem">
    <food-item v-for="(item,index) in menus" :mid="item.mid" :key="index">
      <img slot="img" :src="item.desImg" alt="picture">
      <div class="name" slot="name">
        <h3>{{item.mname}}</h3>
        <el-button type="danger" size="mini" @click="deleteMenu(item.mid)">删除</el-button>
      </div>
      <p slot="description">{{item.description}}</p>
    </food-item>

  </div>
</template>

<script>
import {deleteMenu,deleteMmaterials,deleteComments,deleteCollects,deleteSteps} from '@/network/menu.js'
import FoodItem from '../../common/FoodItem'

export default {
  name:'MenuSortItem',
  components:{
    FoodItem
  },
  props:{
    menus:{
      type:Array,
      default(){
        return []
      }
    }
  },
  watch:{
    menus(newVal){
      this.menus = newVal
    }
  },
  mounted(){
    
  },
  data(){
    return {

    }
  },
  methods:{
    deleteMenu(mid){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteMenu(mid).then(res => {
            this.menus.forEach((item,index) => {
              if(item.mid === mid){
                this.menus.splice(index,1)
              }
            })
          
          deleteMmaterials(mid).then(res=>{}).catch(err => console.log(err))
          deleteComments(mid).then(res=>{}).catch(err => console.log(err))
          deleteSteps(mid).then(res=>{}).catch(err => console.log(err))
          deleteCollects(mid).then(res=>{}).catch(err => console.log(err))


            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(err => {
            this.$message({
              type: 'warning',
              message: '删除失败!'
            });
          })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }
  }
}
</script>

<style scoped>
#ownSortItem{
  margin:0 auto;
  display: flex;
  flex: 1;
  align-content: center;
  flex-wrap: wrap;
}

img{
  width:220px;
  height:160px;
}

p{
  width:13rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.name{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.del{
  color:red;
}
</style>