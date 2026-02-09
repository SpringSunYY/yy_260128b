package com.lz.manage.model.dto.category;

import com.lz.manage.model.domain.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 分类标签Vo对象 tb_category
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CategoryInsert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param categoryInsert 插入对象
     * @return CategoryInsert
     */
    public static Category insertToObj(CategoryInsert categoryInsert) {
        if (categoryInsert == null) {
            return null;
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryInsert, category);
        return category;
    }
}
