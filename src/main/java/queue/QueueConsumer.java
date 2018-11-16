package queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueConsumer {

    public static void main(String[] args) {
        //连接信息设置
        String username = "hzh";
        String password = "Hzh1997";
        String brokerURL = "failover://tcp://120.79.16.31:61616";
        //连接工厂
        ConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination = null;
        //消息消费者
        MessageConsumer messageConsumer = null;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接QueueTest的消息队列
            destination = session.createQueue("QueueTest");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if (textMessage != null) {
                    System.out.println("成功接收消息:" + textMessage.getText());
                } else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
