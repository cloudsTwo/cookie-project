<template>
  <div id="dayMenu">
    <sort-item>
      <span slot="title">今日推荐.RECOMMEND</span>
      
      <div slot="content">
        <div>
          <div class="content">
            <food-item v-for="(item,index) in breakfast.imgSrc" :key="index">
              <img slot="img" :src="item" alt="图片">
              <h3 slot="name">{{breakfast.name[index]}}</h3>
              <p slot="description">{{breakfast.description[index]}}</p>
            </food-item>
          </div>
        </div>
      </div>
    </sort-item>
    <sort-item>
      <span slot="title">今日最佳.{{activeName}}</span>
      
      <div slot="content">
        <div>
          <div class="content">
            <food-item :mid="item.mid" v-for="(item,index) in menus" :key="index">
              <img slot="img" :src="item.desImg" alt="图片">
              <h3 slot="name">{{item.mname}}</h3>
              <p class="des" slot="description">{{item.description}}</p>
            </food-item>
          </div>
        </div>
      </div>
    </sort-item>
  </div>
</template>

<script>
import SortItem from '../../common/SortItem'
import FoodItem from '../../common/FoodItem'

import {findCheckedMenu} from '@/network/menu'

export default {
  name:'DayMenuRecom',
  components:{
    SortItem,
    FoodItem,
  },
  data(){
    return {
      activeName:'BEST',
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
      },
      menus:[]
    }
  },
  mounted(){
    this.init()
  },
  methods:{
    async init(){
      this.menus = (await findCheckedMenu(1)).data
       this.menus?.map((item,index) => {
        let imgUrl = this.menus[index].desImg;
        // 网络图片还是本地图片
        this.menus[index].desImg = imgUrl.indexOf('http') === -1 ? require('../../../assets/img/menu/' + imgUrl) : imgUrl
      })
      console.log(this.menus)

      this.menus.sort((a,b) => {
        return a.views * a.likeCount - b.views * b.likeCount
      })

      this.menus = this.menus.slice(0, 4)
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

  .des{
    max-width: 200px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

</style>