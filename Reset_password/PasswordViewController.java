/**
 * Sample Skeleton for 'PasswordView.fxml' Controller Class
 */

package Reset_password;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswordViewController
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="newpswd"
    private PasswordField newpswd; // Value injected by FXMLLoader

    @FXML // fx:id="confrmpaswd"
    private PasswordField confrmpaswd; // Value injected by FXMLLoader
    

    @FXML // fx:id="txtid"
    private TextField txtid; // Value injected by FXMLLoader

    @FXML // fx:id="lbl"
    private Label lbl; // Value injected by FXMLLoader

    PreparedStatement pst;
    Connection con;
    String np,cp,uid,pas;
    @FXML
    void doRest(ActionEvent event) 
    {
    	lbl.setText("");
    	try 
    	{
        		uid=txtid.getText();
        		
    			/*pst=con.prepareStatement("select * from signup where Userid=?");
    			pst.setString(1,uid);
    			ResultSet table=pst.executeQuery();
    			boolean jasus=false;
    			while(table.next())
    			{
    				jasus=true;
    				pas=table.getString("password");
    			}
    			*/
    			np=newpswd.getText();
    			cp=confrmpaswd.getText();
    			System.out.println(uid+" "+np+" "+cp);
    			if(np.equals(cp))
    			{
    				pst=con.prepareStatement("update signup set password=? where Userid=?");
    				pst.setString(1, cp);
    				pst.setString(2, uid);
    				pst.executeUpdate();
    				lbl.setText("password reset");
    			}
    			else
    			{
    				lbl.setText("");
    				lbl.setText("password does not match");
    			}	
    			/*if(jasus==false)
    			{
    				System.out.println("Invalid numbet");
    			}*/
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    	DBconnection.doconnect();
    }
}
