package io.github.armani.common.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    /**
     * 标记为已登录
     */
    public static void markLogin(Channel channel) {
        channel.attr(AttributeKeyConst.LOGIN).set(true);
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(AttributeKeyConst.LOGIN);
        return attr == null ? false : attr.get();
    }
}
