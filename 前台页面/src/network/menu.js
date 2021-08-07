import {request} from './request'
import qs from 'qs'

// 寻找全部食谱
export function findAllMenu(){
  return request({
    url:'/menu/findAllMenu',
  })
}

export function findCheckedMenu(isChecked){
  return request({
    url:'/menu/findCheckedMenu',
    params:{
      isChecked
    }
  })
}

export function checkMenu(){
  return request({
    url:'/menu/checkMenu',
  })
}

// 通过名字查找食谱
export function findMenuByName(mname){
  return request({
    url:'/menu/findMenuByName',
    params:{
      mname
    }
  })
}

// 添加食谱
export function addMenu(menu){
  return request({
    method:'Post',
    url:'/menu/addMenu',
    data:{
      menu:qs.stringify(menu)
    }
  })
}

// 通过id查找食谱
export function findMenuById(mid){
  return request({
    url:'/menu/findMenuById',
    params:{
      mid
    }
  })
}

// 通过id查找食谱
export function findLastMenu(){
  return request({
    url:'/menu/findLastMenu'
  })
}

// 查找食谱原料
export function findMmaterial(mid){
  return request({
    url:'/mmaterial/findMmaterial',
    params:{
      mid
    }
  })
}

// 查找食谱步骤
export function findSteps(mid){
  return request({
    url:'/mstep/findSteps',
    params:{
      mid
    }
  })
}

// 查找食谱评论
export function findMenuComment(mid){
  return request({
    url:'/comment/findMenuComment',
    params:{
      mid
    }
  })
}


// 删除评论
export function deleteCommentById(cid){
  return request({
    url:'/comment/deleteCommentById',
    params:{
      cid
    }
  })
}

// 增加菜谱浏览量
export function changeMenuView(views,mid){
  return request({
    url:'/menu/changeMenuView',
    params:{
      views,
      mid
    }
  })
}

// 修改菜谱收藏量
export function changeMenuCollect(collectCount,mid){
  return request({
    url:'/menu/changeMenuCollect',
    params:{
      collectCount,
      mid
    }
  })
}


// 修改菜谱点赞量
export function changeMenuLike(likeCount,mid){
  return request({
    url:'/menu/changeMenuLike',
    params:{
      likeCount,
      mid
    }
  })
}

// 通过用户查找菜谱
export function findMenuByUser(uid){
  return request({
    url:'/menu/findMenuByUser',
    params:{
      uid
    }
  })
}


// 通过用户查找菜谱
export function deleteMenu(mid){
  return request({
    url:'/menu/deleteMenu',
    params:{
      mid
    }
  })
}

// 是否收藏
export function loadIsCollect(uid,mid){
  return request({
    url:'/collect/isCollect',
    params:{
      uid,mid
    }
  })
}

// 改变收藏
export function changeCollect(uid,mid){
  return request({
    url:'/collect/changeCollect',
    params:{
      uid,mid
    }
  })
}

// 查找收藏
export function findCollect(uid,mid){
  return request({
    url:'/collect/findCollect',
    params:{
      uid,mid
    }
  })
}

// 取消收藏
export function cancelCollect(uid,mid){
  return request({
    url:'/collect/deleteCollect',
    params:{
      uid,mid
    }
  })
}

// 查找用户收藏
export function findCollectByUser(uid){
  return request({
    url:'/collect/findCollectByUser',
    params:{
      uid
    }
  })
}

// 删除菜谱步骤
export function deleteSteps(mid){
  return request({
    url:'/mstep/deleteSteps',
    params:{
      mid
    }
  })
}

// 删除菜谱材料
export function deleteMmaterials(mid){
  return request({
    url:'/mmaterial/deleteMmaterials',
    params:{
      mid
    }
  })
}

// 删除菜谱评论
export function deleteComments(mid){
  return request({
    url:'/comment/deleteComments',
    params:{
      mid
    }
  })
}

// 删除菜谱收藏
export function deleteCollects(mid){
  return request({
    url:'/collect/deleteCollects',
    params:{
      mid
    }
  })
}

// 添加食材
export function addMmaterial(mmaterial){
  return request({
    method:'Post',
    url:'/mmaterial/addMmaterial',
    data:{
      mmaterial:qs.stringify(mmaterial)
    }
  })
}

// 添加步骤
export function addStep(mstep){
  return request({
    method:'Post',
    url:'/mstep/addStep',
    data:{
      mstep:qs.stringify(mstep)
    }
  })
}

// 是否收藏
export function loadIsLike(uid,mid){
  return request({
    url:'/like/isLike',
    params:{
      uid,mid
    }
  })
}

// 改变收藏
export function changeLike(uid,mid){
  return request({
    url:'/like/changeLike',
    params:{
      uid,mid
    }
  })
}