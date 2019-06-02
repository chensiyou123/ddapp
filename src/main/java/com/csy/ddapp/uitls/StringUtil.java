package com.csy.ddapp.uitls;

import com.csy.ddapp.config.Constant;
import com.taobao.api.internal.toplink.embedded.websocket.util.Base64;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * 字符串工具类
 * @author
 *
 */
public class StringUtil {

    /**
     * 生成由[A-Z,0-9]生成的随机字符串
     * @param length  欲生成的字符串长度
     * @return
     */
    public static String getRandomString(int length){
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i){
            int number = random.nextInt(2);
            long result = 0;

            switch(number){
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:

                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

    /**
     * 客户端签名
     * @param map
     * @return
     */
    public static String getSign(Map<String,Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append(Constant.key);
        // 创建加密对象
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(sb.toString().getBytes("UTF-8"));
            byte byteBuffer[] = messageDigest.digest();
            // 將 byte数组转换为16进制string
            return  HexString(byteBuffer);
        }catch (Exception e){

        }
        return null;
    }

    /**
     * 字节数组转换为16进制字符串
     * @param baSrc
     * @return
     */
    public static String HexString(byte[] baSrc) {
        if (baSrc == null) {
            return "";
        }
        int nByteNum = baSrc.length;
        StringBuilder sbResult = new StringBuilder(nByteNum * 2);
        for (int i = 0; i < nByteNum; i++) {
            char chHex;
            byte btHigh = (byte)((baSrc[i] & 0xF0) >> 4);
            if (btHigh < 10) {
                chHex = (char)('0' + btHigh);
            } else {
                chHex = (char)('A' + (btHigh - 10));
            }
            sbResult.append(chHex);
            byte btLow = (byte)(baSrc[i] & 0x0F);
            if (btLow < 10) {
                chHex = (char)('0' + btLow);
            }
            else {
                chHex = (char)('A' + (btLow - 10));
            }
            sbResult.append(chHex);
        }
        return sbResult.toString();
    }


    /**
     * 验签
     * @param strToSign ：待签名的字符串
     * @param strSign：签名内容为
     * @param publicKey ：招行通知公钥为publicKey
     * @return
     */
    public static boolean isValidSignature(String strToSign, String strSign, String publicKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//算法
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");//加密算法
            signature.initVerify(pubKey);
            signature.update(strToSign.getBytes("UTF-8") );
            boolean bverify = signature.verify( Base64.decode(strSign) );
            return bverify;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取公钥
     * @return
     */
    public static String getPublicKey(){
        Map map = new HashMap();
        map.put("dateTime",new java.sql.Timestamp(System.currentTimeMillis()));
        map.put("txCode","FBPK");
        map.put("branchNo",Constant.branchNo);
        map.put("merchantNo",Constant.merchantNo);
        Map requetMap = new HashMap();
        requetMap.put("version","1.0");
        requetMap.put("charset","UTF-8");
        String sign = StringUtil.getSign(map);
        requetMap.put("sign",sign);
        requetMap.put("signType","SHA-256");
        requetMap.put("reqData",map);
        String s = HttpUtil.post(Constant.PublicKeyUrl,requetMap);
        return s;
    }


    /**
     * 查询单笔数据
     * @param bankSerialNo 银行订单流水号
     * @param managerId 商户结账系统的操作员
     * @return
     */
    public static String getOneOrderDate(String bankSerialNo,String managerId){
        Map map = new HashMap();
        map.put("dateTime",new java.sql.Timestamp(System.currentTimeMillis()));
        map.put("branchNo",Constant.branchNo);
        map.put("merchantNo",Constant.merchantNo);
        map.put("type","A");
        map.put("bankSerialNo",bankSerialNo);
        map.put("date",new Date());
        map.put("operatorNo",managerId);
        Map requetMap = new HashMap();
        requetMap.put("version","1.0");
        requetMap.put("charset","UTF-8");
        String sign = StringUtil.getSign(map);
        requetMap.put("sign",sign);
        requetMap.put("signType","SHA-256");
        requetMap.put("reqData",map);
        String s = HttpUtil.post(Constant.oneOrderDateUrl,requetMap);
        return s;
    }

    /**
     * 查询协议
     * @param merchantSerialNo
     * @param agrNo
     * @return
     */
    public static String getagrNo(String merchantSerialNo,String agrNo){
        Map map = new HashMap();
        map.put("dateTime",new java.sql.Timestamp(System.currentTimeMillis()));
        map.put("txCode","CMCX");
        map.put("branchNo",Constant.branchNo);
        map.put("merchantNo",Constant.merchantNo);
        map.put("merchantSerialNo",merchantSerialNo);//商户做此查询请求的流水号
        map.put("agrNo",agrNo);//商户做此查询请求的流水号

        Map requetMap = new HashMap();
        requetMap.put("version","1.0");
        requetMap.put("charset","UTF-8");
        String sign = StringUtil.getSign(map);
        requetMap.put("sign",sign);
        requetMap.put("signType","SHA-256");
        requetMap.put("reqData",map);
        return HttpUtil.post(Constant.agrNoUrl,requetMap);
    }






}
