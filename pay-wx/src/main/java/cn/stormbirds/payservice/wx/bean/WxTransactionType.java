package cn.stormbirds.payservice.wx.bean;

import com.alibaba.fastjson.JSONObject;
import cn.stormbirds.payservice.common.bean.PayOrder;
import cn.stormbirds.payservice.common.bean.TransactionType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @ Description 微信交易类型
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:36
 *
 */
public enum  WxTransactionType implements TransactionType {
    /**
     * 公众号支付
     */
    JSAPI("pay/unifiedorder") {
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put(parameters.containsKey("sub_appid") ? "sub_openid" : "openid", order.getOpenid());
        }
    },
    /**
     * 扫码付
     */
    NATIVE("pay/unifiedorder") {
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("product_id", order.getOutTradeNo());
        }

        /**
         * 是否直接返回
         *
         * @return 是否直接返回
         */
        @Override
        public boolean isReturn() {
            return true;
        }
    },
    /**
     * 移动支付
     */
    APP("pay/unifiedorder"),
    /**
     * 刷脸支付
     */
    FACEPAY("pay/facepay"){
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("openid", order.getOpenid());
            parameters.put("face_code", order.getAuthCode());
        }
    },
    /**
     * H5支付
     */
    MWEB("pay/unifiedorder"){
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            //H5支付专用
            LinkedHashMap value = new LinkedHashMap();
            value.put("type", "Wap");
            //WAP网站URL地址
            value.put("wap_url", order.getWapUrl());
            //WAP 网站名
            value.put("wap_name", order.getWapName());
            JSONObject sceneInfo = new JSONObject();
            sceneInfo.put("h5_info", value);
            parameters.put("scene_info", sceneInfo.toJSONString());
        }
        /**
         * 是否直接返回
         *
         * @return 是否直接返回
         */
        @Override
        public boolean isReturn() {
            return true;
        }
    },
    /**
     * 刷卡付
     */
    MICROPAY("pay/micropay"){
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("auth_code", order.getAuthCode());
            parameters.remove("notify_url");
            parameters.remove("trade_type");
        }
        /**
         * 是否直接返回
         *
         * @return 是否直接返回
         */
        @Override
        public boolean isReturn() {
            return true;
        }
    },
    // 交易辅助接口
    /**
     * 查询订单
     */
    QUERY("pay/orderquery"),
    /**
     * 关闭订单
     */
    CLOSE("pay/closeorder"),
    /**
     * 撤销订单
     */
    REVERSE("secapi/pay/reverse"),
    /**
     * 申请退款
     */
    REFUND("secapi/pay/refund"),
    /**
     * 查询退款
     */
    REFUNDQUERY("pay/refundquery"),
    /**
     * 下载对账单
     */
    DOWNLOADBILL("pay/downloadbill"),
    /**
     * 获取验签秘钥，沙箱使用
     */
    GETSIGNKEY("pay/getsignkey"),

    ;

    WxTransactionType(String method) {
        this.method = method;
    }

    private String method;

    @Override
    public String getType() {
        return this.name();
    }
    @Override
    public String getMethod() {
        return this.method;
    }

    /**
     * 是否直接返回
     * @return 是否直接返回
     */
    public boolean isReturn(){
        return false;
    }

    public  void setAttribute(Map<String, Object> parameters, PayOrder order){

    }
}
