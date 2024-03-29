package cn.stormbirds.payservice.payoneer.api;

import cn.stormbirds.payservice.common.api.PayService;

import java.util.Map;

/**
 *
 * @ Description 高级支付接口
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:27
 *
 */

public interface AdvancedPayService extends PayService<PayoneerConfigStorage> {
    /**
     * 获取授权页面
     * @param payeeId 用户id
     * @return 返回请求结果
     */
    String getAuthorizationPage(String payeeId);

    /**
     * 授权状态
     * @param payeeId 用户id
     * @return 返回是否认证 true 已认证
     */
    Map<String, Object> getAuthorizationStatus(String payeeId);

    /**
     *  获取授权用户信息
     * @param payeeId 用户id
     * @return 获取授权用户信息，包含用户状态，注册时间，联系人信息，地址信息等等
     */
    Map<String, Object> getAuthorizationUser(String payeeId);

}
