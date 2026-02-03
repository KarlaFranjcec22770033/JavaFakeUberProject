package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SaveObject {
    private static final String INSERT_USER="insert into users(userName,pass,location,userRole) values(?,?,?,?)";
    private static final String INSERT_BOOKING="insert into bookings(bookingDate,locationStart,locationEnd,userId) values(?,?,?,?)";
    private static final String INSERT_VEHICLE="insert into vehicles(model,color,registration,driverId) values(?,?,?,?)";
    private static final String INSERT_USER_noLocation="insert into users(userName,pass,userRole) values(?,?,?)";

    public static void executeQueryUsers(String name,String pass,String locationStart){
        try(Connection con=MyConnection.getConnection()){

            PreparedStatement ps=con.prepareStatement(INSERT_USER);
            ps.setString(1,name);
            ps.setString(2,pass);
            ps.setString(3,locationStart);
            ps.setInt(4,1);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void executeQueryUsersNoLocation(String name,String pass){
        try(Connection con=MyConnection.getConnection()){

            PreparedStatement ps=con.prepareStatement(INSERT_USER_noLocation);
            ps.setString(1,name);
            ps.setString(2,pass);
            ps.setInt(3,2);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void executeQueryBookings(String date,String start,String end,int userId ){
        try(Connection con=MyConnection.getConnection()){

            PreparedStatement ps=con.prepareStatement(INSERT_BOOKING);
            ps.setString(1,date);
            ps.setString(2,start);
            ps.setString(3,end);
            ps.setInt(4,userId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void executeQueryVehicles(String model,String color,String registration,int driverId){

        try(Connection con=MyConnection.getConnection()){

            PreparedStatement ps=con.prepareStatement(INSERT_VEHICLE);
            ps.setString(1,model);
            ps.setString(2,color);
            ps.setString(3,registration);
            ps.setInt(4,driverId);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


