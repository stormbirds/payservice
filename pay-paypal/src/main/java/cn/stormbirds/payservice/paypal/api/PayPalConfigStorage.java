package cn.stormbirds.payservice.paypal.api;

import cn.stormbirds.payservice.common.api.BasePayConfigStorage;

/**
 *
 * @ Description 贝宝支付配置存储
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:31
 *
 */
public class PayPalConfigStorage extends BasePayConfigStorage {

    private String clientID;

    @Override
    public String getAppid() {
        return clientID;
    }

    @Override
    public String getPid() {
        return clientID;
    }

    @Override
    public String getSeller() {
        return clientID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return getKeyPrivate();
    }

    public void setClientSecret(String clientSecret) {
        setKeyPrivate(clientSecret);
    }


    /**
     * 设置取消页面的url
     *
     * @param cancelUrl 取消页面的url
     */
    public void setCancelUrl(String cancelUrl) {
        setNotifyUrl(cancelUrl);
    }

    /**
     * 获取取消页面的url
     */
    public String getCancelUrl() {
        return getNotifyUrl();
    }


}
