import request from '@/utils/request'

// 查询充值记录列表
export function listRechargeHistory(query) {
  return request({
    url: '/manage/rechargeHistory/list',
    method: 'get',
    params: query
  })
}

// 查询充值记录详细
export function getRechargeHistory(id) {
  return request({
    url: '/manage/rechargeHistory/' + id,
    method: 'get'
  })
}

// 新增充值记录
export function addRechargeHistory(data) {
  return request({
    url: '/manage/rechargeHistory',
    method: 'post',
    data: data
  })
}

// 修改充值记录
export function updateRechargeHistory(data) {
  return request({
    url: '/manage/rechargeHistory',
    method: 'put',
    data: data
  })
}

// 删除充值记录
export function delRechargeHistory(id) {
  return request({
    url: '/manage/rechargeHistory/' + id,
    method: 'delete'
  })
}
