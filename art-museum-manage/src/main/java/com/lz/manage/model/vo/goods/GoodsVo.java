package com.lz.manage.model.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Goods;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息Vo对象 tb_goods
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class GoodsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 关联藏品
     */
    private Long collectionId;
    /**
     * 藏品名称
     */
    private String collectionName;

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
     * 创建人
     */
    private Long userId;
    /**
     * 创建人名称
     */
    private String userName;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param goods Goods实体对象
     * @return GoodsVo
     */
    public static GoodsVo objToVo(Goods goods) {
        if (goods == null) {
            return null;
        }
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);
        return goodsVo;
    }
}
