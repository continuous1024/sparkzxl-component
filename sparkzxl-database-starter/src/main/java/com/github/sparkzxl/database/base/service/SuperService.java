package com.github.sparkzxl.database.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.sparkzxl.core.support.ResponseResultStatus;
import com.github.sparkzxl.database.base.mapper.SuperMapper;

import java.util.List;

/**
 * description:
 *
 * @author: zhouxinlei
 * @date: 2020-07-07 20:26:15
 */
public interface SuperService<T> extends IService<T> {

    /**
     * 批量保存
     *
     * @param entityList 实体对象
     * @return boolean
     */
    default boolean saveBatchSomeColumn(List<T> entityList) {
        int size = 5000;
        if (entityList.isEmpty()) {
            return true;
        } else if (entityList.size() > size) {
            ResponseResultStatus.TOO_MUCH_DATA_ERROR.newException(entityList);
        } else {
            return SqlHelper.retBool(((SuperMapper<T>) this.getBaseMapper()).insertBatchSomeColumn(entityList));
        }
        return false;
    }

}
