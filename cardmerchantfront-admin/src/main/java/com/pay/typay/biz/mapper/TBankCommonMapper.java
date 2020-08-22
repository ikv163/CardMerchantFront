package com.pay.typay.biz.mapper;


import com.pay.typay.biz.domain.*;
import com.pay.typay.common.core.domain.Ztree;
import com.pay.typay.system.domain.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 银行卡类型Mapper接口
 *
 * @author oswald
 * @date 2020-01-06
 */
public interface TBankCommonMapper {

    /**
     * 查询银行卡类型列表
     *
     * @return 银行卡类型集合
     */
    @Select(" select BankTypeID, BankTypeName, BankTypeCode, ReMark, CreateTime, LastUpDateTime from t_banktype")
    List<TBanktypeInfo> selectTBanktypeList();

    @Select("SELECT BankPooID,BankPoolName FROM t_bankcardpool td WHERE Status!=-1 and td.SupplierBranchID = #{supplierbranchid}")
    List<TBankcardpoolInfo> selectTBankcardpoolInfoList(String supplierbranchid);

    @Select("SELECT TagID,SupplierBranchID,TagName FROM t_banktag tb WHERE SupplierBranchID=#{supplierbranchid}")
    List<Banktag> getBanktag(Long supplierbranchid);

    @Select("SELECT role_name as name, role_id as id FROM sys_role ar WHERE del_flag != 2")
    List<RoleChecker> getRoleChecker();
    @Select("SELECT * FROM t_bankcardpool tb ORDER BY BankPooID DESC LIMIT 1")
    TBankcardpoolInfo getMaxPoolId();
    @Select("SELECT * FROM t_bankcard t  ORDER BY BankID DESC LIMIT 1")
    TBankcard getMaxBankcardId();

    @Select("SELECT * FROM t_paypool tp   ORDER BY PayPoolID DESC LIMIT 1")
    Paymentpool getMaxPaypoolID();

    @Select("SELECT PayChannelID FROM t_paychannel ORDER BY PayChannelID DESC LIMIT 1")
    Paychannel getMaxPaychannelID();

    /**
     * 支付渠道对象
     */
    @Select("SELECT * FROM t_paychannel tp WHERE SupplierBranchID=#{supplierbranchid}")
    List<Paychannel> getPaychannel(Long supplierbranchid);

/*    *//**
     * 银行卡集合
     * @return
     *//*

    */List<Ztree> bankpoolMenus(Map<String,Object> map);

    @Select("SELECT id,\n" +
            "       companyid,\n" +
            "       branchname,\n" +
            "       status\n" +
            "FROM t_surppilerbranch ts")
    List<Surppilerbranch> getSurppilerbranch();

    @Select("SELECT id,\n" +
            "       companyid,\n" +
            "       branchname,\n" +
            "       status\n" +
            "FROM t_surppilerbranch ts where parent_id > 0")
    List<Surppilerbranch> getChildSupplierBranch();

    @Select("SELECT id,\n" +
            "       companyid,\n" +
            "       branchname,\n" +
            "       status\n" +
            "FROM t_surppilerbranch ts where parent_id = 0")
    List<Surppilerbranch> getParentSupplierBranch();


    @Select("SELECT companyid\n" +
            "FROM t_surppilerbranch ts where ID = #{SupplierBranchId}")
    int getCompanyIdBySupplierBranchId(Long SupplierBranchId);

    List<Surppilerbranch> getCurrentSurppilerbranchGroup(SysUser sysUser);
    /**
     * 判断用户是否有数据
     * @param userid
     * @return
     */

    @Update("UPDATE t_bankcard tb SET PoolID=NULL WHERE tb.BankID=#{BankID}  ")
    int setbankcardpoolidAsNull(Long BankID);

//    @Select("SELECT max(ID) FROM t_surppilerbranch ts")
    @Select("SELECT ID FROM t_surppilerbranch ORDER BY ID DESC LIMIT 1 ")
    Long getMaxSupplierBranchId();

    @Insert("INSERT INTO t_surppilerbranch(ID,CompanyID,BranchName,Status,CreateBy,CreateTime,UpdateBy,UpdateTime,parent_id) \n" +
            " VALUES(#{id},#{companyId},#{branchName},#{status},#{createBy},#{createTime},#{updateBy},#{updateTime},#{parentId}) ")
    int insertSupplierBranch(@Param("id")long id, @Param("companyId")int companyId, @Param("branchName")String branchName, @Param("status")int status, @Param("createBy")Long createBy, @Param("createTime")String createTime, @Param("updateBy")Long updateBy,  @Param("updateTime")String updateTime, @Param("parentId")String parentId);


    @Select("SELECT parent_id FROM t_surppilerbranch ts where id = #{id}")
    String selectParentIdByBranchId(@Param("id")long id);

    @Select("SELECT max_ratio FROM t_surppilerbranch ts where id = #{id}")
    Double selectMaxRatioByBranchId(@Param("id")long id);

}
