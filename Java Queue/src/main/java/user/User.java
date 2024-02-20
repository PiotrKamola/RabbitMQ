package user;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import item.Item;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class User {
    private String name;
    private final static String QUEUE_NAME = "hello";

    //rabbit
    public void buyItem() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            while(true){
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.println("---\n" +
                        "What do you want to order?");

                String itemName = reader.readLine();
                while(true){
                    Boolean containSpecialSymbol = false;
                    String itemNameLower = itemName.toLowerCase();
                    for (char c : itemNameLower.toCharArray()){
                        if(!(c > 96 && c < 123)){
                            containSpecialSymbol = true;
                            break;
                        }
                    }
                    if(!containSpecialSymbol){
                        break;
                    }
                    System.out.println("Wrong name, it can contain only letters.");
                    itemName = reader.readLine();
                }

                System.out.println("How much?");
                String amount = reader.readLine();
                while(true){
                    try{
                        Integer.parseInt(amount);
                        break;
                    }catch(Exception e){
                        System.out.println("Not a integer number.");
                        amount = reader.readLine();
                    }
                }

                String order = itemName + "|" + amount;
                channel.basicPublish("", QUEUE_NAME, null, order.getBytes());
                System.out.println(" [x] Order '" + order + "'");

                if(amount.equals("exit") || itemName.equals("exit")){
                    break;
                }
            }
        }
    }
}
