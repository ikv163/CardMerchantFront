package com.pay.typay.biz.service;

import com.pay.typay.biz.dict.ITDictBankCommonService;
import com.pay.typay.common.core.domain.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommonInfoController
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/12 下午5:27
 **/
@RestController
@RequestMapping("/bankcarcomm")
public class BankcardCommonController {
    @Autowired
    private ITDictBankCommonService dictbank;

    @GetMapping("/bankpoomenus")
    public List<Ztree> getbankpoolgroup() {
        Integer roleType = null;
        return dictbank.bankpoolMenus(roleType);
    }
    @GetMapping("/bankpoomenus/{roleType}")
    public List<Ztree> getbankpoolgroup(@PathVariable Integer roleType ) {
            return dictbank.bankpoolMenus(roleType);
    }
    @GetMapping("/bankpoomenus/{roleType}/{poolid}")
    public List<Ztree> getbankpoolgroup(@PathVariable Long roleType,@PathVariable Long poolid ) {
        Map<String,Object> map= new HashMap<>();
        map.put("roleType",roleType);
        map.put("poolid",poolid);
        return dictbank.bankpoolMenus(map);
    }
}
