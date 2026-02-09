import request from '@/utils/request'

// 查询分类标签列表
export function listCategory(query) {
  return request({
    url: '/manage/category/list',
    method: 'get',
    params: query
  })
}

// 查询分类标签详细
export function getCategory(id) {
  return request({
    url: '/manage/category/' + id,
    method: 'get'
  })
}

// 新增分类标签
export function addCategory(data) {
  return request({
    url: '/manage/category',
    method: 'post',
    data: data
  })
}

// 修改分类标签
export function updateCategory(data) {
  return request({
    url: '/manage/category',
    method: 'put',
    data: data
  })
}

// 删除分类标签
export function delCategory(id) {
  return request({
    url: '/manage/category/' + id,
    method: 'delete'
  })
}
