package com.yuan.middleware.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author yjm
 * @date 2020/4/21 3:38 下午
 */
@Slf4j
@Component
public class BankUtil implements InitializingBean {
    /**
     * 银行编码对应中文
     * 初始化bean的时候为bankMap赋值，由于Bank是单例的因此只会赋值一次
     */
    private static Map<String, String> bankMap = null;

    @Override
    public void afterPropertiesSet() {
        String json = "{\n" +
                "\t\"SRCB\": \"深圳农村商业银行\",\n" +
                "\t\"BGB\": \"广西北部湾银行\",\n" +
                "\t\"SHRCB\": \"上海农村商业银行\",\n" +
                "\t\"BJBANK\": \"北京银行\",\n" +
                "\t\"WHCCB\": \"威海市商业银行\",\n" +
                "\t\"BOZK\": \"周口银行\",\n" +
                "\t\"KORLABANK\": \"库尔勒市商业银行\",\n" +
                "\t\"SPABANK\": \"平安银行\",\n" +
                "\t\"SDEB\": \"顺德农商银行\",\n" +
                "\t\"HURCB\": \"湖北省农村信用社\",\n" +
                "\t\"WRCB\": \"无锡农村商业银行\",\n" +
                "\t\"BOCY\": \"朝阳银行\",\n" +
                "\t\"CZBANK\": \"浙商银行\",\n" +
                "\t\"HDBANK\": \"邯郸银行\",\n" +
                "\t\"BOC\": \"中国银行\",\n" +
                "\t\"BOD\": \"东莞银行\",\n" +
                "\t\"CCB\": \"中国建设银行\",\n" +
                "\t\"ZYCBANK\": \"遵义市商业银行\",\n" +
                "\t\"SXCB\": \"绍兴银行\",\n" +
                "\t\"GZRCU\": \"贵州省农村信用社\",\n" +
                "\t\"ZJKCCB\": \"张家口市商业银行\",\n" +
                "\t\"BOJZ\": \"锦州银行\",\n" +
                "\t\"BOP\": \"平顶山银行\",\n" +
                "\t\"HKB\": \"汉口银行\",\n" +
                "\t\"SPDB\": \"上海浦东发展银行\",\n" +
                "\t\"NXRCU\": \"宁夏黄河农村商业银行\",\n" +
                "\t\"NYNB\": \"广东南粤银行\",\n" +
                "\t\"GRCB\": \"广州农商银行\",\n" +
                "\t\"BOSZ\": \"苏州银行\",\n" +
                "\t\"HZCB\": \"杭州银行\",\n" +
                "\t\"HSBK\": \"衡水银行\",\n" +
                "\t\"HBC\": \"湖北银行\",\n" +
                "\t\"JXBANK\": \"嘉兴银行\",\n" +
                "\t\"HRXJB\": \"华融湘江银行\",\n" +
                "\t\"BODD\": \"丹东银行\",\n" +
                "\t\"AYCB\": \"安阳银行\",\n" +
                "\t\"EGBANK\": \"恒丰银行\",\n" +
                "\t\"CDB\": \"国家开发银行\",\n" +
                "\t\"TCRCB\": \"江苏太仓农村商业银行\",\n" +
                "\t\"NJCB\": \"南京银行\",\n" +
                "\t\"ZZBANK\": \"郑州银行\",\n" +
                "\t\"DYCB\": \"德阳商业银行\",\n" +
                "\t\"YBCCB\": \"宜宾市商业银行\",\n" +
                "\t\"SCRCU\": \"四川省农村信用\",\n" +
                "\t\"KLB\": \"昆仑银行\",\n" +
                "\t\"LSBANK\": \"莱商银行\",\n" +
                "\t\"YDRCB\": \"尧都农商行\",\n" +
                "\t\"CCQTGB\": \"重庆三峡银行\",\n" +
                "\t\"FDB\": \"富滇银行\",\n" +
                "\t\"JSRCU\": \"江苏省农村信用联合社\",\n" +
                "\t\"JNBANK\": \"济宁银行\",\n" +
                "\t\"CMB\": \"招商银行\",\n" +
                "\t\"JINCHB\": \"晋城银行JCBANK\",\n" +
                "\t\"FXCB\": \"阜新银行\",\n" +
                "\t\"WHRCB\": \"武汉农村商业银行\",\n" +
                "\t\"HBYCBANK\": \"湖北银行宜昌分行\",\n" +
                "\t\"TZCB\": \"台州银行\",\n" +
                "\t\"TACCB\": \"泰安市商业银行\",\n" +
                "\t\"XCYH\": \"许昌银行\",\n" +
                "\t\"CEB\": \"中国光大银行\",\n" +
                "\t\"NXBANK\": \"宁夏银行\",\n" +
                "\t\"HSBANK\": \"徽商银行\",\n" +
                "\t\"JJBANK\": \"九江银行\",\n" +
                "\t\"NHQS\": \"农信银清算中心\",\n" +
                "\t\"MTBANK\": \"浙江民泰商业银行\",\n" +
                "\t\"LANGFB\": \"廊坊银行\",\n" +
                "\t\"ASCB\": \"鞍山银行\",\n" +
                "\t\"KSRB\": \"昆山农村商业银行\",\n" +
                "\t\"YXCCB\": \"玉溪市商业银行\",\n" +
                "\t\"DLB\": \"大连银行\",\n" +
                "\t\"DRCBCL\": \"东莞农村商业银行\",\n" +
                "\t\"GCB\": \"广州银行\",\n" +
                "\t\"NBBANK\": \"宁波银行\",\n" +
                "\t\"BOYK\": \"营口银行\",\n" +
                "\t\"SXRCCU\": \"陕西信合\",\n" +
                "\t\"GLBANK\": \"桂林银行\",\n" +
                "\t\"BOQH\": \"青海银行\",\n" +
                "\t\"CDRCB\": \"成都农商银行\",\n" +
                "\t\"QDCCB\": \"青岛银行\",\n" +
                "\t\"HKBEA\": \"东亚银行\",\n" +
                "\t\"HBHSBANK\": \"湖北银行黄石分行\",\n" +
                "\t\"WZCB\": \"温州银行\",\n" +
                "\t\"TRCB\": \"天津农商银行\",\n" +
                "\t\"QLBANK\": \"齐鲁银行\",\n" +
                "\t\"GDRCC\": \"广东省农村信用社联合社\",\n" +
                "\t\"ZJTLCB\": \"浙江泰隆商业银行\",\n" +
                "\t\"GZB\": \"赣州银行\",\n" +
                "\t\"GYCB\": \"贵阳市商业银行\",\n" +
                "\t\"CQBANK\": \"重庆银行\",\n" +
                "\t\"DAQINGB\": \"龙江银行\",\n" +
                "\t\"CGNB\": \"南充市商业银行\",\n" +
                "\t\"SCCB\": \"三门峡银行\",\n" +
                "\t\"CSRCB\": \"常熟农村商业银行\",\n" +
                "\t\"SHBANK\": \"上海银行\",\n" +
                "\t\"JLBANK\": \"吉林银行\",\n" +
                "\t\"CZRCB\": \"常州农村信用联社\",\n" +
                "\t\"BANKWF\": \"潍坊银行\",\n" +
                "\t\"ZRCBANK\": \"张家港农村商业银行\",\n" +
                "\t\"FJHXBC\": \"福建海峡银行\",\n" +
                "\t\"ZJNX\": \"浙江省农村信用社联合社\",\n" +
                "\t\"LZYH\": \"兰州银行\",\n" +
                "\t\"JSB\": \"晋商银行\",\n" +
                "\t\"BOHAIB\": \"渤海银行\",\n" +
                "\t\"CZCB\": \"浙江稠州商业银行\",\n" +
                "\t\"YQCCB\": \"阳泉银行\",\n" +
                "\t\"SJBANK\": \"盛京银行\",\n" +
                "\t\"XABANK\": \"西安银行\",\n" +
                "\t\"BSB\": \"包商银行\",\n" +
                "\t\"JSBANK\": \"江苏银行\",\n" +
                "\t\"FSCB\": \"抚顺银行\",\n" +
                "\t\"HNRCU\": \"河南省农村信用\",\n" +
                "\t\"COMM\": \"交通银行\",\n" +
                "\t\"XTB\": \"邢台银行\",\n" +
                "\t\"CITIC\": \"中信银行\",\n" +
                "\t\"HXBANK\": \"华夏银行\",\n" +
                "\t\"HNRCC\": \"湖南省农村信用社\",\n" +
                "\t\"DYCCB\": \"东营市商业银行\",\n" +
                "\t\"ORBANK\": \"鄂尔多斯银行\",\n" +
                "\t\"BJRCB\": \"北京农村商业银行\",\n" +
                "\t\"XYBANK\": \"信阳银行\",\n" +
                "\t\"ZGCCB\": \"自贡市商业银行\",\n" +
                "\t\"CDCB\": \"成都银行\",\n" +
                "\t\"HANABANK\": \"韩亚银行\",\n" +
                "\t\"CMBC\": \"中国民生银行\",\n" +
                "\t\"LYBANK\": \"洛阳银行\",\n" +
                "\t\"GDB\": \"广东发展银行\",\n" +
                "\t\"ZBCB\": \"齐商银行\",\n" +
                "\t\"CBKF\": \"开封市商业银行\",\n" +
                "\t\"H3CB\": \"内蒙古银行\",\n" +
                "\t\"CIB\": \"兴业银行\",\n" +
                "\t\"CRCBANK\": \"重庆农村商业银行\",\n" +
                "\t\"SZSBK\": \"石嘴山银行\",\n" +
                "\t\"DZBANK\": \"德州银行\",\n" +
                "\t\"SRBANK\": \"上饶银行\",\n" +
                "\t\"LSCCB\": \"乐山市商业银行\",\n" +
                "\t\"JXRCU\": \"江西省农村信用\",\n" +
                "\t\"ICBC\": \"中国工商银行\",\n" +
                "\t\"JZBANK\": \"晋中市商业银行\",\n" +
                "\t\"HZCCB\": \"湖州市商业银行\",\n" +
                "\t\"NHB\": \"南海农村信用联社\",\n" +
                "\t\"XXBANK\": \"新乡银行\",\n" +
                "\t\"JRCB\": \"江苏江阴农村商业银行\",\n" +
                "\t\"YNRCC\": \"云南省农村信用社\",\n" +
                "\t\"ABC\": \"中国农业银行\",\n" +
                "\t\"GXRCU\": \"广西省农村信用\",\n" +
                "\t\"PSBC\": \"中国邮政储蓄银行\",\n" +
                "\t\"BZMD\": \"驻马店银行\",\n" +
                "\t\"ARCU\": \"安徽省农村信用社\",\n" +
                "\t\"GSRCU\": \"甘肃省农村信用\",\n" +
                "\t\"LYCB\": \"辽阳市商业银行\",\n" +
                "\t\"JLRCU\": \"吉林农信\",\n" +
                "\t\"URMQCCB\": \"乌鲁木齐市商业银行\",\n" +
                "\t\"XLBANK\": \"中山小榄村镇银行\",\n" +
                "\t\"CSCB\": \"长沙银行\",\n" +
                "\t\"JHBANK\": \"金华银行\",\n" +
                "\t\"BHB\": \"河北银行\",\n" +
                "\t\"NBYZ\": \"鄞州银行\",\n" +
                "\t\"LSBC\": \"临商银行\",\n" +
                "\t\"BOCD\": \"承德银行\",\n" +
                "\t\"SDRCU\": \"山东农信\",\n" +
                "\t\"NCB\": \"南昌银行\",\n" +
                "\t\"TCCB\": \"天津银行\",\n" +
                "\t\"WJRCB\": \"吴江农商银行\",\n" +
                "\t\"CBBQS\": \"城市商业银行资金清算中心\",\n" +
                "\t\"HBRCU\": \"河北省农村信用社\"\n" +
                "}";

        bankMap = (Map<String, String>) JSON.parse(json);
        log.info("bankInfo:" + bankMap);
    }

    /**
     * 根据银行卡号获取银行信息
     *
     * @param cardNo
     * @return
     */
    public static String getCardDetail(String cardNo) {
        // 创建HttpClient实例 阿里api查询银行接口
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=";
        url += cardNo;
        url += "&cardBinCheck=true";
        StringBuilder sb = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 根据银行卡号获取银行名称
     *
     * @param cardNo
     * @return
     */
    public static String getNameByCardNo(String cardNo) {
        String cardDetail = getCardDetail(cardNo);
        Map<String, String> cardMap = (Map<String, String>) JSON.parse(cardDetail);
        return bankMap.get(cardMap.get("bank"));
    }

    public static void main(String[] args) {
        getNameByCardNo("6215994767897656089");
    }
}
