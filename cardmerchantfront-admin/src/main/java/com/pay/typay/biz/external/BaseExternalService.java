package com.pay.typay.biz.external;

import com.pay.typay.common.core.domain.AjaxResult;

/**
 * @author : aleck
 * @Description: 对外服务基础类
 * @date : 2020年05月26日 17:35
 */
public class BaseExternalService {

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }
    /**
     * 返回成功
     */
    private AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    private AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }
}
