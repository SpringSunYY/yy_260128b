import request from '@/utils/request'


//支付
export function payOrder(id) {
  return request({
    url: '/manage/order/payOrder/' + id,
    method: 'get'
  })
}

//发货
export function deliveryOrder(id) {
  return request({
    url: '/manage/order/deliveryOrder/' + id,
    method: 'get'
  })
}

//收货
export function receiveOrder(id) {
  return request({
    url: '/manage/order/receiveOrder/' + id,
    method: 'get'
  })
}

// 查询订单信息列表
export function listOrder(query) {
  return request({
    url: '/manage/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getOrder(id) {
  return request({
    url: '/manage/order/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addOrder(data) {
  return request({
    url: '/manage/order',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrder(data) {
  return request({
    url: '/manage/order',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrder(id) {
  return request({
    url: '/manage/order/' + id,
    method: 'delete'
  })
}
