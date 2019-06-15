package cn.stormbirds.payservice.common.bean;

/**
 *
 * @description 交易类型
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 17:12
 *
 */
public interface TransactionType {
    /**
     * 获取交易类型
     * @return 交易类型
     */
     String getType();

    /**
     * 获取接口
     * @return 接口
     */
     String getMethod();
}

