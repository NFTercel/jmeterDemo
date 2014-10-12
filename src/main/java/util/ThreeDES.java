package util;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;


public class ThreeDES {

   private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
 
   public static final byte[] keyBytes = "我擦可币卡你大爷".getBytes();    //24字节的密钥

   public static final byte[] getKeyBytes2 = "大爷的大爷的大爷".getBytes();
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] src, byte[] key) {
       try {
            //生成密钥
            byte[] k = null;
           if (key == null) {
               k = keyBytes;
           } else {
               k = key;
           }
            SecretKey deskey = new SecretKeySpec(k, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] src, byte[] key) {
    try {

        byte[] k = null;
        //生成密钥
        if (null == key) {
            k = keyBytes;
        } else {
            k = key;
        }
        SecretKey deskey = new SecretKeySpec(k, Algorithm);

        //解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    } catch (Exception e3) {
          System.err.println("decryptMode异常"+e3.toString());
        }
        return null;
    }

    //转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";

        for (int n=0;n<b.length;n++) {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1)  hs=hs+":";
        }
        return hs.toUpperCase();
    }
    
    public static void main(String[] args) throws IOException {
        //添加新安全算法,如果用JCE就要把它添加进去
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());

  
        
/*        String szSrc = "cardPwd";
        
        System.out.println("加密前的字符串:" + szSrc);


//        szSrc = new String(Base64.encode(ThreeDES.encryptMode(szSrc.getBytes(), null)));
//        byte[] encoded = encryptMode(szSrc.getBytes());
        try {
			System.out.println("加密后的字符串:" + szSrc);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

  /*      String cardId = "in8tz4fImY9DRvKrQIvqVCovd+RkcICh";
        try {
            cardId = new String(ThreeDES.decryptMode(Base64.decode(cardId), null), "UTF-8");
            System.out.println(cardId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        System.out.println("解密后的字符串:" + (new String(decryptMode(Base64.decode("R73Ewiim4mZkLRwzROIIOCovd+RkcICh".getBytes()), null))));
     */
//        FileWriter writer = new FileWriter("/kbcard.txt");
        BufferedReader reader = new BufferedReader(new FileReader("/kbcard.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("/result.txt"));
        String line = null;
        while ((line = reader.readLine()) != null) {

            String encryCardId = line.substring(0, line.indexOf(","));
            String cardId = new String(ThreeDES.decryptMode(Base64.decode(encryCardId), null), "UTF-8");
            String data = line.replace(encryCardId, cardId);
            writer.write(data);
            writer.newLine();
        }


    }

}