<template>
  <div id="addMenuChildren">
    <h1>发布菜谱</h1>

    <div class="top">
      <div class="menu-res-img">
        <el-upload
          class="menu-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">

          <img v-if="imageUrl" :src="imageUrl" class="menu-img">
          <i v-else class="el-icon-plus el-upload menu-uploader-icon"></i>

        </el-upload>
        <p>添加菜谱效果图(必填)</p>
      </div>

      <div class="des">
        <label>
          菜谱名(必填)<input v-model="menu.mname" type="text" />
        </label>

        <label>
          心得描述:<textarea v-model="menu.description"  cols="44" rows="5" ></textarea>
        </label>
      </div>
    </div>

    <div class="ingredient">
      <h3><i class="el-icon-notebook-2"></i>食材清单(至少一项)</h3>

      <div class="in-list" v-for="(item,index) in inlength" :key="index">
        <label>食材名称：<input v-model="ingredients[index].materialName" type="text" placeholder="如：土豆" /></label>
        <label>用量：<input v-model="ingredients[index].mamount" type="text" placeholder="如：一个" /></label>
      </div>
    
      <el-button @click="addIngredients" type="warning" icon="el-icon-plus" style="margin:20px 5px 0 80px;" circle></el-button>添加食材
    </div>

    <div class="steps">
      <h3><i class="el-icon-s-order"></i>做法步骤(至少三步)</h3>

      <div class="step-list" v-for="(item,index) in steplength" :key="index">
        <label>第{{index+1}}步：<input v-model="steps[index].description" type="text" placeholder="不需要填写步骤序号1、2、3..." /></label>
        
        <div class="step-img">
          <el-upload
            class="menu-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">

            <img v-if="imageUrl" :src="imageUrl" class="menu-img">
            <i v-else class="el-icon-plus el-upload menu-uploader-icon"></i>

          </el-upload>
          <p>添加步骤图</p>
        </div>
      </div>

      <el-button style="margin-left:170px;" type="warning" @click="addStep">添加步骤</el-button>
    </div>

    <div class="other">
      <h3><i class="el-icon-news"></i>其它</h3>

      <div class="other-select">
        烹饪时间：
        <el-radio-group v-model="menu.cookieTime">
          <el-radio :label="15">15分钟以内</el-radio>
          <el-radio :label="30">15~30分钟</el-radio>
          <el-radio :label="60">30~60分钟</el-radio>
          <el-radio :label="120">1小时~1天</el-radio>
          <el-radio :label="360">1天以上</el-radio>
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

    <button class="submit" @click="submitMenu">立即发布</button>
  </div>
</template>

<script>
import {findLastMenu} from '@/network/menu.js'
import axios from 'axios'

export default {
  name:'AddMenuChildren',
  data() {
    return {
      imageUrl: '',
      menu:{},
      mid:0,
      ingredients:[
        {
          materialName:'',
          mamount:'',
          mid:0,
        }
      ],
      steps:[{description:''},{description:''},{description:''}],
      steplength:3,
      inlength:1,  // 食材数量
    };
  },
  mounted(){
    
  },
  methods: {

    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
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

    addIngredients(){
      let ingre = {
          materialName:'',
          mamount:'',
          mid:0,
        }

      this.ingredients.push(ingre)
      this.inlength= this.inlength + 1
    },

    addStep(){
      this.steps.push({description:''})
      this.steplength++
    },

    submitMenu(){
      // 是否登录
      if(!this.$store.state.isLogin){
        alert('请先登录')
        this.$router.push('/login')
      }else{
         // 检查菜谱是否合格
        this.checkMenu()
      }
    },

    checkMenu(){
      let reg = /\s+/

      this.menu.uid = this.$store.state.user.uid
      this.menu.desImg = 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180410/ejbs8CRP2a.jpg?x-oss-process=image/crop,x_4,y_4,w_842,h_561'

      // 判断菜谱信息完整
      if(Object.keys(this.menu).length < 5){
        alert('请完整填写菜谱信息')
        return
      }

      for(let item in this.menu){
        if(reg.test(this.menu[item])){
          alert('请完整填写'+item+'信息')
          return
        }
      }

      axios.post('http://localhost:8888/day07/menu/addMenu',this.menu)
        .then(res=>{
          alert("菜谱发布成功")
          this.getNewId()
        })
        .catch(err => {
          alert("发布失败");
          console.log(err);
        })
    },

    getNewId(){
      // 获得刚添加的mid
      findLastMenu()
      .then(res=>{
        this.mid = res.mid
        this.checkOther()
      }).catch(err => {
        console.log(err)
      })
    },

    checkOther(){
      let reg = /\s+/

      // 食材信息
      this.ingredients.forEach((item,index)=>{
        item.mid = this.mid

        for(let i in item){
          if(reg.test(item[i]) || item[i] === ''){
            alert('请完整填写食材信息')
            return
          }
        }
      })

      // 步骤信息
      this.steps.forEach((item,index)=>{
        item.mid = this.mid
        item.stepCount = index + 1
        item.imgUrl = 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg'

        for(let i in item){
          if(reg.test(item[i]) || item[i] === ''){
            alert('请完整填写步骤信息')
            return
          }
        }
      })


      this.ingredients.forEach(mmaterial => {
        axios.post('http://localhost:8888/day07/mmaterial/addMmaterial',mmaterial)
        .then(res=>{

        })
        .catch(err => {
          alert("原料添加失败");
          console.log(err);
        })
      })

      this.steps.forEach(mstep => {
        axios.post('http://localhost:8888/day07/mstep/addStep',mstep)
        .then(res=>{

        })
        .catch(err => {
          alert("原料添加失败");
          console.log(err);
        })
      })

     
      this.$router.replace('/menu')
    }

  }
}
</script>

<style scoped>
 h3{
   color:rgb(128, 0, 70);
 }

 i{
   color:rgb(235, 103, 85);
   padding:3px;
 }

  #addMenuChildren{
    padding:20px;
    padding-top:120px;
    text-align: center;
    width:80%;
    margin:0 auto;
    border:1px dashed purple;
    background: rgb(248, 246, 246);
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
    color:rgb(189, 156, 156);
    opacity: .7;
  }

  .menu-uploader .el-upload {
    border: 1px dashed #b85178;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  
  .menu-uploader .el-upload:hover {
    border-color: #f32e14;
  }

  .menu-uploader-icon {
    font-size: 28px;
    color: #eeab74;
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
  }

  .top .des input{
    width:300px;
    height:30px;
  }

  input{
    border:0;
    margin-left:10px;
  }

  textarea{
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
    padding:30px;
    text-align: left;
  }

  .ingredient input,.step-list input{
    margin:5px;
    height:30px;
    width:250px;
  }

  .ingredient .in-list{
    padding-bottom:5px;
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
    background-color: rgb(230, 119, 119);
    color:white;
  }
</style>