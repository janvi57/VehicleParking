/**
 * Sample Skeleton for 'RegistrationView.fxml' Controller Class
 */

package Customer_Registration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class RegistrationViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtMobile"
    private TextField txtMobile; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtAddress"
    private TextField txtAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtCity"
    private TextField txtCity; // Value injected by FXMLLoader
    
    @FXML
    private ImageView imgpick;
    
    @FXML
    private Label lbl;
    
    @FXML
    private Button sav;
    
    @FXML
    private TextField vno;
    
    Connection con;
    PreparedStatement pst;
    String path;

    @FXML
    void doBrowse(ActionEvent event) 
    {
    	FileChooser fc=new FileChooser();
    	fc.setTitle("Select File");
    	File file=fc.showOpenDialog(null);
    	Image img=new Image(file.toURI().toString());
    	imgpick.setImage(img);
    	
    	path=file.getAbsolutePath();
    	System.out.println(path);
    }

    @FXML
    void doFetch(ActionEvent event) throws FileNotFoundException
    {
    	String mobile=txtMobile.getText();
    	sav.setDisable(true);
    	
    	try 
    	{
			pst=con.prepareStatement("select * from customers where Mobile_No=?");
			pst.setString(1,mobile);
			
			ResultSet table=pst.executeQuery();
			boolean jasus=false;
			while(table.next())
			{
				jasus=true;
				//String mobile=table.getString("Mobile_No");
				String name=table.getString("Name");
				String address=table.getString("Address");
				String city=table.getString("City");
				String vn=table.getString("Vehicle");
				path=table.getString("pic");
				
				System.out.println(mobile+"  "+name+"  "+address+"  "+city+"  "+path+"  "+vn);
				txtName.setText(name);
				txtAddress.setText(address);
				txtCity.setText(city);
				vno.setText(vn);
				FileInputStream fin=new FileInputStream(path);
				Image img=new Image(fin);
				
				imgpick.setImage(img);
				
			}
			
			if(jasus==false)
			{
				System.out.println("Inavalid Mobile Number");
				lbl.setText("Invalid Mobile Number");
				
			}
			
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doNew(ActionEvent event)
    {
    	txtMobile.setText("");
    	txtName.setText("");
    	txtAddress.setText("");
    	txtCity.setText("");
    	vno.setText("");
    	imgpick.setImage(null);
    	lbl.setText("");
    	sav.setDisable(false);
    }

    @FXML
    void doSave(ActionEvent event)
    {
    	save();
    }
    
    public boolean isvalid(String mob)
    {
    	Pattern p=Pattern.compile("(0/91)?[6-9][0-9]{9}");
    	Matcher m=p.matcher(mob);
    	return(m.find() && m.group().equals(mob));
    }
    
    public boolean valid(String vn)
    {
    	Pattern p = Pattern.compile("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}");
    	Matcher m=p.matcher(vn);
    	return (m.find() && m.group().equals(vn));
    }
    
    void save()
    {
    	String mob=txtMobile.getText();
    	String name=txtName.getText();
    	String add=txtAddress.getText();
    	String city=txtCity.getText();
    	String vn=vno.getText();
    	
    	try {
			pst=con.prepareStatement("Insert into customers values (?,?,?,?,?,?)");
			
			pst.setString(1, mob);
			pst.setString(2, name);
			pst.setString(3, add);
			pst.setString(4, city);
			pst.setString(5, path);
			pst.setString(6, vn);
			if(isvalid(mob))
			{
				if(valid(vn))
				{
					pst.executeUpdate();
					lbl.setText("");
			    	lbl.setText("Saved...");
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
				lbl.setText("Invalid Mobile no");
				System.out.print("***********");
			}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }

    @FXML
    void doUpdate(ActionEvent event)
    {
    	String name=txtName.getText();
    	String add=txtAddress.getText();
    	String city=txtCity.getText();
    	String mob=txtMobile.getText();
    	String vn=vno.getText();
		//Image img=imgpick.getImage();	
    	try
    	{
			pst=con.prepareStatement("update customers set pic=?, Name=?, Address=?, City=?, Vehicle=? where Mobile_No=?");
			pst.setString(1,path);
			pst.setString(2, name);
			pst.setString(3, add);
			pst.setString(4, city);
			pst.setString(5, vn);
			pst.setString(6,mob);
			int count=pst.executeUpdate();	
			lbl.setText("");
			lbl.setText("Updated...");
			System.out.println(count+" Records Updated");
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    	con=DBconnection.doconnect();
    }
}
