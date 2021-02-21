import axios from 'axios'


export function request(config){
  const instance = axios.create({
    baseURL:'http://localhost:8888/day07/',
    timeout:5000
  })


  // 响应拦截器
  instance.interceptors.response.use(res =>{
    return res.data
  }, error => {
    console.log(error)
  })

  // 发送真正网络请求并返回
  return instance(config)
}
