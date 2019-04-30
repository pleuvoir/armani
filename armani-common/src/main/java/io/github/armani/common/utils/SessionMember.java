package io.github.armani.common.utils;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录用户
 */
@Data
@Builder
@EqualsAndHashCode
public class SessionMember {

    private String username;
    private String userId;

}
