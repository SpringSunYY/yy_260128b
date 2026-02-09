package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.CategoryMapper;
import com.lz.manage.model.domain.Category;
import com.lz.manage.service.ICategoryService;
import com.lz.manage.model.dto.category.CategoryQuery;
import com.lz.manage.model.vo.category.CategoryVo;

/**
 * 分类标签Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService
{

    @Resource
    private CategoryMapper categoryMapper;

    //region mybatis代码
    /**
     * 查询分类标签
     *
     * @param id 分类标签主键
     * @return 分类标签
     */
    @Override
    public Category selectCategoryById(Long id)
    {
        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询分类标签列表
     *
     * @param category 分类标签
     * @return 分类标签
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增分类标签
     *
     * @param category 分类标签
     * @return 结果
     */
    @Override
    public int insertCategory(Category category)
    {
        category.setCreateTime(DateUtils.getNowDate());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改分类标签
     *
     * @param category 分类标签
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        category.setUpdateTime(DateUtils.getNowDate());
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除分类标签
     *
     * @param ids 需要删除的分类标签主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(Long[] ids)
    {
        return categoryMapper.deleteCategoryByIds(ids);
    }

    /**
     * 删除分类标签信息
     *
     * @param id 分类标签主键
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long id)
    {
        return categoryMapper.deleteCategoryById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Category> getQueryWrapper(CategoryQuery categoryQuery){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = categoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = categoryQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = categoryQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        return queryWrapper;
    }

    @Override
    public List<CategoryVo> convertVoList(List<Category> categoryList) {
        if (StringUtils.isEmpty(categoryList)) {
            return Collections.emptyList();
        }
        return categoryList.stream().map(CategoryVo::objToVo).collect(Collectors.toList());
    }

}
