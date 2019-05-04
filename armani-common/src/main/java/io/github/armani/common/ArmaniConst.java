package io.github.armani.common;

public class ArmaniConst {

	/**
	 * 读空闲时间
	 */
	public static final int READER_IDLE_TIME = 360;

	/**
	 * 心跳间隔，一般设置为空闲检测时间的1/3
	 */
	public static final int HEARTBEAT_INTERVAL = READER_IDLE_TIME / 3;
	
	/**
	 * 服务端启绑定端口
	 */
	public static final int SERVER_PORT = 8443;
	
	/**
	 * 客户端最大重连次数
	 */
	public static final int MAX_RETRY_CONNECT_NUM = 5;
	
	/**
	 * 客户端连接超时时间
	 */
	public static final int CONNECT_TIMEOUT_MILLIS = 5000;
}
