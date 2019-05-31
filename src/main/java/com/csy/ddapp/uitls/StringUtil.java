package com.csy.ddapp.uitls;

import com.csy.ddapp.config.Constant;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

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
		sb.append("key=" + Constant.key);
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
}
