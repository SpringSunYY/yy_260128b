package com.lz.manage.model.dto.noticeInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.NoticeInfo;
/**
 * 资讯信息Vo对象 tb_notice_info
 *
 * @author YY
 * @date 2026-02-10
 */
@Data
public class NoticeInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param noticeInfoEdit 编辑对象
     * @return NoticeInfo
     */
    public static NoticeInfo editToObj(NoticeInfoEdit noticeInfoEdit) {
        if (noticeInfoEdit == null) {
            return null;
        }
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeInfoEdit, noticeInfo);
        return noticeInfo;
    }
}
