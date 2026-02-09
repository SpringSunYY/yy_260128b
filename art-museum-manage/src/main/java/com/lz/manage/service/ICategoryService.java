package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Category;
import com.lz.manage.model.vo.category.CategoryVo;
import com.lz.manage.model.dto.category.CategoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 分类标签Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface ICategoryService extends IService<Category>
{
    //region mybatis代码
    /**
     * 查询分类标签
     * 
     * @param id 分类标签主键
     * @return 分类标签
     */
    public Category selectCategoryById(Long id);

    /**
     * 查询分类标签列表
     * 
     * @param category 分类标签
     * @return 分类标签集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增分类标签
     * 
     * @param category 分类标签
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改分类标签
     * 
     * @param category 分类标签
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除分类标签
     * 
     * @param ids 需要删除的分类标签主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);

    /**
     * 删除分类标签信息
     * 
     * @param id 分类标签主键
     * @return 结果
     */
    public int deleteCategoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param categoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Category> getQueryWrapper(CategoryQuery categoryQuery);

    /**
     * 转换vo
     *
     * @param categoryList Category集合
     * @return CategoryVO集合
     */
    List<CategoryVo> convertVoList(List<Category> categoryList);
}
