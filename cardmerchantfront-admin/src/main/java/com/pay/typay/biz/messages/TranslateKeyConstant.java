package com.pay.typay.biz.messages;

/**
 * 翻译文件key常量
 */
public class TranslateKeyConstant {

    public  static  final String Order_Key="merchantOrderId=商户订单号,orderId=平台订单号,bankAccount=银行卡简码,orderIndex=订单索引," +
            "orderStatus=订单状态,lockRmark=锁定备注,lockType=锁定类型,closeReason=关闭原因,googleCode=谷歌验证码,paidAmount=实际金额," +
            "payAmount=订单金额,orderType=订单类型,operator=操作用户,bankOwner=开户姓名,bankCode=银行类型,approveRemark=一审备注," +
            "approveStatus=一审状态,inputBankAccount=转入银行简码,outputBankAccount=转出银行简码";

    public  static  final String Trans_Key="id=流水编号,merchantOrderID=商户订单号,Remark=银行备注,transTime=交易时间,balance=余额," +
            "merchantId=商户ID,transType=交易类型,bankAccount=银行简码,name=交易姓名,transAccount=交易账号,transAmount=交易金额";

    public  static  final String Payment_Pool="payChannelIds=支付渠道集合,payChannelId=支付渠道编号,remark=备注,payPoolId=支付池编号," +
            "payPoolName=支付池名称,payPoolType=支付池类型,status=服务状态,payChannelName=支付渠道名称";

    public  static  final String Payment_Channel="payChannelId=支付渠道编号,status=服务状态,payChannelName=支付渠道名称," +
            "ClientType=客户端类型,riskyBalance=风控余额,workType=工作模式,remark=备注,minAmountPerTrans=每笔交易最小额度," +
            "version=版本号,balance=渠道盈余,channelLevel=优先等级,maxAmountPerTrans=每笔交易最大额度,payType=支付方式," +
            "sumAmountPerDayTrans=日总限额,bankIds=银行卡编号集合,withdrawStatus=下发方式,minBalance=最小保留额度,maxBalance=最大保留额度," +
            "depositStatus=充值方式,googleCode=谷歌验证码,withdrawBankPoolId=下发卡池编号,depositBankPoolId=充值卡池编号," +
            "testName=测试姓名,testAmount=测试金额,testRemark=测试备注";

    public static  final String BankCard_Monitor="bankId=银行卡编号,bankAccount=银行卡简码,bankCode=银行卡简码,testMoney=测试金额," +
            "clientName=客户端名称,clientId=客户端编号,status=状态";

    public static  final String BankCard_Key="bankid=银行卡编号,bankaccount=银行卡简码,bankcode=银行卡简码,status=服务状态,remark=银行卡备注," +
            "bankIds=银行卡编号集合,bankCodeList=银行卡简码集合,supplierBranchId=财务分支,bankPoolName=卡池名称,bankPoolNames=卡池名称集合,"+
            "comments=人工备注,maxBalance=最大保留额度,minBalance=最小保留额度,每笔最小=每笔最小,balance=余额,poolId=卡池ID,poolIds=卡池ID集合," +
            "aliMinAmountPerTrans=支付宝每笔最小,aliMaxAmountPerTrans=支付宝每笔最大,aliSumAmountPerDayTrans=支付宝日总限额,bankType=银行类型," +
            "aliSumTimesPerDayTrans=支付宝日笔数限制,支付宝日笔数限制,sumAmountPerDayTrans=日总限额,googleCode=谷歌验证码,bankNum=银行卡号," +
            "lastUpDatetime=最后更新时间,loginPwd=登录密码,workType=工作模式,loginAccount=网银账户,minBalance=最小保留额度,ownerName=开户人," +
            "maxBalance=最大保留额度,initBalance=保留额度,depositAddress=充值地址,ownerIdentity=开户人身份证,ownerPhone=手机号码,address=开户地址," +
            "sumTimesPerDayTrans=日笔数限定,payPwd=支付密码,maxAmountPerTrans=每笔最大,uCode=子账号,minAmountPerTrans=每笔最小,auTheStr=查看员组";

    public  static  final  String BankClient_Key="bankAccount=银行卡简码,bankCode=银行卡简码,clientId=客户端ID,clientName=客户端名称," +
            "bankType=银行类型,identity=识别码,workType=工作模式,status=服务状态";

    public  static  final String User_Info_Key="loginName=登录名,treeName=角色名,roleId=角色ID,roleIds=角色ID,userName=用户昵称,status=用户状态,userId=用户ID,orderallotrule=订单角色," +
            "supplierbranchidgroup=财务分支组,supplierbranchid=当前财务分支,googleCode=谷歌密钥,verifyCode=验证码,password=登录密码";

    public static final String AGENT_BANK_CARD = "bankid=银行卡编号,supplierbranchid=财务分支,banknum=银行卡号码,bankcode=银行卡编码," +
            "banktype=银行卡类型代码,address=开户地址,depositaddress=充值地址,ownername=开户人,ownerphone=开户人手机预留号码," +
            "owneridentity=开户人身份证,worktype=工作模式,balance=余额,status=银行卡状态,remark=备注,createtime=创建时间," +
            "lasttime=更新时间,cardindex=银行卡index,createby=创建人,time=修改时间,password=提款密码,agentId=卡商代理ID";

    public static final String AGENT = "id=代理ID,supplierBranchId=财务分支,agentCode=代理编码,agentName=代理名称," +
            "balance=总余额,creditBalance=信用余额,availableBalance=可用余额,fronzenBalance=冻结余额,ExtraBalance=额外支出余额,ratio=费率,profitBalance=收益," +
            "status=状态,remark=备注,createTime=创建时间,lastUpdateTime=更新时间,version=版本,userId=卡商代理登录账号,agentLevel=代理层级,createby=创建人," +
            "parentAgentId=父卡商代理ID,payCode=支付密码,parentSupplierBranchId=父级财务分支";

    public static final String AGENT_CREDIT_ORDER = "orderindex=索引,orderid=订单号,supplierbranchid=财务分支,paytype=充值方式," +
            "payamount=提款金额,paidamount=实现到帐金额,paidchannelfee=渠道手续费,supplierpaidchannelfee=财务分支渠道费用," +
            "notifyurl=商户用于接收订单通知的地址,returnurl=订单提交后立即返回的商户页面地址,bankcode=银行卡简码,md5orderid=md5签名," +
            "paychannelid=处理该订单的充值渠道,banknum=处理该订单的银行卡,bankaccount=处理该订单的银行卡简称,orderstatus=订单状态," +
            "orderpaidstatuschangetime=订单状态最后修改时间,ordernotifystatus=订单通知状态,ordernotifytime=订单通知状态最后修改时间," +
            "remark=附言,name=名称,createtime=创建时间,lastupdatetime=更新时间,agentId=卡商代理ID,orderType=订单类型,agentCode=代理编码";

    public static final String AGENT_DEPOSIT_ORDER = "orderindex=索引,orderid=订单号,supplierbranchid=财务分支,paytype=充值方式," +
            "payamount=提款金额,paidamount=实现到帐金额,paidchannelfee=渠道手续费,supplierpaidchannelfee=财务分支渠道费用," +
            "notifyurl=商户用于接收订单通知的地址,returnurl=订单提交后立即返回的商户页面地址,bankcode=银行卡简码,md5orderid=md5签名," +
            "paychannelid=处理该订单的充值渠道,banknum=处理该订单的银行卡,bankaccount=处理该订单的银行卡简称,orderstatus=订单状态," +
            "orderpaidstatuschangetime=订单状态最后修改时间,ordernotifystatus=订单通知状态,ordernotifytime=订单通知状态最后修改时间," +
            "remark=附言,name=名称,createtime=创建时间,lastupdatetime=更新时间,agentId=卡商代理ID,orderType=订单类型,agentCode=代理编码," +
            "approvePersonnel=审核人员,approveRemark=审核备注,approveTime=审核时间";

    public static final String AGENT_WITHDRAW_ORDER = "orderindex=索引,orderid=订单号,supplierbranchid=财务分支,paytype=充值方式," +
            "payamount=提款金额,paidamount=实现到帐金额,agent_name=代理名称,bankowner=银行卡持有者," +
            "notifyurl=商户用于接收订单通知的地址,returnurl=订单提交后立即返回的商户页面地址,bankcode=银行卡简码,md5orderid=md5签名," +
            "paychannelid=处理该订单的充值渠道,banknum=处理该订单的银行卡,bankaccount=处理该订单的银行卡简称,orderstatus=订单状态," +
            "ordernotifystatus=订单通知状态,ordernotifytime=订单通知状态最后修改时间,digest=digest," +
            "remark=附言,createtime=创建时间,lastupdatetime=更新时间,agent_id=卡商代理ID,orderType=订单类型,agentCode=代理编码," +
            "approvePersonnel=审核人员,approveRemark=审核备注,approveTime=审核时间";

    public static final String BANK_CARD_REVIEW = "id=主键,agentId=代理id,supplierbranchid=财务分支,reviewStatus=审核状态," +
            "applicantId=申请人id,applicantName=申请人名称,reviewerId=审核人id,reviewer=审核人名称,beforWorktype=修改前类型," +
            "afterWorktype=修改后类型,reviewTime=审核时间,bankcardId=银行卡id,createtime=申请时间";

    //用户状态
    public  static  final  String userStatus="{\"0\":\"启用\",\"1\":\"禁用\"}";
    public static final String ClientStatus = "{\"\":\"请选择\",\"1\":\"启用\",\"0\":\"停用\",\"-1\":\"软删除\",\"100\":\"在线\",\"-100\":\"异常\"}";
    public final static String ClientWorkType = "{\"\":\"请选择\",\"1\":\"收款\",\"2\":\"出款\",\"3\":\"中转\",\"4\":\"下发\"}";
    public final static String PaymentPool_PayChannelStatus = "{\"1\":\"启用\",\"0\":\"停用\"}";
    //订单状态
    public final static String OrderStatus       = "{\"\":\"请选择\",\"0\":\"创建\",\"1\":\"处理中\",\"2\":\"清算中\",\"3\":\"成功\",\"4\":\"手工补单\",\"5\":\"超时\",\"6\":\"超时失败\",\"7\":\"失败\",\"8\":\"下发审核中\",\"11\":\"手动处理中\",\"-1\":\"挂起\",\"9\":\"创建失败\", \"-2\":\"未支付订单\"}";
}
