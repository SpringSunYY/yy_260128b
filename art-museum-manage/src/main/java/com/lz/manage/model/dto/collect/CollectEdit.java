package com.lz.manage.model.dto.collect;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Collect;
/**
 * 收藏信息Vo对象 tb_collect
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 类型 */
    private String type;

    /** 目标 */
    private Long targetId;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param collectEdit 编辑对象
     * @return Collect
     */
    public static Collect editToObj(CollectEdit collectEdit) {
        if (collectEdit == null) {
            return null;
        }
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectEdit, collect);
        return collect;
    }
}
