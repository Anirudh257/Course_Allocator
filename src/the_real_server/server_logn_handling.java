package the_real_server;


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
public class server_logn_handling {

    private static String[] items;
    static HashMap<String, String> courses = null;
    static ArrayList<HashMap<String, String>> course_map = null;
    
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
            
            try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/uwe_alloc", "root", "sql@123");

            ArrayList<String> courses_done = new ArrayList<String>();

            stmt = (Statement) con.createStatement();
            query = "Select * from course_taken;";
            rs = (ResultSet) stmt.executeQuery(query);

            while (rs.next()) {

                if (rs.getString("username").equals(student_details.get("username"))) {
                    courses_done.add(rs.getString("course_id"));
                }
            }

            stmt = (Statement) con.createStatement();
            query = "Select * from course;";
            rs = (ResultSet) stmt.executeQuery(query);

            course_map = new ArrayList<HashMap<String, String>>();

            while (rs.next()){

                courses = new HashMap<String, String>();
                
                courses.put("course_id", rs.getString("course_id"));
                courses.put("course_name", rs.getString("course_name"));
                courses.put("course_type", rs.getString("course_type"));
                courses.put("credits", rs.getString("credits"));
                courses.put("pre_req", rs.getString("pre_req"));
                
                course_map.add(courses);
            }
                
            
                
            
            ArrayList<String> temp_items = new ArrayList<String>();
            temp_items.add("--Select a Course--");
            
            for (HashMap<String, String> x: course_map){
                
                if (courses_done.contains(x.get("course_code")))
                    continue;

                if (!(x.get("pre_req") == null))
                    if (!courses_done.contains(x.get("pre_req")))
                        continue;

                temp_items.add(x.get("course_name"));
            }

            int length = temp_items.size();
            items = new String[length];
            int index = 0;

            for (String x: temp_items){

                items[index] = x;
                index++;
            }

            for (String x: items)
                System.out.println(x);
            
        } catch (Exception e) {
            System.out.println(e);
        }
            
            
            ArrayList<Object> info_for_client = new ArrayList<Object>();
            info_for_client.add(student_details);
            info_for_client.add(items);
            info_for_client.add(course_map);
            
            to_client.writeObject(info_for_client);
        }
    }
}
