/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static model.ConnectionPoolMySQL.EXCEPCIONES;
import utilities.ControllerGeneralModel;

/**
 *
 * @author JorgeLPR
 */
public class AccountsDAO {
     //metodo para insertar datos   
    public boolean insertAccount(Account account){

        EXCEPCIONES = new ArrayList<>();
        boolean state = false;
        
        Connection connection = null;
        PreparedStatement pst;
        
        try{
            
            connection = ConnectionPoolMySQL.getInstance().getConnection();
            
            if(connection!=null){
                
                String sql = "INSERT INTO accounts (email, password, user, id_country)  "
                        + "VALUES (?, AES_ENCRYPT(?, 'key'), ?, ?)";
                
                pst = connection.prepareStatement(sql);
                pst.setString(1, account.getEmail());
                pst.setString(2, account.getPassword());
                pst.setString(3, account.getUser());
                pst.setInt(4, account.getCountry().getId());                
                
                state = pst.executeUpdate() > 0;
                
            }else{                
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+"Error al conectar con la base de datos");
            }
            
        }catch(HeadlessException | SQLException ex){
            EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());            
        }finally{
            
            try{
                if(connection != null){
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);            
                }            
            }catch(SQLException ex){
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());
            }

        }
        
        
        return state;
    }    
    
    //metodo para consultar los elementos insertados en la base de datos
    public Account selectAccount(String value, String filter){
        
        EXCEPCIONES = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;        
        
        Account account=null;

        try{
            
            connection = ConnectionPoolMySQL.getInstance().getConnection();
            
            if(connection!=null){
                
                String sql = "SELECT accounts.id AS idAccounts, accounts.email, accounts.user, CAST(AES_DECRYPT(accounts.password, 'key') AS CHAR(50)) AS password, "
                        + " countries.id, countries.country, countries.city  "
                        + " FROM accounts "
                        + " INNER JOIN countries ON (accounts.id_country=countries.id) ";
                
                switch(filter){
                
                    case "email":
                        sql = sql + " WHERE email=?";
                    break;
                    
                    default:
                        sql = sql + " WHERE BINARY user=?";                        
                    break;
                
                }
                
                pst = connection.prepareStatement(sql);
                pst.setString(1, value);
                
                rs = pst.executeQuery();
                
                if(rs.next()){
                    
                    account = new Account();
                    
                    account.setId(rs.getInt("idAccounts"));
                    account.setEmail(rs.getString("email"));
                    account.setUser(rs.getString("user"));
                    account.setPassword(rs.getString("password"));
                    
                    Country country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setCountry(rs.getString("country"));
                    country.setCity(rs.getString("city"));

                    account.setCountry(country);
                    
                }
                
            }else{                
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+"Error al conectar con la base de datos");
            }
            
        }catch(HeadlessException | SQLException ex){
            EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());            
        }finally{
            
            try{
                if(connection != null){
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);            
                }            
            }catch(SQLException ex){
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());
            }

        }
        
        return account;
        
    }    
}
