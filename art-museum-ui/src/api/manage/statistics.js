import request from '@/utils/request'
//收藏统计
export function collectStatistics(query){
  return request({
    url: '/manage/statistics/collect',
    method: 'get',
    params: query
  })
}


//收藏排行统计
export function collectRankStatistics(query){
  return request({
    url: '/manage/statistics/collect/rank',
    method: 'get',
    params: query
  })
}
