<template>
  <div id="userList">
    <list-display :datas="content" @jump="jump">
      <span slot="title">
        <span v-if="currentMode != '全部用户'" class="return" @click="$emit('getAll')">返回</span>
        当前：{{currentMode}} ({{content.length}})
      </span>
      <!-- <span slot="btn">
        <el-button type="danger" size="mini">删除</el-button>
      </span> -->

      <table slot="table">
        <th></th>
        <th>用户名</th>
        <th>手机号</th>
        <th>管理员</th>
        <th>收货地址</th>
        <th>操作</th>

        <tr v-for="(item,index) in currentContent"  :key="index">
          <td class="check"><input type="checkbox" /></td>
          <td>{{item.name}}</td>
          <td>{{item.phoneNumber}}</td>
          <td>{{item.admin == '1'? '是':'否'}}</td>
          <td>{{item.address}}</td>
          <td><el-button type="warning" size="mini" @click="changeU(item)">修改</el-button>
            <el-button type="danger" v-if="item.admin == '0'" size="mini" @click="deleteU(item.uid)">删除</el-button>
          </td>
        </tr>
      </table>
    </list-display>
    <el-dialog title="修改用户" :visible.sync="dialogFormVisible">
      <el-form :model="currentUser">
        <el-form-item label="用户名" label-width="150">
          <el-input v-model="currentUser.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" label-width="150">
          <el-input type="password" v-model="currentUser.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="150">
          <el-input type="phoneNumber" v-model="currentUser.phoneNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货地址" label-width="150">
          <el-input type="address" v-model="currentUser.address" autocomplete="off"></el-input>
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
import {deleteUser} from '@/network/user'
import axios from 'axios'

export default {
  name:'UserList',
  components:{
    ListDisplay
  },
  data(){
    return {
      currentUser: {}, // 要修改的用户
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:4, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面
      dialogFormVisible: false,
    }
  },
  props:{
    content:{
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
  watch:{
    content(newV,oldV){
      this.init(newV)
    }
  },
  mounted(){
    this.init(this.content)
  },
  methods:{
    init(content){
      // 分页
      this.totalNum = content.length
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

    toEditUser(index){
      let selectIndex =  this.onePageNum * (this.currentPage - 1) + index
      this.$router.push({path:'/home/userEdit', query:{selectIndex}})
    },

    deleteU(uid){
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUser(uid).then(res => {

            // 更新
            this.content.forEach((item,index) => {
              if(item.uid === uid){
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

    changeU(user){
      this.dialogFormVisible = true
      this.currentUser = JSON.parse(JSON.stringify(user))
    },

    postChange(){
      axios({
        method:'post',
        url:'http://localhost:8888/day07/user/changeUser',
        data:this.currentUser,
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
        this.currentContent.forEach((item,index) => {
          item.uid === this.currentUser.uid ? this.currentContent[index] = this.currentUser : ''
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
    height:3rem;
    line-height: 3rem;
    text-align: center;
  }

  #userList{
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