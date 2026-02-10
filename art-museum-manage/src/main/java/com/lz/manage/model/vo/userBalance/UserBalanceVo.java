package com.lz.manage.model.vo.userBalance;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.UserBalance;
/**
 * 用户余额Vo对象 tb_user_balance
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserBalanceVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;
    private String userName;

    /** 当前余额 */
    private BigDecimal balance;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


     /**
     * 对象转封装类
     *
     * @param userBalance UserBalance实体对象
     * @return UserBalanceVo
     */
    public static UserBalanceVo objToVo(UserBalance userBalance) {
        if (userBalance == null) {
            return null;
        }
        UserBalanceVo userBalanceVo = new UserBalanceVo();
        BeanUtils.copyProperties(userBalance, userBalanceVo);
        return userBalanceVo;
    }
}
