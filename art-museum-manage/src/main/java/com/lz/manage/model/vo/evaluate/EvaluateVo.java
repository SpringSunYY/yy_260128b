package com.lz.manage.model.vo.evaluate;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Evaluate;
/**
 * 评价信息Vo对象 tb_evaluate
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class EvaluateVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 藏品 */
    private Long collectionId;
    private String collectionName;

    /** 状态 */
    private String status;

    /** 标题 */
    private String title;

    /** 评分 */
    private Long score;

    /** 评价内容 */
    private String content;

    /** 创建人 */
    private Long userId;
    private String userName;

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param evaluate Evaluate实体对象
     * @return EvaluateVo
     */
    public static EvaluateVo objToVo(Evaluate evaluate) {
        if (evaluate == null) {
            return null;
        }
        EvaluateVo evaluateVo = new EvaluateVo();
        BeanUtils.copyProperties(evaluate, evaluateVo);
        return evaluateVo;
    }
}
