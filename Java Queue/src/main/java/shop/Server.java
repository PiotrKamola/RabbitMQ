package shop;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
public class Server {
    private final static String QUEUE_NAME = "hello";
    public void orderItem() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            int pom = 0;
            for (char c : message.toCharArray()){
                pom++;
                if(c == 124){
                    break;
                }
            }

            String itemName = message.substring(0, pom-1);
            int amount = 0;

            try{
                amount = Integer.parseInt(message.substring(pom));
            }catch (Exception e){
                System.out.println("Something went wrong.");
                System.exit(0);
            }

            System.out.println("---");
            System.out.println("You order: " + itemName);
            System.out.println("Amount: " + amount);
            System.out.println("---");

//            if(item.takeItem(amount)){
//                System.out.println("Success");
//            }else{
//                System.out.println("Fail");
//            }
//            System.out.println("Left in stock: " + item.getItemStock());
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
