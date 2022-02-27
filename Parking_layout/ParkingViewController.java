/**

 * Sample Skeleton for 'ParkingView.fxml' Controller Class
 */

package Parking_layout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ParkingViewController {

	 @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="comboFloor"
	    private ComboBox<String> comboFloor; // Value injected by FXMLLoader

	    @FXML // fx:id="RB2W"
	    private RadioButton RB2W; // Value injected by FXMLLoader

	    @FXML // fx:id="typew"
	    private ToggleGroup typew; // Value injected by FXMLLoader

	    @FXML // fx:id="RB4W"
	    private RadioButton RB4W; // Value injected by FXMLLoader

	    @FXML // fx:id="txtCapacity"
	    private TextField txtCapacity; // Value injected by FXMLLoader
	    
	    @FXML
	    private Label lbl;
	    
	    
	   Connection con;
	   PreparedStatement pst;
	 
	   
    @FXML
    void doClose(ActionEvent event) 
    {
    	
    	comboFloor.setValue("select");
    	lbl.setText("");
    	txtCapacity.setText("");
    	if(RB2W.isSelected()==true)
    	{
    		RB2W.setSelected(false);
    	}
    	else
    	{
    		RB4W.setSelected(false);
    	}
    }

    @FXML
    void doSave(ActionEvent event) throws Exception 
    {
    	save();
    }
    void save() throws Exception
    {
    	String rb=null;
    	String flr=comboFloor.getSelectionModel().getSelectedItem();
    	String cp=txtCapacity.getText();
    	if(RB2W.isSelected()==true)
    	{
    		System.out.println("2 Wheeler selected");
    		rb="2w";
    		
    	}
    	else
    	{
    		System.out.println("4 Wheeler selected");
    		rb="4W";
    	}
    	pst=con.prepareStatement("insert into playout values(?,?,?,0)");
    	pst.setString(1, flr);
    	pst.setString(2, cp);
    	pst.setObject(3,rb);
    	pst.executeUpdate();
    	lbl.setText("Saved");
    	System.out.println("Saved");
    }
    
    @FXML
    void doUpdate(ActionEvent event)
    {
     	String floor=comboFloor.getSelectionModel().getSelectedItem();
     	String cap=txtCapacity.getText();
		try {
		PreparedStatement pst=con.prepareStatement("update playout set Capacity=? where Floor=?");
		pst.setString(1,cap);
		pst.setString(2,floor);
		int count=pst.executeUpdate();//fire query in table
		System.out.println(count+" Records Updated");
		lbl.setText("Updated...");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

    }
    
    @FXML
    void doFetch(ActionEvent event) 
    {
    	String floor=comboFloor.getSelectionModel().getSelectedItem();
    	try 
    	{
			pst=con.prepareStatement("select * from playout where Floor=?");
			pst.setString(1,floor);
			
			ResultSet table=pst.executeQuery();
			boolean jasus=false;
			while(table.next())
			{
				jasus=true;
				String cap=table.getString("Capacity");
				String typ=table.getString("Type");
				if(typ.equals("2w"))
				{
					RB2W.setSelected(true);
				}
				else
				{
					RB4W.setSelected(true);
				}
				System.out.println(cap);
				txtCapacity.setText(cap);
			}
			
			if(jasus==false)
			{
				System.out.println("Not exist");
			}
			
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize()
    {
    	String[] floor={"1", "2", "3", "4"};
    	comboFloor.getItems().addAll(floor);
    	con=DBconnection.doconnect();
    }
}
