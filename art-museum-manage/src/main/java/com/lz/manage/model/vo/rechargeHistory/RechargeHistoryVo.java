package com.lz.manage.model.vo.rechargeHistory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RechargeHistory;
/**
 * 充值记录Vo对象 tb_recharge_history
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class RechargeHistoryVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;
    private String userName;

    /** 充值价格 */
    private BigDecimal rechargePrice;

    /** 充值凭证 */
    private String rechargeVoucher;

    /** 审核状态 */
    private String auditStatus;

    /** 审核人 */
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核原因 */
    private String auditDesc;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;


     /**
     * 对象转封装类
     *
     * @param rechargeHistory RechargeHistory实体对象
     * @return RechargeHistoryVo
     */
    public static RechargeHistoryVo objToVo(RechargeHistory rechargeHistory) {
        if (rechargeHistory == null) {
            return null;
        }
        RechargeHistoryVo rechargeHistoryVo = new RechargeHistoryVo();
        BeanUtils.copyProperties(rechargeHistory, rechargeHistoryVo);
        return rechargeHistoryVo;
    }
}
