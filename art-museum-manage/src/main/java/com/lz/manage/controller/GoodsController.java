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
import com.lz.manage.model.domain.Goods;
import com.lz.manage.model.vo.goods.GoodsVo;
import com.lz.manage.model.dto.goods.GoodsQuery;
import com.lz.manage.model.dto.goods.GoodsInsert;
import com.lz.manage.model.dto.goods.GoodsEdit;
import com.lz.manage.service.IGoodsService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 商品信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/goods")
public class GoodsController extends BaseController
{
    @Resource
    private IGoodsService goodsService;

    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsQuery goodsQuery)
    {
        Goods goods = GoodsQuery.queryToObj(goodsQuery);
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        List<GoodsVo> listVo= list.stream().map(GoodsVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsQuery goodsQuery)
    {
        Goods goods = GoodsQuery.queryToObj(goodsQuery);
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "商品信息数据");
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Goods goods = goodsService.selectGoodsById(id);
        return success(GoodsVo.objToVo(goods));
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsInsert goodsInsert)
    {
        Goods goods = GoodsInsert.insertToObj(goodsInsert);
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsEdit goodsEdit)
    {
        Goods goods = GoodsEdit.editToObj(goodsEdit);
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:goods:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }
}
