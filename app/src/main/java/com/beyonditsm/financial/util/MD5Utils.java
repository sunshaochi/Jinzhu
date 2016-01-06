package com.beyonditsm.financial.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangbin on 15/11/19.
 */
public class MD5Utils {

    public static String digest(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int num = b & 0xff;
                String hex = Integer.toHexString(num);
                // System.out.println(hex);
                if (hex.length() < 2) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // can't reach
            return "";
        }

    }
}
