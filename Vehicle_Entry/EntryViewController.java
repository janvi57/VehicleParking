package Vehicle_Entry;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class EntryViewController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton rdoFour;

    @FXML
    private ToggleGroup vtype;

    @FXML
    private RadioButton rdoTwo;

    @FXML
    private ListView<String> lstFloor;

    @FXML
    private ListView<Integer> lstCapacity;

    @FXML
    private RadioButton rdoPer;

    @FXML
    private RadioButton rdoTemp;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtVehicle;
    
    @FXML
    private ToggleGroup cp;
    
    @FXML
    private Label lbl;
    
    Connection con;
    PreparedStatement pst;
   
    @FXML
    void doClear(ActionEvent event)
    {
    	if(rdoPer.isSelected()==true)
    	{
    		rdoPer.setSelected(false);
    	}
    	else
    	{
    		rdoTemp.setSelected(false);
    	}
    	lstFloor.getItems().clear();
    	lstCapacity.getItems().clear();
    	if(rdoTwo.isSelected()==true)
    	{
    		rdoTwo.setSelected(false);
    	}
    	else
    	{
    		rdoFour.setSelected(false);
    	}
    	txtMobile.setText("");
    	txtVehicle.setText("");
    	lbl.setText("");
    }

    @FXML
    void doSave(ActionEvent event) 
    {
    	save();
    }

    public boolean isValid(String mob) 
    {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}"); 
        Matcher m = p.matcher(mob); 
        return (m.find() && m.group().equals(mob)); 
    }
    public boolean valid(String Vno)
    {
    	Pattern p = Pattern.compile("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}");
    	Matcher m=p.matcher(Vno);
    	return (m.find() && m.group().equals(Vno));
    }
    void save()
    {
    	String type;
    	if(rdoTwo.isSelected()==true)
    	{
    		type="2w";
    	}
    	else
    	{
    		type="4W";
    	}
    	
    	String fl=lstFloor.getSelectionModel().getSelectedItem();
    	
    	String mob=txtMobile.getText();
    	
    	
    	String ctype;
    	if(rdoPer.isSelected()==true)
    	{
    		ctype="Permanent";
    	}
    	else
    	{
    		ctype="temporary";
    	} 
    	String Vno=txtVehicle.getText();	
    	
    	try {
			pst=con.prepareStatement("insert into parking values(0,?,?,?,?,1,curdate(),curtime(),?,0.0)");
			pst.setString(1, ctype);
			pst.setString(2, Vno);
			pst.setString(3, type);
			pst.setString(4, fl);
			pst.setString(5, mob); 
			if((rdoPer.isSelected()==true) || (rdoTemp.isSelected()==true))
	    	{
	    		if(txtMobile.getText()!=null && txtVehicle.getText()!=null)
	    		{ 
	    			if (isValid(mob))
	    			{
	    				if(valid(Vno))
	    				{
	    					pst.executeUpdate();
	    					lbl.setText("");
	    					lbl.setText("Saved....");
	    					System.out.println("Saved");
	    					doUpdate(fl);
	    				}
	    				else
	    				{
	    					lbl.setText("");
	    					lbl.setText("Invalid vehicle No.");
	    				}
	    			}
	    			else
	    			{	
	    				lbl.setText("");
	    				lbl.setText("Invalid Mobile no.");
	    			}
	    		}
	    		else
	    		{
	    			lbl.setText("");
	    			lbl.setText("Fill all the columns");
	    		}
		}
		else
		{
			lbl.setText("");
			lbl.setText("select customer type");
		}
    }
				
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    
    void doUpdate(String Floor)
    {
    	try 
    	{
			pst=con.prepareStatement("update playout set Occupied=Occupied+1 where Floor=?");
			pst.setString(1, Floor);
			pst.executeUpdate();
			System.out.println("Update");
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	
    }
    
    

    @FXML
    void doFetch(ActionEvent event) 
    {
    	String mobile=txtMobile.getText();
    	try 
    	{
			pst=con.prepareStatement("select * from customers where Mobile_No=?");
			pst.setString(1,mobile);
			
			ResultSet table=pst.executeQuery();
			boolean jasus=false;
			while(table.next())
			{
				jasus=true;
				String vn=table.getString("Vehicle");
				txtVehicle.setText(vn);
				rdoPer.setSelected(true);
			}
			
			if(jasus==false)
			{
				if(isValid(mobile))
				{
					txtVehicle.setText("");
					rdoTemp.setSelected(true);
				}
				else
				{
					lbl.setText("Invalid Mobile No.");
				}
			}
			
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void select2W(ActionEvent event) 
    {
    	
    	String type="2w";
    		lstFloor.getItems().clear();
    		lstCapacity.getItems().clear();
    		
    		ArrayList<String>flrAry=new ArrayList<>();
    		ArrayList<Integer>flrAry2=new ArrayList<>();
    		try 
    		{
				pst=con.prepareStatement("select * from playout where Type=?");
				pst.setString(1, type);
				ResultSet table= pst.executeQuery();
				
				while(table.next())
				{
				String f=table.getString("Floor");
				int cap=table.getInt("Capacity");
				
				int oc=table.getInt("Occupied");
				flrAry.add(f);
				flrAry2.add(cap-oc);
				}
				lstFloor.getItems().addAll(flrAry);
				lstCapacity.getItems().addAll(flrAry2);
			} 
    		catch (SQLException e) 
    		{
    			
				e.printStackTrace();
    		}
    }

    @FXML
    void select4W(ActionEvent event) 
    {
    	lstFloor.getItems().clear();
    	lstCapacity.getItems().clear();
		try 
		{
			lstFloor.getItems().clear();
			lstCapacity.getItems().clear();
			String type="4W";
			
			ArrayList<String>flrAry=new ArrayList<>();
    		ArrayList<Integer>flrAry2=new ArrayList<>();
    		
			pst=con.prepareStatement("select * from playout where Type=?");
			pst.setString(1, type);
			ResultSet table= pst.executeQuery();
			
			while(table.next())
			{
			String f=table.getString("Floor");
			int cap=table.getInt("Capacity");
			int oc=table.getInt("Occupied");
			int a=(cap-oc);
			flrAry.add(f);
			flrAry2.add(a);
			}
			lstFloor.getItems().addAll(flrAry);
			lstCapacity.getItems().addAll(flrAry2);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void initialize() 
    {
       con=DBconnection.doconnect();
    }
}
