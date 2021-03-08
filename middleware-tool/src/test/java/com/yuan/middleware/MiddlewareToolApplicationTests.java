package com.yuan.middleware;

import com.yuan.middleware.message.email.MailExchangeManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareToolApplicationTests {
    @Resource
    private MailExchangeManager mailExchangeManager;

    @Test
    public void contextLoads() {
        String content = "<table border=\"1\">\n" +
                "<tr>\n" +
                "<td>row 1, cell 1</td>\n" +
                "<td>row 1, cell 2</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>row 2, cell 1</td>\n" +
                "<td>row 2, cell 2</td>\n" +
                "</tr>\n" +
                "</table>";
//        mailExchangeManager.sendMail("yuanjiamin.yjm@sunyur.com", "test-email", content, null);
//        mailExchangeManager.sendMail("zhanghaolin.zhl@sunyur.com", "test-email", content, null);
        mailExchangeManager.sendMail("2459087172@qq.com", "test-email", content, null);
//        mailExchangeManager.sendMail("liuaiwei@vitasoy.com", "test", content, null);

    }

}
