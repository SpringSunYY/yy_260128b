package com.lz.manage.model.dto.noticeInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.NoticeInfo;
/**
 * 咨询信息Vo对象 tb_notice_info
 *
 * @author YY
 * @date 2026-02-10
 */
@Data
public class NoticeInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 标题 */
    private String title;

    /** 类型 */
    private String type;

    /** 排序 */
    private Long sortNum;

    /** 状态 */
    private String status;

    /** 关联藏品 */
    private String collectionIds;

    /** 内容 */
    private String content;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param noticeInfoInsert 插入对象
     * @return NoticeInfoInsert
     */
    public static NoticeInfo insertToObj(NoticeInfoInsert noticeInfoInsert) {
        if (noticeInfoInsert == null) {
            return null;
        }
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeInfoInsert, noticeInfo);
        return noticeInfo;
    }
}
