<template>
  <div id="artList">
    <list-display :datas="menus" @jump="jump">
      <span slot="title">
        <span v-if="currentMode != '全部食谱'" class="return" @click="$emit('getAll')">返回</span>
        当前：{{currentMode}} ({{menus.length}})
      </span>
      <!-- <span slot="btn">
        <el-button type="danger" size="mini">删除</el-button>
      </span> -->

      <table slot="table">
        <th></th>
        <th>食谱名</th>
        <th>描述</th>
        <th>发布时间</th>
        <th>口味</th>
        <th>烹饪时间</th>
        <th>收藏量</th>
        <th>浏览量</th>
        <th>操作</th>

        <tr @click="toMenuDetail(item.mid)" v-for="(item,index) in currentContent" :key="index">
          <td class="check"><input type="checkbox" /></td>
          <td>{{item.mname}}</td>
          <td>{{item.description}}</td>
          <td>{{item.reportTime}}</td>
          <td>{{item.flavour}}</td>
          <td>{{item.cookieTime}}</td>
          <td>{{item.collectCount}}</td>
          <td>{{item.views}}</td>
          <td><el-button type="warning" size="mini" @click="changeMenu(item.mid)">修改</el-button>
            <el-button type="danger" size="mini" @click="deleteMenu(item.mid)">删除</el-button>
          </td>
        </tr>
      </table>
    </list-display>
  </div>
</template>

<script>
import ListDisplay from '@/components/children/ListDisplay'
import {deleteMenu} from '@/network/menu'

export default {
  name:'ArtList',
  components:{
    ListDisplay
  },
  data(){
    return {
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:4, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面
      content: this.menus, // 数据
    }
  },
  watch:{
    menus:function(newV, oldV){
      this.content = newV
      this.init()
    },
    deep: true,
  },
  props:{
    menus:{
      type:Array,
      default(){
        return []
      }
    }, // 所有文章
    currentMode:{
      type:String,
      default:''
    } // 当前模块
  },
  created(){
    this.init()
  },
  methods:{
   
    init(){
        
      // 分页
      this.totalNum = this.content.length
      this.totalPage = parseInt(this.totalNum%this.onePageNum === 0? this.totalNum / this.onePageNum : this.totalNum / this.onePageNum + 1)
      this.speratePage()
    },

    // 分页
    speratePage(){
      // 该页内容开始位置
      let startNum = (this.currentPage - 1) * this.onePageNum
      let endNum = 0
      
      // 是否为最后一页
      if(this.currentPage === this.totalPage){
        endNum = this.totalNum
      }else{
        endNum = startNum + this.onePageNum
      }

      this.currentContent = this.content.slice(startNum,endNum)
    },

    jump(content){
      this.currentContent = content
    },

    toMenuDetail(mid){
      this.$router.push({path:'menuDetail', query:{mid}})
    },

    // 删除
    deleteMenu(mid){

      this.$confirm('此操作将永久删除该食谱, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteMenu(mid).then(res => {

            // 更新
            this.content.forEach((item,index) => {
              if(item.mid === mid){
                this.content.splice(index,1)
                this.speratePage()
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
    },

    // 修改
    changeMenu(mid){
      this.$router.push({path:'/home/editMenu',query:{mid}})
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
    padding: 0 5px;
    height:3rem;
    line-height: 3rem;
    text-align: center;
  }

  #artList{
    border-radius: 3px;
    margin-top:10px;
    border:1px solid rgb(206, 205, 205);
  }

  .return{
    cursor: pointer;
    color:blue;
    padding:10px;
    text-decoration: underline;
  }

  .list-top{
    border-bottom: 1px solid rgb(206, 205, 205);
    padding:10px 10% 10px 2%;
    display: flex;
    justify-content: space-between;
  }

  .list-content{
    margin:20px;
    display: flex;
    justify-content: center;
  }

  .list-content .check{
    width:3rem;
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
    height:30px;
  }

  .page-jump span{
    color: rgb(141, 140, 140);
    padding:0 10px 0;
  }

</style>