package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.UserAddress;
import com.lz.manage.model.vo.userAddress.UserAddressVo;
import com.lz.manage.model.dto.userAddress.UserAddressQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 用户地址Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface IUserAddressService extends IService<UserAddress>
{
    //region mybatis代码
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
     * 批量删除用户地址
     * 
     * @param ids 需要删除的用户地址主键集合
     * @return 结果
     */
    public int deleteUserAddressByIds(Long[] ids);

    /**
     * 删除用户地址信息
     * 
     * @param id 用户地址主键
     * @return 结果
     */
    public int deleteUserAddressById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param userAddressQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<UserAddress> getQueryWrapper(UserAddressQuery userAddressQuery);

    /**
     * 转换vo
     *
     * @param userAddressList UserAddress集合
     * @return UserAddressVO集合
     */
    List<UserAddressVo> convertVoList(List<UserAddress> userAddressList);
}
