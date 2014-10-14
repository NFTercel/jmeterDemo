/**
 * Copyright (c) 2011, TNT All Rights Reserved.
 * FileName:RsaUtil.java
 * ProjectName:insidepay
 * PackageName:com.oppo.common
 * Description:TODO 
 * Create Date:2011-8-29
 * History:
 *   ver	date	  author		desc	
 * ────────────────────────────────────────────────────────
 *   1.0	2011-8-29	  80051745		
 *
 * 
 */

package util;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
//import com.oppo.base.security.Base64;

/**
 * ClassName:RsaUtil Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 80051745
 * @version
 * @since Ver 1.1
 * @Date 2011-8-29 下午07:21:45
 */
public class RsaUtil {
    

    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 获取系统私钥
     * 
     * @param
     * @return
     */
    
    public static String getPrivateKey() {
    
        Reader rd = new InputStreamReader(RsaUtil.class.getClassLoader().getResourceAsStream("nearme_rsa_private_key.pem"));
        int count = 0;
        char[] str = new char[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((count = rd.read(str)) != -1) {
                sb.append(str);
            }
            rd.close();
            
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getPrivateKeyFromFile(String partnerId) {
        
    	StringBuilder filename = new StringBuilder();
    	filename.append(partnerId);
    	filename.append("rsa_private_key.pem");
        Reader rd = new InputStreamReader(RsaUtil.class.getClassLoader().getResourceAsStream(filename.toString()));
        int count = 0;
        char[] str = new char[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((count = rd.read(str)) != -1) {
                sb.append(str);
            }
            rd.close();
            
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public static String getPrivateKey(String partnerId) {
        
        //获取开发者上传的公钥
        HmacKeyEntity hke= Tools.getUserKey(partnerId);
        if(null!=hke){
            return hke.getSystem_private_key();
        }
        return null;
    }*/
    
    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 获取系统公钥
     * 
     * @param
     * @return
     */
    
    public static String getPublicKey() {
    
        Reader rd = new InputStreamReader(RsaUtil.class.getClassLoader().getResourceAsStream("com/oppo/config/rsa_public_key.pem"));
        int count = 0;
        char[] str = new char[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((count = rd.read(str)) != -1) {
                sb.append(str);
            }
            rd.close();
            return sb.toString().replace("\n", "").replace("\r", "").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getPublicKeyFromFile(String partnerId) {
        
    	StringBuilder filename = new StringBuilder();
    	filename.append(partnerId);
    	filename.append("rsa_public_key.pem");
        Reader rd = new InputStreamReader(RsaUtil.class.getClassLoader().getResourceAsStream(filename.toString()));
        int count = 0;
        char[] str = new char[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((count = rd.read(str)) != -1) {
                sb.append(str);
            }
            rd.close();
            return sb.toString().replace("\n", "").replace("\r", "").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
/*    public static String getPublicKey(String partnerId) {
        
        //获取开发者上传的公钥
        HmacKeyEntity hke= Tools.getUserKey(partnerId);
        if(null!=hke){
        	if (log.isDebugEnabled()) {
        		log.debug("签名公钥：" + hke.getHmac() + ", partnerId:" + partnerId); 
        	}
            return hke.getHmac();
        }
        return null;
    }*/
    
    public static void main(String[] arg) throws UnsupportedEncodingException {
    
      /*  String str = "NP+lQ6iKISUbfR4ENz5zQf7tVZvYDQaP1vtQlOG67EKjoy8+SsMykd/3mQYOd8/UbNSQTEWp5iBgNYiWSNvuLfxAwNaPrbfbFNGsGv/Bk5zFF0NOhq1ROZ2/1dCORWKk0RPn4eqCfN2DopPMF2MA/73punrOsadUCIArywmS/A4=";
        
        String baseString = "partner_id=4692862&partner_order=ST2013011612032300051909&product_name=中国好声音图片作品集&product_describe=null&product_totle_fee=0.01&product_count=1&packageName=com.android.individuationSettings&notify_url=http://appstorejava2.wanyol.com/MobileAPI/PayCallback.ashx&pay_result=success";
        
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdmErtzWi09mfD0C2j8Mb42tH3hgta+cgGlCRv" + "XI7ToQbcEf8xGEYYDg7hLqK6hUeCs4ZEKsdwcvo0g1iKUCHiVRDzpOUWiGrGEgnYhj4YAMrSx7Ng" + "7EB5J81QseCX3gWKmqIV+RO1Jpc6Qu4XcLi8t7pCaAZP3z7cU+Z+hr0OkQIDAQAB";
        
        // System.out.println(doCheck(baseString, str, pubKey));
//        System.out.println(new String(encrypt(pubKey, baseString.getBytes())));
        
        String data = "I4dF5j6QhmYjpRhGGcpW+X1HHSquvZhm5BxV4bXYC3LJptUcvQRPkn8Myv4IkZ0yJkjV7jV/yMsczeATvcvNXdxf8+Zy+pqomogzMHVKuIoWmx26fG/uyXfuPLgXEQdsiIBQhkBtKWQJZgxrwWGfRqfC6T2+JOWVMUIYZGrIGTVpnFjIV11FucCPe9kVWj9SK41BnnoKETYmFC2VB63X3+tfKQbOSS5ypblZO1Vgfvq3WEsh1OQ4UIZfV0tWMNJPKFmIlq7QwFuLg1zUWNgdQ+DYlmfpp08Li75LIQywOrUegAvw7migpvCaowjZNKgcpozrV73oGwm3SwYvbIFytuJBAbyO6N1QC0AxRmbClTQZsIxFe4BF1PTydUglGGHzJEHbuyjAB9F9+JcVd3GES+lxJj4/DCQunXfK8PLoaU41QOMY7dq1QlrKbSc5waC19Hc9S0MTeMk7/8+xTiuZev9tSkX/9ROA0cafUviF+09WNCkhKiPNVUw18/ha7Sec" +
        		"";
        
        System.out.println(new String(encrypt(data.getBytes())));
        
//       String d = decrypt(data);          */
    	
/*   	String d = "hello";
     	String key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOmBJblWRnr/G+H7Axd7Os6iQ12K6m6+4yPhFUnLjhvJYO6mXgffvP3TsDbpFV0XMx7ItRAHiuwMhpd3EI4OWWNZb78r3q4XkywO04VQHDtWU5yUiNxhO/9gNAS6Xwmheg67LVh/dvmxtOlO5VNyX3u0C4snw+gZ7Yrs3cHV5RCpAgMBAAECgYBsrxgH9AhKJ4Oq41LFEy6EDzKbz5TehyriAFoQRxaWCF19tyH9OD6XRni2ljbDZZD9ux3LoDchN5NN3LVv6W3g1JaOqKIIfdrOhTEtHhcO5wGHHxl61D/bJ3ojxFieMtsRb1BaDRbfrQkLsZtiw0qghrfYxUte9xtU/YiBBQD8oQJBAPh8B3WXRuPOWWMfl94pIrnOoEC1s39PEvjKIFeSk0q+DxQLI2oXcOmkZaYRarrZv/qdhUWYb6rdkOJTWSuSYqsCQQDwkSF2W2aqgnQBTqm1UmQoomh7kOCpCVQBLx5W4NngW6+JTxyZbWRcCEAHTLjbqjmH8gWjj86egp2ESMJiMvn7AkA4QiyWLRf5v+BxFtgVjo60LtoNjJYFPuv2tBy6dw8uHvXe/d6YyJHh9Dynas4VW/OhVES2SxsNPnjtJR3NRaldAkEAlbPK3gts7Si3JeUcyOBXwc2nNVXn6GkMpJv8xlWwX+TObKUViAjCDl938NL2qoPYv1eF2M3x50Qi36IjjNZqJwJBAJsxYNIYWwk+clCy8rMEkcwYv9XbhDAF3paRwMEgWAOXKjlmCwa/gXFgIVLxoYqxuSTcqVoMQsUV/NBF6wQoSL4=";
     	  
   	String k = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpgSW5VkZ6/xvh+wMXezrOokNdiupuvuMj4RVJy44byWDupl4H37z907A26RVdFzMeyLUQB4rsDIaXdxCODlljWW+/K96uF5MsDtOFUBw7VlOclIjcYTv/YDQEul8JoXoOuy1Yf3b5sbTpTuVTcl97tAuLJ8PoGe2K7N3B1eUQqQIDAQAB";

       byte[] b = encrypt(k, d.getBytes("UTF-8"));
       
//       System.out.println(new String(b));
       
       System.out.println(new String(decrypt(b)));*/

//    	String str = "HottuROV9sKgi6LYJ4f1lnio+eGtPlUMJaGitkiiV+uXRq3tYxUDh8o0u2TrFLJmua6hOOJNu/DVuBePw2RJqYPMxZbdK4EXLv0PGji5tpyXY7xXXOmWSJjnekwXdEWAFdVwsBNvS4wBKLYvSsp78GAoguy6lwDF/ElilwZHLKbCqbCxHtiBiLSzjbOea25omZWNdQntY4VZS9FNhjz1xCX8zjvy610l4g+/v5P8+la9ckJ2Qjp4m1Roshmg/xH1MJw2ZR0/m4EYHe+1/Nej2IhP6d9imNwSF+8XThidjWTjnPy4fUyhhGNFlFOu3TjQZJ/dpVWTeVzUq4Lq6YHFSb6MgTN4XrqazVVyKcwhkXpuxnzEAFyttm2+e3ddcFJCCm2xaco75SnpicKr0drBQ4391DidujtL+oS9Az3H6LJfm4OXU86q9+wk8DgK/TISj4BaYa/vxd9oxIipO37a4X1hJWvWaxETamtCV6uQyYwm2Zod7POU5iDN+c2BKlT6";
    
//    	str = RsaUtil.decrypt(str);
    	
//    	System.out.println(str);
    	

//    	String pK = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOmBJblWRnr/G+H7Axd7Os6iQ12K6m6+4yPhFUnLjhvJYO6mXgffvP3TsDbpFV0XMx7ItRAHiuwMhpd3EI4OWWNZb78r3q4XkywO04VQHDtWU5yUiNxhO/9gNAS6Xwmheg67LVh/dvmxtOlO5VNyX3u0C4snw+gZ7Yrs3cHV5RCpAgMBAAECgYBsrxgH9AhKJ4Oq41LFEy6EDzKbz5TehyriAFoQRxaWCF19tyH9OD6XRni2ljbDZZD9ux3LoDchN5NN3LVv6W3g1JaOqKIIfdrOhTEtHhcO5wGHHxl61D/bJ3ojxFieMtsRb1BaDRbfrQkLsZtiw0qghrfYxUte9xtU/YiBBQD8oQJBAPh8B3WXRuPOWWMfl94pIrnOoEC1s39PEvjKIFeSk0q+DxQLI2oXcOmkZaYRarrZv/qdhUWYb6rdkOJTWSuSYqsCQQDwkSF2W2aqgnQBTqm1UmQoomh7kOCpCVQBLx5W4NngW6+JTxyZbWRcCEAHTLjbqjmH8gWjj86egp2ESMJiMvn7AkA4QiyWLRf5v+BxFtgVjo60LtoNjJYFPuv2tBy6dw8uHvXe/d6YyJHh9Dynas4VW/OhVES2SxsNPnjtJR3NRaldAkEAlbPK3gts7Si3JeUcyOBXwc2nNVXn6GkMpJv8xlWwX+TObKUViAjCDl938NL2qoPYv1eF2M3x50Qi36IjjNZqJwJBAJsxYNIYWwk+clCy8rMEkcwYv9XbhDAF3paRwMEgWAOXKjlmCwa/gXFgIVLxoYqxuSTcqVoMQsUV/NBF6wQoSL4=";
//    	String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpgSW5VkZ6/xvh+wMXezrOokNdiupuvuMj4RVJy44byWDupl4H37z907A26RVdFzMeyLUQB4rsDIaXdxCODlljWW+/K96uF5MsDtOFUBw7VlOclIjcYTv/YDQEul8JoXoOuy1Yf3b5sbTpTuVTcl97tAuLJ8PoGe2K7N3B1eUQqQIDAQAB";
        String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIxWEg3nFA9F2ajV+abQcE6VGTZecmy4qA4GLpvUgUBRa1E6mbqVGfDjkzzdr9eIcRm/kU6n6uywaWDBxZ4u57EjqQIXmEVPqCwRjL1dF+lIE5QDZTBxqM9GwygQpGYQib42mbCcYB55krZjtfCveKYd0snzpWAnnMx52p3gh9dZAgMBAAECgYAYGB75bBcxmBiKuFIopdjiZQ7zGrwiloGkBsOx1YZreI8oXxtNwZO2nBwHczhhlPd2KEHWc1YOVSuChUJcqkj1C6tionQdOcU02wWXEkxTuGJIArw6ntqYn2ZZZMLBejjRETJZfH/YEPkzUd0FuD0cIfrJUIZOyH3qOWJlEafqQQJBAMkRcGOP4TO/aOasOszDLZe2b15tLjKQe363hV34IgUi4hWQLSdvFG76gpcloi5JXU8IpXeF1q8/bTa4U7RDYw0CQQCyrRXNXGWc/dDnehShi3kOayYrGzKAIvykquDIyLMkAyIobAMSzoj56V+A1sE8C/cyMVwDo8jWcvhCZE3nK+J9AkBd/QvnTnt8AA6ePYYi712hnIMExc6hjk5cFpd+LJ5ifkLmx4WD+HW5xtpCozHjpyG57xXCAEsxklmQCav/CL0FAkApYsYGBzzSHEhjFXfp4zBrEo6ItYgA/hme2qWuXC6CTOeAjWQ42vYHTPL+GMAxdGQRkDVL8of2hDLUzf7taNDRAkBLT1q2MLR1WSJg/EqzJ1enOvxp4L+Pma91IYi0Yr2rA0R06elC3Eut+IMjH3I8ck49TaVbSSClTou11CkSJCyJ";

        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMVhIN5xQPRdmo1fmm0HBOlRk2XnJsuKgOBi6b1IFAUWtROpm6lRnw45M83a/XiHEZv5FOp+rssGlgwcWeLuexI6kCF5hFT6gsEYy9XRfpSBOUA2UwcajPRsMoEKRmEIm+NpmwnGAeeZK2Y7Xwr3imHdLJ86VgJ5zMedqd4IfXWQIDAQAB";
	String base = "hello";
    	
    	String sign = RsaUtil.sign(base, priKey);
    	
    	System.out.println(sign);
    	System.out.println(RsaUtil.doCheck(base, sign, pub));
       
    	 /*
		String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpgSW5VkZ6/xvh+wMXezrOokNdiupuvuMj4RVJy44byWDupl4H37z907A26RVdFzMeyLUQB4rsDIaXdxCODlljWW+/K96uF5MsDtOFUBw7VlOclIjcYTv/YDQEul8JoXoOuy1Yf3b5sbTpTuVTcl97tAuLJ8PoGe2K7N3B1eUQqQIDAQAB";
		
		String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOmBJblWRnr/G+H7Axd7Os6iQ12K6m6+4yPhFUnLjhvJYO6mXgffvP3TsDbpFV0XMx7ItRAHiuwMhpd3EI4OWWNZb78r3q4XkywO04VQHDtWU5yUiNxhO/9gNAS6Xwmheg67LVh/dvmxtOlO5VNyX3u0C4snw+gZ7Yrs3cHV5RCpAgMBAAECgYBsrxgH9AhKJ4Oq41LFEy6EDzKbz5TehyriAFoQRxaWCF19tyH9OD6XRni2ljbDZZD9ux3LoDchN5NN3LVv6W3g1JaOqKIIfdrOhTEtHhcO5wGHHxl61D/bJ3ojxFieMtsRb1BaDRbfrQkLsZtiw0qghrfYxUte9xtU/YiBBQD8oQJBAPh8B3WXRuPOWWMfl94pIrnOoEC1s39PEvjKIFeSk0q+DxQLI2oXcOmkZaYRarrZv/qdhUWYb6rdkOJTWSuSYqsCQQDwkSF2W2aqgnQBTqm1UmQoomh7kOCpCVQBLx5W4NngW6+JTxyZbWRcCEAHTLjbqjmH8gWjj86egp2ESMJiMvn7AkA4QiyWLRf5v+BxFtgVjo60LtoNjJYFPuv2tBy6dw8uHvXe/d6YyJHh9Dynas4VW/OhVES2SxsNPnjtJR3NRaldAkEAlbPK3gts7Si3JeUcyOBXwc2nNVXn6GkMpJv8xlWwX+TObKUViAjCDl938NL2qoPYv1eF2M3x50Qi36IjjNZqJwJBAJsxYNIYWwk+clCy8rMEkcwYv9XbhDAF3paRwMEgWAOXKjlmCwa/gXFgIVLxoYqxuSTcqVoMQsUV/NBF6wQoSL4=";
		
		String base = "hello";
		String NearME_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpgSW5VkZ6/xvh+wMXezrOokNdiupuvuMj4RVJy44byWDupl4H37z907A26RVdFzMeyLUQB4rsDIaXdxCODlljWW+/K96uF5MsDtOFUBw7VlOclIjcYTv/YDQEul8JoXoOuy1Yf3b5sbTpTuVTcl97tAuLJ8PoGe2K7N3B1eUQqQIDAQAB";

		byte[] bs = encrypt(NearME_PUBLIC_KEY, base.getBytes("UTF-8"));
		
/*		String content = org.apache.commons.codec.binary.Base64.encodeBase64String(bs);
//		content = Base64Util.base64Encode(bs);
		content = decrypt(content);
		System.out.println(content);*//*
    	String RELEASE_RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANLL6E1Qaj6jkS9ZRuN2I6KEx+80v6CS/0hYvDTBUY8OAuEm8jh3ckvSwZ/lkA/3YsZU+Gqi99r41RAq3oO/xCZXyctQkqMGJX4vytSs3HHvqDXVaRxVNX1iQ6qffeBDbH1FFpIKHCvsc92sApuiHieY6KKPlhMMFcEbIwKYIBIZAgMBAAECgYEAnxUZrHdapwkkAXq7v9+hhv30MTc6wwEMvVmSN7IJTg/B5o9Qz4J9dHGKhJRnw1Tsa0cm7rssmEmf6gKgSQeJDBL0Y/7gNNqIJbLbt5mf72u654gOVx+sVueUE1Tru7qA0xIGDvqrJVh6w/3r5P9Xf3HUuXamaFjEolRQZ6hUJF0CQQD8RFTrtqy6rEHt/rc8ZRjNJ3DAsG/7zkYPh7Eh22nJ9CltolelAbh7I/Xv6usLni6uYr4hcjEyU7DDRq5GM9M/AkEA1ep48xWdI7YlOUkriXf9YaivkylWoR1EyqRGLFxUakfgjAIGVLcnhzIZbmFuyLxSCj7xe3Af8jxIya1cyhG8pwJAUJtQ+0SSqs9R/ccvK6guJZedqqR2E+LUdi/ohKYEhphzCGogUURce1SBSFkrI7o2tlgs4qkHWUpup+cU1q/GpwJBAI0vt2qBTni+hGz93S1bM7JcOp2/f/zQq5GvwN0Qh2qqTAs8Xt9VD1LM/zn6NrtoiCFZD9rv0zX3EfeCY6a+9CsCQE0vs7edZzyNP7c3Dn+D5bPPyghxrCZwqgZZxoN+irL8evybHdGnfk6xvDi6FfVD8Wk+vDI6gtUwcHrc00E4r00=";
    	String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSy+hNUGo+o5EvWUbjdiOihMfvNL+gkv9IWLw0wVGPDgLhJvI4d3JL0sGf5ZAP92LGVPhqovfa+NUQKt6Dv8QmV8nLUJKjBiV+L8rUrNxx76g11WkcVTV9YkOqn33gQ2x9RRaSChwr7HPdrAKboh4nmOiij5YTDBXBGyMCmCASGQIDAQAB";
    	String content = "ssoid=\"TOKEN_9tzaq68wuvqSHN3jBfEMX4kjctu/6wJsgX69UF3clu8=\"&tokenSecret=\"\"&appPackage=\"com.nearme.themespace\"&partnerId=\"9809089\"";
    	String b = RsaUtil.sign(content, RELEASE_RSA_PRIVATE_KEY);
    	System.out.println(b);
    	
    	String sign = "bYk2O7nXOdo8r/boZ4bcLD9t9k/wknEBueaqYVnvFKBhLk3iNdRhyQcnv03UZeoVgrEUrJze9QMMvdwOZ7tsBaGX522gHtSLiuKQ7ow5cnrtRLULyVmCGXa1zxo6p0Xw4h1TnfLFa9JGn027YjFtsdD9BdXa1Y+p+Jf5gMWfg88=";
    	System.out.println("=:" + b.equals(sign));
    	System.out.println(doCheck(content, sign, getPublicKey("9809089")));
//    	getPublicKey("9809089");
    *//*	String b = "uMG8wGtTPDLf9RmhBwW/5TFP7h4az1uSAcj+8KyC7CknHE+66fbj2EEsGflCsZ7mBS5AQL0J+V0UbxqYAD/+5MV8tXn/RnFPW6ZdAIuD4YrTXf32SFZp+P3zYroEl5znzt6TCDsl+cugyFVlvJX7ZgljLAACtV3+QwDmZySzGEg=";
    	System.out.println(RsaUtil.doCheck("sign", 
    			b, 
    			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJOiGsoifR0qAwpb72gbbDonYgJ973LBOzSa+SGccbl9Hyv/7Rnkoet015dieP5lTHbQiUcWrX3DVhLUM+9q8loTYETVvBjYi+fDtOIbUUdmaObCKmdHl1SSZlMHVGkbQ8yys8bqkw0DbBQuqN6WdYexcyFfrh1EvDol0c9o1l/wIDAQAB"));    
    			
  *//*
//    	System.out.println(getPrivateKey("2032"));*/

//        String str
    	
    	}
    
    
    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 使用系统的私钥加密
     * 
     * @param
     * @return
     */
    
    public static byte[] encrypt(byte[] data) {
    
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(getPrivateKey()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey key = keyf.generatePrivate(priPKCS8);
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。
                
                i++;
            }
            return raw;
        } catch (Exception e) {
        }
        return data;
    }
    
    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 使用指定的密钥加密
     * 
     * @param
     * @return
     */
    
   /* public static byte[] encrypt(String k , byte[] data) {
    
        try {
            // PKCS8EncodedKeySpec priPKCS8 = new
            // PKCS8EncodedKeySpec(Base64Util.base64Decode(k));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            // PrivateKey key = keyf.generatePrivate(priPKCS8);
            PublicKey key = keyf.generatePublic(new X509EncodedKeySpec(com.nearme.platform.common.security.Base64.base64Decode(k)));
            
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。
                
                i++;
            }
            return raw;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }*/

    public static String encryptByPublicKey(String content, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        InputStream ins = new ByteArrayInputStream(content.getBytes("UTF-8"));

        // rsa解密的字节大小最多是117，将需要解密的内容，按100位拆开解密
        byte[] buf = new byte[100];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return org.apache.commons.codec.binary.Base64.encodeBase64String(writer.toByteArray());
    }

    public static byte[] encryptByPublicKey(byte[] content, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decode(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        InputStream ins = new ByteArrayInputStream(content);

        // rsa解密的字节大小最多是117，将需要解密的内容，按100位拆开解密
        byte[] buf = new byte[100];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return writer.toByteArray();
    }
    
    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 使用私钥解密
     * 
     * @param
     * @return
     */
    
    public static byte[] decrypt(byte[] raw) {
    
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(getPrivateKey()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey key = keyf.generatePrivate(priPKCS8);
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raw;
    }
    
    public static String decrypt(String content) {
    
        PKCS8EncodedKeySpec priPKCS8;
		long start = System.currentTimeMillis();
		priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(getPrivateKey()));
		long end = System.currentTimeMillis();

        KeyFactory keyf;
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        try {
            keyf = KeyFactory.getInstance("RSA");
            PrivateKey key = keyf.generatePrivate(priPKCS8);
            
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            InputStream ins = new ByteArrayInputStream(Base64.decode(content));
            
            // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;
            
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;
                
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptFromByte(byte[] content) {

        PKCS8EncodedKeySpec priPKCS8;
        long start = System.currentTimeMillis();
        priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(getPrivateKey()));
        long end = System.currentTimeMillis();

        KeyFactory keyf;
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        try {
            keyf = KeyFactory.getInstance("RSA");
            PrivateKey key = keyf.generatePrivate(priPKCS8);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            InputStream ins = new ByteArrayInputStream(content);

            // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;

            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }

                writer.write(cipher.doFinal(block));
            }
            return writer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * OPPO 2011-9-6 80051745 Add OPPO 2011-9-6 80051745 for reason Function
     * Description here 使用开发者公钥解密
     * 
     * @param
     * @return
     */
    
    public static byte[] decrypt(String key , byte[] raw) {
    
        try {
            
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, pubKey);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
        }
        return raw;
    }
    
    /*
     * 主要用于支付宝字段签名
     */
    public static String sign(String content , String privateKey) {
    
        String charset = "utf-8";
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            byte[] signed = signature.sign();

            return new String(Base64.encode(signed), charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param content
     *            加密前的内容
     * @param sign
     *            签名后的内容
     * @param publicKey
     *            解密用的开发者公钥
     * @return boolean 判断签名后的信息是否匹对
     */
    public static boolean doCheck(String content , String sign , String publicKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            boolean bverify = signature.verify(Base64.decode(sign));
            return bverify;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static byte[] rsaEncode(byte[] signsRSA , String alias , String pwd , InputStream dataSign) {
    
        // 以PKCS2类型打开密钥库
        try {
            
            KeyStore store = KeyStore.getInstance("PKCS12");
            InputStream inStream = dataSign;
            store.load(inStream, pwd.toCharArray());
            inStream.close();
            // 从密钥库中提取密钥
            PrivateKey pKey = (PrivateKey) store.getKey(alias, pwd.toCharArray());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pKey);
            return cipher.doFinal(signsRSA);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static String createSign(String original_string , String alias , String password , InputStream PrivateSign) {
    
        try {
            byte[] signsMD5 = MD5(original_string);
            
            byte[] signsRSA = rsaEncode(signsMD5, alias, password, PrivateSign);
            
            return new String(Base64.encode(signsRSA));
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("Exception" + e);
        }
        
        return null;
    }
    
    static byte[] MD5(String src) {
    
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(src.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            return digest;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
}
