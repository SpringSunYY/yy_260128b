package com.lz.manage.model.dto.inventory;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.Inventory;
/**
 * 库存信息Query对象 tb_inventory
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class InventoryQuery implements Serializable
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

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inventory;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param inventoryQuery 查询对象
     * @return Inventory
     */
    public static Inventory queryToObj(InventoryQuery inventoryQuery) {
        if (inventoryQuery == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(inventoryQuery, inventory);
        return inventory;
    }
}
