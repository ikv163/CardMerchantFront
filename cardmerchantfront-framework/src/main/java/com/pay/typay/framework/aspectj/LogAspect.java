package com.pay.typay.framework.aspectj;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Maps;
import com.pay.typay.common.annotation.ChineseName;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.Translate;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.enums.BusinessStatus;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.json.JSON;
import com.pay.typay.common.utils.IpUtils;
import com.pay.typay.common.utils.JsonUtils;
import com.pay.typay.common.utils.ServletUtils;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.spring.SpringUtils;
import com.pay.typay.common.xss.XssHttpServletRequestWrapper;
import com.pay.typay.framework.manager.AsyncManager;
import com.pay.typay.framework.manager.factory.AsyncFactory;
import com.pay.typay.framework.util.LogContextHolder;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysOperLog;
import com.pay.typay.system.domain.SysUser;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 操作日志记录处理
 *
 * @author js-oswald
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static  final  String FORMAT_TYPE_ALL="all";//全部格式
    private static  final String FORMAT_TYPE_SECTION="section";//格式部分
    public static  Map<String,String> sensitiveMap=new HashMap<>();

    static {
        //需要过敏字段标识
        sensitiveMap.put("LOGINPWD",FORMAT_TYPE_ALL);//网银登录密码
        sensitiveMap.put("PAYPWD",FORMAT_TYPE_ALL);//支付密码
        sensitiveMap.put("UKEYPWD",FORMAT_TYPE_ALL);//U盾密码
        sensitiveMap.put("ULOGINPWD",FORMAT_TYPE_ALL);//U盾登录密码
        sensitiveMap.put("PASSWORD",FORMAT_TYPE_ALL);//账户密码
        sensitiveMap.put("GOOGLECODE",FORMAT_TYPE_ALL);//google 密钥
        sensitiveMap.put("NEWPASSWORD",FORMAT_TYPE_ALL);//新密码
        sensitiveMap.put("OLDPASSWORD",FORMAT_TYPE_ALL);//旧密码
        sensitiveMap.put("VERIFEDPASSWORD",FORMAT_TYPE_ALL);//验证密码
        sensitiveMap.put("CONFIRM",FORMAT_TYPE_ALL);//确认密码
        sensitiveMap.put("UNDEFINED",FORMAT_TYPE_ALL);//confirm(确认密码)
        sensitiveMap.put("BANKNUM",FORMAT_TYPE_SECTION);//银行卡号
    }

    // 配置织入点
    @Pointcut("@annotation(com.pay.typay.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 请求前拦截
     * @param proceedingJoinPoint
     */
    @SneakyThrows
    @Around(value = "logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
        SysOperLog operLog = setOperContent(proceedingJoinPoint);
        Object result = proceedingJoinPoint.proceed();
        if (operLog != null){
            handleLog(proceedingJoinPoint,null,result,operLog);
        }
        return result;
    }
    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        SysOperLog operLog = new SysOperLog();
        operLog.setOperContent("");
        handleLog(joinPoint, e, null,operLog);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult,SysOperLog operLog) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            SysUser currentUser = ShiroUtils.getSysUser();

            // *========数据库日志=========*//
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
//             请求的地址
              HttpServletRequest request = ServletUtils.getRequest();
              String ip = IpUtils.getIpAddr(request);
            // final String ip =  ShiroUtils.getSysUser().getLoginIp();
//            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);
            // 返回参数
            if(jsonResult != null){
                String resultStr = JSON.marshal(jsonResult);
                operLog.setJsonResult(resultStr);
                JSONObject resultObj = JSONObject.parseObject(resultStr);
                String code = resultObj.getString("code");
                if(StringUtils.isEmpty(code) || !StringUtils.equals("0",code)){
                    operLog.setStatus(BusinessStatus.FAIL.ordinal());
                }
            }
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (currentUser != null) {
                operLog.setOperName(currentUser.getLoginName());
            }

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod() + "");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog,getClassTitle(joinPoint));
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }finally {
            LogContextHolder.clearLogType();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SysOperLog operLog, String classTitle) throws Exception {
        BusinessType type = log.businessType();
        // 设置action动作
        operLog.setBusinessType(type.ordinal());
        // 设置标题
        operLog.setTitle(classTitle + log.title());

        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());

        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            String params = handleInput(log);
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 通过反射获取更新前数据   --- 以后扩展类型
     *
     * @param result
     * @param methodName
     * @param params
     * @return
     */
    private Object selectObjectById(Object result, String methodName, String params) {

        Method method = ReflectionUtils.findMethod(result.getClass(), methodName, Long.class);

        Object object = ReflectionUtils.invokeMethod(method, result, Long.valueOf(params));

        return object;
    }

    /**
     * 获取类注解
     */
    private String getClassTitle(JoinPoint joinPoint){
        Class<?> clazz =joinPoint.getTarget().getClass();
        Log log=clazz.getAnnotation(Log.class);
        if(log!=null && !"".equals(log.classTitle())){
            return log.classTitle()+"-";
        }
        return "";
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * JSONArray转map
     * @param array
     * @return
     */
    private Map jsonArrayToMap(JSONArray array){
        Map map = new LinkedHashMap();
        if(array == null){
            return map;
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            object.entrySet().stream().forEach(o -> {
                map.put(o.getKey(),o.getValue());
            });
        }
        return map;
    }

    /**
     * 处理输入参数
     *
     * @return 特殊处理都的入参
     */
    private String handleInput(Log l) {
        try{

            String params;
            Map<String, String[]> parameterMap=ServletUtils.getRequest().getParameterMap();
            if(parameterMap.size()<=0){
                XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(ServletUtils.getRequest());
                params = xssRequest.getBody();
                if(StringUtils.isEmpty(params)){
                    return "{}";
                }
                try {

                    JSONArray jsonArray = JSONArray.parseArray(params);
                    Translate[] translations = l.translate();
                    if (jsonArray.size() <= 0 && translations.length <= 0) {
                        return params;
                    }

                    for (Translate translation : translations) {
                        Map map = com.alibaba.fastjson.JSON.parseObject(translation.data(), LinkedHashMap.class, Feature.OrderedField);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            Object o = jsonArray.get(i);
                            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
                            for (String object : jsonObject.keySet()) {
                                if (object.equalsIgnoreCase(translation.filed())) {
                                    String key = String.valueOf(jsonObject.get(object));
                                    if (map.containsKey(key)) {
                                        Object status = map.get(key);
                                        jsonObject.put(object, status);
                                    }
                                }
                            }
                            jsonArray.set(i, jsonObject);
                        }
                    }
                    TranslateKey[] translateKeys = l.translateKeys();
                    for (TranslateKey translateKey : translateKeys) {
                        jsonArray=changeJsonArr(jsonArray,translateKey.filedData());
                    }
                    return jsonArray.toJSONString();
                }catch (JSONException e){

                }

            }else{
                params=JSON.marshal(parameterMap);
            }

            JSONObject  jsonObject = (JSONObject) com.alibaba.fastjson.JSON.parse(params);
            for(String requestParam: jsonObject.keySet()){
                Object object=com.alibaba.fastjson.JSON.parse(String.valueOf(jsonObject.get(requestParam)));
                if(sensitiveMap.containsKey(requestParam.toUpperCase())){
                    String formatType=sensitiveMap.get(requestParam.toUpperCase());
                    if (object instanceof JSONArray) {
                        JSONArray jsonToArray = (JSONArray) jsonObject.get(requestParam);
                        for (int i1 = 0; i1 < jsonToArray.size(); i1++) {
                            jsonToArray.set(i1,replace(String.valueOf(jsonToArray.get(i1)),formatType));
                        }
                    }else if(object instanceof  JSONObject){
                        jsonObject.put(requestParam, replace(String.valueOf(jsonObject.get(requestParam)),formatType));
                    }
                }else{
                    Translate[] translations=l.translate();
                    for (Translate translation : translations) {
                        if (translation.filed().equalsIgnoreCase(requestParam)) {
                            Map map = com.alibaba.fastjson.JSON.parseObject(translation.data(), LinkedHashMap.class, Feature.OrderedField);
                            if (object instanceof JSONArray) {
                                JSONArray jsonToArray = (JSONArray) jsonObject.get(requestParam);
                                for (int i1 = 0; i1 < jsonToArray.size(); i1++) {
                                    String key= String.valueOf(jsonToArray.get(i1));
                                    if (map.containsKey(key)) {
                                        Object status = map.get(key);
                                        jsonToArray.set(i1, status);
                                    }
                                }
                            } else if (object instanceof JSONObject) {
                                String key = String.valueOf(jsonObject.get(requestParam));
                                if (map.containsKey(key)) {
                                    Object status = map.get(key);
                                    jsonObject.put(requestParam, status);
                                }
                            }
                        }
                    }
                }
            }
            TranslateKey[] translateKeys = l.translateKeys();
            for (TranslateKey translateKey : translateKeys) {
                jsonObject=changeJsonObj(jsonObject,translateKey.filedData());
            }
            return   jsonObject.toJSONString();
        }catch (Exception e){
            log.error("参数解析异常",e);
        }
        return "{}";
    }

    private static JSONObject changeJsonObj(JSONObject jsonObj, String converterExp) {
        JSONObject resJson = new JSONObject();
        Set<String> keySet = jsonObj.keySet();
        for (String key : keySet) {
            String resKey = StringUtils.convertByExp(key,converterExp);
            try {
                JSONObject jsonobj1 = jsonObj.getJSONObject(key);
                resJson.put(resKey, changeJsonObj(jsonobj1, converterExp));
            } catch (Exception e) {
                try {
                    JSONArray jsonArr = jsonObj.getJSONArray(key);
                    resJson.put(resKey, changeJsonArr(jsonArr, converterExp));
                } catch (Exception x) {
                    resJson.put(resKey, jsonObj.get(key));
                }
            }
        }
        return resJson;
    }

    private static JSONArray changeJsonArr(JSONArray jsonArr, String converterExp) {
        JSONArray resJson = new JSONArray();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            resJson.add(changeJsonObj(jsonObj, converterExp));
        }
        return resJson;
    }

    /**
     * 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String replace(String content,String formatType) {
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            return "";
        }
        if(FORMAT_TYPE_ALL.equals(formatType)){
            return "******";
        }

        int length=content.length();
        if(length<=8){
            return org.apache.commons.lang3.StringUtils.rightPad(
                    org.apache.commons.lang3.StringUtils.left(content, length-3),
                    org.apache.commons.lang3.StringUtils.length(content), "****");
        }

        if(length<=12){ ;
            return org.apache.commons.lang3.StringUtils.left(content, 3)
                    .concat(org.apache.commons.lang3.StringUtils.removeStart(
                            org.apache.commons.lang3.StringUtils.leftPad(
                                    org.apache.commons.lang3.StringUtils.right(content, 3),
                                    org.apache.commons.lang3.StringUtils.length(content), "******"), "****"));
        }

        return org.apache.commons.lang3.StringUtils.left(content, 6)
                .concat(org.apache.commons.lang3.StringUtils.removeStart(
                        org.apache.commons.lang3.StringUtils.leftPad(
                                org.apache.commons.lang3.StringUtils.right(content, 4),
                                org.apache.commons.lang3.StringUtils.length(content), "*"), "******"));
    }

    /**
     * 设置操作详情
     * @param proceedingJoinPoint
     * @return
     */
    @SneakyThrows
    private SysOperLog setOperContent(ProceedingJoinPoint proceedingJoinPoint){
        SysOperLog operLog = new SysOperLog();
        Log log = getAnnotationLog(proceedingJoinPoint);
        BusinessType type = log.businessType();
        String message = "";
        if (type == BusinessType.UPDATE){
            Object[] params = proceedingJoinPoint.getArgs();
            String param = "";
            JSONObject paramObj = null;
            try {
                if(params.length > 1){
                    param = JSON.marshal(params);
                }else {
                    param = JSON.marshal(params[0]);
                }
                paramObj = JSONObject.parseObject(param);
            } catch (Exception e) {
                return null;
            }
            if (paramObj.isEmpty()) {
                return null;
            }
            String methodName = log.methodName();
            String idVal = paramObj.getString(log.id());
            String serviceName = log.serviceName();
            if(StringUtils.isNotEmpty(methodName) &&
                    StringUtils.isNotEmpty(idVal) &&
                    StringUtils.isNotEmpty(serviceName)){
                Object clazz = SpringUtils.getBean(log.serviceName());
                Object object = selectObjectById(clazz, methodName, idVal);

                Field[] fields = object.getClass().getDeclaredFields();
                Map<String, String> beforeMap = Maps.newHashMap();
                Map<String, String> afterMap = Maps.newHashMap();
                for (Field field : fields) {
                    ChineseName chineseName = field.getAnnotation(ChineseName.class);
                    if (chineseName != null) {
                        String name = field.getName();
                        field.setAccessible(true);
                        beforeMap.put(name, field.get(object).toString());
                        afterMap.put(name,paramObj.getString(name));
                    }
                }

                String diff = JsonUtils.diff(JSON.marshal(beforeMap), JSON.marshal(afterMap));
                JSONObject diffJson = JSONObject.parseObject(diff);
                JSONArray diffArray = diffJson.getJSONArray("~");
                Map diffMap = jsonArrayToMap(diffArray);
                for (Field field : fields) {
                    ChineseName chineseName = field.getAnnotation(ChineseName.class);
                    if (chineseName != null) {
                        String name = field.getName();
                        if(diffMap.containsKey(name)){
                            message += "【" + chineseName.value() + "】字段，修改前的值：【" + beforeMap.get(name) + "】，修改后的值：【" + afterMap.get(name) + "】\n";
                        }
                    }
                }
            }
        }
        operLog.setOperContent(StringUtils.substring(setOperContent(log,message), 0, 2000));
        return operLog;
    }

    /**
     * 操作详情   现阶段仅仅提供了新增、修改、删除操作详情 其他类型以后扩展
     * @param log
     * @param message
     */
    private String setOperContent(Log log,String message) {
        BusinessType type = log.businessType();
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        boolean isAdmin = currentUser.isAdmin();
        String roleName = isAdmin ? "管理员" : "代理";
        String username = currentUser.getUserName();
        String title = log.title();
        String content = roleName+"【"+username+"】";
        switch (type) {
            /**
             * 授权
             */
            case GRANT:{
                content += "授权" + title;
                break;
            }
            /**
             * 导出
             */
            case EXPORT:{
                content += "导出" + title;
                break;
            }
            /**
             * 导入
             */
            case IMPORT:{
                content += "导入" + title;
                break;
            }
            /**
             * 强退
             */
            case FORCE:{
                content += "强退" + title;
                break;
            }
            /**
             * 生成代码
             */
            case GENCODE:{
                content += "生成代码" + title;
                break;
            }
            /**
             * 清空
             */
            case CLEAN:{
                content += "清空" + title;
                break;
            }
            /**
             * 其它
             */
            case OTHER:{
                content += "其它操作";
                break;
            }
            /**
             * 新增
             */
            case INSERT: {
                content += "新增" + title;
                break;
            }
            /**
             * 修改
             */
            case UPDATE: {
                content += "修改" + title + "," + message;
                break;
            }
            /**
             * 删除
             */
            case DELETE: {
                content += "删除" + title;
                break;
            }
        }
        return content;
    }
}
