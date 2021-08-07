<template>
  <div id="goodList">
    <list-display  :datas="newContent" @jump="jump">
      <span slot="title">
        <span v-if="currentMode != '全部商品'" class="return" @click="$emit('getAll')">返回</span>
        当前：{{newMode}} ({{newContent.length}})
      </span>

      <table slot="table" v-if="newContent.length">
        <th></th>
        <th>商品名字</th>
        <th>价格</th>
        <th>库存量</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>状态</th>
        <th>操作</th>

        <tr v-for="(item,index) in currentContent"  :key="index">
          <td class="check"><input type="checkbox" /></td>
          <td>{{item.menu.mname}}</td>
          <td>{{item.goodInfo.price}}</td>
          <td>{{item.goodInfo.reserve}}</td>
          <td>{{item.goodInfo.createTime}}</td>
          <td>{{item.goodInfo.updateTime}}</td>
          <td>{{item.goodInfo.status === 0 ? '禁用' : item.goodInfo.status === 1 ? '正常' : '删除'}}</td>
          <td>
            <el-button type="warning" size="mini" @click="changeGood(item,index)">修改</el-button>
          </td>
        </tr>
      </table>
      <div slot="table" class="no-content" v-else>
        当前无({{newMode}})
      </div>
    </list-display>
    <el-dialog class="pad" title="修改商品信息"  :visible.sync="showChangePad">
      <el-form :model="currentGood">
        <el-form-item label="商品名" label-width="150">
          <el-input :disabled="true" v-model="currentGood.menu.mname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" label-width="150">
          <el-input v-model="currentGood.goodInfo.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="库存" label-width="150">
          <el-input type="number"  v-model="currentGood.goodInfo.reserve" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" label-width="150">
            <el-select v-model="currentGood.goodInfo.status" placeholder="请选择">
              <el-option
                label="正常"
                :value="1">
              </el-option>
              <el-option
                label="禁用"
                :value="0">
              </el-option>
              <el-option
                label="删除"
                :value="-1">
              </el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showChangePad = false">取 消</el-button>
        <el-button type="primary" @click="postChange">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  
</template>

<script>
import ListDisplay from '@/components/children/ListDisplay'
import axios from 'axios'

export default {
  name:'GoodList',
  components:{
    ListDisplay
  },
  data(){
    return {
      selectIndex:0,  // 选择的订单
      currentPage:1,  // 当前页数
      currentContent:[],  // 当前页显示内容
      onePageNum:4, //每一页显示数量
      totalNum:1, // 内容总数
      totalPage:1,  // 总页数
      jumpPage:1, //跳转页面
      newContent:[],
      newMode:this.currentMode,
      showChangePad:false,
      currentGood:{
        menu:{},
        goodInfo:{},
      },
    }
  },
  computed:{
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
    },
    currentMode(newM){
      this.newMode = newM
    }
  },
  mounted(){
    this.init(this.content)
  },
  methods:{
    init(content){
      // 传给分页组件
      this.newContent = content

      // 分页
      this.totalNum = content.length
      this.totalPage = parseInt(this.totalNum%this.onePageNum === 0? this.totalNum / this.onePageNum : this.totalNum / this.onePageNum + 1)
      this.speratePage(content)
    },

    // 分页
    speratePage(content){

      // 该页内容开始位置
      let startNum = (this.currentPage - 1) * this.onePageNum
      let endNum = 0
      
      // 是否为最后一页
      if(this.currentPage === this.totalPage){
        endNum = this.totalNum
      }else{
        endNum = startNum + this.onePageNum
      }

      this.currentContent = content.slice(startNum,endNum)
    },

    jump(content){
      this.currentContent = content
    },

    changeGood(item,index){
      this.showChangePad = !this.showChangePad
      this.selectIndex = index
      for(let key in item.menu){
        this.$set(this.currentGood.menu, key, item.menu[key])
      }

      for(let key in item.goodInfo){
        this.$set(this.currentGood.goodInfo, key, item.goodInfo[key])
      }
    },
    
    postChange(){
      this.showChangePad = false

      if(this.currentGood.goodInfo.reserve < 0 || this.currentGood.goodInfo.price < 0){
        this.$message({
          type:'warning',
          message:'请输入0以上的合法数值！'
        })  
        return    
      }else{
        axios({
          method:'post',
          url:'http://localhost:8888/day07/goodInfo/changeGoodInfo',
          data:this.currentGood.goodInfo,
          headers:{
            Authorization:localStorage.eleToken,
            timeout:5000,
            'Content-Type': 'application/json',
          }
        }).then(res => {
          this.currentContent.splice(this.selectIndex, 1, this.currentGood)
          this.$message({
            message: '修改成功！',
            type: 'success'
          });
        }).catch( err => {
          this.$message({
            message: '修改失败！',
            type: 'error'
          });
        })
      }
    },

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

  #goodList{
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
    height:30px!important;
  }

  .page-jump span{
    color: rgb(141, 140, 140);
    padding:0 10px 0;
  }

  .good-pad{
    width:600px;
    z-index: 9;
    text-align: center;
    position: fixed;
    left:50%;
    top: 50%;
    transform: translate(-50%, -50%);

    background-color: rgb(213, 235, 248);
    color:rgb(77, 76, 76);
    padding: 20px 30px;
    border: 2px solid rgb(34, 1, 180);
  }

  .good-pad table, .good-pad td, .good-pad tr{
    border: 0;
  }

  .good-pad .cancel{
    position: absolute;
    top:8px;
    right: 10px;
    cursor: pointer;
  }

  .good-pad .title{
    text-align: center;
    color:rgb(92, 150, 205);
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 15px;
  }

  .good-pad tr{
    text-align: left;
  }

  .good-pad input{
    outline: none;
    height:30px;
  }

  td input.username{
    width: 150px;
  }

  td input.phone{
    width:150px;
  }

  td input.address{
    width:390px;
  }

  .good-pad tr td:nth-of-type(2n+1){
    width: 70px;
    color:black;
  }

  .good-pad tr td:nth-of-type(2n){
    width: 150px;
    color:rgb(110, 110, 110);
  }

  .tip .allpay{
    font-size: 19px;
    color:rgb(228, 74, 3);
    font-weight: 500;
  }

  .pay{
    cursor: pointer;
    margin:10px auto;
    width:130px;
    padding: 12px 0;
    background-color: rgb(8, 107, 221);
    color:white;
    border-radius: 10px;
  }

  .material{
    width:200px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    flex-wrap: wrap;
    background-color: white;
    border: 2px solid rgb(2, 22, 204);
    border-radius: 10px;
  }

  .material .material-item{
    width:100%;
    color:grey;
    display: flex;
    align-items: flex-start;
    justify-content: space-around;
  }

  .good-pad input{
    border:1px solid rgb(53, 53, 53);
    background-color: white;
  }

  .status{
    color:rgb(87, 87, 87);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .noShip, .isShip, .hasShip{
    height:30px;
    padding:0;
    margin:0 auto;
    width:60px;
    border-radius: 15px;
    color:white;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .noShip{
    background-color: tomato;
  }

  .isShip{
    background-color:rgb(7, 127, 207);
  }

  .hasShip{
    background-color:rgb(112, 110, 110);
  }

  .classify{
    cursor: pointer;
  }

  .no-content{
    display: flex;
    justify-content: center;
    align-items: center;
    height:300px;
    font-size: 22px;
  }

  .pad{
    position: fixed;
    left: 40%;
    top: 10%;
    background-color: white;
    height: 500px;
    width:400px;
  }

  .pad >>> .el-dialog{
    position: absolute;
    padding:10px;
    width: 100%;
    height:100%;
    margin: 0;
    margin-top:0 !important;
  }

  .pad >>> .el-dialog .el-dialog__header{
    padding: 10px;
  }

  .pad >>> .el-dialog .el-dialog__body{
    padding: 10px;
  }

  .pad >>> .el-dialog .el-dialog__body .el-form-item{
    margin-bottom: 10px;
  }

</style>