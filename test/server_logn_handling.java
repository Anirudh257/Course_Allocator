
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class server_logn_handling {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        // TODO code application logic here

        ServerSocket server_socket = new ServerSocket(6000);

        while (true) {

            Socket clientSocket = server_socket.accept();
            ObjectInputStream from_client = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            ObjectOutputStream to_client = new ObjectOutputStream(clientSocket.getOutputStream());
            String[] id_details = (String[]) from_client.readObject();
            String username = id_details[0];
            String password = id_details[1];

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/uwe_alloc", "root", "sql@123");
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Student where username = '" + username + "' and password = '" + password + "';";
            ResultSet rs = (ResultSet) stmt.executeQuery(query);
            
            HashMap<String, String> student_details = new HashMap<String, String>();
                    
            String [] columns = {"name", "username", "password", "snu_id", "year", "credits_rem", "gpa", "credits_ccc", "credits_uwe"};
                    
            while(rs.next())
                for (String x: columns)
                    student_details.put(x, rs.getString(x));
            
            to_client.writeObject(student_details);
        }
    }
}
