import axios from 'axios'
import { Loading } from 'element-ui';   /*elementUI的loading*/
import { Message } from 'element-ui';   /*elementUI消息提醒*/
import router from '../router/index';

let loading;
function startLoading () {
  loading = Loading.service({    /*在需要调用时：*/
    lock: true,
    text: '拼命加载中...',
    background: 'rgba(0,0,0,0,7)'
  });
}

function endLoading () {
  loading.close();
}

export function request(config){
  const instance = axios.create({
    baseURL:'http://localhost:8888/day07/',
    timeout:5000
  })

  //请求拦截
  instance.interceptors.request.use(config => {

    //加载动画
    //startLoading();
    /*判断token存在   登录拦截*/
    if(localStorage.eleToken){
      /*设置统一的header*/
      config.headers.Authorization  = localStorage.eleToken;
    }
    return config;
  },error => {
    return Promise.reject(error);
  });

  //响应拦截
  axios.interceptors.response.use(Response => {
    if(Response.data.code === '401'){
      router.push('/login')
    }
    
    //结束加载动画
    //endLoading();
    return Response;
  },error => {
    //错误提醒
    //endLoading();
    Message.error(error.response.data);
    /*获取错误状态码*/
    const  { status } =error.response;
    if(status == 401){
      Message.error("token失效，重新登录");
      /*清楚token*/
      localStorage.removeItem('eleToken');
      /*跳转登录*/
      router.push('/login')
    }

    return Promise.reject(error);
  })

  // 发送真正网络请求并返回
  return instance(config)
}
