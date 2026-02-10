package com.lz.manage.model.vo.collect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Collect;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏信息Vo对象 tb_collect
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 目标
     */
    private Long targetId;
    /**
     * 目标名称
     */
    private String targetName;

    /**
     * 创建人
     */
    private Long userId;
    /**
     * 创建人名称
     */
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    /**
     * 对象转封装类
     *
     * @param collect Collect实体对象
     * @return CollectVo
     */
    public static CollectVo objToVo(Collect collect) {
        if (collect == null) {
            return null;
        }
        CollectVo collectVo = new CollectVo();
        BeanUtils.copyProperties(collect, collectVo);
        return collectVo;
    }
}
