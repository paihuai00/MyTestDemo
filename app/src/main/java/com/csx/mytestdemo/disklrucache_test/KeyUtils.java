package com.csx.mytestdemo.disklrucache_test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Date: 2019/6/25
 * create by cuishuxiang
 * description:
 */
public class KeyUtils {

    /**
     * 根据 url，获取一个 MD5 加密的key
     * @param url
     * @return
     */
    public static String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
