/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



/**
 *
 * @author Latitude-3500
 */
public class SigninController implements Initializable {
    
    @FXML
    private Button btnSignIn,btnSignUp;
    
    @FXML
    private StackPane containerForm;
    
    @FXML
    private VBox panelFormSignIn,panelFormSignUp;
    
    
    //evento para seleccion de panel
    public void actionEvent(ActionEvent e){
    
        Object evt = e.getSource();
        
        if(evt.equals(btnSignIn)){
            panelFormSignIn.setVisible(true);
            panelFormSignUp.setVisible(false);
        }else if(evt.equals(btnSignUp)){
            panelFormSignIn.setVisible(false);
            panelFormSignUp.setVisible(true);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ubicar los archivos y sirve para mostrar los elementos
        try {
            panelFormSignIn = loadForm("/main/SigIn/SignInForm.fxml");
            panelFormSignUp = loadForm("/main/SigUp/SignUpForm.fxml");
            containerForm.getChildren().addAll(panelFormSignIn,panelFormSignUp);
            //muestra un panel en concreto
            panelFormSignIn.setVisible(true);
            panelFormSignUp.setVisible(false);

        } catch (IOException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    //metodo para generar los formularios que se quiere mostrar
    private VBox loadForm(String url) throws IOException{
    
        return (VBox) FXMLLoader.load(getClass().getResource(url));
    
    }
    
}
