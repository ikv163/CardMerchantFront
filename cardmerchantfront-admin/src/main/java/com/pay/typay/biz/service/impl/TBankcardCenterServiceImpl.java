package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.TBankcard;
import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.domain.TBankcardpool;
import com.pay.typay.biz.domain.TBankcardsummary;
import com.pay.typay.biz.mapper.TBankCommonMapper;
import com.pay.typay.biz.mapper.TBankcardCenterMapper;
import com.pay.typay.biz.mapper.TBankcardMapper;
import com.pay.typay.biz.mapper.TBankcardsummaryMapper;
import com.pay.typay.biz.service.ITBankcardcenterService;
import com.pay.typay.biz.service.ITBankcardpoolService;
import com.pay.typay.common.annotation.DataScope;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.cache.RedisUtil;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;

import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.mapper.SysUserMapper;
import com.pay.typay.system.service.ISysUserService;
import com.pay.typay.utils.DesUntil;
import com.pay.typay.utils.TyPayConstants;
import com.pay.typay.utils.UtilsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 银行卡Service业务层处理
 *
 * @author oswald
 * @date 2020-01-06
 */
@Service
@DataSource(DataSourceType.typayv2)
public class TBankcardCenterServiceImpl implements ITBankcardcenterService {
    private static final Logger log = LoggerFactory.getLogger(TBankcardCenterServiceImpl.class);
    @Resource
    private TBankcardCenterMapper tbankcardCenterMapper;

    @Resource
    private TBankcardsummaryMapper tBankcardsummaryMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询银行卡
     *
     * @param bankid 银行卡ID
     * @return 银行卡
     */
    @Override
    public TBankcardcenter selectTBankcardById(Long bankid) {

        TBankcardcenter tBankcard = tbankcardCenterMapper.selectTBankcardById(bankid,UtilsUser.getUserSupplierbranchid().toString());
        try {
            String paypwdraw = tBankcard.getPaypwd();
            if (!StringUtils.isEmpty(paypwdraw)) {
                String paypwd = DesUntil.decrypt(paypwdraw);
                tBankcard.setPaypwd(paypwd);
            }
            String loginpwdraw = tBankcard.getLoginpwd();
            if (!StringUtils.isEmpty(loginpwdraw)) {
                String loginpwd = DesUntil.decrypt(loginpwdraw);
                tBankcard.setLoginpwd(loginpwd);
            }
        } catch (Exception e) {

            log.error(e.getMessage());
        }
        return tBankcard;
    }

    @Override
    public List<TBankcardcenter> getSummary(TBankcardcenter tBankcard) {
        return tbankcardCenterMapper.getSummary(tBankcard);
    }

    /**
     * 查询银行卡列表
     *
     * @param tBankcard 银行卡
     * @return 银行卡
     */
    @Override

    public List<TBankcardcenter> selectTBankcardList(TBankcardcenter tBankcard) {
        List<TBankcardcenter> bankcards = tbankcardCenterMapper.selectTBankcardList(tBankcard);
        return bankcards;
    }

    @Override
    public List<TBankcardcenter> selectCheck(TBankcardcenter tBankcard) {
        return tbankcardCenterMapper.selectTBankcardList(tBankcard);
    }


    @Override

    public List<TBankcardcenter> selectListByWorktype(TBankcardcenter tBankcard) {

        return tbankcardCenterMapper.selectListByWorktype(tBankcard);
    }

    @Override

    public List<TBankcardcenter> selectTBankcardListsum(TBankcardcenter tBankcard) {


        return tbankcardCenterMapper.selectTBankcardListsum(tBankcard);
    }

    @Resource
    TBankCommonMapper tBankCommonMapper;

    /**
     * 新增银行卡
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    @Resource
    SysUserMapper userMapper;

    @Override
    @Transactional
    public int insertTBankcard(TBankcardcenter tBankcard) {
        tBankcard.setCreatetime(new Date());
        tBankcard.setSupplierbranchid(UtilsUser.getUserSupplierbranchid());
        tBankcard.setCreateby(UtilsUser.getUserId());
        tBankcard.setAgentId(ShiroUtils.getAgentId());
        TBankcard info = tBankCommonMapper.getMaxBankcardId();
        Long maxId;
        if (info == null) {
            maxId = 0L;
        } else {
            maxId = info.getBankid();
        }
        //1.插入卡
        long bankcardId = maxId + 1;
        tBankcard.setBankid(bankcardId);
        //2.加密,paypwd,loginpwd
        try {
            String paypwdraw = tBankcard.getPaypwd();
            if (!StringUtils.isEmpty(paypwdraw)) {
                String paypwd = DesUntil.anyLengthEncrypt(paypwdraw);
                tBankcard.setPaypwd(paypwd);
            }
            String loginpwdraw = tBankcard.getLoginpwd();
            if (!StringUtils.isEmpty(loginpwdraw)) {
                String loginpwd = DesUntil.anyLengthEncrypt(loginpwdraw);
                tBankcard.setLoginpwd(loginpwd);
            }
            redisUtil.set("OfflineCard_" + tBankcard.getBankcode(), TyPayConstants.CARD_SWITCH_CLOSE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        tBankcard.setUpdatetime(DateUtils.getNowDate());
        return tbankcardCenterMapper.insertTBankcard(tBankcard);
    }


    @Autowired
    private ITBankcardpoolService tBankcardpoolService;

    /**
     * 修改银行卡
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTBankcard(TBankcardcenter tBankcard) {
        tBankcard.setUpdateTime(new Date());
        //银行卡池的卡池类型.如果在银行卡中心改变了该卡的工作模式,对应的银行卡池的卡池类型自动解除
        Map<Long, Long> pm = new HashMap<>();
        // left为银行卡工作模式,right卡池的roletype,
//                 入款 ok
//                0: 1,
        pm.put(1L, 0L);
//                // 出款 ok
//                1: 2,
        pm.put(2L, 1L);
//                // 下发  ok
//                2: 4,
        pm.put(4L, 2L);
//                // 其他
//                3: '',
        pm.put(0L, 3L);
//                // 中转 ok
//                4: 3,
        pm.put(3L, 4L);
//                // 手动出款 ok
//                5: 5,
        pm.put(5L, 5L);
//                // 四方 ok
//                6: 10
        pm.put(6L, 10L);
        Long worktype = tBankcard.getWorktype();
        Long poolid = tBankcard.getPoolid();

        try {
            String paypwdraw = tBankcard.getPaypwd();
            if (!StringUtils.isEmpty(paypwdraw)) {
                String paypwd = DesUntil.anyLengthEncrypt(paypwdraw);
                tBankcard.setPaypwd(paypwd);
            }
            String loginpwdraw = tBankcard.getLoginpwd();
            if (!StringUtils.isEmpty(loginpwdraw)) {
                String loginpwd = DesUntil.anyLengthEncrypt(loginpwdraw);
                tBankcard.setLoginpwd(loginpwd);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        String bankids = tBankcard.getBankids();
        if (bankids != null && !StringUtils.isEmpty(bankids)) {
            int i = tbankcardCenterMapper.updateTBankcardmutiple(tBankcard);

            String[] split = bankids.split(",");
            for (String s : split) {
                Long bankid = Long.valueOf(s);
                updatecardpoolRelationship(pm, worktype, poolid, bankid);
            }
            return i;
        }

        int i = tbankcardCenterMapper.updateTBankcard(tBankcard);
        Long bankid = tBankcard.getBankid();
        updatecardpoolRelationship(pm, worktype, poolid, bankid);
        return i;
    }

    /**
     * 修改已充余额
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTBankcardmutiple(TBankcardcenter tBankcard) {//1
        return tbankcardCenterMapper.updateTBankcardmutiple(tBankcard);

    }

    @Override
    @Transactional
    public int updateCardDepositAmount(TBankcardcenter tBankcard) {//2
        return tbankcardCenterMapper.updateCardDepositAmount(tBankcard);

    }

    private void updatecardpoolRelationship(Map<Long, Long> pm, Long worktype, Long poolid, Long bankid) {
        if (worktype != null && poolid != null) {
            Long cardpoolRoleType = pm.get(worktype);
            if (cardpoolRoleType != null) {
                TBankcardpool tBankcardpool = tBankcardpoolService.selectTBankcardpoolById(poolid);
                //判断银行卡工作模式和银行卡池类型是否一样,如果不一样,就解除卡池与该卡的关系关系
                if (tBankcardpool != null) {
                    Long roletype = tBankcardpool.getRoletype();
                    if (!cardpoolRoleType.equals(roletype)) {
                        tBankCommonMapper.setbankcardpoolidAsNull(bankid);
                    }
                }
            }
        }
    }

    @Autowired
    private ISysUserService userService;

    @Override
    @Transactional
    public int distupdateTBankcard(TBankcardcenter tBankcard) {
        Long[] authes = tBankcard.getAuthes();
        for (Long auth : authes) {
            SysUser sysUser = new SysUser();
            sysUser.setRoleId(auth);
            tBankcard.setAuth(auth);
            this.updateTBankcard(tBankcard);
            List<SysUser> sysUsers = userService.selectUserList(sysUser);
        }

        return 1;
    }

    @Override
    @Transactional
    public int distributionCardpool(TBankcardcenter tBankcard) {
        tBankcard.setUpdateby(ShiroUtils.getUserId());
        tBankcard.setUpdateTime(new Date());
        return tbankcardCenterMapper.updateTBankcardmutiple(tBankcard);
    }

    /**
     * 删除银行卡对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTBankcardByIds(String ids) {
        return tbankcardCenterMapper.deleteTBankcardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除银行卡信息
     *
     * @param bankid 银行卡ID
     * @return 结果
     */
    @Override
    public int deleteTBankcardById(Long bankid) {
        return tbankcardCenterMapper.deleteTBankcardById(bankid);
    }

    @Override
    public List<TBankcardcenter> selectTBankcardListByPool(TBankcardcenter tBankcard) {
        return tbankcardCenterMapper.selectTBankcardListByPool(tBankcard);
    }


    /**
     * 修改银行卡
     *
     * @param tBankcard 银行卡
     * @return 结果
     */
    @Override
    public int updateCardSupplierBranch(TBankcardcenter tBankcard) {
        tBankcard.setUpdateby(ShiroUtils.getUserId());
        tBankcard.setUpdateTime(new Date());
        return tbankcardCenterMapper.updateCardSupplierBranch(tBankcard);
    }

    @Override

    @DataSource(DataSourceType.typayv2)
    public List<TBankcardcenter> getBankcardTotal(TBankcardcenter tBankcard) {
        return tbankcardCenterMapper.getBankcardTotal(tBankcard);
    }

    @Override
    public List<TBankcardsummary> selectSummaryByBank(Long bankid) {
        TBankcardsummary summary = new TBankcardsummary();
        summary.setBankid(bankid);
        return tBankcardsummaryMapper.selectTBankcardsummaryList(summary);
    }

    @Override

    public List<TBankcardcenter> selectListByPool(TBankcardcenter tBankcard) {
        return tbankcardCenterMapper.selectListByPool(tBankcard);
    }

    @Autowired
    private TBankcardMapper tBankcardMapper;

    /**
     * 开关
     *
     * @param bankids
     * @param status
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int openAndClose(String bankids, Long status) {
        List<TBankcard> bankcards = tBankcardMapper.selectTBankcardsByIds(buildBankIds(bankids));
        if (bankcards.isEmpty()) {
            return 0;
        }
        bankcards.stream().forEach(tBankcard -> {
            if (status == 1) {
                redisUtil.set("OfflineCard_" + tBankcard.getBankcode().trim(), 1);
            } else {
                redisUtil.del("OfflineCard_" + tBankcard.getBankcode().trim());
            }
        });
        return 1;
    }

    private List buildBankIds(String bankids) {
        return Arrays.asList(Optional.ofNullable(bankids).orElse("").split(","));
    }
}
