package cn.stormbirds.payservice.common.bean;

import cn.stormbirds.payservice.common.api.CertStore;
import cn.stormbirds.payservice.common.http.HttpRequestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @description 证书存储类型
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:58
 *
 */
public enum CertStoreType implements CertStore {

    /**
     * 路径，建议绝对路径
     */
    PATH {
        /**
         * 证书信息转化为对应的输入流
         *
         * @param cert 证书信息
         * @return 输入流
         * @throws IOException 文件异常
         */
        @Override
        public InputStream getInputStream(Object cert) throws IOException {
            return new FileInputStream(new File((String) cert));
        }
    },
    /**
     * 文件流转化成字符串存储至文件或者数据库中
     */
    STR {
        /**
         * 证书信息转化为对应的输入流
         *
         * @param cert 证书信息
         * @return 输入流
         */
        @Override
        public InputStream getInputStream(Object cert) {
            return new ByteArrayInputStream(((String) cert).getBytes(StandardCharsets.ISO_8859_1));
        }
    },

    /**
     * 文件流
     */
    INPUT_STREAM {
        /**
         * 证书信息转化为对应的输入流
         *
         * @param cert 证书信息
         * @return 输入流
         */
        @Override
        public InputStream getInputStream(Object cert) {
            return (InputStream) cert;
        }
    },

    /**
     * URL获取的方式
     */
    URL {
        /**
         * 证书信息转化为对应的输入流
         *
         * @param url 获取证书信息的URL
         * @return 输入流
         */
        @Override
        public InputStream getInputStream(Object url) {
            return new HttpRequestTemplate().getForObject((String) url, InputStream.class);
        }
    },
    BEAN {
        /**
         * 证书信息转化为对应的输入流
         *
         * @param beanClazz 获取证书信息的类路径（字符串），该类必须实现{@link CertStore}
         * @return 输入流
         * @throws IOException 文件异常
         */
        @Override
        public InputStream getInputStream(Object beanClazz) throws IOException {
            try {
                Class<?> clazz = Class.forName((String) beanClazz);
                CertStore certStore =   (CertStore)clazz.newInstance();
                return certStore.getInputStream(beanClazz);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
