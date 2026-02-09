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
public class UserBalanceEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;

    /** 当前余额 */
    private BigDecimal balance;

    /**
     * 对象转封装类
     *
     * @param userBalanceEdit 编辑对象
     * @return UserBalance
     */
    public static UserBalance editToObj(UserBalanceEdit userBalanceEdit) {
        if (userBalanceEdit == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        BeanUtils.copyProperties(userBalanceEdit, userBalance);
        return userBalance;
    }
}
