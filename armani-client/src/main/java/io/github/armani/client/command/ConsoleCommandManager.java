package io.github.armani.client.command;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsoleCommandManager implements ConsoleCommand {


    public static final ConsoleCommandManager INSTANCE = new ConsoleCommandManager();

    public static final Map<String, ConsoleCommand> map = new HashMap<>();

    public static final AtomicBoolean started = new AtomicBoolean();

    static {
        map.put("login", new LoginCommand());
        map.put("sendToUser", new SendToUserCommand());
        map.put("createGroup", new CreateGroupCommand());
        map.put("SendToGroup", new SendToGroupCommand());
    }

    @Override
    public void excute(Scanner scanner, Channel channel) {

        while (true) {

            if (!started.get()) {
                map.get("login").excute(scanner, channel);
                started.compareAndSet(false, true);
            }

            log.info("进入指令窗口，请输入指令：当前支持的指令{}", map.keySet());

            String command = scanner.nextLine();

            ConsoleCommand consoleCommand = map.get(command);
            if (consoleCommand == null) {
                log.info("请输入正确指令：当前支持的指令{}", map.keySet());
            } else {
                consoleCommand.excute(scanner, channel);
            }
        }
    }


    //这里必须启用新的线程来处理，否则无法接受到响应，可能是阻塞了原来的启动流程
    public void startConsoleInput(Channel channel) {
        new Thread(() -> {
            excute(new Scanner(System.in), channel);
        }).start();
    }

}
