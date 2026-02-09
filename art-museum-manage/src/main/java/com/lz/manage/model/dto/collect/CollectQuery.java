package com.lz.manage.model.dto.collect;

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
import com.lz.manage.model.domain.Collect;
/**
 * 收藏信息Query对象 tb_collect
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 类型 */
    private String type;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param collectQuery 查询对象
     * @return Collect
     */
    public static Collect queryToObj(CollectQuery collectQuery) {
        if (collectQuery == null) {
            return null;
        }
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectQuery, collect);
        return collect;
    }
}
