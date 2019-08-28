package api;

import bean.CitconTransactionType;
import bean.CitConTransactionsBean;
import cn.stormbirds.payservice.common.api.BasePayService;
import cn.stormbirds.payservice.common.bean.*;
import cn.stormbirds.payservice.common.http.HttpConfigStorage;
import cn.stormbirds.payservice.common.http.HttpHeader;
import cn.stormbirds.payservice.common.http.HttpStringEntity;
import cn.stormbirds.payservice.common.http.UriVariables;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    public final static String DEV_BASE_URL = "https://uat.citconpay.%s/";
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

    /**
     * 获取APP支付预订单
     *
     * @param order 支付订单
     * @return
     */
    @Override
    public Map<String, Object> orderInfo(PayOrder order) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("amount", conversionCent2YuanAmount(order.getPrice()));
        params.put("currency", order.getCurType().getType());
        params.put("reference", order.getOutTradeNo());
        params.put("ipn_url", payConfigStorage.getNotifyUrl());

        params.put("vendor", order.getTransactionType().getMethod());
        params.put("subject", order.getSubject());
        params.put("body", order.getBody());
        params.put("callback_url", payConfigStorage.getReturnUrl());
        params.put("allow_duplicates", "yes");

        //设置不同支付方式返回类型
        boolean isJsonContent = true;
        switch (order.getTransactionType().getType()) {
            case "ALI_APP":
                isJsonContent = true;
                params.put("vendor", order.getTransactionType().getMethod());
                params.put("subject", order.getSubject());
                params.put("body", order.getBody());
                params.put("callback_url", payConfigStorage.getReturnUrl());
                params.put("allow_duplicates", "yes");
                break;
            case "WX_APP":
                isJsonContent = true;
                params.put("vendor", order.getTransactionType().getMethod());
                params.put("subject", order.getSubject());
                params.put("body", order.getBody());
                params.put("callback_url", payConfigStorage.getReturnUrl());
                params.put("allow_duplicates", "yes");
                break;
            case "H5":
                isJsonContent = false;
                params.put("vendor", order.getTransactionType().getMethod());
                params.put("callback_url", payConfigStorage.getReturnUrl());
                params.put("allow_duplicates", "yes");
                break;
            case "QR_PAY":
                isJsonContent = false;
                params.put("vendor", order.getTransactionType().getMethod());
                params.put("subject", order.getSubject());
                params.put("body", order.getBody());
                params.put("callback_url", payConfigStorage.getReturnUrl());
                params.put("allow_duplicates", "yes");
                break;
            case "WXMINI":
                isJsonContent = true;
                params.put("openid", order.getOpenid());

                break;
            default:
                isJsonContent = false;
                params.put("vendor", order.getTransactionType().getMethod());
                params.put("subject", order.getSubject());
                params.put("body", order.getBody());
                params.put("callback_url", payConfigStorage.getReturnUrl());
                params.put("allow_duplicates", "yes");
        }

        HttpStringEntity entity = new HttpStringEntity(params, ContentType.APPLICATION_FORM_URLENCODED);
        //设置 base atuh
        entity.setHeaders(authHeader());

        if (isJsonContent) {
            return getHttpRequestTemplate().postForObject(getReqUrlByCurTypeAndTransactionType(order.getTransactionType(), order.getCurType())
                    , entity, JSONObject.class);
        } else {
            String result = getHttpRequestTemplate().postForObject(getReqUrlByCurTypeAndTransactionType(order.getTransactionType(), order.getCurType())
                    , entity, String.class);
            return new HashMap<String, Object>(2) {{
                put("url", result);
            }};
        }

    }

    @Override
    public PayOutMessage getPayOutMessage(String code, String message) {
        return PayOutMessage.TEXT().content(code.toLowerCase()).build();
    }

    @Override
    public PayOutMessage successPayOutMessage(PayMessage payMessage) {
        return PayOutMessage.TEXT().content("success").build();
    }

    /**
     * @param orderInfo 发起支付的订单信息
     * @param method    请求方式  "post" "get",
     * @return
     */
    @Override
    public String buildRequest(Map<String, Object> orderInfo, MethodType method) {
        if (orderInfo.containsKey("url")) {
            return "<script language=\"javascript\" type=\"text/javascript\">\n" +
                    "window.location.href='" + orderInfo.get("url") + "';\n" +
                    "</script>";
        }
        return "";
    }

    /**
     * 获取支付二维码
     *
     * @param order 发起支付的订单信息
     * @return
     */
    @Override
    public BufferedImage genQrPay(PayOrder order) {
        try {
            return ImageIO.read(new URL(getQrPay(order)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取二维码信息字符串
     *
     * @param order 发起支付的订单信息
     * @return
     */
    @Override
    public String getQrPay(PayOrder order) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("amount", conversionCent2YuanAmount(order.getPrice()));
        params.put("currency", order.getCurType().getType());
        params.put("vendor", "generic");
        params.put("reference", order.getOutTradeNo());
        params.put("ipn_url", payConfigStorage.getNotifyUrl());
        params.put("callback_url", payConfigStorage.getReturnUrl());
        params.put("allow_duplicates", "yes");

        return getHttpRequestTemplate().getForObject(getReqUrlByCurTypeAndTransactionType(order.getTransactionType(), order.getCurType()) + "?" + UriVariables.getMapToParameters(params),
                authHeader(), String.class);
    }

    @Override
    public Map<String, Object> microPay(PayOrder order) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询订单
     *
     * @param tradeNo    支付平台订单号
     * @param outTradeNo 商户单号
     * @return Example Response: {
     * "id": "D0000001079-83c6b94bf5b61afe2e21",
     * "type": "charge",
     * "amount": 1,
     * "time": "2016-11-05 06:17:12",
     * "reference": "1000002061",
     * "status": "success",
     * "currency": "USD",
     * "note": "null"
     * }
     */
    @Override
    public Map<String, Object> query(String tradeNo, String outTradeNo) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        if (tradeNo != null && !tradeNo.isEmpty()) {
            params.put("transaction_id", tradeNo);
        } else if (outTradeNo != null && !outTradeNo.isEmpty()) {
            params.put("reference", outTradeNo);
        } else {
            throw new IllegalArgumentException("参数有误，tradeNo或outTradeNo不能同时为空，必须填写一个");
        }
        params.put("inquire_method", "real");

        return getHttpRequestTemplate().getForObject(getReqUrl(null), authHeader(), JSONObject.class, params);
    }

    @Override
    public Map<String, Object> close(String tradeNo, String outTradeNo) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param refundOrder   退款订单信息
     * @return
     */
    @Override
    public Map<String, Object> refund(RefundOrder refundOrder) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("amount", refundOrder.getTotalAmount());
        params.put("currency", refundOrder.getCurType().getType());
        if (refundOrder.getDescription() != null && !refundOrder.getDescription().isEmpty()) {
            params.put("reason", refundOrder.getDescription());
        }
        if (refundOrder.getTradeNo() != null && !refundOrder.getTradeNo().isEmpty()) {
            params.put("transaction_id", refundOrder.getTradeNo());
        } else if (refundOrder.getOutTradeNo() != null && !refundOrder.getOutTradeNo().isEmpty()) {
            params.put("transaction_reference", refundOrder.getOutTradeNo());
        } else {
            throw new IllegalArgumentException("参数有误，transaction_id或transaction_reference不能同时为空，必须填写一个");
        }
        HttpStringEntity entity = new HttpStringEntity(params, ContentType.APPLICATION_FORM_URLENCODED);
        //设置 base atuh
        entity.setHeaders(authHeader());
        return getHttpRequestTemplate().postForObject(getReqUrlByCurTypeAndTransactionType(CitconTransactionType.REFUND, refundOrder.getCurType()), entity, JSONObject.class);
    }

    @Override
    public Map<String, Object> refundquery(RefundOrder refundOrder) {
        return query(refundOrder.getTradeNo(), refundOrder.getOutTradeNo());
    }

    /**
     *
     * @param billDate 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     * @param billType 账单时间类型：日账单为字符串"yyyy-MM-dd"、月账单为字符串"yyyy-MM"。
     * @return 账单数据在返回Map类型结果的data对应value
     */
    @Override
    public Map<String, Object> downloadbill(Date billDate, String billType) {

        if (!monthBillTypeFormat.equals(billType) && !dayBillTypeFormat.equals(billType)) {
            throw new IllegalArgumentException("参数错误，billType必须符合日期格式且只能是\"yyyy-MM-dd\"(日账单格式)或者\"yyyy-MM\"(月账单格式)");
        }
        String resultRequest = getHttpRequestTemplate()
                .getForObject(
                        getReqUrlByCurTypeAndTransactionType(CitconTransactionType.TRANSACTIONS, DefaultCurType.USD),
                        authHeader(),
                        String.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(billType);
        try {
            List<CitConTransactionsBean> resultBean = JSONObject.parseArray(resultRequest, CitConTransactionsBean.class)
                    .stream()
                    .filter(citConTransactionsBean -> simpleDateFormat
                            .format(billDate)
                            .equals(citConTransactionsBean.getTime().format(DateTimeFormatter.ofPattern(billType))))
                    .collect(Collectors.toList());

            return new HashMap<String, Object>(4) {{
                put("data", resultBean);
            }};
        } catch (JSONException e) {
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, Object> secondaryInterface(Object tradeNoOrBillDate, String outTradeNoBillType, TransactionType transactionType) {
        return null;
    }

    @Override
    public String getReqUrl(TransactionType transactionType) {
        return payConfigStorage.isTest() ? DEV_BASE_URL : PROD_BASE_URL;
    }

    private String getReqUrlByCurTypeAndTransactionType(TransactionType transactionType, CurType curType) {
        String extendUrl = "payment/";
        switch (transactionType.getType()) {
            case "ALI_APP":
                extendUrl += "pay_app";
                break;
            case "WX_APP":
                extendUrl += "pay_app";
                break;
            case "H5":
                extendUrl += "pay";
                break;
            case "QR_PAY":
                extendUrl += "pay_qr";
                break;
            case "WXMINI":
                extendUrl += "pay_wxmini";
                break;
            case "REFUND":
                extendUrl += "refund";
                break;
            case "INQUIRE":
                extendUrl += "inquire";
                break;
            case "TRANSACTIONS":
                extendUrl += "transactions";
                break;
            default:
                extendUrl += "pay";
        }
        return String.format(getReqUrl(transactionType), "CNY".equals(curType.getName()) ? CNY_BASE_DOMAIN : NONCNY_BASE_DOMAIN) + extendUrl;
    }

    private HttpHeader authHeader() {

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Authorization", "Bearer " + payConfigStorage.getAccessToken()));

        return new HttpHeader(headers);
    }
}
