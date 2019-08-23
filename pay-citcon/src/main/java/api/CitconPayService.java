package api;

import cn.stormbirds.payservice.common.api.BasePayService;
import cn.stormbirds.payservice.common.bean.*;
import cn.stormbirds.payservice.common.http.HttpConfigStorage;
import cn.stormbirds.payservice.common.http.HttpHeader;
import cn.stormbirds.payservice.common.http.UriVariables;
import cn.stormbirds.payservice.common.util.MatrixToImageWriter;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHeader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static cn.stormbirds.payservice.common.util.Util.conversionCent2YuanAmount;

/**
 * <p>
 * api
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/8/23 09:34
 */
public class CitconPayService extends BasePayService<CitconPayConfigStorage> {

    private final static String CNY_BASE_DOMAIN = "cn";
    private final static String NONCNY_BASE_DOMAIN = "com";

    public final static String DEV_BASE_URL = "https://dev.citconpay.%s/";
    public final static String PROD_BASE_URL = "https://citconpay.%s/";

    public CitconPayService(CitconPayConfigStorage payConfigStorage) {
        super(payConfigStorage);
    }

    public CitconPayService(CitconPayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }

    @Override
    public boolean verify(Map<String, Object> params) {
        return true;
    }

    @Override
    public boolean signVerify(Map<String, Object> params, String sign) {
        return true;
    }

    @Override
    public boolean verifySource(String id) {
        return false;
    }

    @Override
    public Map<String, Object> orderInfo(PayOrder order) {
        return null;
    }

    @Override
    public PayOutMessage getPayOutMessage(String code, String message) {
        return null;
    }

    @Override
    public PayOutMessage successPayOutMessage(PayMessage payMessage) {
        return null;
    }

    @Override
    public String buildRequest(Map<String, Object> orderInfo, MethodType method) {
        return null;
    }

    @Override
    public BufferedImage genQrPay(PayOrder order) {
        try {
            return ImageIO.read(new URL(getQrPay(order)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getQrPay(PayOrder order) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("amount", conversionCent2YuanAmount(order.getPrice()));
        params.put("currency",order.getCurType().getType());
        params.put("vendor", "generic");
        params.put("reference", order.getOutTradeNo());
        params.put("ipn_url", payConfigStorage.getNotifyUrl());
        params.put("callback_url", payConfigStorage.getReturnUrl());
        params.put("allow_duplicates", "yes");

        return getHttpRequestTemplate().getForObject(String.format(getReqUrl(null),order.getCurType().getName().equals("CNY")?CNY_BASE_DOMAIN:NONCNY_BASE_DOMAIN)+"payment/pay_qr" + "?" + UriVariables.getMapToParameters(params) ,
                authHeader(),String.class);
    }

    @Override
    public Map<String, Object> microPay(PayOrder order) {
        return null;
    }

    @Override
    public Map<String, Object> query(String tradeNo, String outTradeNo) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        if(tradeNo!=null && !tradeNo.isEmpty()) {
            params.put("transaction_id", tradeNo);
        }else {
            params.put("reference", outTradeNo);
        }
        params.put("inquire_method", "real");

        return getHttpRequestTemplate().getForObject(getReqUrl(null),authHeader(),JSONObject.class,params);
    }

    @Override
    public Map<String, Object> close(String tradeNo, String outTradeNo) {
        return null;
    }

    @Override
    public Map<String, Object> refund(RefundOrder refundOrder) {
        return null;
    }

    @Override
    public Map<String, Object> refundquery(RefundOrder refundOrder) {
        return null;
    }

    @Override
    public Map<String, Object> downloadbill(Date billDate, String billType) {
        return null;
    }

    @Override
    public Map<String, Object> secondaryInterface(Object tradeNoOrBillDate, String outTradeNoBillType, TransactionType transactionType) {
        return null;
    }

    @Override
    public String getReqUrl(TransactionType transactionType) {
        return payConfigStorage.isTest()?DEV_BASE_URL:PROD_BASE_URL;
    }

    private HttpHeader authHeader(){

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Authorization", "Bearer "+payConfigStorage.getAccessToken()));

        return new HttpHeader(headers);
    }
}
