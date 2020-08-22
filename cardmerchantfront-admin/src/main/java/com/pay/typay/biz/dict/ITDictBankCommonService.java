package com.pay.typay.biz.dict;


import com.alibaba.fastjson.JSON;
import com.pay.typay.biz.agent.domain.AgentBankcard;
import com.pay.typay.biz.agent.service.IAgentBankcardService;
import com.pay.typay.biz.domain.*;
import com.pay.typay.biz.mapper.PaytypeMapper;
import com.pay.typay.biz.mapper.TBankCommonMapper;
import com.pay.typay.biz.mapper.TBankcardCenterMapper;
import com.pay.typay.biz.mapper.TBankcardpoolMapper;
import com.pay.typay.biz.messages.ConstantsSelectUI;
import com.pay.typay.biz.service.ITBankcardpoolService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.domain.Ztree;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.reflect.ReflectUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行卡类型Service接口
 *
 * @author oswald
 * @date 2020-01-06
 */

@Service("dictbank")

public class ITDictBankCommonService {

    @Autowired
    private TBankCommonMapper tBankCommonMapper;

    @Autowired
    private PaytypeMapper paytypeMapper;

    @Autowired
    private TBankcardpoolMapper bankcardpoolMapper;

    @Autowired
    private TBankcardCenterMapper bankcardCenterMapper;

    /**
     * 可用银行卡
     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<TBankcardcenter> selectAvailableBankList() {
        TBankcardcenter tBankcard=new TBankcardcenter();
        tBankcard.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        tBankcard.setSurplier_type(1L);
        tBankcard.setStatuses("0,1");//启用/停用
        return bankcardCenterMapper.selectTBankcardList(tBankcard);
    }


    /**
     * 支付渠道对象
     *
     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Paychannel> getPaychannel() {
        SysUser sysUser = ShiroUtils.getSysUser();
        Long supplierbranchid = sysUser.getSupplierbranchid();
        return tBankCommonMapper.getPaychannel(supplierbranchid);
    }



    /**
     * 财务信息     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Surppilerbranch> getSurppilerbranch() {
        return tBankCommonMapper.getSurppilerbranch();
    }

    /**
     * 获取子财务分支     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Surppilerbranch> getChildSupplierBranch() {
        return tBankCommonMapper.getChildSupplierBranch();
    }

    /**
     * 获取父财务分支     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Surppilerbranch> getParentSupplierBranch() {
        return tBankCommonMapper.getParentSupplierBranch();
    }

    /**
     * 获取财务分支的公司id
     * @param SupplierBranchId
     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public int getCompanyIdBySupplierBranchId(Long SupplierBranchId){
        return tBankCommonMapper.getCompanyIdBySupplierBranchId(SupplierBranchId);
    }


    /**
     * 获取最大的财务分支id
     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public Long getMaxSupplierBranchId()
    {
        return tBankCommonMapper.getMaxSupplierBranchId();
    }

    @DataSource(DataSourceType.typayv2)
    public int insertSupplierBranch(Long id, int companyId, String branchName, int status, Long createBy, String createTime, Long updateBy, String updateTime,String parentId)
    {
        return tBankCommonMapper.insertSupplierBranch(id,companyId,branchName,status, createBy, createTime, updateBy,updateTime,parentId);
    }

    /**
     * 财务信息     * @return
     */
    @DataSource(DataSourceType.typayv2)
    public List<Surppilerbranch> getCurrentSurppilerbranchGroup() {
        SysUser sysUser = ShiroUtils.getSysUser();
        return tBankCommonMapper.getCurrentSurppilerbranchGroup(sysUser);
    }


    @DataSource(DataSourceType.typayv2)
    public String selectParentIdByBranchId(Long id) {
        return tBankCommonMapper.selectParentIdByBranchId(id);
    }

    @DataSource(DataSourceType.typayv2)
    public Double selectMaxRatioByBranchId(Long id) {
        return tBankCommonMapper.selectMaxRatioByBranchId(id);
    }

    /**
     * 银行标签
     */
    @DataSource(DataSourceType.typayv2)
    public List<Banktag> getBanktag() {
        SysUser sysUser = ShiroUtils.getSysUser();
        Long supplierbranchid = sysUser.getSupplierbranchid();
        List<Banktag> banktags = tBankCommonMapper.getBanktag(supplierbranchid);
        return banktags;
    }

    /**
     * 查询银行卡类型
     *
     * @return 银行卡类型
     */
    @DataSource(DataSourceType.typayv2)
    public List<TBanktypeInfo> getTBanktype() {
        List<TBanktypeInfo> tBanktypeInfos = tBankCommonMapper.selectTBanktypeList();
        return tBanktypeInfos;
    }

    /**
     * 查询银行卡类型
     *
     * @return 银行卡类型
     */
    @Autowired
    private IAgentBankcardService tAgentbankcardService;
    @DataSource(DataSourceType.typayv2)
    public List<AgentBankcard> getCurrentUserBankCard() {
        AgentBankcard tAgentbankcard = new AgentBankcard();
        List<AgentBankcard> list = tAgentbankcardService.selectAgentBankcardList(tAgentbankcard);
        return list;
    }

    /**
     * 银行卡池
     *
     * @return
     */
    public List<TBankcardpool> selectTBankcardpoolInfoList() {
        TBankcardpool tBankcardpool = new TBankcardpool();
        List<TBankcardpool> list = tBankcardpoolService.selectBankcardPoolList(tBankcardpool);
        return list;
    }

    /**
     * 银行卡池
     *
     * @return
     */
    public List<TBankcardpool> selectTBankcardpoolInfoListFromRoletype(Long roletype) {
        TBankcardpool tBankcardpool = new TBankcardpool();
        tBankcardpool.setRoletype(roletype);
        List<TBankcardpool> list = tBankcardpoolService.selectBankcardPoolList(tBankcardpool);
        return list;
    }

    public Map getSelectUIType(String key) {
        ConstantsSelectUI constantsSelectUI = new ConstantsSelectUI();
        String workType = ReflectUtils.getFieldValue(constantsSelectUI, key);
        Map obj = (Map) JSON.parse(workType);
        return obj;
    }

    @Autowired
    private ITBankcardpoolService tBankcardpoolService;

    public List<TBankcardpool> selectBankcardPoolList() {
        TBankcardpool tBankcardpool = new TBankcardpool();
        List<TBankcardpool> list = tBankcardpoolService.selectBankcardPoolList(tBankcardpool);
        return list;
    }

    @DataSource(DataSourceType.cardmerchantfront)
    public List<RoleChecker> getRoleChecker() {
        List<RoleChecker> roleChecker = tBankCommonMapper.getRoleChecker();
        return roleChecker;
    }

    @DataSource(DataSourceType.typayv2)
    public List<Ztree> bankpoolMenus(Integer roleType) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleType", roleType);
        List<Ztree> ztrees = tBankCommonMapper.bankpoolMenus(map);
        return ztrees;
    }

    @DataSource(DataSourceType.typayv2)
    public List<Ztree> bankpoolMenus(Map map) {
        List<Ztree> ztrees = tBankCommonMapper.bankpoolMenus(map);
        return ztrees;
    }

    @DataSource(DataSourceType.typayv2)
    public List<Paytype> paytypeList() {
        return paytypeMapper.selectPaytypeList(new Paytype());
    }

    @DataSource(DataSourceType.typayv2)
    public List<Paytype> paytypeBankcardpayOnlyList() {
        return paytypeMapper.paytypeBankcardpayOnlyList(new Paytype());
    }

    @DataSource(DataSourceType.typayv2)
    public List<Paytype> paytypeThirtyPartOnlyList() {
        return paytypeMapper.paytypeThirtyPartOnlyList(new Paytype());
    }

    @DataSource(DataSourceType.typayv2)
    public List<TBankcardpool> selectTBankcardpoolList() {
        TBankcardpool bankcardpool = new TBankcardpool();
        bankcardpool.setSupplierbranchid(ShiroUtils.getSysUser().getSupplierbranchid());
        return bankcardpoolMapper.selectTBankcardpoolList(bankcardpool);
    }
}