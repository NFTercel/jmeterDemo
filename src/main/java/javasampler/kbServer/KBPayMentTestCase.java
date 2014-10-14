package javasampler.kbServer;

import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import util.Constants;
import util.HttpConnectUtil;
import util.RsaUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 80059138
 * Date: 14-9-13
 * Time: 下午4:03
 */
public class KBPayMentTestCase extends AbstractJavaSamplerClient implements Serializable {

    private String partnerId;

    private String accessToken;

    private String appPackage;

    private String channelId;

    private String productName;

    private String productDesc;

    private int count;

    private int price;

    @Override
    public void setupTest(JavaSamplerContext context) {
        partnerId = Constants.partnerId;
        accessToken = Constants.accessToken;
        appPackage = "com.demo.test";
        channelId = "-1";

        productDesc = "descttt";

        productName = "helloworld";

        count = 1;

        price = 1;
    }


    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        String url = javaSamplerContext.getParameter("url");
        String ssoid = javaSamplerContext.getParameter("ssoid");

        String partnerOrder = DateFormatUtils.format(new Date(), "yyyyMMddmmss") + ssoid;
        String imei = "8600000000";

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("ssoid", ssoid);
        requestMap.put("appPackage", appPackage);
        requestMap.put("partnerId", partnerId);
        requestMap.put("channelId", channelId);
        requestMap.put("imei", imei);
        requestMap.put("ver", "1.3");
        requestMap.put("sign", sign(ssoid, partnerOrder));
        requestMap.put("productName", productName);
        requestMap.put("productDesc", productDesc);
        requestMap.put("price", price);
        requestMap.put("count", count);
        requestMap.put("partnerOrder", partnerOrder);



        String requestData = JSONObject.fromObject(requestMap).toString();

        String data = null;
        try {
            data = RsaUtil.encryptByPublicKey(requestData, Constants.pubKey);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            byte[] bs = HttpConnectUtil.getResponseByPost(url, null, data);
            if (bs != null) {
                String response = new String(bs);
                if (response.contains("resultCode")) {
                    result.setResponseCodeOK();
                    result.setResponseMessageOK();
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                }

                result.setResponseData(response, "UTF-8");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            result.sampleEnd();
        }

        result.setSuccessful(false);
        return result;
    }

    private String sign(String ssoid, String partnerOrder) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ssoid=\"");
        stringBuilder.append(ssoid);
        stringBuilder.append("\"&");
        stringBuilder.append("appPackage=\"");
        stringBuilder.append(appPackage);
        stringBuilder.append("\"&");
        stringBuilder.append("partnerId=\"");
        stringBuilder.append(partnerId);
        stringBuilder.append("\"&");
        stringBuilder.append("partnerOrder=\"");
        stringBuilder.append(partnerOrder);
        stringBuilder.append("\"&");
        stringBuilder.append("productName=\"");
        stringBuilder.append(productName);
        stringBuilder.append("\"&");
        stringBuilder.append("productDesc=\"");
        stringBuilder.append(productDesc);
        stringBuilder.append("\"&");
        stringBuilder.append("price=\"");
        stringBuilder.append(price);
        stringBuilder.append("\"&");
        stringBuilder.append("count=\"");
        stringBuilder.append(count);
        stringBuilder.append("\"");
        return RsaUtil.sign(stringBuilder.toString(), Constants.priKey);
    }


    @Override
    public Arguments getDefaultParameters() {

        Arguments arguments = new Arguments();
        arguments.addArgument("ssoid", "");
        arguments.addArgument("url", "");
        return arguments;
    }

}
