package io.github.armani.client.command;

import io.github.armani.common.protocol.packet.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class CreateGroupCommand implements ConsoleCommand {

    @Override
    public void excute(Scanner scanner, Channel channel) {
        log.info("创建群聊，输入userId以,分隔，如：1-2,3  其中1为发起人");
        String line = scanner.nextLine();

        String[] command = line.split("-");

        String fromUserId = command[0];
        String joinUsers = command[1];

        CreateGroupRequestPacket createGroupRequestPacket =
                CreateGroupRequestPacket.builder()
                        .fromUserId(fromUserId).
                        userIdList(Arrays.stream(joinUsers.split(",")).collect(Collectors.toList()))
                        .build();

        channel.writeAndFlush(createGroupRequestPacket);

    }

}
