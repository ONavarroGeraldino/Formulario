/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static model.ConnectionPoolMySQL.EXCEPCIONES;
import utilities.ControllerGeneralModel;

/**
 *
 * @author Latitude-3500
 */
public class CountryDAO {
    
    public ArrayList<Country> selectCountries(){
        //para cargar las excepciones al momento de fallar la conexion con la base de datos
        EXCEPCIONES = new ArrayList<>();        
        //para conectar con la base de datos
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;
        
        Country country;
        ArrayList<Country> list = new ArrayList<>();
         
        try{
        
            connection = ConnectionPoolMySQL.getInstance().getConnection();
            
            if(connection != null){
                String sql = "SELECT id, country, city "
                + "FROM countries "
                + "ORDER by country ASC";
                
                pst = connection.prepareStatement(sql);
                
                rs = pst.executeQuery();
                //asignacion de atributos 
                while(rs.next()){
                    country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setCountry(rs.getString("country"));
                    country.setCity(rs.getString("city"));
                    list.add(country);
                
                }
            }else{
          
            EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+"Error al conectar con la base de datos");                
                
            }
        
        }catch(SQLException ex){
        
        EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());
        
        }finally{
        //para finalizar la conexion con la base de datos
            try{
                if(connection != null){
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);            
                }            
            }catch(SQLException ex){
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());
            }      
        
        }
        System.out.println(EXCEPCIONES);
        
        
        return list;
    
    }
}
