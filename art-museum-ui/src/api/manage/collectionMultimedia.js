import request from '@/utils/request'

// 查询藏品多媒体列表
export function listCollectionMultimedia(query) {
  return request({
    url: '/manage/collectionMultimedia/list',
    method: 'get',
    params: query
  })
}

// 查询藏品多媒体详细
export function getCollectionMultimedia(id) {
  return request({
    url: '/manage/collectionMultimedia/' + id,
    method: 'get'
  })
}

// 新增藏品多媒体
export function addCollectionMultimedia(data) {
  return request({
    url: '/manage/collectionMultimedia',
    method: 'post',
    data: data
  })
}

// 修改藏品多媒体
export function updateCollectionMultimedia(data) {
  return request({
    url: '/manage/collectionMultimedia',
    method: 'put',
    data: data
  })
}

// 删除藏品多媒体
export function delCollectionMultimedia(id) {
  return request({
    url: '/manage/collectionMultimedia/' + id,
    method: 'delete'
  })
}
