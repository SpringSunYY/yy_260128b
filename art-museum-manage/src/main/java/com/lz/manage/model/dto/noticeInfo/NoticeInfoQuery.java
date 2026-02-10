package com.lz.manage.model.dto.noticeInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.NoticeInfo;
/**
 * 咨询信息Query对象 tb_notice_info
 *
 * @author YY
 * @date 2026-02-10
 */
@Data
public class NoticeInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 标题 */
    private String title;

    /** 类型 */
    private String type;

    /** 状态 */
    private String status;

    /** 关联藏品 */
    private String collectionIds;

    /** 更新人 */
    private String updateBy;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param noticeInfoQuery 查询对象
     * @return NoticeInfo
     */
    public static NoticeInfo queryToObj(NoticeInfoQuery noticeInfoQuery) {
        if (noticeInfoQuery == null) {
            return null;
        }
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeInfoQuery, noticeInfo);
        return noticeInfo;
    }
}
