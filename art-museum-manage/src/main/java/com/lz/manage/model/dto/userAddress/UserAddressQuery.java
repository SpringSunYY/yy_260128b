package com.lz.manage.model.dto.userAddress;

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
import com.lz.manage.model.domain.UserAddress;
/**
 * 用户地址Query对象 tb_user_address
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserAddressQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 是否默认 */
    private String isDefault;

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
     * @param userAddressQuery 查询对象
     * @return UserAddress
     */
    public static UserAddress queryToObj(UserAddressQuery userAddressQuery) {
        if (userAddressQuery == null) {
            return null;
        }
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressQuery, userAddress);
        return userAddress;
    }
}
