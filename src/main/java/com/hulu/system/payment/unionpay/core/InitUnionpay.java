package com.hulu.system.payment.unionpay.core;

import com.unionpay.acp.sdk.SDKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Created by huangyiwei on 15/10/28.
 */
@Service
public class InitUnionpay {
    private Logger logger = LoggerFactory.getLogger(InitUnionpay.class);

    @Autowired
    private Properties configProperties;

    @PostConstruct
    public void init(){
        logger.info("开始初始化银联配置...");
        try {
            String currentAppPath = InitUnionpay.class.getResource("/").getPath();
            configProperties.setProperty(SDKConfig.SDK_SIGNCERT_PATH,
                    currentAppPath + configProperties.get(SDKConfig.SDK_SIGNCERT_PATH));
            configProperties.setProperty(SDKConfig.SDK_VALIDATECERT_DIR,
                    currentAppPath + configProperties.get(SDKConfig.SDK_VALIDATECERT_DIR));
            configProperties.setProperty(SDKConfig.SDK_ENCRYPTCERT_PATH,
                    currentAppPath + configProperties.get(SDKConfig.SDK_ENCRYPTCERT_PATH));
            configProperties.setProperty(SDKConfig.SDK_ENCRYPTTRACKCERT_PATH,
                    currentAppPath + configProperties.get(SDKConfig.SDK_ENCRYPTTRACKCERT_PATH));
            SDKConfig.getConfig().loadProperties(configProperties);
            logger.info("初始化银联配置成功！");
        } catch (Exception e) {
            logger.info("初始化银联配置失败！", e);
        }

    }
}
