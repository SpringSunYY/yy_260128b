import request from '@/utils/request'

// 查询资讯信息列表
export function listNoticeInfo(query) {
  return request({
    url: '/manage/noticeInfo/list',
    method: 'get',
    params: query
  })
}

// 查询资讯信息详细
export function getNoticeInfo(id) {
  return request({
    url: '/manage/noticeInfo/' + id,
    method: 'get'
  })
}

//查询资讯信息详细
export function getNoticeInfoDetail(id) {
  return request({
    url: '/manage/noticeInfo/detail/' + id,
    method: 'get'
  })
}

// 新增资讯信息
export function addNoticeInfo(data) {
  return request({
    url: '/manage/noticeInfo',
    method: 'post',
    data: data
  })
}

// 修改资讯信息
export function updateNoticeInfo(data) {
  return request({
    url: '/manage/noticeInfo',
    method: 'put',
    data: data
  })
}

// 删除资讯信息
export function delNoticeInfo(id) {
  return request({
    url: '/manage/noticeInfo/' + id,
    method: 'delete'
  })
}
