package cn.stormbirds.payservice.wx.bean;

import cn.stormbirds.payservice.common.bean.TransferType;

/**
 *
 * @ Description 微信转账类型
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:35
 *
 */
public enum WxTransferType implements TransferType{
    /**
     * 转账到零钱
     */
    TRANSFERS("mmpaymkttransfers/promotion/transfers"),
    /**
     * 查询转账到零钱的记录
     */
    GETTRANSFERINFO("mmpaymkttransfers/gettransferinfo"),
    /**
     * 转账到银行卡
     */
    PAY_BANK("mmpaysptrans/pay_bank"),
    /**
     * 查询转账到银行卡的记录
     */
    QUERY_BANK("mmpaysptrans/query_bank"),

    ;

    WxTransferType(String method) {
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
}
