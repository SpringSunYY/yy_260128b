package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.NoticeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 资讯信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-10
 */
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo>
{
    /**
     * 查询资讯信息
     * 
     * @param id 资讯信息主键
     * @return 资讯信息
     */
    public NoticeInfo selectNoticeInfoById(Long id);

    /**
     * 查询资讯信息列表
     * 
     * @param noticeInfo 资讯信息
     * @return 资讯信息集合
     */
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo);

    /**
     * 新增资讯信息
     * 
     * @param noticeInfo 资讯信息
     * @return 结果
     */
    public int insertNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 修改资讯信息
     * 
     * @param noticeInfo 资讯信息
     * @return 结果
     */
    public int updateNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 删除资讯信息
     * 
     * @param id 资讯信息主键
     * @return 结果
     */
    public int deleteNoticeInfoById(Long id);

    /**
     * 批量删除资讯信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNoticeInfoByIds(Long[] ids);
}
