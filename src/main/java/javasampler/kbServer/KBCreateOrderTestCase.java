package javasampler.kbServer;

import com.oppo.base.security.Hmac;
import net.sf.json.JSONObject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import util.Constants;
import util.HttpConnectUtil;
import util.RsaUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 80059138
 * Date: 14-9-12
 * Time: 下午4:41
 */
public class KBCreateOrderTestCase extends AbstractJavaSamplerClient implements Serializable {


    private String partnerId;

    private String accessToken;

    private String appPackage;

    private String channelId;

    @Override
    public void setupTest(JavaSamplerContext context) {
        partnerId = Constants.partnerId;
        accessToken = Constants.accessToken;
        appPackage = "com.demo.test";
        channelId = "-1";
    }


    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        String accessToken = javaSamplerContext.getParameter("accessToken");
        String tokenSecret = javaSamplerContext.getParameter("tokenSecret");
        String requestUrl = javaSamplerContext.getParameter("url");

        String imei = "8600000000";

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("userId", accessToken);
        requestMap.put("appPackage", appPackage);
        requestMap.put("partnerId", partnerId);
        requestMap.put("channelId", channelId);
        requestMap.put("imei", imei);
        requestMap.put("ver", "1.3");
        requestMap.put("tokenSecret", tokenSecret);
        requestMap.put("sign", sign(accessToken, partnerId, appPackage));

        String requestData = JSONObject.fromObject(requestMap).toString();

        String data = null;
        try {
            data = RsaUtil.encryptByPublicKey(requestData, Constants.pubKey);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("createOrder.data", true));
            writer.write(data);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  finally {

        }

        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            byte[] bs = HttpConnectUtil.getResponseByPost(requestUrl, null, data);
            if (bs != null) {
                String response = new String(bs);
                if (response.contains("1001")) {
                    result.setResponseCodeOK();
                    result.setResponseMessageOK();
                    result.setSuccessful(true);
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    String order = jsonObject.getString("systemOrder");
                    saveOrder2File(order);
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

    private void saveOrder2File(String order) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true));
        writer.write(order);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    private static String sign(String userId, String partnerId, String appPackage) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("userId=\"");
        stringBuilder.append(userId);
        stringBuilder.append("\"&");
        stringBuilder.append("partnerId=\"");
        stringBuilder.append(partnerId);
        stringBuilder.append("\"&");
        stringBuilder.append("appPackage=\"");
        stringBuilder.append(appPackage);
        stringBuilder.append("\"");

        return RsaUtil.sign(stringBuilder.toString(), Constants.priKey);
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("url", "");
        arguments.addArgument("accessToken", "");
        arguments.addArgument("tokenSecret", "");
        return arguments;
    }

    public static void main(String[] args) throws IOException {

        String partnerId = Constants.partnerId;
        String appPackage = "com.demo.test";
        String channelId = "-1";

        BufferedWriter writer = new BufferedWriter(new FileWriter("createOrder.txt", true));

        BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
        String line = null;
        int count = 0;
        while ((line = reader.readLine()) != null && count++ <= 10000) {
            String accessToken = line;
            String appKey = "myKey";
            String appSecret = "mySecret";

            String imei = "8600000000";

            Map<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("userId", accessToken);
            requestMap.put("appPackage", appPackage);
            requestMap.put("partnerId", partnerId);
            requestMap.put("channelId", channelId);
            requestMap.put("imei", imei);
            requestMap.put("ver", "1.3");
            requestMap.put("tokenSecret", "");
            requestMap.put("sign", sign(accessToken, partnerId, appPackage));

            String requestData = JSONObject.fromObject(requestMap).toString();

            String data = null;
            try {
                data = RsaUtil.encryptByPublicKey(requestData, Constants.pubKey);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            data = data.replace("\r\n", "");
            writer.write(data);
            writer.newLine();
        }

        writer.flush();
        writer.close();
        reader.close();
    }

}
