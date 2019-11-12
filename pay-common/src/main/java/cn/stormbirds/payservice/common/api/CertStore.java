package cn.stormbirds.payservice.common.api;

import java.io.IOException;
import java.io.InputStream;


/**
 *
 * <p> 证书存储方式
 * </p>
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/11/12 10:50
 *
 */
public interface CertStore {

    /**
     * 证书信息转化为对应的输入流
     *
     * @param cert 证书信息
     * @return 输入流
     * @throws IOException 文件异常
     */
    InputStream getInputStream(Object cert) throws IOException;
}