package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 *
 * @author 
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {

    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(data);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5.digest();

    }

    /**
     * MD5加密,处理字符串
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String data) {
        byte[] messageDigest = encryptMD5(data.getBytes());
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        }
        return hexString.toString();
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }

    public static void main(String[] args){
    	String a = "5TMDAwMDZ8MTAwMDF8aW1laXxpbXNpdGVzdHxHN3w0ODB4ODAwfGFuZHJvaWR8MXwxLjF8MnwwVy8dN";
    	String b;
    	String c = "23|12345|1.0|12,14,87,jj,ff|1|土豪金5S|15000993473|123786|haha|123456|321";
    	String d = "23|3212332|M28|20141001101010|116.404|1|39.915|1|100|132|16|460|1|89|2206|AC|1|88,23|3212332|M28|20141001101010|128.404|1|40.915|1|100|132|16|460|1|89|2206|AC|1|88,23|3212332|M28|20141001101010|119.404|1|39.915|1|100|132|16|460|1|89|2206|AC|89|2206|AC|89|2206|AC|89|2206|AC|89|2206|AC|89|2206|AC|1|88";
    	String e = "01|ovi00000002|oviphone123|20040101000002|0.000000|0|0.000000|0|0.000000|0|0|460|0|62833|6338|57|62339|6338|159|62835|6338|152|62593|6338|151|62626|6338|142|62834|6338|140|255|01";
		try {
//			String[] ss = c.split("\\|");
//			System.out.println(ss[3]);
	String dd = Coder.encryptBASE64(e.getBytes());
	System.out.println(dd);
	
	String ee = "MTJ8MjE0fHNmc2R8Z2ZkZ3xmZC0tLS0tLS0tLS0tLS0tLS0tPT09PT09PT09PWdnc2YzMzN8MjIy";
	byte[] f= Coder.decryptBASE64(e);
	String strRead = new String(f);
	String strRead1 = new String(strRead);
	String[] tt = strRead1.split("|");
	System.out.println(tt[8]);
	
	String aa = Coder.encryptBASE64("23|12345|12,14,87,jj,ff|123456|123456|1|15000993473|80655|123456|321".getBytes());
	System.out.println("aa:"+aa);
		} catch (Exception eee) {
			// TODO Auto-generated catch block
			eee.printStackTrace();
		}
    	
    }
}
