import shop.Server;
import user.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        User user = new User();

        System.out.println("To run docker run:");
        System.out.println("docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management");
        System.out.println("in command prompt.\n");

        System.out.println("Who are you?\n" +
                "1 - server,\n" +
                "2 - client");


        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String who = reader.readLine();
        int whoInt;

        while(true){
            try{
                whoInt = Integer.parseInt(who);
                if(whoInt > 2 || whoInt < 1){
                    throw new Exception();
                }
                break;
            }catch(Exception e){
                System.out.println("Not a integer number or not valid number.");
                who = reader.readLine();
            }
        }

        if(whoInt == 1){
            server.orderItem();
        }else{
            user.buyItem();
        }
    }
}