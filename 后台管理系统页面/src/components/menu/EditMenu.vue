<template>
  <div id="editMenu" v-if="menuInfo">
    <h1>编辑食谱</h1>

    <div class="top" v-if="menu.mid">
      <div class="menu-res-img">
        <el-upload
          class="menu-uploader"
          action="http://localhost:8888/day07/menu/fileUpload"
          :show-file-list="false"
          :limit="1"
          :headers = "fileHeaders"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">

          <img v-if="menuImg" :src="menuImg" class="menu-img">
          <i v-else class="el-icon-plus el-upload menu-uploader-icon"></i>

        </el-upload>
        <p>添加食谱效果图(必填)</p>
      </div>

      <div class="des">
        <label>
          食谱名(必填)<input v-model="menu.mname" type="text" />
        </label>

        <label>
          食谱描述:<textarea v-model="menu.description"  cols="44" rows="5" ></textarea>
        </label>
      </div>
    </div>

    <div class="ingredient" >
      <h3><i class="el-icon-notebook-2"></i>食材清单(至少一项)</h3>

      <div class="in-list" v-for="(item,index) in inlength" :key="index">
        <label>食材名称：<input v-model="ingredients[index].materialName" type="text" placeholder="如：土豆" /></label>
        <label>用量：<input v-model="ingredients[index].mamount" type="text" placeholder="如：一个" /></label>
      </div>
    
      <el-button @click="addIngredients" type="primary" icon="el-icon-plus" style="margin:20px 5px 0 80px;" circle></el-button>添加食材
    </div>

    <div class="steps">
      <h3><i class="el-icon-s-order"></i>做法步骤(至少三步)</h3>

      <div class="step-list" v-for="(item,index) in steplength" :key="index">
        <label>第{{index+1}}步：<input v-model="steps[index].description" type="text" placeholder="不需要填写步骤序号1、2、3..." /></label>
        
        <div class="step-img">
          <div @click="uploadStepIndex(index)" >
            <el-upload
              class="menu-uploader"
              action="http://localhost:8888/day07/mstep/fileUpload"
              :show-file-list="false"
              :headers = "fileHeaders"
              :on-success="handleStepSuccess"
              :before-upload="beforeAvatarUpload">

              <img v-if="stepsImg[index]" :src="stepsImg[index]" class="menu-img">
              <i v-else class="el-icon-plus el-upload menu-uploader-icon"></i>

            </el-upload>
          </div>
          <p>添加步骤图</p>
        </div>
      </div>

      <el-button style="margin:30px 0 0 170px;" type="primary"  @click="addStep">添加步骤</el-button>
    </div>

    <div class="other">
      <h3><i class="el-icon-news"></i>其它</h3>

      <div class="other-select">
        烹饪时间：
        <el-radio-group v-model="menu.cookieTime">
          <el-radio :label="'1'">15分钟以内</el-radio>
          <el-radio :label="'2'">15分钟~1小时</el-radio>
          <el-radio :label="'3'">1~3小时</el-radio>
          <el-radio :label="'4'">>3小时</el-radio>
          <el-radio :label="'5'">>一天</el-radio>
        </el-radio-group>
      </div>

      <div class="other-select">
        口味：
        <el-radio-group v-model="menu.flavour">
          <el-radio label="咸鲜">咸鲜</el-radio>
          <el-radio label="酸甜">酸甜</el-radio>
          <el-radio label="五香">五香</el-radio>
          <el-radio label="香辣">香辣</el-radio>
          <el-radio label="孜然">孜然</el-radio>
          <el-radio label="酸辣">酸辣</el-radio>
        </el-radio-group>
      </div>

      <div class="other-select">
        难度：
        <el-radio-group v-model="menu.hard">
          <el-radio :label="1">简单</el-radio>
          <el-radio :label="2">普通</el-radio>
          <el-radio :label="3">高级</el-radio>
        </el-radio-group>
      </div>
    </div>

    <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    <button class="submit" @click="submitMenu">立即更新</button>
  </div>
</template>

<script>
import axios from 'axios'
import {findMenuById} from '@/network/menu'

export default {
  name:'EditMenu',
  data() {
    return {
      menuImg:'', // 食谱展示图
      imageUrl: '',
      menu:{},
      mid:0,
      ingredients:[],
      steps:[],
      stepsImg:[],  // 步骤图
      steplength:3, // 步骤数
      currentStepPic:0, // 上传步骤图的位置
      inlength:1,  // 食材数量
      menuInfo:{},

      fileHeaders:{ // 文件头
        Authorization:localStorage.eleToken
      }
    };
  },
  mounted(){
    this.mid = this.$route.query.mid
    this.init()
  },
  methods: {
    async init(){
      let res = await findMenuById(this.mid)
      let menuInfo = this.menuInfo = res.data
      console.log(this.menuInfo)

      this.menu = menuInfo.menu
      //this.menuImg = require("D:/vue_workspace/cookie-project1/src/assets/img/menu/" + this.menu.desImg)
      this.steps = menuInfo.steps
      this.steplength = this.steps.length
      this.ingredients = menuInfo.materials
      this.inlength = this.ingredients.length
      // this.steps.map((item, index)=> {
      //   this.stepsImg[index] =  item.imgUrl.indexOf('http') === -1 ? require('D:/vue_workspace/cookie-project1/src/assets/img/menu/' + item.imgUrl) : item.imgUrl;
      // })
    },

    // 食谱封面
    handleAvatarSuccess(res, file) {
      //this.menuImg = require("D:/vue_workspace/cookie-project1/src/assets/img/menu/" + file.name);
      this.menu.desImg = file.name
    },

    // 步骤图
    handleStepSuccess(res, file) {
      // 给食谱步骤返回值
      //this.stepsImg.splice(this.currentStepPic, 0, require("D:/vue_workspace/cookie-project1/src/assets/img/menu/" + file.name));
      this.steps[this.currentStepPic].imgUrl = file.name
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    // 增加食材
    addIngredients(){
      let ingre = {
          materialName:'',
          mamount:'',
          mid:this.menu.mid,
        }

      this.ingredients.push(ingre)
      this.inlength= this.inlength + 1
    },

    // 增加步骤
    addStep(){
      this.steps.push({description:''})
      this.steplength++
    },

    submitMenu(){
      // 是否登录
      if(!this.$store.state.isLogin){
        alert('请先登录')
        this.$router.replace('/login')
      }else{
         // 检查菜谱是否合格
        this.checkMenu()
      }
    },

    // 判断食谱基本信息
    checkMenu(){

      for(let item in this.menu){
        // 去除前后空格
        item = item.replace(/(^\s*)|(\s*$)/g, ""); 
        if(item===''){
          alert('请完整填写'+item+'信息')
          return
        }
      }

      this.checkOther()
    },

    checkOther(){
      let reg = /\s+/

      // 食材信息
      this.ingredients.forEach((item,index)=>{

        for(let i in item){
          if(reg.test(item[i]) || item[i] === ''){
            alert('请完整填写食材信息')
            return
          }
        }
      })

      // 步骤信息
      this.steps.forEach((item,index)=>{
        item.mid = this.menu.mid
        item.stepCount = index + 1

        for(let i in item){
          if(reg.test(item[i]) || item[i] === ''){
            alert('请完成食谱步骤' + (i+1) + '')
            return
          }
        }
      })

      // 添加其他食材信息
      this.postOtherMenuInfo()
    },
    postOtherMenuInfo(){
      axios({
        method:'post',
        url:'http://localhost:8888/day07/menu/changeMenu',
        data:this.menu,
        headers:{
          Authorization:localStorage.eleToken,
          timeout:5000,
          'Content-Type': 'application/json',
        }
      }).then(res => {}).catch(err => {
        alert('修改食谱失败')
        console.log(err)
        return
      })

      // 上传材料
      this.ingredients.forEach((mmaterial,index) => {
        // 新增的材料
        if(index >= this.menuInfo.materials.length){
          console.log(mmaterial,index, this.menuInfo.materials.length)
          axios({
            method:'post',
            url:'http://localhost:8888/day07/mmaterial/addMmaterial',
            data:mmaterial,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          }).catch(err => {
            alert("修改食谱失败");
            console.log(err);
            return
          })
        }else{
          axios({
            method:'post',
            url:'http://localhost:8888/day07/mmaterial/changeMmaterial',
            data:mmaterial,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          }).catch(err => {
            alert("修改食谱失败");
            console.log(err);
            return
          })
        }
      })

      // 上传步骤
      this.steps.forEach((mstep,index) => {
        if(index >= this.menuInfo.steps.length){
          axios({
            method:'post',
            url:'http://localhost:8888/day07/mstep/addStep',
            data:mstep,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          }).catch(err => {
            alert("修改食谱失败");
            console.log(err);
            return;
          })
        }else{
          axios({
            method:'post',
            url:'http://localhost:8888/day07/mstep/changeStep',
            data:mstep,
            headers:{
              Authorization:localStorage.eleToken,
              timeout:5000,
              'Content-Type': 'application/json',
            }
          }).catch(err => {
            alert("修改食谱失败");
            console.log(err);
            return;
          })
        }
      })
     
      this.$router.go(-1)
    },

    // 获取当前上传的步骤下标
    uploadStepIndex(index){
      this.currentStepPic = index;
    }
  }
}
</script>

<style scoped>
 h3{
   color:rgb(0, 30, 128);
 }

 i{
   color:rgb(85, 113, 235);
   padding:3px;
 }

  #editMenu{
    position:relative;
    right:130px;
    width: 80%;
    padding-top:20px;
    text-align: center;
    margin:0 auto;
    border:1px dashed rgb(0, 9, 128);
    background: rgb(240, 245, 247);
  }

  .top{
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    margin:20px;
  }

  .menu-res-img{
    text-align: center;
  }

  .menu-res-img p,.step-img p{
    font-size: 15px;
    color:rgb(156, 189, 178);
    opacity: .7;
  }

  .menu-uploader .el-upload {
    border: 1px dashed #5192b8;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  
  .menu-uploader .el-upload:hover {
    border-color: #14c6f3;
  }

  .menu-uploader-icon {
    font-size: 28px;
    color: #74bdee;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .menu-img {
    width: 150px;
    height: 150px;
    display: block;
  }

  .top .des{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: flex-start;
  }

  .top .des input{
    width:360px;
    height:30px;
  }

  input{
    padding:0 5px;
    background:white;
    border:0;
    margin-left:10px;
  }

  textarea{
    padding: 0 5px;
    background: white;
    border:0;
    margin-left:30px;
  }

  input:focus,textarea:focus{
    outline: none;
    border:0;
  }

  .top .des label{
    padding:20px;
  }

  .ingredient{
    margin-top: 30px;
    padding:30px;
    text-align: left;
  }

  .ingredient input,.step-list input{
    margin:5px;
    height:30px;
    width:250px;
  }

  .ingredient h3{
    padding:20px 0;
  }

  .ingredient .in-list{
    padding:5px;
    display: flex;
    justify-content: space-evenly;
  }

  .steps{
    padding:30px;
    text-align: left;
  }

  .step-list{
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    padding:20px;
  }

  .step-list .step-img{
    text-align: center;
  }

  .other{
    text-align: left;
    padding:30px;
  }

  .other .other-select{
    margin-left:100px;
    padding:10px;
  }

  .submit{
    margin:20px;
    height:40px;
    width:300px;
    border: 0;
    background-color: rgb(66, 113, 241);
    color:white;
  }
</style>