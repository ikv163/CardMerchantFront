package com.pay.typay.biz.external;

import com.pay.typay.biz.domain.BankClient;
import com.pay.typay.biz.service.IBankClientService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 银行卡客户端对外服务
 * @date : 2020年05月26日 17:35
 */
@Controller
@RequestMapping("/external/bankcard_client")
public class BankClientExternalService extends BaseExternalService {
    @Autowired
    private IBankClientService bankClientService;
    /**
     * 查询银行卡客户端列表
     */
    @PostMapping("/list")
    @ResponseBody
    public List<BankClient> list(BankClient bankClient) {
        return bankClientService.selectBankClientList(bankClient);
    }
    /**
     * 查看银行卡客户端
     */
    @GetMapping("/get/{clientId}")
    @ResponseBody
    public BankClient get(@PathVariable("clientId") Long clientId) {
        return  bankClientService.selectBankClientById(clientId);
    }
}
