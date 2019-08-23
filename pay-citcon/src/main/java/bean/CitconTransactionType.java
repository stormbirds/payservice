package bean;

import cn.stormbirds.payservice.common.bean.TransactionType;

/**
 * <p>
 * bean
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/8/23 15:35
 */


public enum CitconTransactionType implements TransactionType {
    /**
     * h5 支付用于web支付
     */
    H5("h5"),
    /**
     * 移动端支付，用于app使用
     */
    APP("app"),
    /**
     * 微信小程序订单支付
     */
    WXMINI("wexin_mini"),

    TRANSACTIONS("transactions"),

    INQUIRE("inquire"),

    REFUND("refund")

    ;

    private String method;

    CitconTransactionType(String method) {
        this.method = method;
    }

    @Override
    public String getType() {
        return this.name();
    }

    @Override
    public String getMethod() {
        return this.method;
    }
}
