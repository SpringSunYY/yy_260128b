package com.lz.manage.model.dto.goods;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Goods;
/**
 * 商品信息Vo对象 tb_goods
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class GoodsEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 关联藏品 */
    private Long collectionId;

    /** 名称 */
    private String name;

    /** 价格 */
    private Long price;

    /** 销量 */
    private Long sales;

    /** 状态 */
    private String status;

    /** 库存 */
    private Long inventory;

    /** 主图 */
    private String imageSrc;

    /** 描述 */
    private String description;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param goodsEdit 编辑对象
     * @return Goods
     */
    public static Goods editToObj(GoodsEdit goodsEdit) {
        if (goodsEdit == null) {
            return null;
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsEdit, goods);
        return goods;
    }
}
