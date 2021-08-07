<template>
  <div id="ownSortItem">
    <food-item class="food-item" v-for="(item,index) in menus" :mid="item.mid" :key="index">
      <el-image class="img" slot="img" :src="item.desImg" fit="contain"></el-image>
      <div class="cover" slot="cover" v-if="item.isChecked === 0">食谱审核中~</div>

      <div class="name" slot="name">
        <h3>{{item.mname}}</h3>
        <span class="del-btn" @click.stop="deleteMenu(item.mid)">删除</span>
      </div>
      <p slot="description">{{item.description}}</p>
    </food-item>

  </div>
</template>

<script>
import {deleteMenu} from '@/network/menu.js'
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
      this.init()
    }
  },
  mounted(){
    this.init()
  },
  data(){
    return {

    }
  },
  methods:{
    init(){
      this.menus.forEach(item => {
        item.desImg =  item.desImg.indexOf('http') === -1 ? require('../../../assets/img/menu/' + item.desImg) : item.desImg;
      })
    },

    deleteMenu(mid){
      this.$confirm('此操作将永久删除该食谱, 是否继续?', '提示', {
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
h3{
  margin:0;
}

#ownSortItem{
  margin:0 auto;
  display: flex;
  flex: 1;
  align-content: center;
  flex-wrap: wrap;
}

.img{
  width:220px;
  height:160px;
  padding-bottom: 10px;
}

p{
  width:13rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.name{
  height:35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.del-btn{
  display: flex;
  justify-content: center;
  align-items: center;


  height:20px;
  width:50px;
  margin-right:5px;
  color:red;
  border-bottom: 1px solid rgb(199, 9, 9);
}

</style>