package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.UserAddressMapper;
import com.lz.manage.model.domain.UserAddress;
import com.lz.manage.service.IUserAddressService;
import com.lz.manage.model.dto.userAddress.UserAddressQuery;
import com.lz.manage.model.vo.userAddress.UserAddressVo;

/**
 * 用户地址Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService
{

    @Resource
    private UserAddressMapper userAddressMapper;

    //region mybatis代码
    /**
     * 查询用户地址
     *
     * @param id 用户地址主键
     * @return 用户地址
     */
    @Override
    public UserAddress selectUserAddressById(Long id)
    {
        return userAddressMapper.selectUserAddressById(id);
    }

    /**
     * 查询用户地址列表
     *
     * @param userAddress 用户地址
     * @return 用户地址
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddress userAddress)
    {
        return userAddressMapper.selectUserAddressList(userAddress);
    }

    /**
     * 新增用户地址
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress)
    {
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
    public int updateUserAddress(UserAddress userAddress)
    {
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
    public int deleteUserAddressByIds(Long[] ids)
    {
        return userAddressMapper.deleteUserAddressByIds(ids);
    }

    /**
     * 删除用户地址信息
     *
     * @param id 用户地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressById(Long id)
    {
        return userAddressMapper.deleteUserAddressById(id);
    }
    //endregion
    @Override
    public QueryWrapper<UserAddress> getQueryWrapper(UserAddressQuery userAddressQuery){
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = userAddressQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = userAddressQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long userId = userAddressQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String phone = userAddressQuery.getPhone();
        queryWrapper.like(StringUtils.isNotEmpty(phone) ,"phone",phone);

        String province = userAddressQuery.getProvince();
        queryWrapper.like(StringUtils.isNotEmpty(province) ,"province",province);

        String city = userAddressQuery.getCity();
        queryWrapper.like(StringUtils.isNotEmpty(city) ,"city",city);

        String county = userAddressQuery.getCounty();
        queryWrapper.like(StringUtils.isNotEmpty(county) ,"county",county);

        String isDefault = userAddressQuery.getIsDefault();
        queryWrapper.eq(StringUtils.isNotEmpty(isDefault) ,"is_default",isDefault);

        Date createTime = userAddressQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
