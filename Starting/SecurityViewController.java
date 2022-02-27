package Starting;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SecurityViewController
{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password;

    @FXML
    private TextField txtId;

    @FXML
    private Label lbl;
    
    @FXML
    private Label resetpswd;
    
    @FXML
    private Label paslbl;
    
    @FXML
    private Label lbl1;

    Connection con;
    PreparedStatement pst;
    String id;
    String pas;
    String a;
    String b;
    @FXML
    void doLogin(ActionEvent event)
    {
    	login();
			if(a.equals(id))
			{
				if(b.equals(pas))
				{
					  try
					    {
							Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dash_Board/dashView.fxml")); 
							Scene scene = new Scene(root,714,547);
							Stage primaryStage=new Stage();
							primaryStage.setScene(scene);
							primaryStage.show();
					    } 
					  catch(Exception e)
						{
							e.printStackTrace();
						}
					   
					   Scene scene1=(Scene)txtId.getScene();
					   scene1.getWindow().hide();
				}
				else
				{
					lbl.setText("Invalid Password");
				}
			}
			else
			{
				lbl.setText("Invalid User Id or Password");
			}
    }

    void login()
    {
    	a=txtId.getText();
		b=password.getText();
    	try 
    	{
			pst=con.prepareStatement("select * from signup");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				id=table.getString("Userid");
				pas=table.getString("password");
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
   
    @FXML
    void doForget(MouseEvent event) 
    {
    	if(event.getClickCount()==1)
    	{
    		try {
    			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Reset_password/PasswordView.fxml")); 
    			Scene scene = new Scene(root,400,500);
    			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			Stage primaryStage=new Stage();
    			primaryStage.setScene(scene);
    			primaryStage.show();
    		} 
    		catch(Exception e) 
    		{
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void click(MouseEvent event)
    {
    	if(event.getClickCount()==1)
    	{
    		paslbl.setVisible(true);
    	}
    }
    
    @FXML
    void click2(MouseEvent event)
    {
    	if(event.getClickCount()==1)
    	{
    		try
    		{
    			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Change_Password/ChangeView.fxml")); 
    			Scene scene = new Scene(root,369,350);
    			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			Stage primaryStage=new Stage();
    			primaryStage.setScene(scene);
    			primaryStage.show();
    			paslbl.setVisible(false);
    			
    		}
    		catch(Exception e) 
    		{
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void abc(MouseEvent event) 
    {
    	if(event.getClickCount()==1)
    	{
    		paslbl.setVisible(false);
    	}
    }
    
    @FXML
    void initialize()
    {
    	con=DBconnection.doconnect();
    }
}
