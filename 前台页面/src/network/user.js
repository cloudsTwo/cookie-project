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

export function findOneUserByName(name){
  return request({
    url:'/user/findOneUserByName',
    params:{
      name
    }
  })
}

export function changeAvatar(uid, avatar){
  return request({
    url:'/user/changeAvatar',
    params:{
      uid,
      avatar
    }
  })
}

export function changeFocu(uid,followId){
  return request({
    url:'/focuUser/changeFocu',
    params:{
      uid,
      followId
    }
  })
}

export function loadIsFocued(uid, followId){
  return request({
    url:'/focuUser/isFocued',
    params:{
      uid,
      followId
    }
  })
}