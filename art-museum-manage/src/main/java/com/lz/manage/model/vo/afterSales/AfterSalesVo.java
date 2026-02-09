package com.lz.manage.model.vo.afterSales;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.AfterSales;
/**
 * 售后信息Vo对象 tb_after_sales
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class AfterSalesVo implements Serializable
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
    private Long auditStatus;

    /** 审核人 */
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核原因 */
    private String auditDesc;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param afterSales AfterSales实体对象
     * @return AfterSalesVo
     */
    public static AfterSalesVo objToVo(AfterSales afterSales) {
        if (afterSales == null) {
            return null;
        }
        AfterSalesVo afterSalesVo = new AfterSalesVo();
        BeanUtils.copyProperties(afterSales, afterSalesVo);
        return afterSalesVo;
    }
}
