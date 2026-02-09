package com.lz.manage.model.dto.category;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Category;
/**
 * 分类标签Vo对象 tb_category
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CategoryEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 父级编号 */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param categoryEdit 编辑对象
     * @return Category
     */
    public static Category editToObj(CategoryEdit categoryEdit) {
        if (categoryEdit == null) {
            return null;
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryEdit, category);
        return category;
    }
}
