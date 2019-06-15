package cn.stormbirds.payservice.common.bean.result;


/**
 *
 * @description 支付错误码说明
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:55
 *
 */
public interface PayError {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    String getErrorCode();

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    String getErrorMsg();

    /**
     * 获取异常信息
     * @return 异常信息
     */
    String getString();

}
