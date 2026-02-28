import request from '@/utils/request'

// 查询图书馆信息列表
export function listGalleryInfo(query) {
  return request({
    url: '/manage/galleryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询图书馆信息详细
export function getGalleryInfo(id) {
  return request({
    url: '/manage/galleryInfo/' + id,
    method: 'get'
  })
}

// 新增图书馆信息
export function addGalleryInfo(data) {
  return request({
    url: '/manage/galleryInfo',
    method: 'post',
    data: data
  })
}

// 修改图书馆信息
export function updateGalleryInfo(data) {
  return request({
    url: '/manage/galleryInfo',
    method: 'put',
    data: data
  })
}

// 删除图书馆信息
export function delGalleryInfo(id) {
  return request({
    url: '/manage/galleryInfo/' + id,
    method: 'delete'
  })
}
