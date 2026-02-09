package com.lz.manage.model.dto.userBalance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.UserBalance;
/**
 * 用户余额Vo对象 tb_user_balance
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserBalanceInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 用户 */
    private Long userId;

    /** 当前余额 */
    private BigDecimal balance;

    /**
     * 对象转封装类
     *
     * @param userBalanceInsert 插入对象
     * @return UserBalanceInsert
     */
    public static UserBalance insertToObj(UserBalanceInsert userBalanceInsert) {
        if (userBalanceInsert == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        BeanUtils.copyProperties(userBalanceInsert, userBalance);
        return userBalance;
    }
}
