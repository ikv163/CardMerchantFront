package com.pay.typay.biz.external;

/**
 * @author : Administrator
 * @Description: TODO
 * @date : 2020年05月26日 18:10
 */

import com.pay.typay.biz.orders.domain.Userecharge;
import com.pay.typay.biz.orders.service.IUserechargeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 充值订单对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/order/deposit")
public class DepositOrderExternalService extends BaseExternalService{
    @Autowired
    private IUserechargeService userechargeService;
    /**
     * 查询用户充值订单列表
     */
    @PostMapping("/list")
    @ResponseBody
    public List<Userecharge> list(Userecharge userecharge)
    {
        return userechargeService.selectUserechargeList(userecharge);
    }

    /**
     * 查看用户充值订单
     */
    @GetMapping("/get/{orderIndex}")
    @ResponseBody
    public Userecharge get(@PathVariable("orderIndex") Long orderIndex)
    {
        return userechargeService.selectUserechargeById(orderIndex);
    }
}
