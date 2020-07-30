package com.sparksys.database.context;

import com.sparksys.core.constant.BaseContextConstants;
import com.sparksys.core.repository.CacheRepository;

/**
 * description: 上下文全局获取
 *
 * @author: zhouxinlei
 * @date: 2020-07-29 13:40:55
 */
public class BaseContextHandler {

    public static CacheRepository cacheRepository;

    public static Long getUserId() {
        return cacheRepository.get(BaseContextConstants.APPLICATION_AUTH_USER_ID);
    }

    public static String getAccount() {
        return cacheRepository.get(BaseContextConstants.APPLICATION_AUTH_ACCOUNT);
    }

    public static String getUserName() {
        return cacheRepository.get(BaseContextConstants.APPLICATION_AUTH_NAME);
    }

    public void setCacheRepository(CacheRepository cacheRepository) {
        BaseContextHandler.cacheRepository = cacheRepository;
    }
}