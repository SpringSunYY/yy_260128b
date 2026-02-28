package com.lz.manage.model.vo.noticeInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.NoticeInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯信息Vo对象 tb_notice_info
 *
 * @author YY
 * @date 2026-02-10
 */
@Data
public class NoticeInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private String type;

    /**
     * 排序
     */
    private Long sortNum;

    /**
     * 状态
     */
    private String status;

    /**
     * 关联藏品
     */
    private String collectionIds;
    private String collectionNames;

    /**
     * 内容
     */
    private String content;

    /**
     * 收藏数
     */
    private Long collectNumber;

    private Boolean isCollect;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param noticeInfo NoticeInfo实体对象
     * @return NoticeInfoVo
     */
    public static NoticeInfoVo objToVo(NoticeInfo noticeInfo) {
        if (noticeInfo == null) {
            return null;
        }
        NoticeInfoVo noticeInfoVo = new NoticeInfoVo();
        BeanUtils.copyProperties(noticeInfo, noticeInfoVo);
        return noticeInfoVo;
    }
}
