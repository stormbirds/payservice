package cn.stormbirds.payservice.common.bean;


/**
 *
 * @description 基础的支付类型
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:56
 *
 */
public interface BasePayType {


    /**
     * 根据支付类型获取交易类型
     * @param transactionType 类型值
     * @return  交易类型
     */
    TransactionType getTransactionType(String transactionType);

}
