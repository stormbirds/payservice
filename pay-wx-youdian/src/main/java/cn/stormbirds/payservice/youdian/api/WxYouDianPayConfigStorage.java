package cn.stormbirds.payservice.youdian.api;

import cn.stormbirds.payservice.common.api.BasePayConfigStorage;

/**
 *
 * @ Description 支付客户端配置存储
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:38
 *
 */
public class WxYouDianPayConfigStorage extends BasePayConfigStorage {


    /**
     * 账号
     */
    public String seller;


    @Override
    public String getAppid() {
        return null;
    }


    @Override
    public String getPid() {
        return null;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String getSeller() {
        return seller;
    }


    public void setToken(String accessToken) {
        setAccessToken(accessToken);
    }

    @Override
    public String getToken() {
        return getAccessToken();
    }


}
