package cn.stormbirds.payservice.common.api;


import cn.stormbirds.payservice.common.bean.PayMessage;
import cn.stormbirds.payservice.common.exception.PayErrorException;

import java.util.Map;


/**
 *
 * @description 支付消息拦截器，可以用来做验证等等
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/3/15 16:50
 *
 */
public interface PayMessageInterceptor<M extends PayMessage, S extends PayService> {

    /**
     * 拦截微信消息
     *
     * @param payMessage 支付消息
     * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return true代表OK，false代表不OK
     */
    boolean intercept(M payMessage,
                      Map<String, Object> context,
                      S payService
    ) throws PayErrorException;

}
