package com.pay.typay;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName FinancialServletInitializer
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/3 下午8:31
 **/
public class CardmerchantfrontServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CardmerchantfrontAppliction.class);
    }
}
