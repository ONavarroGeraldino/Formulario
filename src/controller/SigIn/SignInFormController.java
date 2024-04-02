/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.SigIn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import model.Account;
import model.AccountsDAO;
import static model.ConnectionPoolMySQL.EXCEPCIONES;
import utilities.ControllerGeneralModel;

/**
 * FXML Controller class
 *
 * @author Latitude-3500
 */
public class SignInFormController implements Initializable {
    @FXML
    private TextField txtUserSignIn,txtPasswordSignInMask;
    
    
    @FXML 
    private PasswordField txtPasswordSignIn;
    
    
    @FXML
    private CheckBox checkViewPassSignIn;
    
    @FXML 
    private Button btnSignIn,btnClean;
    
    @FXML
    private VBox panelFormSignIn;
    
    private Account account;
    private AccountsDAO modelUser = new AccountsDAO();
    
    public void cleanFields(){
        txtPasswordSignIn.setText("");
        txtPasswordSignInMask.setText("");
        txtUserSignIn.setText("");        
    }
    
    
    @FXML
    public void actionEvent(ActionEvent e){
        
        Object evt = e.getSource();
        
        if(btnSignIn.equals(evt)){                    
                         
            if(!txtUserSignIn.getText().isEmpty() && !txtPasswordSignIn.getText().isEmpty()){
               //sirve para poder identificar si el correo y el usuario pertenecen a un campo     
                String filter;
                
                if(ControllerGeneralModel.validateEmail(txtUserSignIn.getText())){
                    filter = "email";
                }else{
                    filter = "user";                
                }
                //////////
                account = modelUser.selectAccount(txtUserSignIn.getText(), filter);
                
                if(account != null){

                    if(account.getPassword().equals(txtPasswordSignIn.getText())){
                        JOptionPane.showMessageDialog(null, "Bienvenido", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);                    
                        cleanFields();
                    }else{
                        JOptionPane.showMessageDialog(null, "La Contraseña que ha ingresado no es la correcta", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);                                            
                    }

                }else{

                    if(EXCEPCIONES.size()>0){
                        JOptionPane.showMessageDialog(null, "Surgieron errores en el proceso de consulta, posibles errores:\n"+
                                                      ControllerGeneralModel.toString(EXCEPCIONES), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "El Usuario no existe en la Base de Datos", "SIN RESULTADOS", JOptionPane.ERROR_MESSAGE);
                    }

                }
            
            }else{
                JOptionPane.showMessageDialog(null, "Debe llenar los campos obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
                        
        }else if(btnClean.equals(evt)){        
            cleanFields();
        }
    
    }
    @FXML
    public void eventKey(KeyEvent e){
        String c = e.getCharacter();
        
        if(c.equalsIgnoreCase(" ")){
          e.consume();
        }
        
    }
    
    
    
    
    
    
    /**
     * 
     * 
     * 
     * 
     * 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        maskPassword(txtPasswordSignIn,txtPasswordSignInMask,checkViewPassSignIn);
    }    
    
    
    public void maskPassword(PasswordField pass, TextField text, CheckBox check){
    
        text.setVisible(false);
        text.setManaged(false);
        
        //se usa en metodo bind para genera una funcion que haga que al momento de seleccionar el check, se oculte el PasswordField
        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());
        //con esta funcion podemos hacer que dos campos se relacionen
        text.textProperty().bindBidirectional(pass.textProperty());
    }
}
