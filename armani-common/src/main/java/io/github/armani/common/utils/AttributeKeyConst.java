package io.github.armani.common.utils;

import io.netty.util.AttributeKey;

/**
 * 方便绑定Channel自定义属性，保证单例
 */
public class AttributeKeyConst {

    public static final AttributeKey<SessionMember> SESSION_MEMBER = AttributeKey.newInstance("session_member");
}
