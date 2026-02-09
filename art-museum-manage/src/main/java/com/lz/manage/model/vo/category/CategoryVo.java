package com.lz.manage.model.vo.category;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Category;
/**
 * 分类标签Vo对象 tb_category
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CategoryVo implements Serializable
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

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param category Category实体对象
     * @return CategoryVo
     */
    public static CategoryVo objToVo(Category category) {
        if (category == null) {
            return null;
        }
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
