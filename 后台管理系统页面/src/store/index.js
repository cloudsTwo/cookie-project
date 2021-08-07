import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

const types ={
  SET_LOGIN: 'SET_LOGIN',
  SET_USER: 'SET_USER',
};

const state = {
  isLogin:false,
  user:{},

  content:[
    {
      readPeople:1234,
      commentPeople:23,
      artTitle:'今天吃什么',
      author:'陈大哥',
      artContent:'内容1',
      deliverDate:'2019-01-03',
      finalCommentTime:'2020-03-21 13:03:42'
    },
    {
      readPeople:1234,
      commentPeople:23,
      artTitle:'今天吃什么',
      author:'陈大哥',
      artContent:'内容2',
      deliverDate:'2019-01-03',
      finalCommentTime:'2020-03-21 13:03:42'
    },
    {
      readPeople:1234,
      commentPeople:23,
      artTitle:'今天吃什么',
      author:'陈大哥',
      artContent:'内容3',
      deliverDate:'2019-01-03',
      finalCommentTime:'2020-03-21 13:03:42'
    },
    {
      readPeople:1234,
      commentPeople:23,
      artTitle:'今天吃什么',
      author:'陈大哥',
      artContent:'内容4',
      deliverDate:'2019-01-03',
      finalCommentTime:'2020-03-21 13:03:42'
    },
    {
      readPeople:132,
      commentPeople:83,
      artTitle:'熬夜冠军',
      author:'小仙女',
      artContent:'内容5',
      deliverDate:'2020-01-03',
      finalCommentTime:'2020-06-02 13:03:42'
    },
     {
      readPeople:132,
      commentPeople:83,
      artTitle:'熬夜冠军',
      author:'小仙女',
      artContent:'内容6',
      deliverDate:'2020-01-03',
      finalCommentTime:'2020-06-02 13:03:42'
    },
     {
      readPeople:132,
      commentPeople:83,
      artTitle:'熬夜冠军',
      author:'小仙女',
      artContent:'内容6',
      deliverDate:'2020-01-03',
      finalCommentTime:'2020-06-02 13:03:42'
    },
    {
      readPeople:32232,
      commentPeople:323,
      artTitle:'养猪大户',
      author:'周树人',
      artContent:'内容7',
      deliverDate:'2018-12-18',
      finalCommentTime:'2020-06-21 22:45:13'
    },
    {
     readPeople:32232,
      commentPeople:323,
      artTitle:'养猪大户',
      author:'周树人',
      artContent:'内容8',
      deliverDate:'2018-12-18',
      finalCommentTime:'2020-06-21 22:45:13' 
    },
    {
      readPeople:32232,
      commentPeople:323,
      artTitle:'养猪大户',
      author:'周树人',
      artContent:'内容9',
      deliverDate:'2018-12-18',
      finalCommentTime:'2020-06-21 22:45:13'
    },
    {
      readPeople:32232,
      commentPeople:323,
      artTitle:'养猪大户',
      author:'周树人',
      artContent:'内容10',
      deliverDate:'2018-12-18',
      finalCommentTime:'2020-06-21 22:45:13'
    },
  ],
  comment:[
    {
      id:1,
      commenter:'jjie',
      commentArt:'今天吃什么',
      commentContent:'番茄炒蛋',
      registerTime:'2019-01-23',
      commentTime:'2020-05-19',
      thinkGood:3,
    },
    {
      id:2,
      commenter:'fei',
      commentArt:'今天吃什么',
      commentContent:'土豆丝炒肉',
      registerTime:'2019-01-23',
      commentTime:'2020-05-19',
      thinkGood:5,
    },
    {
      id:3,
      commenter:'兔子',
      commentArt:'今天吃什么',
      commentContent:'什么都不好吃',
      registerTime:'2019-01-23',
      commentTime:'2020-05-19',
      thinkGood:15,
    },
  ],
  users:[
    {
      name:'cc',
      age:18,
      sex:'女',
      pwd:'111',
      email:'123@11.com',
      registerTime:'1999-08-13',
      lastRegisterTime:'2020-09-12',
      power:'普通用户',
    },
    {
      name:'admin',
      age:28,
      sex:'男',
      pwd:'111',
      email:'123@11.com',
      registerTime:'2012-02-31',
      lastRegisterTime:'2020-09-21',
      power:'管理员',
    },
    {
      name:'us',
      age:12,
      sex:'男',
      pwd:'111',
      email:'123@11.com',
      registerTime:'2018-09-18',
      lastRegisterTime:'2019-01-23',
      power:'普通用户',
    }

  ],
  findArts:[],
  detailArt:{},
  currentComment:[],
  selectUser:[]
}
const store = new Vuex.Store({
    state,

    mutations:{
      addArt(state,newArt){
        state.content.push(newArt)
      },
      getArt(state, artName){
        
        state.findArts = state.content.filter((con) => {
          return con.artTitle == artName
        })
      },
      getOneArt(state, index){
        state.detailArt = state.content[index]
      },
      getCurrentComment(state,name){
        state.currentComment = state.comment.filter((com) => {
          return com.commentArt == name
        })
        // 可进行排序，排出点赞数前五的评论
      },
      addComment(state,comment){
        state.currentComment.push(comment)
      },
      addUser(state,newUser){
        state.user.push(newUser)
      },
      findUser(state,name){
        
        state.selectUser = state.user.filter((u) => {
          return u.name == name
        })
      },
      changeUser(state,newUser,index){
        state.user.splice(index,1,newUser)
      },
      

      [types.SET_LOGIN](state,isLogin){      /*设置是否授权*/
        if(isLogin) state.isLogin = isLogin;
        else state.isLogin = false;
      },
  
      /*类型，参数*/
      [types.SET_USER](state,user){
        if (user) state.user = user;
        else state.user = {};
      },
  
      login(state,user){
        state.isLogin = true
        state.user = user
      }
    },
    actions: {
      setLogin:( {commit},isLogin) =>{
        commit(types.SET_LOGIN,isLogin);
      },
      setUser:({commit},user) =>{
        commit(types.SET_USER,user);
      }
    },
  })
export default store