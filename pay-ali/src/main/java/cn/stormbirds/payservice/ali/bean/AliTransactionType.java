package cn.stormbirds.payservice.ali.bean;

import cn.stormbirds.payservice.common.bean.TransactionType;


/**
 *
 * 说明交易类型主要用于支付接口调用参数所需
 * {@link #APP 新版app支付}
 * @description 阿里交易类型
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 17:26
 *
 */
public enum  AliTransactionType implements TransactionType {

    /**
     * 网页支付
     */
    PAGE("alipay.trade.page.pay"),
    /**
     * APP支付
     */
    APP("alipay.trade.app.pay"),
    /**
     * 手机网站支付
     */
    WAP("alipay.trade.wap.pay"),

    /**
     *  扫码付
     */
    SWEEPPAY("alipay.trade.precreate"),
    /**
     * 条码付
     */
    BAR_CODE("alipay.trade.pay"),
    /**
     * 声波付
     */
    WAVE_CODE("alipay.trade.pay"),
    //交易辅助接口

    /**
     *  统一收单交易结算接口
     */
    SETTLE("alipay.trade.order.settle"),
    /**
     * 交易订单查询
     */
    QUERY("alipay.trade.query"),
    /**
     * 交易订单关闭
     */
    CLOSE("alipay.trade.close"),
    /**
     * 交易订单撤销
     */
    CANCEL("alipay.trade.cancel"),
    /**
     * 退款
     */
    REFUND("alipay.trade.refund"),
    /**
     * 退款查询
     */
    REFUNDQUERY("alipay.trade.fastpay.refund.query"),
    /**
     * 下载对账单
     */
    DOWNLOADBILL("alipay.data.dataservice.bill.downloadurl.query"),
    /**
     * 转账到支付宝
     */
    TRANS("alipay.fund.trans.toaccount.transfer"),
    /**
     * 转账查询
     */
    TRANS_QUERY("alipay.fund.trans.order.query")
    ;



    private String method;

    AliTransactionType(String method) {
        this.method = method;
    }

    @Override
    public String getType() {
        return this.name();
    }

    /**
     * 获取接口名称
     * @return 接口名称
     */
    @Override
    public String getMethod() {
        return this.method;
    }

}
