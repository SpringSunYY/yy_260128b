package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.UserBalance;
import com.lz.manage.model.vo.userBalance.UserBalanceVo;
import com.lz.manage.model.dto.userBalance.UserBalanceQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 用户余额Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface IUserBalanceService extends IService<UserBalance>
{
    //region mybatis代码
    /**
     * 查询用户余额
     * 
     * @param id 用户余额主键
     * @return 用户余额
     */
    public UserBalance selectUserBalanceById(Long id);

    /**
     * 查询用户余额列表
     * 
     * @param userBalance 用户余额
     * @return 用户余额集合
     */
    public List<UserBalance> selectUserBalanceList(UserBalance userBalance);

    /**
     * 新增用户余额
     * 
     * @param userBalance 用户余额
     * @return 结果
     */
    public int insertUserBalance(UserBalance userBalance);

    /**
     * 修改用户余额
     * 
     * @param userBalance 用户余额
     * @return 结果
     */
    public int updateUserBalance(UserBalance userBalance);

    /**
     * 批量删除用户余额
     * 
     * @param ids 需要删除的用户余额主键集合
     * @return 结果
     */
    public int deleteUserBalanceByIds(Long[] ids);

    /**
     * 删除用户余额信息
     * 
     * @param id 用户余额主键
     * @return 结果
     */
    public int deleteUserBalanceById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param userBalanceQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<UserBalance> getQueryWrapper(UserBalanceQuery userBalanceQuery);

    /**
     * 转换vo
     *
     * @param userBalanceList UserBalance集合
     * @return UserBalanceVO集合
     */
    List<UserBalanceVo> convertVoList(List<UserBalance> userBalanceList);
}
