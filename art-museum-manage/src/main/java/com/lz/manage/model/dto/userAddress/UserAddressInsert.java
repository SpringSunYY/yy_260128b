package com.lz.manage.model.dto.userAddress;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.UserAddress;
/**
 * 用户地址Vo对象 tb_user_address
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserAddressInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 用户 */
    private Long userId;

    /** 手机号码 */
    private String phone;

    /** 省份 */
    private String province;

    /** 市区 */
    private String city;

    /** 区县 */
    private String county;

    /** 详细地址 */
    private String address;

    /** 是否默认 */
    private String isDefault;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param userAddressInsert 插入对象
     * @return UserAddressInsert
     */
    public static UserAddress insertToObj(UserAddressInsert userAddressInsert) {
        if (userAddressInsert == null) {
            return null;
        }
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressInsert, userAddress);
        return userAddress;
    }
}
