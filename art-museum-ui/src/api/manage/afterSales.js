import request from '@/utils/request'

// 查询售后信息列表
export function listAfterSales(query) {
  return request({
    url: '/manage/afterSales/list',
    method: 'get',
    params: query
  })
}

// 查询售后信息详细
export function getAfterSales(id) {
  return request({
    url: '/manage/afterSales/' + id,
    method: 'get'
  })
}

// 新增售后信息
export function addAfterSales(data) {
  return request({
    url: '/manage/afterSales',
    method: 'post',
    data: data
  })
}

// 修改售后信息
export function updateAfterSales(data) {
  return request({
    url: '/manage/afterSales',
    method: 'put',
    data: data
  })
}

//审核售后信息
export function auditAfterSales(data) {
  return request({
    url: '/manage/afterSales/audit',
    method: 'put',
    data: data
  })
}

// 删除售后信息
export function delAfterSales(id) {
  return request({
    url: '/manage/afterSales/' + id,
    method: 'delete'
  })
}
