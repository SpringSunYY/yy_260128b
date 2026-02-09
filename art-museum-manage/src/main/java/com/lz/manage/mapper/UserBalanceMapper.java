package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.UserBalance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户余额Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface UserBalanceMapper extends BaseMapper<UserBalance>
{
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
     * 删除用户余额
     * 
     * @param id 用户余额主键
     * @return 结果
     */
    public int deleteUserBalanceById(Long id);

    /**
     * 批量删除用户余额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserBalanceByIds(Long[] ids);
}
