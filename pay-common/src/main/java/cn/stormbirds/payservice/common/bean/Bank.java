package cn.stormbirds.payservice.common.bean;

/**
 *
 * @description 银行代码
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:56
 *
 */
public interface Bank {

    /**
     * 获取银行的代码
     * @return 银行的代码
     */
    String getCode();

    /**
     * 获取银行的名称
     * @return 银行的名称
     */
    String getName();
}
