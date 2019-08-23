package api;

import cn.stormbirds.payservice.common.api.BasePayConfigStorage;

/**
 * <p>
 * api
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/8/20 15:17
 */


public class CitconPayConfigStorage extends BasePayConfigStorage {

    @Override
    public String getAppid() {
        return null;
    }


    @Override
    public String getPid() {
        return null;
    }

    @Override
    public String getSeller() {
        return null;
    }

    public void setToken(String accessToken) {
        setAccessToken(accessToken);
    }
}
