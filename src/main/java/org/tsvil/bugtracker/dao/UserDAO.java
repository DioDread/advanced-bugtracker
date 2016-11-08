package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.User;

public class UserDAO extends GenericDAO {
    
    private RoleDAO roleDAO;
    
        public User findUserById(long userId) throws SQLException {
        User user = new User();
        roleDAO = new RoleDAO();
        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select username, email, password, create_time, user_id, role_id from `"
                    + AppConfig.getDbName() + "`.user where user_id=" + userId + ";");
            while (rs.next()) {
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getDate("create_time"));
                user.setRole(roleDAO.findRoleById(rs.getLong("role_id")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        return user;
    }

    public User findUserByUsername(String username) throws SQLException {
        User user = new User();
        roleDAO = new RoleDAO();
        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select username, email, password, create_time, user_id, role_id from `"
                    + AppConfig.getDbName() + "`.user where username='" + username + "'");
            while (rs.next()) {
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getDate("create_time"));
                user.setRole(roleDAO.findRoleById(rs.getLong("role_id")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        return user;
    }
    
    public ArrayList<User> getAllDevelopers() {
        ArrayList<User> developers = new ArrayList<>();
        roleDAO = new RoleDAO();
        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select username, email, password, create_time, user_id, role_id from `"
                    + AppConfig.getDbName() + "`.user where role_id=2");
            while (rs.next()) {
                User dev = new User();
                dev.setUserId(rs.getLong("user_id"));
                dev.setUsername(rs.getString("username"));
                dev.setEmail(rs.getString("email"));
                dev.setPassword(rs.getString("password"));
                dev.setCreateTime(rs.getDate("create_time"));
                dev.setRole(roleDAO.findRoleById(rs.getLong("role_id")));
                developers.add(dev);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        return developers;
    }

}
