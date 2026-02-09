package com.lz.manage.model.dto.evaluate;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Evaluate;
/**
 * 评价信息Vo对象 tb_evaluate
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class EvaluateEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 藏品 */
    private Long collectionId;

    /** 状态 */
    private String status;

    /** 标题 */
    private String title;

    /** 评分 */
    private Long score;

    /** 评价内容 */
    private String content;

    /**
     * 对象转封装类
     *
     * @param evaluateEdit 编辑对象
     * @return Evaluate
     */
    public static Evaluate editToObj(EvaluateEdit evaluateEdit) {
        if (evaluateEdit == null) {
            return null;
        }
        Evaluate evaluate = new Evaluate();
        BeanUtils.copyProperties(evaluateEdit, evaluate);
        return evaluate;
    }
}
