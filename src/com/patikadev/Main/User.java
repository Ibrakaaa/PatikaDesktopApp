package com.patikadev.Main;

import Helper.DBConnecctor;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String pass;
    private String type;

    public User() {
    }

    public User(int id, String name, String username, String pass, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.pass = pass;
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userlist = new ArrayList<>();
        String query = "SELECT * FROM users";
        User obj;
        try {
            Statement st = DBConnecctor.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userlist.add(obj);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userlist;
    }

    public static boolean add(String name, String username, String pass, String type) {
        String query = "INSERT INTO users(name,username,pass,type) VALUES(?,?,?,? )";
        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("Bu kullanıcı adı daha önceden alınmış! Lütfen yeni bir kullanıcı adı giriniz.");
            return false;
        }

        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, pass);
            pr.setString(4, type);

            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static User getFetch(String username) {
        User obj = null;
        String Query = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id) {
        String Query = "DELETE FROM users WHERE id = ?";
        ArrayList<Course> courseList= Course.getListByUser(id);
        for(Course c: courseList){
            Course.delete(c.getId());

        }
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static boolean upDate(int id,String name,String username,String pass,String type){
        String Query = "UPDATE users SET name =?,username=?,pass=?,type=? WHERE id =?";
        User findUser = User.getFetch(username);
        if (findUser != null&& findUser.getId() != id) {
            Helper.showMsg("Bu kullanıcı adı daha önceden alınmış! Lütfen yeni bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static ArrayList<User> searchUserList(String Query) {
        ArrayList<User> userlist = new ArrayList<>();
        String query = "SELECT * FROM users";
        User obj;
        try {
            Statement st = DBConnecctor.getInstance().createStatement();
            ResultSet rs = st.executeQuery(Query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userlist.add(obj);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userlist;
    }
    public static String searchQuery(String name,String username,String type){
        String Query = "SELECT * FROM users WHERE name LIKE '%{{name}}%' AND username LIKE '%{{username}}%' ";
        Query = Query.replace("{{name}}",name);
        Query = Query.replace("{{username}}",username);



        if(!type.isEmpty()){
            Query += "AND type = '{{type}}'";
            Query = Query.replace("{{type}}",type);

        }
        return Query;

    }
    public static User getFetch(int id) {
        User obj = null;
        String Query = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
    public static User getFetch(String username,String pass) {
        User obj = null;
        String Query = "SELECT * FROM users WHERE username = ? AND pass =?";
        try {
            PreparedStatement pr = DBConnecctor.getInstance().prepareStatement(Query);
            pr.setString(1, username);
            pr.setString(2,pass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")){
                    case "Operator":
                        obj = new Operator();
                        break;
                    default:
                        obj = new User();
                }

                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
}
