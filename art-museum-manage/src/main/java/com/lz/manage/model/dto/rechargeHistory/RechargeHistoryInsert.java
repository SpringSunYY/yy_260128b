package com.lz.manage.model.dto.rechargeHistory;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.RechargeHistory;
/**
 * 充值记录Vo对象 tb_recharge_history
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class RechargeHistoryInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 用户 */
    private Long userId;

    /** 充值价格 */
    private Long rechargePrice;

    /** 充值凭证 */
    private String rechargeVoucher;

    /** 审核状态 */
    private Long auditStatus;

    /** 审核人 */
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核原因 */
    private String auditDesc;

    /**
     * 对象转封装类
     *
     * @param rechargeHistoryInsert 插入对象
     * @return RechargeHistoryInsert
     */
    public static RechargeHistory insertToObj(RechargeHistoryInsert rechargeHistoryInsert) {
        if (rechargeHistoryInsert == null) {
            return null;
        }
        RechargeHistory rechargeHistory = new RechargeHistory();
        BeanUtils.copyProperties(rechargeHistoryInsert, rechargeHistory);
        return rechargeHistory;
    }
}
