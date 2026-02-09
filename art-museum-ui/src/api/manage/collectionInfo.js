import request from '@/utils/request'

// 查询藏品信息列表
export function listCollectionInfo(query) {
  return request({
    url: '/manage/collectionInfo/list',
    method: 'get',
    params: query
  })
}

// 查询藏品信息详细
export function getCollectionInfo(id) {
  return request({
    url: '/manage/collectionInfo/' + id,
    method: 'get'
  })
}

// 新增藏品信息
export function addCollectionInfo(data) {
  return request({
    url: '/manage/collectionInfo',
    method: 'post',
    data: data
  })
}

// 修改藏品信息
export function updateCollectionInfo(data) {
  return request({
    url: '/manage/collectionInfo',
    method: 'put',
    data: data
  })
}

// 删除藏品信息
export function delCollectionInfo(id) {
  return request({
    url: '/manage/collectionInfo/' + id,
    method: 'delete'
  })
}
