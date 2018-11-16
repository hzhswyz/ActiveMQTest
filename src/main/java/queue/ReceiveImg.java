package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;

public class ReceiveImg {
    public static void main(String[] args) {
        //连接信息设置
        String username = "hzh";
        String password = "Hzh1997";
        String brokerURL = "failover://tcp://localhost:61616";
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
            destination = session.createQueue("image");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024*100);
            byte[] bytes;
            int num = 0;
            long startTime=System.currentTimeMillis();   //获取开始时间
            while (true) {
                //System.out.println("等待接收第"+(++num)+"张照片");
                BytesMessage bytesMessage = (BytesMessage) messageConsumer.receive();
                System.out.println("接收到第"+num+"张照片");
                if ( bytesMessage != null) {
                        System.out.println(bytesMessage.getIntProperty("id"));
                        System.out.println(bytesMessage.getIntProperty("status"));
                        int i = 0;
                        while (i++<bytesMessage.getBodyLength())
                        byteArrayOutputStream.write(bytesMessage.readByte());
                        /*bytes = byteArrayOutputStream.toByteArray();
                        int len;
                        try {
                            OutputStream out = new FileOutputStream("F:\\img\\"+i+".jpg");
                            InputStream inputStream = new ByteArrayInputStream(bytes);
                            byte[] buff = new byte[1024];
                            while((len=inputStream.read(buff))!=-1){
                                out.write(buff, 0, len);
                            }
                            out.close();
                            inputStream.close();
                            byteArrayOutputStream.close();
                            long endTime=System.currentTimeMillis(); //获取结束时间
                            System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                    System.out.println("程序运行时间： "+(-startTime)+"ms");
                } else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
