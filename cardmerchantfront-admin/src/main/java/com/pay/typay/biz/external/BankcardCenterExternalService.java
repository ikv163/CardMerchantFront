package com.pay.typay.biz.external;

import com.pay.typay.biz.domain.TBankcardcenter;
import com.pay.typay.biz.service.ITBankcardcenterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 银行卡对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/bankcard")
public class BankcardCenterExternalService extends BaseExternalService{
    @Autowired
    private ITBankcardcenterService tBankcardService;

    /**
     * 查询银行卡列表
     */
    @PostMapping("/list")
    @ResponseBody
    public  List<TBankcardcenter> list(TBankcardcenter tBankcard) {
        return tBankcardService.selectTBankcardList(tBankcard);
    }

    @GetMapping("/get/{bankid}")
    @ResponseBody
    public TBankcardcenter get(@PathVariable("bankid") Long bankid) {
        return  tBankcardService.selectTBankcardById(bankid);
    }
}
