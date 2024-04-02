/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.SigUp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import model.Account;
import model.AccountsDAO;
import static model.ConnectionPoolMySQL.EXCEPCIONES;
import model.Country;
import model.CountryDAO;
import utilities.ControllerGeneralModel;

/**
 * FXML Controller class
 *
 * @author Latitude-3500
 */
public class SignUpFormController implements Initializable {
    @FXML
    private VBox checkViewPassSignIn;
    
    
    @FXML
    private TextField txtEmailSignUp,txtUsuarioSignUp,txtPassword,txtConfirmPassword;
    
    
    @FXML 
    private ComboBox<Country> cbCountry;
    
    
    @FXML
    private Button btnSignUp, btnCleanSignUp;
    
    private CountryDAO model;
    private ArrayList<Country> listCountries = new ArrayList<>();
    
    private AccountsDAO modelAccount = new AccountsDAO();
   
    //metodo para limpiar los campos 
    public void cleanFields(){
        txtEmailSignUp.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtUsuarioSignUp.setText("");
        if(cbCountry.getItems().size()>0){
            cbCountry.getSelectionModel().select(0);            
        }else{
            listCountries = model.selectCountries();
            fillCombobox(listCountries);        
        }                
    }
    @FXML 
    public void actionEvent(ActionEvent e){
    Object evt = e.getSource();
        
        if(btnSignUp.equals(evt)){
        
            if(!txtEmailSignUp.getText().isEmpty() && !txtUsuarioSignUp.getText().isEmpty() && 
               !txtConfirmPassword.getText().isEmpty() && !txtPassword.getText().isEmpty()){
                //validacion para que solo pueda ingresar el correo electronico en el campo de texto //
                if(ControllerGeneralModel.validateEmail(txtEmailSignUp.getText())){
                 ///////////////////////////////////////////////////////////////////////////////////   
                    if(txtUsuarioSignUp.getText().length()>=3){

                        Country country = cbCountry.getSelectionModel().getSelectedItem();

                        if(country!=null){

                            if(txtConfirmPassword.getText().equals(txtPassword.getText())){

                                Account account = new Account();
                                account.setEmail(txtEmailSignUp.getText());
                                account.setPassword(txtPassword.getText());
                                account.setUser(txtUsuarioSignUp.getText());
                                account.setCountry(country);

                                if(modelAccount.insertAccount(account)){

                                    JOptionPane.showMessageDialog(null, "El Usuario ha sido registrado de manera éxitosa", "OPERACIÓN ÉXITOSA", JOptionPane.INFORMATION_MESSAGE);
                                    //llamada del metodo limpiar
                                    cleanFields();

                                }else{

                                    if(EXCEPCIONES.size()>0){
                                        JOptionPane.showMessageDialog(null, "Surgieron errores en el proceso, posibles errores: \n"
                                                + ControllerGeneralModel.toString(EXCEPCIONES));
                                    }

                                }

                            }else{
                                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden, por favor verifique e intente nuevamente", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }

                        }else{
                            JOptionPane.showMessageDialog(null, "Surgieron errores al conectar con la Base de Datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "El Nombre de usuario debe contener al menos TRES caracteres", "ERROR", JOptionPane.ERROR_MESSAGE);                                                                                                       
                    }
                    
                }else{
                    
                    JOptionPane.showMessageDialog(null, "El Email que ha ingresado no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);                                    
                
                }
                                
                
            }else{
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }                        
                                
        }else if(btnCleanSignUp.equals(evt)){        
            cleanFields();        
        }
    
    
    }
    
    //evento para no permitir espacios en blanco
    @FXML
    public void keyEvent(KeyEvent e){
             String c = e.getCharacter();
        
                if(c.equalsIgnoreCase(" ")){
                     e.consume();
         }
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        model = new CountryDAO();
        listCountries = model.selectCountries();
        fillCombobox(listCountries);
    }    

//metodo usado para mostrar los paises en el combobox

    private void fillCombobox(ArrayList<Country> listCountries) {
        
        //este condicional se usa para verificar que no haya ningun error con el mostrar los elemtos que estan en la base de datos
        if(listCountries.size() > 0){
        cbCountry.getItems().addAll(listCountries);
        
        //metodo usado para mostrar los paises en el combobox por sus respectivos nombres
        cbCountry.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country object) {
                return object.getCountry();
            }

            @Override
            public Country fromString(String string) {
                return null;
            }
        });
        
        //para mostrar el primer elemento  de la tabla Country en el combobox
        cbCountry.getSelectionModel().select(0);
        
        }
        
        
        
        

    }

   
    
}
