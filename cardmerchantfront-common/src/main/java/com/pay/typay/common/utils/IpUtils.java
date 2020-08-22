package com.pay.typay.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP方法
 *
 * @author js-oswald
 */
public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String XFor ="未知";
        try{
            if (request == null) {
                return "unknown" ;
            }
            XFor = request.getHeader("X-Forwarded-For");
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = XFor.indexOf(",");
                if (index != -1) {
                    return XFor.substring(0, index);
                } else {
                    return XFor;
                }
            }

            XFor = request.getHeader("X-Real-IP");
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
                return XFor;
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("Proxy-Client-IP");
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("WL-Proxy-Client-IP");
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("HTTP_CLIENT_IP");
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getRemoteAddr();
            }
        }catch (Throwable e){
            return XFor;
        }
      return XFor;
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        if (StringUtils.isNull(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    private static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        try {
            String[] elements = text.split("\\." , -1);
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1" ;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知" ;
    }
}