package com.pay.typay.biz.external;

import com.pay.typay.biz.domain.Paychannel;
import com.pay.typay.biz.service.IPayChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : aleck
 * @Description: 渠道对外服务
 * @date : 2020年05月26日 17:35
 */
@Slf4j
@Controller
@RequestMapping("/external/channel")
public class ChannelExternalService extends BaseExternalService {
    @Autowired
    private IPayChannelService payChannelService;
    /**
     * 查询支付渠道列表
     */
    @PostMapping("/list")
    @ResponseBody
    public   List<Paychannel> list(Paychannel paychannel) {
        return payChannelService.selectBankPaychannelList(paychannel);
    }
    /**
     * 查看支付渠道
     */
    @RequiresPermissions("external:channel:list")
    @GetMapping("/get/{channelId}")
    @ResponseBody
    public Paychannel get(@PathVariable("channelId") Long channelId) {
        return payChannelService.selectPaychannelById(channelId, 2L);
    }
}
