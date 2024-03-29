package cn.stormbirds.payservice.payoneer.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.stormbirds.payservice.common.api.BasePayService;
import cn.stormbirds.payservice.common.bean.*;
import cn.stormbirds.payservice.common.bean.outbuilder.PayTextOutMessage;
import cn.stormbirds.payservice.common.bean.result.PayException;
import cn.stormbirds.payservice.common.exception.PayErrorException;
import cn.stormbirds.payservice.common.http.HttpConfigStorage;
import cn.stormbirds.payservice.common.http.HttpHeader;
import cn.stormbirds.payservice.common.http.HttpStringEntity;
import cn.stormbirds.payservice.common.http.UriVariables;
import cn.stormbirds.payservice.common.util.DateUtils;
import cn.stormbirds.payservice.common.util.Util;
import cn.stormbirds.payservice.payoneer.bean.PayoneerTransactionType;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @ Description payoneer业务逻辑
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:26
 *
 */
public class PayoneerPayService extends BasePayService<PayoneerConfigStorage> implements AdvancedPayService {
    /**
     * 测试地址
     */
    public final static String SANDBOX_DOMAIN = "https://api.sandbox.payoneer.com/v2/programs/";
    /**
     * 正式地址
     */
    public final static String RELEASE_DOMAIN = "https://api.payoneer.com/v2/programs/";
    /**
     * 响应状态码
     */
    public final static String CODE = "code";
    /**
     * 响应状态码
     */
    private final static String OUT_TRADE_NO = "client_reference_id";


    public PayoneerPayService(PayoneerConfigStorage payConfigStorage) {
        super(payConfigStorage);
    }

    public PayoneerPayService(PayoneerConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }

    /**
     * 获取授权请求头
     * @return 授权请求头
     */
    private HttpHeader authHeader(){

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Authorization",  "Basic " + authorizationString(getPayConfigStorage().getSeller(), getPayConfigStorage().getKeyPrivate())));

        return new HttpHeader(headers);
    }
    /**
     * 获取授权页面
     *
     * @param payeeId 收款id
     *
     * @return 返回请求结果
     */
    @Override
    public String getAuthorizationPage(String payeeId) {

        HttpStringEntity entity = new HttpStringEntity("{\"payee_id\":\"" + payeeId + "\"}", ContentType.APPLICATION_JSON);
        //设置 base atuh
        entity.setHeaders(authHeader());
        JSONObject response = getHttpRequestTemplate().postForObject(getReqUrl(PayoneerTransactionType.REGISTRATION), entity, JSONObject.class);
        if (null == response) {
            return null;
        }
        if (0 == response.getIntValue(CODE)) {
            return response.getString("registration_link");
        }
        throw new PayErrorException(new PayException("fail", "Payoneer获取授权页面失败,原因:" + response.getString("hint"), response.toJSONString()));
    }

    /**
     * 授权状态
     *
     * @param payeeId 用户id
     *
     * @return 返回是否认证 true 已认证
     */
    @Override
    public Map<String, Object> getAuthorizationStatus(String payeeId) {
        JSONObject result = (JSONObject) secondaryInterface(null, payeeId, PayoneerTransactionType.PAYEES_STATUS);
        return result;
    }

    /**
     * 获取授权用户信息
     *
     * @param payeeId 用户id
     *
     * @return 获取授权用户信息，包含用户状态，注册时间，联系人信息，地址信息等等
     */
    @Override
    public Map<String, Object> getAuthorizationUser(String payeeId) {
        JSONObject result = (JSONObject) secondaryInterface(null, payeeId, PayoneerTransactionType.PAYEES_DETAILS);
        return result;
    }

    /**
     * 回调校验
     *
     * @param params 回调回来的参数集
     *
     * @return 签名校验 true通过
     */
    @Override
    public boolean verify(Map<String, Object> params) {
        if (params != null && 0 == Integer.parseInt(params.get(CODE).toString())) {
            if (params.containsKey(OUT_TRADE_NO)) {
                return verifySource((String) params.get(OUT_TRADE_NO));
            }
            return true;
        }
        return false;
    }

    /**
     * 签名校验
     *
     * @param params 参数集
     * @param sign   签名原文
     *
     * @return 签名校验 true通过
     */
    @Override
    public boolean signVerify(Map<String, Object> params, String sign) {
        return true;
    }

    /**
     * 支付宝需要,微信是否也需要再次校验来源，进行订单查询
     * 校验数据来源
     *
     * @param outTradeNo 业务id, 数据的真实性.
     *
     * @return true通过
     */
    @Override
    public boolean verifySource(String outTradeNo) {
        Map<String, Object> data = query(null, outTradeNo);
        return "DONE".equals(data.get("status"));
    }

    /**
     * 返回创建的订单信息
     *
     * @param order 支付订单
     *
     * @return 订单信息
     * @see PayOrder 支付订单信息
     */
    @Override
    public Map<String, Object> orderInfo(PayOrder order) {
        Map<String, Object> params = new HashMap<>(7);
        params.put("payee_id", order.getAuthCode());
        params.put("amount", Util.trimmingAccuracyAmount(order.getPrice()));
        params.put("client_reference_id", order.getOutTradeNo());
        if (null == order.getCurType()) {
            order.setCurType(DefaultCurType.USD);
        }
        params.put("currency", order.getCurType());
        params.put("description", order.getSubject());

        return params;
    }

    /**
     * 创建签名
     *
     * @param content           需要签名的内容
     * @param characterEncoding 字符编码
     *
     * @return 签名
     */
    @Override
    public String createSign(String content, String characterEncoding) {
        throw new UnsupportedOperationException();
    }


    /**
     * 获取输出消息，用户返回给支付端
     *
     * @param code    状态
     * @param message 消息
     *
     * @return 返回输出消息
     */
    @Override
    public PayOutMessage getPayOutMessage(String code, String message) {
        return PayTextOutMessage.TEXT().content(code.toLowerCase()).build();
    }

    /**
     * 获取成功输出消息，用户返回给支付端
     * 主要用于拦截器中返回
     *
     * @param payMessage 支付回调消息
     *
     * @return 返回输出消息
     */
    @Override
    public PayOutMessage successPayOutMessage(PayMessage payMessage) {
        return getPayOutMessage("ok", null);
    }

    /**
     * 获取输出消息，用户返回给支付端, 针对于web端
     *
     * @param orderInfo 发起支付的订单信息
     * @param method    请求方式  "post" "get",
     *
     * @return 获取输出消息，用户返回给支付端, 针对于web端
     * @see MethodType 请求类型
     */
    @Override
    public String buildRequest(Map<String, Object> orderInfo, MethodType method) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取输出二维码，用户返回给支付端,
     *
     * @param order 发起支付的订单信息
     *
     * @return 返回图片信息，支付时需要的
     */
    @Override
    public BufferedImage genQrPay(PayOrder order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getQrPay(PayOrder order) {
        throw new UnsupportedOperationException();
    }

    /**
     * 刷卡付,pos主动扫码付款(条码付)
     *
     * @param order 发起支付的订单信息
     *
     * @return 返回支付结果
     */
    @Override
    public Map<String, Object> microPay(PayOrder order) {
        HttpStringEntity entity = new HttpStringEntity(JSON.toJSONString(orderInfo(order)), ContentType.APPLICATION_JSON);
        //设置 base atuh
        entity.setHeaders(authHeader());
        JSONObject response = getHttpRequestTemplate().postForObject(getReqUrl(PayoneerTransactionType.CHARGE), entity, JSONObject.class);
        if (response != null) {
            return response;
        }
        throw new PayErrorException(new PayException("fail", "Payoneer申请收款失败,原因:未有返回值" ));
    }

    /**
     * 交易查询接口
     *
     * @param tradeNo    支付平台订单号
     * @param outTradeNo 商户单号
     *
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @Override
    public Map<String, Object> query(String tradeNo, String outTradeNo) {
        return secondaryInterface(tradeNo, outTradeNo, PayoneerTransactionType.CHARGE_STATUS);
    }


    /**
     * 交易关闭接口
     *
     * @param tradeNo    支付平台订单号
     * @param outTradeNo 商户单号
     *
     * @return 返回支付方交易关闭后的结果
     */
    @Override
    public Map<String, Object> close(String tradeNo, String outTradeNo) {
        return secondaryInterface(tradeNo, outTradeNo, PayoneerTransactionType.CHARGE_CANCEL);
    }

    /**
     * 交易交易撤销
     *
     * @param tradeNo    支付平台订单号
     * @param outTradeNo 商户单号
     * @return 返回支付方交易撤销后的结果
     */
    @Override
    public Map<String, Object> cancel(String tradeNo, String outTradeNo) {
        return secondaryInterface(tradeNo, outTradeNo, PayoneerTransactionType.CHARGE_CANCEL);
    }


    /**
     * 申请退款接口
     *
     * @param refundOrder 退款订单信息
     *
     * @return 返回支付方申请退款后的结果
     */
    @Override
    public Map<String, Object> refund(RefundOrder refundOrder) {
        return close(refundOrder.getTradeNo(), refundOrder.getOutTradeNo());
    }

    /**
     * 查询退款
     *
     * @param refundOrder 退款订单单号信息
     * @return 返回支付方查询退款后的结果
     */
    @Override
    public Map<String, Object> refundquery(RefundOrder refundOrder) {
        return Collections.emptyMap();
    }

    /**
     * 下载对账单
     *
     * @param billDate 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     * @param billType 账单类型，具体请查看对应支付平台；
     *
     * @return 返回支付方下载对账单的结果
     */
    @Override
    public Map<String, Object> downloadbill(Date billDate, String billType) {

        return Collections.emptyMap();
    }

    /**
     * @param tradeNoOrBillDate  支付平台订单号或者账单类型， 具体请
     *                           类型为{@link String }或者 {@link Date }，类型须强制限制，类型不对应则抛出异常{@link PayErrorException}
     * @param outTradeNoBillType 商户单号或者 账单类型
     * @param transactionType    交易类型
     *
     * @return 返回支付方对应接口的结果
     */
    @Override
    public Map<String, Object> secondaryInterface(Object tradeNoOrBillDate, String outTradeNoBillType, TransactionType transactionType) {
        MethodType methodType = null;
        if (transactionType == PayoneerTransactionType.CHARGE_CANCEL) { // 退款
            methodType = MethodType.POST;
        }else {
            methodType = MethodType.GET;
        }
        JSONObject result = getHttpRequestTemplate().doExecute(UriVariables.getUri(getReqUrl(transactionType), outTradeNoBillType),   authHeader() , JSONObject.class, methodType);
        return result;
    }

    /**
     * 转账
     *
     * @param order 转账订单
     *
     * @return 对应的转账结果
     */
    @Override
    public Map<String, Object> transfer(TransferOrder order) {
        PayOrder payOrder = new PayOrder();
        payOrder.setCurType(order.getCurType());
        payOrder.setAuthCode(order.getPayeeAccount());
        payOrder.setSubject(order.getRemark());
        payOrder.setPrice(order.getAmount());
        payOrder.setOutTradeNo(order.getOutNo());

        Map<String, Object> info = orderInfo(payOrder);
        info.put("payout_date", DateUtils.formatDay(new Date()));
        info.put("group_id", order.getPayerName());
        HttpStringEntity entity = new HttpStringEntity(JSON.toJSONString(info), ContentType.APPLICATION_JSON);
        entity.setHeaders(authHeader());
        JSONObject response = getHttpRequestTemplate().postForObject(getReqUrl(PayoneerTransactionType.PAYOUTS), entity, JSONObject.class);
        return response;
    }

    /**
     * 转账
     *
     * @param outNo   商户转账订单号
     * @param tradeNo 支付平台转账订单号
     *
     * @return 对应的转账订单
     */
    @Override
    public Map<String, Object> transferQuery(String outNo, String tradeNo) {
        return secondaryInterface(tradeNo, outNo, PayoneerTransactionType.PAYOUT_STATUS);
    }

    /**
     * 根据是否为沙箱环境进行获取请求地址
     *
     * @return 请求地址
     */
    @Override
    public String getReqUrl(TransactionType type) {
        return (payConfigStorage.isTest() ? SANDBOX_DOMAIN : RELEASE_DOMAIN) + payConfigStorage.getPid() + "/" + type.getMethod();
    }


}
