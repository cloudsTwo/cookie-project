import {request} from './request'

export function findGoodInfoByMenu(mid){
  return request({
    url:'/goodInfo/findGoodInfoByMenu',
    params:{
      mid
    }
  })
}

export function findShopcart(uid,mid){
  return request({
    url:'/shopcart/findShopcart',
    params:{
      uid,mid
    }
  })
}

export function findShopcartByUser(uid){
  return request({
    url:'/shopcart/findShopcartByUser',
    params:{
      uid
    }
  })
}

export function deleteShopcartById(sid){
  return request({
    url:'/shopcart/deleteShopcartById',
    params:{
      sid
    }
  })
}

export function findOrderByUser(uid){
  return request({
    url:'/order/findOrderByUser',
    params:{
      uid
    }
  })
}

export function findOrderGoodsByOrder(oid){
  return request({
    url:'/orderGoods/findOrderGoodsByOrder',
    params:{
      oid
    }
  })
}

export function changeOrderShip(oid,isShip){
  return request({
    url:'/order/changeOrderShip',
    params:{
      oid,isShip
    }
  })
}