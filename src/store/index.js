import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const types ={
  SET_LOGIN: 'SET_LOGIN',
  SET_USER: 'SET_USER',
};

export default new Vuex.Store({
  state: {
    isLogin:false,
    user:{}
  },
  mutations: {
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
  modules: {
  }
})
