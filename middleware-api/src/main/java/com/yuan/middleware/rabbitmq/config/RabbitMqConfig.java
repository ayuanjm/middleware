package com.yuan.middleware.rabbitmq.config;

/**
 * @author yuan
 */
public interface RabbitMqConfig {
    String HOST = "localhost";
    Integer PORT = 5672;
    String DEFAULT_VIRTUAL_HOST = "/";
    String PASSWORD = "guest";
    String USERNAME = "guest";
}
