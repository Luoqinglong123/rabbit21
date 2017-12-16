package com.queuework;

import java.io.IOException;
  
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;  
  
public class NewTask  
{  
    //队列名称  
    private final static String QUEUE_NAME = "workqueue2";  
  
    public static void main(String[] args) throws IOException  
    {  
        //创建连接和频道  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        //我们需要确认RabbitMQ永远不会丢失我们的队列。为了这样，我们需要声明它为持久化的。
        boolean durable = true;
        //声明队列  
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);  
        //发送10条消息，依次在消息后面附加1-10个点  
        for (int i = 0; i < 10; i++)  
        {  
            String dots = "";  
            for (int j = 0; j <= i; j++)  
            {  
                dots += ".";  
            }  
            String message = "helloworld" + dots+dots.length();  
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
        //关闭频道和资源  
        channel.close();  
        connection.close();  
  
    }  
  
  
}