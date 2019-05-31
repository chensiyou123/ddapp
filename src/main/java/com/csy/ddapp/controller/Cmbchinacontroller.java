package com.csy.ddapp.controller;

import com.csy.ddapp.config.Constant;
import com.csy.ddapp.domain.ReqData;
import com.csy.ddapp.uitls.DataUtils;
import com.csy.ddapp.uitls.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("cmb")
public class Cmbchinacontroller {

    @RequestMapping("index")
    public static String check(HttpServletRequest req, HttpServletResponse resp, Model model) throws Exception {
        Map map = new HashMap();
        map.put("dateTime", new Timestamp(System.currentTimeMillis())); // 请求时间,格式：yyyyMMddHHmmss
        map.put("branchNo", Constant.branchNo); // 分行号，4位数字
        map.put("date", DataUtils.getDate(null)); // 订单日期,格式：yyyyMMdd
        map.put("orderNo", StringUtil.getRandomString(32)); // 订单号
        DecimalFormat df = new DecimalFormat("#.00");
        double f = 111231.5585;
        map.put("amount",df.format(f)); // 金额，格式：xxxx.xx
        map.put("payNoticeUrl",Constant.retrunUrl); //商户接收成功支付结果通知的地址。
        map.put("signNoticeUrl",Constant.retrunUrl);//成功签约结果通知地址
        map.put("signNoticeUrl", Constant.retrunUrl);//成功签约结果通知地址
        Map reqData = map;
        String sign = StringUtil.getSign(map);
        model.addAttribute("sign",sign);
        model.addAttribute("reqData",StringUtil.mapToObject(reqData, ReqData.class));
        model.addAttribute("title","张三");
        return "index";
    }

    @RequestMapping("save")
    public static String save(@RequestBody Model model){
        return "index";
    }


}
