package com.lz.manage.model.dto.userBalance;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.UserBalance;
/**
 * 用户余额Query对象 tb_user_balance
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserBalanceQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户 */
    private Long userId;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param userBalanceQuery 查询对象
     * @return UserBalance
     */
    public static UserBalance queryToObj(UserBalanceQuery userBalanceQuery) {
        if (userBalanceQuery == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        BeanUtils.copyProperties(userBalanceQuery, userBalance);
        return userBalance;
    }
}
