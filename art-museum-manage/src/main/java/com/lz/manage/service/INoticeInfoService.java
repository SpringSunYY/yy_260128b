package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.model.vo.noticeInfo.NoticeInfoVo;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 咨询信息Service接口
 *
 * @author YY
 * @date 2026-02-10
 */
public interface INoticeInfoService extends IService<NoticeInfo>
{
    //region mybatis代码
    /**
     * 查询咨询信息
     *
     * @param id 咨询信息主键
     * @return 咨询信息
     */
    public NoticeInfo selectNoticeInfoById(Long id);

    /**
     * 查询咨询信息
     *
     * @param id 咨询信息主键
     * @return 咨询信息
     */
    NoticeInfoVo selectNoticeInfoDetailById(Long id);

    /**
     * 查询咨询信息列表
     *
     * @param noticeInfo 咨询信息
     * @return 咨询信息集合
     */
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo);

    /**
     * 新增咨询信息
     *
     * @param noticeInfo 咨询信息
     * @return 结果
     */
    public int insertNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 修改咨询信息
     *
     * @param noticeInfo 咨询信息
     * @return 结果
     */
    public int updateNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 批量删除咨询信息
     *
     * @param ids 需要删除的咨询信息主键集合
     * @return 结果
     */
    public int deleteNoticeInfoByIds(Long[] ids);

    /**
     * 删除咨询信息信息
     *
     * @param id 咨询信息主键
     * @return 结果
     */
    public int deleteNoticeInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param noticeInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<NoticeInfo> getQueryWrapper(NoticeInfoQuery noticeInfoQuery);

    /**
     * 转换vo
     *
     * @param noticeInfoList NoticeInfo集合
     * @return NoticeInfoVO集合
     */
    List<NoticeInfoVo> convertVoList(List<NoticeInfo> noticeInfoList);
}
