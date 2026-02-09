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
public class CollectInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 类型 */
    private String type;

    /** 目标 */
    private Long targetId;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param collectInsert 插入对象
     * @return CollectInsert
     */
    public static Collect insertToObj(CollectInsert collectInsert) {
        if (collectInsert == null) {
            return null;
        }
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectInsert, collect);
        return collect;
    }
}
