package io.github.armani.client.command;

import io.github.armani.common.protocol.packet.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToGroupCommand implements ConsoleCommand {

    @Override
    public void excute(Scanner scanner, Channel channel) {
        log.info("请在控制台输入要发送的消息，回车发送，格式 群组名称-你好");
        String line = scanner.nextLine();
        String[] split = line.split("-");
        sendMessage(split[0], split[1], channel);
    }

    private void sendMessage(String groupId, String message, Channel channel) {
        log.info("发消息给[{}]，{}", groupId, message);
        GroupMessageRequestPacket packet = new GroupMessageRequestPacket();
        packet.setGroupId(groupId);
        packet.setMessage(message);
        channel.writeAndFlush(packet);
    }
}
