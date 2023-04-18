package com.hz.sellcloud;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class redisPubsubTest {
        public static void main(String[] args) {
            // 从命令行参数中获取Redis相关参数
            String host = "localhost";
            int port = 6379;
            String channel ="flink-demo-channel";
            // 创建Jedis实例
            Jedis jedis = new Jedis(host, port);
            // 读取控制台输入的数据，并发送到指定的channel
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                jedis.publish(channel, line);
            }
            // 关闭Jedis连接
            jedis.close();
        }


}
