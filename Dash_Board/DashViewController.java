package Dash_Board;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class DashViewController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    
    URL url;
   	Media media;
   	AudioClip audio;
   	
   	
    @FXML
    void CustomLog(MouseEvent event)
    {
    	playSound();
    			try {
    				Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("AllCustomers/CustomerView.fxml")); 
    				Scene scene = new Scene(root,500,600);
    				Stage primaryStage=new Stage();
    				primaryStage.setScene(scene);
    				primaryStage.show();
    			} 
    			catch(Exception e) 
    			{
    				e.printStackTrace();
    			}

    }

    @FXML
    void PLog(MouseEvent event) 
    {
    	playSound1();
		try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("AllParking/ParkingView.fxml")); 
			Scene scene = new Scene(root,955,608);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void Playout(MouseEvent event) 
    {
    	playSound1();
		try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Parking_layout/ParkingView.fxml")); 
			Scene scene = new Scene(root,500,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void VEntry(MouseEvent event)
    {
    	playSound1();
		try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Vehicle_Entry/EntryView.fxml")); 
			Scene scene = new Scene(root,500,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void VExit(MouseEvent event)
    {
    	playSound();
		try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Vehicle_Exit/ExitView.fxml")); 
			Scene scene = new Scene(root,500,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void customRegister(MouseEvent event) 
    {
    	playSound();
		try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Customer_Registration/RegistrationView.fxml")); 
			Scene scene = new Scene(root,500,625);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    void playSound(){
       	url=getClass().getResource("crash.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }

    void playSound1(){
       	url=getClass().getResource("abc.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }
    
    @FXML
    void initialize() 
    {
    	
    }
}
