package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 分类标签Mapper接口
 *
 * @author YY
 * @date 2026-02-09
 */
public interface CategoryMapper extends BaseMapper<Category>
{
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
     * 删除分类标签
     *
     * @param id 分类标签主键
     * @return 结果
     */
    public int deleteCategoryById(Long id);

    /**
     * 批量删除分类标签
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);

    List<Category> selectCategoryChildrenById(@Param("id") Long id);
}
