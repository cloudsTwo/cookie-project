<template>
  <div id="artList">
    <list-display :datas="menus" @jump="jump">
      <span slot="title">
        <span v-if="currentMode!='待审核食谱'" @click="$emit('getAll')" class="return">返回</span>
        当前：{{currentMode}} ({{content.length}})
      </span>
      <span slot="btn">
        <el-button type="primary" :disabled="!selectedList.length" @click="check">审核</el-button>
      </span>

      <table slot="table">
        <th><input @click="selectAll" type="checkbox" /></th>
        <th>食谱名</th>
        <th>描述</th>
        <th>发布时间</th>
        <th>口味</th>
        <th>烹饪时间</th>
        <th>操作</th>

        <tr v-for="(item,index) in currentContent" :key="index">
          <td class="check"><input type="checkbox" @click="selectOne(item)" :checked="item.isChecked === 1" /></td>
          <td>{{item.mname}}</td>
          <td>{{item.description}}</td>
          <td>{{item.reportTime}}</td>
          <td>{{item.flavour}}</td>
          <td>{{item.cookieTime}}</td>
          <td>
            <el-button type="warning" size="mini" @click="changeMenu(item.mid)">修改</el-button>
            <el-button type="danger" size="mini" @click="deleteMenu(item.mid)">删除</el-button>
          </td>
        </tr>
      </table>
    </list-display>
  </div>
</template>

<script>
import ListDisplay from '@/components/children/ListDisplay'
import {deleteMenu, checkMenu} from '@/network/menu'

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
      isSelectedAll: false, // 是否全选
      selectedList:[], // 选择的列表
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

    toDetailArt(index){
      let selectIndex =  this.onePageNum * (this.currentPage - 1) + index
      this.$router.push({path:'/home/artDetail', query:{selectIndex}})
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
    },

    // 选择全部
    selectAll(){
      this.isSelectedAll = !this.isSelectedAll
      this.currentContent.forEach((item,index) => {
        item.isChecked = this.isSelectedAll? 1 : 0
        this.selectedList[index] = item.mid
      })

      // 取消全选
      if(!this.isSelectedAll){
        this.selectedList.length = 0
      }
    },

    selectOne(menu){
      let le = this.selectedList.length

      if(menu.isChecked === 1){
        menu.isChecked = 0
        
        let index = this.selectedList.findIndex(item => {
          item = menu.mid
        })
        this.selectedList.splice(index, 1)
        
      }else{
        menu.isChecked = 1
        this.selectedList[le] = menu.mid
      }

    },

    // 审核
    check(){

      this.selectedList.forEach((item,index) => {
        checkMenu(item)
      })

      setInterval(() => {
        this.$message({
          type:'success',
          message:'审核成功!'
        })
        this.$router.go(0)
      }, 1000)
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