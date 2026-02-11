import request from '@/utils/request'

//收藏统计
export function collectStatistics(query) {
  return request({
    url: '/manage/statistics/collect',
    method: 'get',
    params: query
  })
}


//收藏排行统计
export function collectRankStatistics(query) {
  return request({
    url: '/manage/statistics/collect/rank',
    method: 'get',
    params: query
  })
}


//订单成交比例
export function orderRatioStatistics(query) {
  return request({
    url: '/manage/statistics/order/ratio',
    method: 'get',
    params: query
  })
}

//订单每日金额
export function orderAmountStatistics(query) {
  return request({
    url: '/manage/statistics/order/amount',
    method: 'get',
    params: query
  })
}

//订单统计
export function orderStatistics(query) {
  return request({
    url: '/manage/statistics/order',
    method: 'get',
    params: query
  })
}
