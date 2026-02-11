package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.UserAddressMapper;
import com.lz.manage.model.domain.UserAddress;
import com.lz.manage.model.dto.userAddress.UserAddressQuery;
import com.lz.manage.model.enums.YesNoEnum;
import com.lz.manage.model.vo.userAddress.UserAddressVo;
import com.lz.manage.service.IUserAddressService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户地址Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;


    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询用户地址
     *
     * @param id 用户地址主键
     * @return 用户地址
     */
    @Override
    public UserAddress selectUserAddressById(Long id) {
        return userAddressMapper.selectUserAddressById(id);
    }

    /**
     * 查询用户地址列表
     *
     * @param userAddress 用户地址
     * @return 用户地址
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddress userAddress) {
        List<UserAddress> userAddresses = userAddressMapper.selectUserAddressList(userAddress);
        for (UserAddress info : userAddresses) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return userAddresses;
    }

    /**
     * 新增用户地址
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress) {

        userAddress.setUserId(SecurityUtils.getUserId());
        // 如果传过来的是默认地址，则将所有地址改为非默认
        if (userAddress.getIsDefault().equals(YesNoEnum.SYS_YES_NO_Y.getValue())) {
            UserAddress updateAddress = new UserAddress();
            updateAddress.setIsDefault(YesNoEnum.SYS_YES_NO_N.getValue());
            userAddressMapper.update(
                    updateAddress,
                    new LambdaQueryWrapper<UserAddress>()
                            .eq(UserAddress::getUserId, userAddress.getUserId())
            );
        }

        userAddress.setCreateTime(DateUtils.getNowDate());
        return userAddressMapper.insertUserAddress(userAddress);
    }

    /**
     * 修改用户地址
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @Override
    public int updateUserAddress(UserAddress userAddress) {
             // 如果传过来的是默认地址，则将所有地址改为非默认
        if (userAddress.getIsDefault().equals(YesNoEnum.SYS_YES_NO_Y.getValue())) {
            UserAddress updateAddress = new UserAddress();
            updateAddress.setIsDefault(YesNoEnum.SYS_YES_NO_N.getValue());
            userAddressMapper.update(
                    updateAddress,
                    new LambdaQueryWrapper<UserAddress>()
                            .eq(UserAddress::getUserId, userAddress.getUserId())
            );
        }
        userAddress.setUpdateBy(SecurityUtils.getUsername());
        userAddress.setUpdateTime(DateUtils.getNowDate());
        return userAddressMapper.updateUserAddress(userAddress);
    }

    /**
     * 批量删除用户地址
     *
     * @param ids 需要删除的用户地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByIds(Long[] ids) {
        return userAddressMapper.deleteUserAddressByIds(ids);
    }

    /**
     * 删除用户地址信息
     *
     * @param id 用户地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressById(Long id) {
        return userAddressMapper.deleteUserAddressById(id);
    }

    //endregion
    @Override
    public QueryWrapper<UserAddress> getQueryWrapper(UserAddressQuery userAddressQuery) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = userAddressQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = userAddressQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long userId = userAddressQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String phone = userAddressQuery.getPhone();
        queryWrapper.like(StringUtils.isNotEmpty(phone), "phone", phone);

        String province = userAddressQuery.getProvince();
        queryWrapper.like(StringUtils.isNotEmpty(province), "province", province);

        String city = userAddressQuery.getCity();
        queryWrapper.like(StringUtils.isNotEmpty(city), "city", city);

        String county = userAddressQuery.getCounty();
        queryWrapper.like(StringUtils.isNotEmpty(county), "county", county);

        String isDefault = userAddressQuery.getIsDefault();
        queryWrapper.eq(StringUtils.isNotEmpty(isDefault), "is_default", isDefault);

        Date createTime = userAddressQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<UserAddressVo> convertVoList(List<UserAddress> userAddressList) {
        if (StringUtils.isEmpty(userAddressList)) {
            return Collections.emptyList();
        }
        return userAddressList.stream().map(UserAddressVo::objToVo).collect(Collectors.toList());
    }

}
