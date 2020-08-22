package com.pay.typay.biz.external;

import com.pay.typay.biz.bankcard.domain.Statementcenter;
import com.pay.typay.biz.bankcard.service.IStatementcenterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 订单流水对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/order_flow")
public class OrderFlowExternalService extends BaseExternalService{

    @Autowired
    private IStatementcenterService tBanktransService;

    /**
     * 查询流水列表
     */
    @PostMapping("/list")
    @ResponseBody
    public List<Statementcenter> list(Statementcenter tBanktrans) {
        return tBanktransService.selectStatementcenterList(tBanktrans);
    }

    /**
     * 查询单条流水
     * @param transId
     * @return
     */
    @GetMapping("/get/{transId}")
    @ResponseBody
    public Statementcenter get(@PathVariable("transId") Long transId) {
        return  tBanktransService.selectStatementcenterById(transId);
    }
}
