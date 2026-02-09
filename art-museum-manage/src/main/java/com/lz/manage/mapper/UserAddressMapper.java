package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户地址Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface UserAddressMapper extends BaseMapper<UserAddress>
{
    /**
     * 查询用户地址
     * 
     * @param id 用户地址主键
     * @return 用户地址
     */
    public UserAddress selectUserAddressById(Long id);

    /**
     * 查询用户地址列表
     * 
     * @param userAddress 用户地址
     * @return 用户地址集合
     */
    public List<UserAddress> selectUserAddressList(UserAddress userAddress);

    /**
     * 新增用户地址
     * 
     * @param userAddress 用户地址
     * @return 结果
     */
    public int insertUserAddress(UserAddress userAddress);

    /**
     * 修改用户地址
     * 
     * @param userAddress 用户地址
     * @return 结果
     */
    public int updateUserAddress(UserAddress userAddress);

    /**
     * 删除用户地址
     * 
     * @param id 用户地址主键
     * @return 结果
     */
    public int deleteUserAddressById(Long id);

    /**
     * 批量删除用户地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserAddressByIds(Long[] ids);
}
