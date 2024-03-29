package cn.stormbirds.payservice.wx.api;

import cn.stormbirds.payservice.common.api.BasePayConfigStorage;

/**
 *
 * @ Description 微信配置容器
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:36
 *
 */
public class WxPayConfigStorage extends BasePayConfigStorage {


    /**
     * 微信分配的公众账号ID
     */
    private String appid ;
    /**
     * 微信分配的子商户公众账号ID
     */
    private String subAppid ;
    /**
     *  微信支付分配的商户号 合作者id
     */
    private String mchId;
    /**
     *  微信支付分配的子商户号，开发者模式下必填 合作者id
     */
    private String subMchId;





    @Override
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }




    /**
     * 合作商唯一标识
     */
    @Override
    public String getPid() {
        return mchId;
    }




    @Override
    public String getSeller() {
        return null;
    }


    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     *  为商户平台设置的密钥key
     * @return 微信密钥
     */
    public String getSecretKey() {
        return getKeyPrivate();
    }

    public void setSecretKey(String secretKey) {
         setKeyPrivate(secretKey);
    }

    public String getSubAppid() {
        return subAppid;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }
}
