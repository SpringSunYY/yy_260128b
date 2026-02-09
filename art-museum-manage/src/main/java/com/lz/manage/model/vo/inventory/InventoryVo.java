package com.lz.manage.model.vo.inventory;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Inventory;
/**
 * 库存信息Vo对象 tb_inventory
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class InventoryVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 商品 */
    private Long goodsId;

    /** 类型 */
    private String type;

    /** 名称 */
    private String name;

    /** 价格 */
    private Long price;

    /** 数量 */
    private Long numbers;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inventory;

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
     * @param inventory Inventory实体对象
     * @return InventoryVo
     */
    public static InventoryVo objToVo(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryVo inventoryVo = new InventoryVo();
        BeanUtils.copyProperties(inventory, inventoryVo);
        return inventoryVo;
    }
}
