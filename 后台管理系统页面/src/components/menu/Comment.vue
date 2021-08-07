<template>
  <div id="comment">
    <list-display v-if="comments.length" :datas="comments" @jump="jump">
      <!-- <span slot="btn">
        <el-button type="danger" size="mini">删除</el-button>
      </span> -->
      <h2 slot="title">
        全部评论 ({{comments.length}})
      </h2>

      <table slot="table">
        <th></th>
        <th>评论内容</th>
        <th>评论者</th>
        <th>评论时间</th>
        <th>评论食谱</th>
        <th>操作</th>

        <tr v-for="(item,index) in currentContent" :key="index">
          <td class="check"><input type="checkbox" /></td>
          <td>{{item.comment.ccontent}}</td>
          <td>{{item.user.name}}</td>
          <td>{{item.comment.ctime}}</td>
          <td>{{item.menu ? item.menu.mname : ''}}</td>
          <td><el-button type="warning" size="mini" @click="changeComment(item.comment)">修改</el-button>
            <el-button type="danger" size="mini" @click="deleteComment(item.comment.cid)">删除</el-button>
          </td>
        </tr>
      </table>
    </list-display>

    <div class="no-content" v-else>
      当前未查找到评论
    </div>

    <el-dialog title="修改评论" :visible.sync="dialogFormVisible">
      <el-form :model="currentComment">
        <el-form-item label="评论内容" label-width="150">
          <el-input type="textarea"
            :rows="2" v-model="currentComment.ccontent" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="postChange">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ListDisplay from '@/components/children/ListDisplay'
import {deleteCommentById, findAllComments} from '@/network/menu'

import axios from 'axios'

export default {
  name:'ArtList',
  components:{
    ListDisplay
  },
  data(){
    return {
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:7, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面
      content: [], // 数据
      comments: [],
      dialogFormVisible: false,
      currentComment: {},
    }
  },
  created(){
    this.init()
  },
  methods:{
   
    async init(){
        this.content = this.comments = (await findAllComments()).data
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

    // 删除
    deleteComment(cid){

      this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCommentById(cid).then(res => {

            // 更新
            this.content.forEach((item,index) => {
              if(item.comment.cid === cid){
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
    changeComment(comment){
      this.dialogFormVisible = true
      this.currentComment = JSON.parse(JSON.stringify(comment))
    },

    postChange(){
      axios({
        method:'post',
        url:'http://localhost:8888/day07/comment/changeComment',
        data:this.currentComment,
        headers:{
          Authorization:localStorage.eleToken,
          timeout:5000,
          'Content-Type': 'application/json',
        }
      }).then(res => {
        this.dialogFormVisible = false
         this.$message({
          message: '修改成功！',
          type: 'success'
        });
        this.currentContent.forEach(item => {
          item.comment.cid === this.currentComment.cid ? 
            item.comment.ccontent = this.currentComment.ccontent : '';

        })
      }).catch(err => {
         this.$message({
          message: '修改失败！',
          type: 'warn'
        });
        console.log(err)
        return
      })
    }
  }
}
</script>

<style scoped>

  table{
    width:90%;
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

  #comment{
    width: 80%;
    border-radius: 3px;
    margin-top:10px;
    border:1px solid rgb(206, 205, 205);
    color:grey;
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

  .no-content{
    width:100%;
    height:300px;
    font-size: 25px;
    color:grey;
    display: flex;
    align-items: center;
    justify-content: center;
  }

</style>