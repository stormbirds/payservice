package cn.stormbirds.payservice.fuiou.api;
import cn.stormbirds.payservice.common.api.BasePayConfigStorage;

/**
 *
 * @ Description FuiouPayConfigStorage.java
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:24
 *
 */
public class FuiouPayConfigStorage extends BasePayConfigStorage {
    /**
     * 商户代码
     */
    private String mchntCd;

    /**
     *  应用id
     * @return 空
     */
    @Override
    public String getAppid() {
        return null;
    }


    /**
     * 合作商唯一标识
     *
     */
    @Override
    public String getPid () {
        return mchntCd;
    }

    public String getMchntCd () {
        return mchntCd;
    }

    public void setMchntCd (String mchntCd) {
        this.mchntCd = mchntCd;
    }

    @Override
    public String getSeller() {
        return null;
    }

}
