package com.lz.manage.model.dto.inventory;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Inventory;
/**
 * 库存信息Vo对象 tb_inventory
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class InventoryEdit implements Serializable
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

    /**
     * 对象转封装类
     *
     * @param inventoryEdit 编辑对象
     * @return Inventory
     */
    public static Inventory editToObj(InventoryEdit inventoryEdit) {
        if (inventoryEdit == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(inventoryEdit, inventory);
        return inventory;
    }
}
