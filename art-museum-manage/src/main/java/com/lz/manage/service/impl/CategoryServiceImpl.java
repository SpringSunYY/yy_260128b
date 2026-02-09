package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CategoryMapper;
import com.lz.manage.model.domain.Category;
import com.lz.manage.model.dto.category.CategoryQuery;
import com.lz.manage.model.vo.category.CategoryVo;
import com.lz.manage.service.ICategoryService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分类标签Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询分类标签
     *
     * @param id 分类标签主键
     * @return 分类标签
     */
    @Override
    public Category selectCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询分类标签列表
     *
     * @param category 分类标签
     * @return 分类标签
     */
    @Override
    public List<Category> selectCategoryList(Category category) {
        List<Category> categories = categoryMapper.selectCategoryList(category);
        for (Category info : categories) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return categories;
    }

    /**
     * 新增分类标签
     *
     * @param category 分类标签
     * @return 结果
     */
    @Override
    public int insertCategory(Category category) {
        //查询父级
        Category parent = categoryMapper.selectCategoryById(category.getParentId());
        if (StringUtils.isNotNull(parent)) {
            category.setAncestors(parent.getAncestors() + "," + category.getParentId());
        }else {
            category.setAncestors("0");
        }
        category.setUserId(SecurityUtils.getUserId());
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
    public int updateCategory(Category category) {
        Category categoryDb = categoryMapper.selectCategoryById(category.getId());
        ThrowUtils.throwIf(StringUtils.isNull(categoryDb), "分类标签不存在");
        //判断是否父级编号改变
        if (category.getParentId().longValue() != categoryDb.getParentId().longValue()){
            Category oldParent = categoryMapper.selectCategoryById(category.getParentId());
            Category newParent = categoryMapper.selectCategoryById(category.getParentId());
            String newAncestor = "";
            String oldAncestor = "";
            if (StringUtils.isNotNull(oldParent)) {
                oldAncestor = oldParent.getAncestors();
            }
            if (StringUtils.isNotNull(newParent)) {
                newAncestor = newParent.getAncestors()+","+category.getId();
            }
            category.setAncestors(newAncestor);
            updateCategoryChildren(category.getId(), newAncestor, oldAncestor);
        }
        category.setUpdateBy(SecurityUtils.getUsername());
        category.setUpdateTime(DateUtils.getNowDate());
        return categoryMapper.updateCategory(category);
    }

    private void updateCategoryChildren(Long id, String newAncestor, String oldAncestor) {
        //查询所有子部门
        List<Category> children = categoryMapper.selectCategoryChildrenById(id);
        for (Category child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestor, newAncestor));
        }
        if (!children.isEmpty()) {
            categoryMapper.insertOrUpdate(children);
        }
    }

    /**
     * 批量删除分类标签
     *
     * @param ids 需要删除的分类标签主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(Long[] ids) {
        return categoryMapper.deleteCategoryByIds(ids);
    }

    /**
     * 删除分类标签信息
     *
     * @param id 分类标签主键
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long id) {
        return categoryMapper.deleteCategoryById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Category> getQueryWrapper(CategoryQuery categoryQuery) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = categoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = categoryQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String name = categoryQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

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
