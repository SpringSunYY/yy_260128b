package com.lz.manage.model.dto.goods;

import com.lz.manage.model.domain.Goods;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息Vo对象 tb_goods
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class GoodsInsert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联藏品
     */
    private Long collectionId;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 销量
     */
    private Long sales;

    /**
     * 状态
     */
    private String status;

    /**
     * 库存
     */
    private Long inventory;

    /**
     * 主图
     */
    private String imageSrc;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param goodsInsert 插入对象
     * @return GoodsInsert
     */
    public static Goods insertToObj(GoodsInsert goodsInsert) {
        if (goodsInsert == null) {
            return null;
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsInsert, goods);
        return goods;
    }
}
