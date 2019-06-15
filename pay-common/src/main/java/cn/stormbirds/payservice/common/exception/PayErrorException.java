package cn.stormbirds.payservice.common.exception;

import cn.stormbirds.payservice.common.bean.result.PayError;

/**
 *
 * @description PayErrorException.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 17:12
 *
 */
public class PayErrorException extends RuntimeException  {

    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}
