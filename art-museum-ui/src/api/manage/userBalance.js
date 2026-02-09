import request from '@/utils/request'

// 查询用户余额列表
export function listUserBalance(query) {
  return request({
    url: '/manage/userBalance/list',
    method: 'get',
    params: query
  })
}

// 查询用户余额详细
export function getUserBalance(id) {
  return request({
    url: '/manage/userBalance/' + id,
    method: 'get'
  })
}

// 新增用户余额
export function addUserBalance(data) {
  return request({
    url: '/manage/userBalance',
    method: 'post',
    data: data
  })
}

// 修改用户余额
export function updateUserBalance(data) {
  return request({
    url: '/manage/userBalance',
    method: 'put',
    data: data
  })
}

// 删除用户余额
export function delUserBalance(id) {
  return request({
    url: '/manage/userBalance/' + id,
    method: 'delete'
  })
}
