
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Umang
 */
public class fake_allocator_server {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        // TODO code application logic here

        ServerSocket server_socket = new ServerSocket(8000);
        int on_flag = 1;

        while (true) {

            Socket clientSocket = server_socket.accept();
            ObjectInputStream from_client = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            ObjectOutputStream to_client = new ObjectOutputStream(clientSocket.getOutputStream());
            ArrayList<Object> received = (ArrayList<Object>) from_client.readObject();
            ArrayList<String> course_ids = (ArrayList<String>) received.get(1);
            int roll_num = Integer.parseInt(String.valueOf(received.get(0)));
            
            if (roll_num == 1){
                
                to_client.writeObject(on_flag);
                clientSocket.close();
                continue;
            }
            
            for (String x: course_ids)
                System.out.println(x);
        }
    }
}
