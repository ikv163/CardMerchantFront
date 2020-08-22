package com.pay.typay.biz.reports.controller;

import com.pay.typay.biz.reports.service.IReportsDayService;
import com.pay.typay.biz.reports.vo.ReportsDayVO;
import com.pay.typay.biz.reports.vo.ReportsVO;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.system.domain.SysDictData;
import com.pay.typay.system.service.ISysDictDataService;
import com.pay.typay.system.service.ISysDictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 银行卡账变Controller
 * 
 * @author js-bucky
 * @date 2020-01-13
 */
@Controller
@RequestMapping("/reports/paymentstatement")
public class ReportsController extends BaseController
{
    private String prefix = "reports/paymentstatement";

    @Autowired
    private IReportsDayService reportsDayService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @RequiresPermissions("reports:paymentstatement:view")
    @GetMapping()
    public String paymentstatement()
    {
        return prefix + "/paymentstatement";
    }

    /**
     * 查询银行卡日交易报表列表
     */
    @RequiresPermissions("reports:paymentstatement:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReportsDayVO reportsDayVO)
    {
        startPage();
        List<ReportsDayVO> list = reportsDayService.selectReportsList(reportsDayVO);
        return getDataTable(list);
    }

    @RequiresPermissions("reports:paymentstatement:list")
    @PostMapping("/childList")
    @ResponseBody
    public TableDataInfo childList(ReportsDayVO reportsVO)
    {
        startPage();
        List<ReportsDayVO> list = reportsDayService.selectChildList(reportsVO);
        return getDataTable(list);
    }

    /**
     * 导出银行卡日交易报表列表
     */
    @RequiresPermissions("reports:paymentstatement:export")
    @Log(title = "导出收支報表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReportsDayVO reportsVO)
    {
        startPage();
        List<ReportsDayVO> list = reportsDayService.selectReportsList(reportsVO);
        list.stream().forEach(v -> {
            String transType = v.getTransType();
            String status = v.getStatus();
            String payType = v.getPayType();
            v.setTransTypeStr(transType == null ? "" : dictDataService.selectDictLabel("trans_type", transType));
            v.setStatusStr(status == null ? "" : dictDataService.selectDictLabel("order_status", status.toString()));
            v.setPayTypeStr(payType == null ? "" : dictDataService.selectDictLabel("payment_type", payType.toString()));
        });
        ExcelUtil<ReportsDayVO> util = new ExcelUtil<ReportsDayVO>(ReportsDayVO.class);
        return util.exportExcel(list, "reports","收支報表"+ DateUtils.getDate());
    }

}
