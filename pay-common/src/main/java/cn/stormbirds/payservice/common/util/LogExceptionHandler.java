package cn.stormbirds.payservice.common.util;

import cn.stormbirds.payservice.common.api.PayErrorExceptionHandler;
import cn.stormbirds.payservice.common.exception.PayErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @description LogExceptionHandler 日志处理器
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 17:20
 *
 */
public class LogExceptionHandler implements PayErrorExceptionHandler {

    protected final Log log = LogFactory.getLog(PayErrorExceptionHandler.class);

    @Override
    public void handle(PayErrorException e) {

        log.error("Error happens", e);

    }

}
