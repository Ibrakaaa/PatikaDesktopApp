package com.patikadev.Main;

import Helper.DBConnecctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikalist = new ArrayList<>();
        Patika obj;

        try {
            Statement st = DBConnecctor.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patikas");
            while (rs.next()){
                obj = new Patika(rs.getInt("id"),rs.getString("name"));
                patikalist.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patikalist;
    }
    public static boolean add(String name){
        String query = "INSERT INTO patikas (name) VALUES (?)";

        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(query);
            pr.setString(1,name);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static boolean update(int id, String name){
        String query = "UPDATE patikas SET name =? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }
    public static Patika getFetch(int id) {
        Patika obj = null;
        String Query = "SELECT * FROM patikas WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Patika(rs.getInt("id"),rs.getString("name"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM patikas WHERE id = ?";
        ArrayList<Course> courseList = new ArrayList<>();
        for(Course obj : courseList){
            if(obj.getPatika().getId() == id){
                Course.delete(obj.getId());
            }
        }
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
