package io.github.armani.common.utils;

import com.alibaba.fastjson.JSON;

public interface ToJSON {

    default String toJSON() {
        return JSON.toJSONString(this);
    }
}
