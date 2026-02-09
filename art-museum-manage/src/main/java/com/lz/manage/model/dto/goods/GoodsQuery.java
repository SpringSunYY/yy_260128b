package com.lz.manage.model.dto.goods;

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
import com.lz.manage.model.domain.Goods;
/**
 * 商品信息Query对象 tb_goods
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class GoodsQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 关联藏品 */
    private Long collectionId;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

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
     * @param goodsQuery 查询对象
     * @return Goods
     */
    public static Goods queryToObj(GoodsQuery goodsQuery) {
        if (goodsQuery == null) {
            return null;
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsQuery, goods);
        return goods;
    }
}
