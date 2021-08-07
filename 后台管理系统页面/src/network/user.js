import {request} from './request'

export function Login(name,password){
  return request({
    url:'/user/login',
    params:{
      name,password
    }
  })
}


export function Registered(user){
  return request({
    method:'Post',
    url:'/user/registered',
    data:{
      user
    }
  })
}

export function findOneUserById(uid){
  return request({
    url:'/user/findOneUserById',
    params:{
      uid
    }
  })
}

export function deleteUser(uid){
  return request({
    url:'/user/deleteUser',
    params:{
      uid
    }
  })
}

export function findOneUserByName(name){
  return request({
    url:'/user/findOneUserByName',
    params:{
      name
    }
  })
}

export function findAllUser(){
  return request({
    url:'/user/findAllUser'
  })
}

export function changePassword(uid, password){
  return request({
    url:'/user/changePassword',
    params:{
      uid, password
    }
  })
}