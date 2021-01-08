package cn.deliver.water.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

/**
 * @ClassName WxCharUtil
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.09 下午 5:48
 * Version 1.0
 **/
public class WxCharUtil {

        /**
         * 解密 微信密文
         * @param encryptedData 密文
         * @param sessionKey sessionKey
         * @param iv iv
         * @return 明文
         */
        public static String decrypt(String encryptedData, String sessionKey, String iv) {
            String result = "";
            // 被加密的数据
            byte[] dataByte = Base64.decodeBase64(encryptedData);
            // 加密秘钥
            byte[] keyByte = Base64.decodeBase64(sessionKey);
            // 偏移量
            byte[] ivByte = Base64.decodeBase64(iv);
            try {
                // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
                int base = 16;
                if (keyByte.length % base != 0) {
                    int groups = keyByte.length / base + 1;
                    byte[] temp = new byte[groups * base];
                    Arrays.fill(temp, (byte) 0);
                    System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                    keyByte = temp;
                }
                // 初始化
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
                AlgorithmParameters parameters = AlgorithmParameters
                        .getInstance("AES");
                parameters.init(new IvParameterSpec(ivByte));
                cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
                byte[] resultByte = cipher.doFinal(dataByte);
                if (null != resultByte && resultByte.length > 0) {
                    result = new String(resultByte, StandardCharsets.UTF_8);
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidParameterSpecException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


