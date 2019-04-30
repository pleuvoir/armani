package io.github.armani.client.command;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginCommand implements ConsoleCommand {

    @Override
    public void excute(Scanner scanner, Channel channel) {

        log.info("请输入userId ..");
        String userId = scanner.nextLine();
        log.info("请输入username ..");
        String username = scanner.nextLine();

        login(userId, username, channel);
    }

    private void login(String userId, String username, Channel channel) {
        LoginRequestPacket requestPacket = LoginRequestPacket.builder()
                .userId(userId)
                .username(username)
                .password("数字电路").build();
        channel.writeAndFlush(requestPacket);
    }
}
