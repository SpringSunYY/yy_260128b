package com.lz.manage.model.dto.afterSales;

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
import com.lz.manage.model.domain.AfterSales;
/**
 * 售后信息Query对象 tb_after_sales
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class AfterSalesQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 售后类型 */
    private String type;

    /** 审核状态 */
    private String auditStatus;

    /** 审核人 */
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param afterSalesQuery 查询对象
     * @return AfterSales
     */
    public static AfterSales queryToObj(AfterSalesQuery afterSalesQuery) {
        if (afterSalesQuery == null) {
            return null;
        }
        AfterSales afterSales = new AfterSales();
        BeanUtils.copyProperties(afterSalesQuery, afterSales);
        return afterSales;
    }
}
