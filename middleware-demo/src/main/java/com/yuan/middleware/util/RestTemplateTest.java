package com.yuan.middleware.util;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest {
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        String url = "http://10.253.124.53:8082/v1/accounts/getByUserId/capital/8a0bb234549ebd6b01549ed1d3db0858";
        long start = System.currentTimeMillis();
        Object forObject = restTemplate.getForObject(url, Object.class);
        System.out.println(forObject);
        System.out.println("时间：" + (System.currentTimeMillis() - start));

    }

    private static void noticeYuYin() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> param = new HashMap<>();
        param.put("phones", "18812069097");
        param.put("content", "123456");
        param.put("contentType", "");
        Object object = restTemplate.postForObject("http://10.139.39.43:8623/notice/sendYuYin", param, Object.class);
        System.out.println(object);
    }

    private static void notice() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        map.put("toName", "18812069097");
        map.put("msType", "[\"ms_message\"]");
        map.put("msgTempOrList", "[{\"smsValidateCode\":\"917150\"}]");
        map.put("busiType", 1);
        restTemplate.postForObject("http://10.139.39.43:8623/notice/excuteSend", map, Object.class);
    }

    private static void repay() {
        RestTemplate restTemplate = new RestTemplate();
        //获取T_REPAY_FINANCE_DETAIL状态等于4的订单号
        String str = "101004_201909291529158507864";
        String[] orderIds = str.split(",");
        for (String orderId : orderIds) {
            restTemplate.delete("http://10.253.126.9:9999/trade-web/trade/deleteTrade?orderId=" + orderId);
            Object object = restTemplate.getForObject("http://10.253.126.9:9999/finance-repay-support/repay/repayRetry/" + orderId + "/S", Object.class);
            System.out.println(object);
        }
    }

}
