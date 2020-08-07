package cn.ccoco.platform.common.utils;

import com.google.common.io.ByteSource;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @author CCoco
 */
public class Md5Util {

    private static final String HEX_NUMS_STR="0123456789ABCDEF";

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 5;


    public static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITH_NAME);
            byte[] digest = md.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }
}
