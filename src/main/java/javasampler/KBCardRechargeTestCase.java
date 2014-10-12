package javasampler;

import bean.VcCardRechargeBean;
import com.google.protobuf.ByteString;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.bouncycastle.util.encoders.Base64;
import util.Constants;
import util.HttpConnectUtil;
import util.RsaUtil;
import util.ThreeDES;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: 80059138
 * Date: 14-7-23
 * Time: 下午3:27
 */
public class KBCardRechargeTestCase extends AbstractJavaSamplerClient implements Serializable {


    private String partnerId;

    private String accessToken;

    @Override
    public void setupTest(JavaSamplerContext context) {
        partnerId = Constants.partnerId;
        accessToken = Constants.accessToken;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        String url = javaSamplerContext.getParameter("url");
        String type = javaSamplerContext.getParameter("type");
        String cardId = javaSamplerContext.getParameter("cardId");
        String cardPwd = javaSamplerContext.getParameter("cardPwd");
        String barCode = javaSamplerContext.getParameter("barCode");
        accessToken = javaSamplerContext.getParameter("accessToken");

        SampleResult result = new SampleResult();
        VcCardRechargeBean.VcCardRechargeRes res = null;
        try {

            byte[] bs = new byte[0];
            try {
                bs = buildRequestData(type, barCode, cardId, cardPwd);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccessful(false);
                result.setResponseMessage("Exception: " + e);
            }

            result.sampleStart();
            bs = HttpConnectUtil.getResponseByPost(url, null, bs);
            result.sampleEnd();
            if (bs == null) {
                result.setSuccessful(false);
                System.out.println("服务器没有返回");
            } else {
                res = VcCardRechargeBean.VcCardRechargeRes.parseFrom(bs);
              /*  if ("1001".equals(res.getResultCode())) {
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                }*/

                System.out.println(res.getResultCode());
                result.setResponseCodeOK();
                result.setResponseMessageOK();
                result.setSuccessful(true);
                result.setResponseData(res.getResultCode(), "UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
        }

        return result;
    }


    private String decryCardId(String cardId) {
        try {
            return new String(ThreeDES.decryptMode(Base64.decode(cardId), null), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] buildRequestData(String type, String barCode, String cardId, String cardPwd) throws Exception {
        VcCardRechargeBean.VcCardRecharReq.Builder builder = VcCardRechargeBean.VcCardRecharReq.newBuilder();

        VcCardRechargeBean.VcCardRecharReqBody.Builder reqBuilder = VcCardRechargeBean.VcCardRecharReqBody.newBuilder();

        reqBuilder.setModel("MI1");
        reqBuilder.setSource("game_center");
        reqBuilder.setAccessToken(accessToken);
        reqBuilder.setAppPackage("com.demo");
        reqBuilder.setType(type);

        if ("barcode".equals(type)) {
            reqBuilder.setBarCode(barCode);
        } else {
            reqBuilder.setCardId(cardId);
            reqBuilder.setCardPwd(cardPwd);
        }
        reqBuilder.setImei("866000000000");
        reqBuilder.setPartnerId(partnerId);

        VcCardRechargeBean.VcCardRecharReqBody reqBody = reqBuilder.build();

        byte[] data = RsaUtil.encryptByPublicKey(reqBody.toByteArray(), Constants.pubKey);

        builder.setSign(sign(reqBuilder));
        builder.setBody(ByteString.copyFrom(data));
        VcCardRechargeBean.VcCardRecharReq req = builder.build();

        byte[] bs = req.toByteArray();
        return bs;
    }

    private String sign(VcCardRechargeBean.VcCardRecharReqBody.Builder builder) {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("accessToken:");
        stringBuilder.append(builder.getAccessToken());
        stringBuilder.append("&");
        stringBuilder.append("appPackage:");
        stringBuilder.append(builder.getAppPackage());
        stringBuilder.append("&");
        stringBuilder.append("partnerId:");
        stringBuilder.append(partnerId);

        return RsaUtil.sign(stringBuilder.toString(), Constants.priKey);

    }

    /**
     * Provide a list of parameters which this test supports. Any parameter names and associated values returned
     * by this method will appear in the GUI by default so the user doesn't have to remember the exact names.
     * The user can add other parameters which are not listed here. If this method returns null then no parameters will be listed.
     * If the value for some parameter is null then that parameter will be listed in the GUI with an empty value.
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("url", "");
        arguments.addArgument("type", "");
        arguments.addArgument("cardId", "");
        arguments.addArgument("cardPwd", "");
        arguments.addArgument("barCode", "");
        arguments.addArgument("accessToken", "");

        return arguments;
    }

    public static void main(String[] args) {
       /* try {
            System.out.println(new String(ThreeDES.decryptMode(Base64.decode("in8tz4fImY8z02pi2ocBkCovd+RkcICh"), null), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/

        KBCardRechargeTestCase testCase = new KBCardRechargeTestCase();
        System.out.println(testCase.decryCardId("in8tz4fImY/JsW55PUJ5cyovd+RkcICh"));
    }
}
