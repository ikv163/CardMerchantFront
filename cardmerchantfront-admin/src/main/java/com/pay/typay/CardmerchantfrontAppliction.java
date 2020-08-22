package com.pay.typay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * @ClassName FinancialFrontAppliction
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/3 下午8:30
 **/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CardmerchantfrontAppliction {
    public static void main(String[] args) {
        SpringApplication.run(CardmerchantfrontAppliction.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 天下支付前台  启动成功   ლ(´ڡ`ლ)ﾞ  \n" + ",--------.                                         ,---.                            ,--.   \n" +
                "'--.  .--' ,--. ,--.  ,---.   ,--,--. ,--. ,--.   /  .-' ,--.--.  ,---.  ,--,--,  ,-'  '-. \n" +
                "   |  |     \\  '  /  | .-. | ' ,-.  |  \\  '  /    |  `-, |  .--' | .-. | |      \\ '-.  .-' \n" +
                "   |  |      \\   '   | '-' ' \\ '-'  |   \\   '     |  .-' |  |    ' '-' ' |  ||  |   |  |   \n" +
                "   `--'    .-'  /    |  |-'   `--`--' .-'  /      `--'   `--'     `---'  `--''--'   `--'   \n" +
                "           `---'     `--'             `---'                       ");

    }
}
