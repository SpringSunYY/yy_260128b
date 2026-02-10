package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.NoticeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 咨询信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-10
 */
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo>
{
    /**
     * 查询咨询信息
     * 
     * @param id 咨询信息主键
     * @return 咨询信息
     */
    public NoticeInfo selectNoticeInfoById(Long id);

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
     * 删除咨询信息
     * 
     * @param id 咨询信息主键
     * @return 结果
     */
    public int deleteNoticeInfoById(Long id);

    /**
     * 批量删除咨询信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNoticeInfoByIds(Long[] ids);
}
