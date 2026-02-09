package com.lz.manage.model.vo.userAddress;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.UserAddress;
/**
 * 用户地址Vo对象 tb_user_address
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class UserAddressVo implements Serializable
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

    /** 详细地址 */
    private String address;

    /** 是否默认 */
    private String isDefault;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param userAddress UserAddress实体对象
     * @return UserAddressVo
     */
    public static UserAddressVo objToVo(UserAddress userAddress) {
        if (userAddress == null) {
            return null;
        }
        UserAddressVo userAddressVo = new UserAddressVo();
        BeanUtils.copyProperties(userAddress, userAddressVo);
        return userAddressVo;
    }
}
