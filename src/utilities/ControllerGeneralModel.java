package utilities;

import java.util.ArrayList;

/**
 *
 * @author JorgeLPR
 */
public class ControllerGeneralModel {
    
    public static int enumSizeExcepcion(ArrayList<String>list){
        return list.size()+1;
    }
    
    public static String toString(ArrayList<String> datos){
        String text="";
        for (int i = 0; i < datos.size(); i++) {
            text=text+datos.get(i)+"\n";
        }
        return text;
    }    

    public static boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);                
    }
    
}
