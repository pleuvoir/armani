package io.github.armani.client.command;

import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToUserCommand implements ConsoleCommand {

    @Override
    public void excute(Scanner scanner, Channel channel) {
        log.info("请在控制台输入要发送的消息，回车发送，格式 1-123-你好");
        String line = scanner.nextLine();
        String[] split = line.split("-");
        sendMessage(split[0], split[1], split[2], channel);
    }

    private void sendMessage(String fromUserId, String toUserId, String message, Channel channel) {
        log.info("[{}]发消息给[{}]", fromUserId, toUserId);
        ChatMessageRequestPacket chat = ChatMessageRequestPacket.builder().fromUserId(fromUserId).toUserId(toUserId).message(message).build();
        channel.writeAndFlush(chat);
    }
}
