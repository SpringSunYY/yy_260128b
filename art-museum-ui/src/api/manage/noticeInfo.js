import request from '@/utils/request'

// 查询咨询信息列表
export function listNoticeInfo(query) {
  return request({
    url: '/manage/noticeInfo/list',
    method: 'get',
    params: query
  })
}

// 查询咨询信息详细
export function getNoticeInfo(id) {
  return request({
    url: '/manage/noticeInfo/' + id,
    method: 'get'
  })
}

// 新增咨询信息
export function addNoticeInfo(data) {
  return request({
    url: '/manage/noticeInfo',
    method: 'post',
    data: data
  })
}

// 修改咨询信息
export function updateNoticeInfo(data) {
  return request({
    url: '/manage/noticeInfo',
    method: 'put',
    data: data
  })
}

// 删除咨询信息
export function delNoticeInfo(id) {
  return request({
    url: '/manage/noticeInfo/' + id,
    method: 'delete'
  })
}
