package com.pay.typay.biz.external;

/**
 * @author : Administrator
 * @Description: TODO
 * @date : 2020年05月26日 18:10
 */

import com.pay.typay.biz.orders.domain.Userwithdrew;
import com.pay.typay.biz.orders.service.IUserwithdrewService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 提款订单对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/order/withdrew")
public class WithdrewOrderExternalService extends BaseExternalService{
    @Autowired
    private IUserwithdrewService userwithdrewService;
    /**
     * 查询用户提款订单列表
     */
    @PostMapping("/list")
    @ResponseBody
    public   List<Userwithdrew> list(Userwithdrew userwithdrew)
    {
        return userwithdrewService.selectUserwithdrewList(userwithdrew);
    }

    /**
     * 查看用户提款订单
     */
    @GetMapping("/get/{orderIndex}")
    @ResponseBody
    public Userwithdrew get(@PathVariable("orderIndex") Long orderIndex)
    {
        return userwithdrewService.selectUserwithdrewById(orderIndex);
    }
}
