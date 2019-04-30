package io.github.armani.common.utils;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    public static Map<SessionMember /*登录用户*/, Channel /*连接*/> sessionMemberChannelMap = new ConcurrentHashMap<>();

    public static Map<String /*userId*/, SessionMember /*登录用户*/> memberMap = new ConcurrentHashMap<>();


    /**
     * 绑定登录用户，初始化登录用户session
     */
    public static void bindSessionMember(SessionMember member, Channel channel) {
        memberMap.put(member.getUserId(), member);
        sessionMemberChannelMap.put(member, channel);
    }

    /**
     * 解绑登录用户，销毁登录用户session
     */
    public static void unbindSessionMember(SessionMember member, Channel channel) {
        memberMap.remove(member.getUserId());
        sessionMemberChannelMap.remove(member);
    }

    /**
     * 获取当前登录用户信息
     */
    public static SessionMember getMember(String userId) {
        final SessionMember sessionMember = memberMap.get(userId);
        if (sessionMember == null) {
            throw new IllegalStateException("用户未登录");
        }
        return sessionMember;
    }

    /**
     * 获取消息通道
     */
    public static Channel getChannel(String userId) {
        final SessionMember sessionMember = memberMap.get(userId);
        return getChannel(sessionMember);
    }

    /**
     * 获取消息通道
     */
    private static Channel getChannel(SessionMember member) {
        Channel channel = sessionMemberChannelMap.get(member);
        if (channel == null) {
            throw new IllegalStateException("用户未登录");
        }
        return channel;
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin(String userId) {
        final SessionMember sessionMember = memberMap.get(userId);
        return sessionMember == null ? false : true;
    }

}
