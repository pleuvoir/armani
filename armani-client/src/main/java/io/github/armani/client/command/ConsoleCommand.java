package io.github.armani.client.command;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface ConsoleCommand {

    void excute(Scanner scanner, Channel channel);
}
