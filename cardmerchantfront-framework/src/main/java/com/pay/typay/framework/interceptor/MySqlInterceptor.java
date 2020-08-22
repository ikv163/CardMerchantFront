
package com.pay.typay.framework.interceptor;

import com.pay.typay.common.annotation.InterceptAnnotation;
import com.pay.typay.common.json.JSONObject;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.aspectj.LogAspect;
import com.pay.typay.framework.util.LogContextHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Intercepts(value = {
        //@Signature(type = StatementHandler.class, method = "upda e", args = {Statement.class}),
        //@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class}),
        //@Signature(type = StatementHandler.class, method = "query", args = {Statement.class,ResultHandler.class}),
        //@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        //@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        //@Signature(type = Executor.class, method = "commit", args = {boolean.class}),
        //@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        //@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,BoundSql.class})
})
public class MySqlInterceptor implements Interceptor {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //Executor.class  sql 获取方法
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
        String mId = ms.getId();
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = ms.getSqlCommandType().toString();

        //获取到原始sql语句
        try {
            if ("UPDATE".equalsIgnoreCase(sqlCommandType)) {
                //注解逻辑判断  添加注解了才拦截//InterceptAnnotation
                Class<?> classType = Class.forName(mId.substring(0, mId.lastIndexOf(".")));
                if (classType != null) {
                    String mName = mId.substring(mId.lastIndexOf(".") + 1);
                    for (Method method : classType.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(InterceptAnnotation.class) && mName.equals(method.getName())) {
                            InterceptAnnotation interceptorAnnotation = method.getAnnotation(InterceptAnnotation.class);
                            //flag 为false ,直接返回，不执行记录操作前信息
                            if (!interceptorAnnotation.flag()) {
                                System.err.println("interceptorAnnotation--" + mName);
                                return invocation.proceed();
                            }
                        }
                    }
                }
                LogContextHolder.clearLogType();
                Object parameter = args[1];
                if (selectOperateBeforeInfo(ms, parameter)) {
                    return invocation.proceed();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return invocation.proceed();
    }

    /**
     * 查询操作前信息
     */
    private boolean selectOperateBeforeInfo(MappedStatement ms, Object parameter) {
        String selectSql;
        String originalSql = null;
        try {
            BoundSql boundSql = ms.getBoundSql(parameter);
            // 获取节点的配置
            Configuration configuration = ms.getConfiguration();

            // 获取到最终的sql语句
            originalSql = getSql(configuration, boundSql);
            //这里使用原始语句，带？号的
            selectSql = getQuerySqlByRegEx(boundSql.getSql());
            if (StringUtils.isNotEmpty(selectSql)) {
                //得到where
                String where = getWhere(originalSql);
                //最终查询 sql
                if (StringUtils.isNotEmpty(where)) {
                    selectSql += where;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            selectSql = null;
        }

        if (StringUtils.isEmpty(selectSql)) {
            selectSql = getQuerySql(originalSql);
            if (StringUtils.isEmpty(selectSql)) {
                return true;
            }
        }
        if (null != selectSql && !"".equals(selectSql)) {
            List<Map<String, Object>> returnList = jdbcTemplate.queryForList(selectSql);
            if (returnList.size() != 1) {
                return true;
            }
            assembleJson(returnList);
        }
        return false;
    }

    /**
     * 组装json数据
     */
    private void assembleJson(List<Map<String, Object>> returnList) {
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> list = LogContextHolder.getLogType();
        Map<String, Object> returnMap = returnList.get(0);
        for (String key : returnMap.keySet()) {
            JSONObject.JSONArray jsonArray = new JSONObject.JSONArray();
            //数据脱敏
            if (LogAspect.sensitiveMap.containsKey(key.toLowerCase())) {
                String formatType = LogAspect.sensitiveMap.get(key.toLowerCase());
                jsonArray.add(LogAspect.replace(String.valueOf(returnMap.get(key)), formatType));
            } else {
                jsonArray.add(returnMap.get(key));
            }
            jsonObject.put(key, jsonArray);
        }
        if (jsonObject.size() > 0) {
            list.add(jsonObject);
            LogContextHolder.setLogType(list);
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;

    }

    @Override
    public void setProperties(Properties properties) {
    }


    /**
     * 封装了一下sql语句，
     * 使得结果返回完整xml路径下的sql语句节点id + sql语句
     */
    private String getSql(Configuration configuration, BoundSql boundSql) {
        return showSql(configuration, boundSql);
    }

    /**
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号；
     * 对参数是null和不是null的情况作了处理<br>
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }


    /**
     * 进行？的替换
     */
    private String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换　　　　　　　
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                //MetaObject主要是封装了originalObject对象，
                // 提供了get和set的方法用于获取和设置originalObject的属性值,
                // 主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));

                    } else {
                        //打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    private static String getQuerySql(String sql) {
        String reg = "\\s+[^\\s]+\\s+";
        ;
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(sql);
        String mSql = "select * from ";
        if (m.find()) {
            mSql += m.group();
        }
        if (sql.toUpperCase().contains(" WHERE ")) {
            return mSql + sql.substring(sql.toLowerCase().lastIndexOf(" where "));
        }
        //如果不包含where语句，不启用查询
        return "";
    }

    /**
     * 通过正则获取sql
     *
     * @param originalSql 原始sql
     * @return 查询语句
     */
    private static String getQuerySqlByRegEx(String originalSql) {
        //1. select
        StringBuilder builder = new StringBuilder(" select ");

        //2. 得到查询的字段
        String regEx = "\\w+\\s*=";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(originalSql);
        boolean result;

        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String group = (matcher.group().replaceAll("=", "")).toLowerCase().trim();
            if (!list.contains(group)) {
                list.add(group);
            }
        }
        if (list.isEmpty()) {
            builder.append(" * ");
        } else {
            builder.append(String.join(",", list));
        }

        //3.from
        builder.append(" from ");

        //4.得到table
        String reg = "\\s+[^\\s]+\\s+";
        ;
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(originalSql);
        result = matcher.find();
        if (!result) {
            return null;
        }
        builder.append(matcher.group());
        return builder.toString();
    }

    private static String getWhere(String newSql) {
        //5.得到where 字符串
        //匹配: 前面至少一个空格，后面至少一个空格
        String regEx = "\\swhere\\s.*$";
        //Pattern.CASE_INSENSITIVE  区分大小写
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(newSql);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        //直接返回空字符串
        return null;
    }
}
