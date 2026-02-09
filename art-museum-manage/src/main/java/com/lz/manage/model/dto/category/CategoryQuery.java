package com.lz.manage.model.dto.category;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.manage.model.domain.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 分类标签Query对象 tb_category
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CategoryQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param categoryQuery 查询对象
     * @return Category
     */
    public static Category queryToObj(CategoryQuery categoryQuery) {
        if (categoryQuery == null) {
            return null;
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryQuery, category);
        return category;
    }
}
