package io.github.armani.common.utils;

import io.netty.util.AttributeKey;

/**
 * 方便绑定Channel自定义属性
 */
public class AttributeKeyConst {

    public static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
