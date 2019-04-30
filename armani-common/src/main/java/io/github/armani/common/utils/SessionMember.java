package io.github.armani.common.utils;

import io.netty.util.internal.StringUtil;
import lombok.*;

/**
 * 登录用户
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SessionMember {

    public static SessionMember EMPTY = new SessionMember();

    private String username;
    private String userId;

    public boolean isNotNull() {
        return !StringUtil.isNullOrEmpty(userId);
    }
}
