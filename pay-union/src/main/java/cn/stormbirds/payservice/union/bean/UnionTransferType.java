package cn.stormbirds.payservice.union.bean;

import cn.stormbirds.payservice.common.bean.TransferType;

/**
 * <p>
 * cn.stormbirds.payservice.union.bean
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/7/10 19:01
 */


public enum UnionTransferType implements TransferType {
    /**
     * 转账到银行卡
     */
    BANK_CARD;

    @Override
    public String getType() {
        return name();
    }

    @Override
    public String getMethod() {
        return name();
    }
}
