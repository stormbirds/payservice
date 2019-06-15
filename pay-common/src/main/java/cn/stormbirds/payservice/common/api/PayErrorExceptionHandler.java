package cn.stormbirds.payservice.common.api;


import cn.stormbirds.payservice.common.exception.PayErrorException;


/**
 *
 * @description PayErrorExceptionHandler.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/14 16:48
 *
 */
public interface PayErrorExceptionHandler {

    /**
     * 异常统一处理器
     * @param e 支付异常
     */
     void handle(PayErrorException e);

}
