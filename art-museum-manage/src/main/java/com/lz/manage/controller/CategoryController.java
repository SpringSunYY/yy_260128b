package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.Category;
import com.lz.manage.model.vo.category.CategoryVo;
import com.lz.manage.model.dto.category.CategoryQuery;
import com.lz.manage.model.dto.category.CategoryInsert;
import com.lz.manage.model.dto.category.CategoryEdit;
import com.lz.manage.service.ICategoryService;
import com.lz.common.utils.poi.ExcelUtil;

/**
 * 分类标签Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryController extends BaseController
{
    @Resource
    private ICategoryService categoryService;

    /**
     * 查询分类标签列表
     */
    @PreAuthorize("@ss.hasPermi('manage:category:list')")
    @GetMapping("/list")
    public AjaxResult list(CategoryQuery categoryQuery)
    {
        Category category = CategoryQuery.queryToObj(categoryQuery);
        List<Category> list = categoryService.selectCategoryList(category);
        List<CategoryVo> listVo= list.stream().map(CategoryVo::objToVo).collect(Collectors.toList());
        return success(listVo);
    }

    /**
     * 导出分类标签列表
     */
    @PreAuthorize("@ss.hasPermi('manage:category:export')")
    @Log(title = "分类标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CategoryQuery categoryQuery)
    {
        Category category = CategoryQuery.queryToObj(categoryQuery);
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        util.exportExcel(response, list, "分类标签数据");
    }

    /**
     * 获取分类标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Category category = categoryService.selectCategoryById(id);
        return success(CategoryVo.objToVo(category));
    }

    /**
     * 新增分类标签
     */
    @PreAuthorize("@ss.hasPermi('manage:category:add')")
    @Log(title = "分类标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryInsert categoryInsert)
    {
        Category category = CategoryInsert.insertToObj(categoryInsert);
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改分类标签
     */
    @PreAuthorize("@ss.hasPermi('manage:category:edit')")
    @Log(title = "分类标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryEdit categoryEdit)
    {
        Category category = CategoryEdit.editToObj(categoryEdit);
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除分类标签
     */
    @PreAuthorize("@ss.hasPermi('manage:category:remove')")
    @Log(title = "分类标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }
}
