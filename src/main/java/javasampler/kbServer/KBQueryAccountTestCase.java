package javasampler.kbServer;

import com.oppo.base.security.Hmac;
import net.sf.json.JSONObject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import util.HttpConnectUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 80059138
 * Date: 14-9-11
 * Time: 下午7:03
 */
public class KBQueryAccountTestCase extends AbstractJavaSamplerClient implements Serializable {


    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        String ssoid = javaSamplerContext.getParameter("ssoid");
        String url = javaSamplerContext.getParameter("url");
        String appKey = "myKey";
        String appSecret = "mySecret";

        StringBuilder sb = new StringBuilder();
        sb.append(ssoid);
        sb.append("&");
        sb.append(appKey);
        String sign = Hmac.signSHA1(sb.toString(), appSecret, "UTF-8");

        Map<String, String> params = new HashMap<String, String>();
        params.put("ssoid", ssoid);
        params.put("appKey", appKey);
        params.put("sign", sign);

        JSONObject jobj = JSONObject.fromObject(params);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("queryAccount.data", true));
            writer.write(jobj.toString());
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  finally {

        }

        SampleResult result = new SampleResult();
        byte[] bs = jobj.toString().getBytes();
        try {
            result.sampleStart();
            bs = HttpConnectUtil.getResponseByPost(url, null, bs);
            if (null == bs) {
                result.setSuccessful(false);
                System.out.println("服务器没有返回");
            } else {
                String res = new String(bs);
                JSONObject j = JSONObject.fromObject(res);
                String resultCode = j.getString("resultCode");
                result.setResponseCodeOK();
                result.setResponseMessageOK();
                result.setSuccessful(true);
                result.setResponseData(resultCode, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
        } finally {
            result.sampleEnd();
        }

        return result;
    }

    @Override
    public Arguments getDefaultParameters() {

        Arguments arguments = new Arguments();
        arguments.addArgument("ssoid", "");
        arguments.addArgument("url", "");
        return arguments;
    }

    public static void main(String[] args) throws Exception {
      /*  String url = "http://121.52.231.203:8000/queryAccount";
        String data = "{\"sign\":\"f32fff63878e49d5f447fce81b7f76a888cffc57\",\"ssoid\":\"7761527\",\"appKey\":\"myKey\"}";
        byte[] bs = HttpConnectUtil.getResponseByPost(url, null, data.getBytes());
        if (bs == null) {
            System.out.println("fail");
        } else {
            System.out.println(new String(bs));
        }*/

        BufferedWriter writer = new BufferedWriter(new FileWriter("queryAccount.txt", true));

        BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String ssoid = line;
            String appKey = "myKey";
            String appSecret = "mySecret";

            StringBuilder sb = new StringBuilder();
            sb.append(ssoid);
            sb.append("&");
            sb.append(appKey);
            String sign = Hmac.signSHA1(sb.toString(), appSecret, "UTF-8");

            Map<String, String> params = new HashMap<String, String>();
            params.put("ssoid", ssoid);
            params.put("appKey", appKey);
            params.put("sign", sign);

            JSONObject jobj = JSONObject.fromObject(params);
            writer.write(jobj.toString());
            writer.newLine();
        }

        writer.flush();
        writer.close();
        reader.close();

    }
}
