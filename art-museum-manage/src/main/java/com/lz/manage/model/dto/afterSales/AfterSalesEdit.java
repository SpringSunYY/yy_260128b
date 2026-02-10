package com.lz.manage.model.dto.afterSales;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.AfterSales;
/**
 * 售后信息Vo对象 tb_after_sales
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class AfterSalesEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 订单 */
    private Long orderId;

    /** 商品 */
    private Long goodsId;

    /** 用户 */
    private Long userId;

    /** 售后类型 */
    private String type;

    /** 申请理由 */
    private String apply;

    /** 审核状态 */
    private String auditStatus;

    /** 审核人 */
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核原因 */
    private String auditDesc;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param afterSalesEdit 编辑对象
     * @return AfterSales
     */
    public static AfterSales editToObj(AfterSalesEdit afterSalesEdit) {
        if (afterSalesEdit == null) {
            return null;
        }
        AfterSales afterSales = new AfterSales();
        BeanUtils.copyProperties(afterSalesEdit, afterSales);
        return afterSales;
    }
}
