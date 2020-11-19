<template>
  <div id="dayMenu">
    <sort-item>
      <span slot="title">一日三餐.{{activeName}}</span>
      
      <el-tabs slot="content" v-model="activeName">
        <el-tab-pane label="早餐" name="breakfast">
          <span slot="label" class="tab">
            早餐
          </span>
          <div class="content">
            <food-item v-for="(item,index) in breakfast.imgSrc" :key="index">
              <img slot="img" :src="item" alt="早餐">
              <h3 slot="name">{{breakfast.name[index]}}</h3>
              <p slot="description">{{breakfast.description[index]}}</p>
            </food-item>
          </div>
        </el-tab-pane>
        <el-tab-pane label="午餐" name="lunch">
          <span slot="label" class="tab">午餐</span>
          午餐
        </el-tab-pane>
        <el-tab-pane label="晚餐" name="dinner">
          <span slot="label" class="tab">晚餐</span>
          晚餐
        </el-tab-pane>
      </el-tabs>
    </sort-item>
  </div>
</template>

<script>
import SortItem from '../../common/SortItem'
import FoodItem from '../../common/FoodItem'

export default {
  name:'DayMenuRecom',
  components:{
    SortItem,
    FoodItem,
  },
  data(){
    return {
      activeName:'breakfast',
      //  menu:{
      //   breakfast:[
      //     {
      //       src:'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180425/rP6tN4YkjK.jpg?x-oss-process=image/crop,x_361,y_158,w_314,h_314/resize,w_456/format,webp',
      //       name:奶香红枣窝头,
      //       description:''
      //     },
      //     {
      //       src:'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180410/ejbs8CRP2a.jpg?x-oss-process=image/crop,x_170,y_82,w_435,h_435/resize,w_456/format,webp',
      //       name:奶香红枣窝头,
      //       description:''
      //     },
      //     {
      //       src:'http://btcf.oss-cn-hangzhou.aliyuncs.com/img/310636AF348FC3F27D7F7C2040807A97/20171107/jJ885tEdTd.jpg?x-oss-process=image/crop,x_1326,y_621,w_2553,h_2553/resize,w_456/format,webp',
      //       name:奶香红枣窝头,
      //       description:''
      //     },
      //     {
      //       src:'http://btcf.oss-cn-hangzhou.aliyuncs.com/img/310636AF348FC3F27D7F7C2040807A97/20180107/paWdKSkhdP.jpg?x-oss-process=image/crop,x_225,y_75,w_595,h_595/resize,w_456/format,webp',
      //       name:奶香红枣窝头,
      //       description:''
      //     },
      //   ],
      // }
      breakfast:{
          imgSrc:[
          'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180425/rP6tN4YkjK.jpg?x-oss-process=image/crop,x_361,y_158,w_314,h_314/resize,w_456/format,webp',
          'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180410/ejbs8CRP2a.jpg?x-oss-process=image/crop,x_170,y_82,w_435,h_435/resize,w_456/format,webp',
          'http://btcf.oss-cn-hangzhou.aliyuncs.com/img/310636AF348FC3F27D7F7C2040807A97/20171107/jJ885tEdTd.jpg?x-oss-process=image/crop,x_1326,y_621,w_2553,h_2553/resize,w_456/format,webp',
          'http://btcf.oss-cn-hangzhou.aliyuncs.com/img/310636AF348FC3F27D7F7C2040807A97/20180107/paWdKSkhdP.jpg?x-oss-process=image/crop,x_225,y_75,w_595,h_595/resize,w_456/format,webp'
        ],
        name:[
          '奶香红枣窝头',
          '鸡蛋豆腐饼',
          '白蛤鱼片粥',
          '奶香燕麦馒头'
        ],
        description:[
          '',
          '',
          '',
          ''
        ]
      }
    }
  }
}
</script>

<style scoped>

  .tab{
    color:rgb(175, 57, 57);
    font-size:18px;
  }

  .content{
    width:100%;
    display: flex;
    align-items: center;
  }

  .content img{
    width:220px;
    height:220px;
  }

  .content .title{
    font-size: 20px;
    font-weight: 800;
  }

</style>