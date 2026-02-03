package database;

import model.entity.Booking;
import model.entity.Passanger;
import model.entity.User;
import model.entity.Vehicle;
import model.entity.interfaces.Driver;
import utils.DialogAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadObject {

    private static final String SELECT_PASSANGERS="select*from users where userRole=?";
    private static final String SELECT_DRIVERS="select*from users where userRole=?";
    private static final String SELECT_BOOKINGS_Id="select *from BOOKINGS where userId=?";
    private static final String SELECT_BOOKINGS_Date="select *from BOOKINGS where bookingDate=?";
    private static final String SELECT_BOOKINGS_Location="select *from BOOKINGS where locationStart=?";
    private static final String SELECT_VEHICLE_Id="select *from VEHICLES where driverId=?";
    private static final String SELECT_VEHICLE_Color="select *from VEHICLES where color=?";
    private static final String SELECT_VEHICLE_Model="select *from VEHICLES where model=?";




    public static List<User> getPassangers() throws SQLException
    {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_PASSANGERS);
            ps.setInt(1,1);
            ResultSet rs=ps.executeQuery();
            List<User> list=new ArrayList<>();
            while(rs.next()){
                User novi=
                        new Passanger(rs.getString("userName"),rs.getString("pass"));
                novi.setId(rs.getInt("id"));

                list.add(novi);
            }
            return list;
        }
    }

    public static List<User> getDrivers() throws SQLException
    {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_DRIVERS);
            ps.setInt(1,2);
            ResultSet rs=ps.executeQuery();
            List<User> list=new ArrayList<>();
            while(rs.next()){
                User novi=
                        new Driver(rs.getString("userName"));
                novi.setId(rs.getInt("id"));

                list.add(novi);
            }
            return list;
        }
    }

    public static List<Booking> getAllBookings(){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement("select*from bookings");
            ResultSet rs=ps.executeQuery();
            List<Booking> list=new ArrayList<>();
            while(rs.next()){
                Booking novi=new Booking();
                novi.setId(rs.getInt("id"));
                novi.setUserId(rs.getInt("userId"));
                novi.setStart(rs.getString("locationStart"));
                novi.setEnd(rs.getString("locationEnd"));
                novi.setDate(rs.getString("bookingDate"));

                list.add(novi);
            }

            return list;
        }catch(SQLException e){
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Vehicle> getVehicles(){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement("select*from vehicles");
            ResultSet rs=ps.executeQuery();
            List<Vehicle> list=new ArrayList<>();
            while(rs.next()){
                Vehicle novi=new Vehicle();
                novi.setId(rs.getInt("id"));
                novi.setRegistration(rs.getString("registration"));
                novi.setModel(rs.getString("model"));
                novi.setDriverId(rs.getInt("driverId"));

                list.add(novi);
            }

            return list;

        } catch (Exception e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Booking> getBookingsId(int id) throws SQLException
    {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_BOOKINGS_Id);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            List<Booking> list=new ArrayList<>();
            while(rs.next()){
                Booking novi=
                        new Booking();
                novi.setUserId(rs.getInt("userId"));
                novi.setId(rs.getInt("id"));
                novi.setStart(rs.getString("locationStart"));
                novi.setEnd(rs.getString("locationEnd"));
                novi.setDate(rs.getString("bookingDate").toString());

                list.add(novi);
            }
            return list;
        }
    }

    public static List<Booking> getBookingsDate(String date) throws SQLException
    {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_BOOKINGS_Date);
            ps.setString(1,date);
            ResultSet rs=ps.executeQuery();
            List<Booking> list=new ArrayList<>();
            while(rs.next()){
                Booking novi=
                        new Booking();
                novi.setUserId(rs.getInt("userId"));
                novi.setId(rs.getInt("id"));
                novi.setStart(rs.getString("locationStart"));
                novi.setEnd(rs.getString("locationEnd"));
                novi.setDate(rs.getString("bookingDate").toString());

                list.add(novi);
            }
            return list;
        }
    }

    public static List<String> getLocations(){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement("select distinct locationStart from bookings");
            ResultSet rs=ps.executeQuery();
            List<String> list=new ArrayList<>();
            while(rs.next()){
                String location=rs.getString("locationStart");
                list.add(location);
            }
            return list;
        } catch (Exception e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Booking> getBookingsLocation(String location) throws SQLException
    {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_BOOKINGS_Location);
            ps.setString(1,location);
            ResultSet rs=ps.executeQuery();
            List<Booking> list=new ArrayList<>();
            while(rs.next()){
                Booking novi=
                        new Booking();
                novi.setUserId(rs.getInt("userId"));
                novi.setId(rs.getInt("id"));
                novi.setStart(rs.getString("locationStart"));
                novi.setEnd(rs.getString("locationEnd"));
                novi.setDate(rs.getString("bookingDate").toString());

                list.add(novi);
            }
            return list;
        }
    }

    public static List<Vehicle> getVehiclesId(int id){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_VEHICLE_Id);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            List<Vehicle> list=new ArrayList<>();
            while(rs.next()){
                Vehicle novi= new Vehicle();
                novi.setDriverId(rs.getInt("driverId"));
                novi.setModel(rs.getString("model"));
                novi.setColor(rs.getString("color"));
                novi.setRegistration(rs.getString("registration"));
                novi.setId(rs.getInt("id"));

                list.add(novi);
            }
            return list;
        }catch(SQLException e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Vehicle> getVehiclesColor(String color) {
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_VEHICLE_Color);
            ps.setString(1,color);
            ResultSet rs=ps.executeQuery();
            List<Vehicle> list=new ArrayList<>();
            while(rs.next()){
                Vehicle novi= new Vehicle();
                novi.setDriverId(rs.getInt("driverId"));
                novi.setModel(rs.getString("model"));
                novi.setColor(rs.getString("color"));
                novi.setRegistration(rs.getString("registration"));
                novi.setId(rs.getInt("id"));

                list.add(novi);
            }
            return list;

        }catch(SQLException e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Vehicle> getVehiclesModel(String model){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement(SELECT_VEHICLE_Model);
            ps.setString(1,model);
            ResultSet rs=ps.executeQuery();
            List<Vehicle> list=new ArrayList<>();
            while(rs.next()){
                Vehicle novi= new Vehicle();
                novi.setDriverId(rs.getInt("driverId"));
                novi.setModel(rs.getString("model"));
                novi.setColor(rs.getString("color"));
                novi.setRegistration(rs.getString("registration"));
                novi.setId(rs.getInt("id"));

                list.add(novi);
            }
            return list;
            }catch(SQLException e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    public static List<String> getModels(){
        try(Connection con=MyConnection.getConnection()){
            PreparedStatement ps=con.prepareStatement("select distinct model from vehicles");
            ResultSet rs=ps.executeQuery();
            List<String> list=new ArrayList<>();
            while(rs.next()){
                list.add(rs.getString("model"));
            }
            return list;
        }catch(SQLException e) {
            DialogAlert.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
