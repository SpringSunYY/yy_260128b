package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.UserBalanceMapper;
import com.lz.manage.model.domain.UserBalance;
import com.lz.manage.model.dto.userBalance.UserBalanceQuery;
import com.lz.manage.model.vo.userBalance.UserBalanceVo;
import com.lz.manage.service.IUserBalanceService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户余额Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceMapper, UserBalance> implements IUserBalanceService {

    @Resource
    private UserBalanceMapper userBalanceMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询用户余额
     *
     * @param id 用户余额主键
     * @return 用户余额
     */
    @Override
    public UserBalance selectUserBalanceById(Long id) {
        return userBalanceMapper.selectUserBalanceById(id);
    }

    /**
     * 查询用户余额列表
     *
     * @param userBalance 用户余额
     * @return 用户余额
     */
    @Override
    @DataScope(deptAlias = "tb_user_balance", userAlias = "tb_user_balance")
    public List<UserBalance> selectUserBalanceList(UserBalance userBalance) {
        List<UserBalance> userBalances = userBalanceMapper.selectUserBalanceList(userBalance);
        for (UserBalance info : userBalances) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return userBalances;
    }

    /**
     * 新增用户余额
     *
     * @param userBalance 用户余额
     * @return 结果
     */
    @Override
    public int insertUserBalance(UserBalance userBalance) {
        userBalance.setCreateTime(DateUtils.getNowDate());
        return userBalanceMapper.insertUserBalance(userBalance);
    }

    /**
     * 修改用户余额
     *
     * @param userBalance 用户余额
     * @return 结果
     */
    @Override
    public int updateUserBalance(UserBalance userBalance) {
        userBalance.setUpdateTime(DateUtils.getNowDate());
        return userBalanceMapper.updateUserBalance(userBalance);
    }

    /**
     * 批量删除用户余额
     *
     * @param ids 需要删除的用户余额主键
     * @return 结果
     */
    @Override
    public int deleteUserBalanceByIds(Long[] ids) {
        return userBalanceMapper.deleteUserBalanceByIds(ids);
    }

    /**
     * 删除用户余额信息
     *
     * @param id 用户余额主键
     * @return 结果
     */
    @Override
    public int deleteUserBalanceById(Long id) {
        return userBalanceMapper.deleteUserBalanceById(id);
    }

    //endregion
    @Override
    public QueryWrapper<UserBalance> getQueryWrapper(UserBalanceQuery userBalanceQuery) {
        QueryWrapper<UserBalance> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = userBalanceQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = userBalanceQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long userId = userBalanceQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        return queryWrapper;
    }

    @Override
    public List<UserBalanceVo> convertVoList(List<UserBalance> userBalanceList) {
        if (StringUtils.isEmpty(userBalanceList)) {
            return Collections.emptyList();
        }
        return userBalanceList.stream().map(UserBalanceVo::objToVo).collect(Collectors.toList());
    }

}
