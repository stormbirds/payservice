package cn.stormbirds.payservice.yiji.bean;

import cn.stormbirds.payservice.common.bean.PayOrder;
import cn.stormbirds.payservice.common.bean.TransactionType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ Description 易极付交易类型
 *
 * 说明交易类型主要用于支付接口调用参数所需
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:40
 *
 */
public enum YiJiTransactionType implements TransactionType {
    /**
     * 跳转微支付
     */
    commonWchatTradeRedirect("commonWchatTradeRedirect"),
    /**
     * 跳转收银台支付
     */
    commonTradePay("commonTradePay"){
        @Override
        public String getVersion() {
            return "2.0";
        }
    },
    tradeRefund("tradeRefund"),
    /**
     * 跨境订单同步
     *//*
    corderRemittanceSynOrder("corderRemittanceSynOrder"),
    */
    /**
     * 国际转账
     */
    applyRemittranceWithSynOrder("applyRemittranceWithSynOrder")
    ;

    private String method;
    /**
     * 版本
     */
    private String version = "1.0";

    private static final Map<String, TransactionType> transactiontypes = new HashMap<String, TransactionType>();
    static {
        for (TransactionType type : YiJiTransactionType.values()){
            transactiontypes.put(type.getMethod(), type);
        }
    }

    YiJiTransactionType(String method) {
        this.method = method;
    }

    @Override
    public String getType() {
        return this.name();
    }

    public String getVersion() {
        return version;
    }

    /**
     * 获取接口名称
     *
     * @return 接口名称
     */
    @Override
    public String getMethod() {
        return this.method;
    }


    public void setAttribute(Map<String, Object> parameters, PayOrder order) {
        parameters.put("version", getVersion());
    }

    public static TransactionType getTransactionType(String method) {
        return transactiontypes.get(method);
    }

}
