<template>
  <div id="menuSort">
    <sort-item>
      <h3 slot="title">{{searchText}}</h3>
      
      <el-tabs slot="content" v-model="activeName">
        <el-tab-pane label="综合最佳" name="best">
          <span slot="label" class="tab">
            综合最佳
          </span>
          <div class="content">
            <menu-sort-item :menus="bestMenus.slice((currentpage-1)*pagesize,currentpage*pagesize)" />
          </div>
        </el-tab-pane>
        <el-tab-pane label="收藏最多" name="collectMost">
          <span slot="label" class="tab">收藏最多</span>
          <div class="content">
            <menu-sort-item :menus="mostCollectMenus.slice((currentpage-1)*pagesize,currentpage*pagesize)" />
          </div>
        </el-tab-pane>
        <el-tab-pane label="浏览最多" name="watchMost">
          <span slot="label" class="tab">浏览最多</span>
          <div class="content">
            <menu-sort-item :menus="mostViewsMenus.slice((currentpage-1)*pagesize,currentpage*pagesize)" />
          </div>
        </el-tab-pane>
      </el-tabs>
    </sort-item>

    
    <div  class="pageSepe">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentpage"
        :page-sizes="[4, 8, 16, 32]" 
        :page-size="pagesize" 
        layout="total, sizes, prev, pager, next, jumper"
        :total="menus.length">    
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {findAllMenu,findMenuByName} from '@/network/menu.js'

import SortItem from '../../common/SortItem'
import FoodItem from '../../common/FoodItem'
import MenuSortItem from '@/components/comment/menu/MenuSortItem'

export default {
  name:'MenuSort',
  components:{
    SortItem,
    FoodItem,
    MenuSortItem
  },
  data(){
    return {
      currentpage:1,
      pagesize:8,
      activeName:'best',
      menus:[], //全部菜谱
      bestMenus:[],
      mostCollectMenus:[],  // 按收藏排序
      mostViewsMenus:[],  // 按浏览量排序
      searchText:'全部食谱',  // 搜索菜谱名
    }
  },
  created(){
    // 获得全部食谱
    findAllMenu()
    .then(res => {
      // 初始化赋值
      this.init(res)
    }).catch(err => {
      console.log(err)
    })
  },
  mounted(){
    let the = this

    // 监听查找事件
    this.$bus.$on('search',function(searchText){

      the.searchText = searchText

      // 若尚未获取数据
      if(the.menus.length === 0){
        setTimeout(() => {
          findMenuByName(searchText)
          .then(res => {
            the.init(res)
            // 综合排序
          }).catch(err => {
            console.log(err)
          })
        },1000)

      }else{
        // 搜索为空
        if(searchText === ''){
          the.searchText = '全部食谱'
          
          findAllMenu()
          .then(res => {
            // 初始化赋值
            the.init(res)
          }).catch(err => {
            console.log(err)
          })
        }else{
          findMenuByName(searchText)
            .then(res => {
              if(res.length === 0){
                return
              }else{
                the.init(res)
              }
              // 综合排序
            }).catch(err => {
              console.log(err)
            })
          }
        }
    })
  },
  methods:{
    init(res){
      this.menus = []
      this.bestMenus = []
      this.mostViewsMenus = []
      this.mostCollectMenus = []
       // 直接赋值时使用的是同一个地址
      res.forEach((item) => {
        this.menus.push(item)
        this.bestMenus.push(item)
        this.mostViewsMenus.push(item)
        this.mostCollectMenus.push(item)
      })

      
      // 综合排序
      this.allSort()
      this.mostCollect()
      this.mostView()
    },

    // 综合排序
    // 冒泡排序
    allSort(){
      // this.menus = this.menus.sort(function(a,b){
      //   return b.views * b.collectCount - a.views * a.collectCount
      // })
      // this.menus.forEach((item)=>console.log(item.mname))


     for (var i =0;i<this.bestMenus.length;i++) { 
        for (var j = 0; j < this.bestMenus.length - 1; j++) {
          if ((this.bestMenus[j].collectCount * this.bestMenus[j].views) < (this.bestMenus[j + 1].collectCount * this.bestMenus[j+1].views)){ 
            let s = this.bestMenus[j]; 
            this.bestMenus[j]=this.bestMenus[j+1]; 
            this.bestMenus[j+1]=s; 

          } 
        }
      } 
    },

    // 收藏最多
    mostCollect(){

      for (let i =0;i<this.mostCollectMenus.length;i++) { 
        for (let j = 0; j < this.mostCollectMenus.length - 1; j++) {
          if (this.mostCollectMenus[j].collectCount < this.mostCollectMenus[j + 1].collectCount){ 
            let s = this.mostCollectMenus[j]; 
            this.mostCollectMenus[j]=this.mostCollectMenus[j+1]; 
            this.mostCollectMenus[j+1]=s; 
          } 
          
         // console.log(this.mostCollectMenus[j].mname +'--' + this.mostCollectMenus[j + 1].mname)

        }
      }
    },

    // 浏览最多
    mostView(){
      this.mostViewsMenus = this.mostViewsMenus.sort(function(a,b){
        return b.views - a.views
      })

    },

    handleClick(num) {
      console.log(tab, event);
    },

    // 初始页currentPage、初始每页数据数pagesize和数据data
    handleSizeChange: function (size) {
      this.pagesize = size;
    },
    handleCurrentChange: function(currentpage){
      this.currentpage = currentpage;
    },
  }
}
</script>

<style scoped>

#menuSort{
  width:100%;
}

.tab{
    color:rgb(175, 57, 57);
    font-size:18px;
  }

.pageSepe{
  display: flex;
  justify-content: center;
  padding-bottom:10px;
}
</style>