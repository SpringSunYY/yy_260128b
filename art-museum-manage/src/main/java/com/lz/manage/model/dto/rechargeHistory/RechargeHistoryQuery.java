package com.lz.manage.model.dto.rechargeHistory;

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
import com.lz.manage.model.domain.RechargeHistory;
/**
 * 充值记录Query对象 tb_recharge_history
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class RechargeHistoryQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;

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

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param rechargeHistoryQuery 查询对象
     * @return RechargeHistory
     */
    public static RechargeHistory queryToObj(RechargeHistoryQuery rechargeHistoryQuery) {
        if (rechargeHistoryQuery == null) {
            return null;
        }
        RechargeHistory rechargeHistory = new RechargeHistory();
        BeanUtils.copyProperties(rechargeHistoryQuery, rechargeHistory);
        return rechargeHistory;
    }
}
