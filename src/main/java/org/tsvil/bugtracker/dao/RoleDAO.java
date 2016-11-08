package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.Role;

public class RoleDAO extends GenericDAO {

    public Role findRoleById(long roleId) {
        Role role = new Role();

        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select role_id, role_name from `" + AppConfig.getDbName() + "`.role "
                    + "where role_id=" + roleId + ";");
            while (rs.next()) {
                long id = rs.getLong("role_id");
                String name = rs.getString("role_name");
                role = new Role(id, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }

        return role;
    }

}
