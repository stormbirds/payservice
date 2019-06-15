

package cn.stormbirds.payservice.common.util.sign.encrypt;
/**
 *
 * @description Base64.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 17:15
 *
 */
public class Base64 {

    private Base64() {}

    public static byte[] decode(String str) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(str);
    }

    public static String encode(byte[] bytes) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }

}
