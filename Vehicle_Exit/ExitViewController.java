/**
 * Sample Skeleton for 'ExitView.fxml' Controller Class
 */

package Vehicle_Exit;

import java.net.URL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import connectdb.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ExitViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtVno"
    private TextField txtVno; // Value injected by FXMLLoader

    @FXML // fx:id="txtType"
    private TextField txtType; // Value injected by FXMLLoader

    @FXML // fx:id="txtFloor"
    private TextField txtFloor; // Value injected by FXMLLoader

    @FXML // fx:id="txtMobile"
    private TextField txtMobile; // Value injected by FXMLLoader

    @FXML // fx:id="txtDate"
    private TextField txtDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtTime"
    private TextField txtTime; // Value injected by FXMLLoader

    @FXML // fx:id="txtAmount"
    private TextField txtAmount; // Value injected by FXMLLoader
    
    @FXML
    private TextField txtEdate;

    @FXML
    private TextField txtEtime;
    
    @FXML
    private TextField ctype;

    @FXML
    private Label lbl;
    
    Connection con;
    PreparedStatement pst;
    String edate;
    String etime;
    LocalDate cd;
    LocalTime ct;
    String type;
    String c;
    String v;
    int occ;
    float bi;
   
    @FXML
    void doBill(ActionEvent event)
    {
    	String dateStart=edate+" "+etime;
    	
    	String dateStop=cd+" "+ct;
    	 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 Date d1=null;
    	 Date d2=null;
    	 try
    	 {
    	 d1=format.parse(dateStart);
    	 d2=format.parse(dateStop);  
    	 long diff=d2.getTime()-d1.getTime();
    	 long diffminutes=diff/(60*1000)%60;
    	 long diffhours=diff/(60*60*1000)%24;
    	 long diffDays=diff/(24*60*60*1000);
    
    	 System.out.println("minutes "+ diffminutes);
    	 System.out.println("hours "+ diffhours);
    	 System.out.println("days "+ diffDays);
    	 
    	 if(type.equals("2w"))
    	 {
    		float bill=100*diffDays+5*diffhours;
    		 if(c.equals("Permanent"))
    		 {
    			 float dis=bill*(0.1f);
    			 float b=bill-dis;
    			 txtAmount.setText("Rs."+String.valueOf(b));
    			 bi=b;
    		 }
    		 else
    		 {
    			 txtAmount.setText("Rs."+String.valueOf(bill));
    			 bi=bill;
    		 }
    	 }
    	 else if(type.equals("4W"))
    	 {
    		 float bill=200*diffDays+10*diffhours;
    		 if(c.equals("Permanent"))
    		 {
    			 float dis=bill*(0.1f);
    			 float b=bill-dis;
    			 txtAmount.setText(" Rs."+String.valueOf(b));
    			 bi=b;
    		 }
    		 else
    		 {
    			 txtAmount.setText("Rs."+String.valueOf(bill));
    			 bi=bill;
    		 }
    	 }
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 
     	System.out.println("******");
     	String resp=sms.SST_SMS.bceSunSoftSend(txtMobile.getText(),txtAmount.getText() );
     	if(resp.contains("successfully"))
 			System.out.println("Sent...");
     	else
 		if(resp.contains("Unknown"))
 			System.out.println("Check Internet connection");
 		else
 			System.out.println("Invalid Mobile Number");

    	 
    }

	@FXML
    void doFetch(ActionEvent event) 
    {
    	 v=txtVno.getText();
    	try
    	{
			pst=con.prepareStatement("select * from parking where Vno = ?");
			pst.setString(1, v);
			ResultSet table=pst.executeQuery();
			boolean jasus=false;
			while(table.next())
			{
				jasus=true;
				String m=table.getString("Mobile_No");
				c=table.getString("Ctype");
				type=table.getString("Type");
				String fl=table.getString("Floor");
				String stat=table.getString("Status");
				edate=table.getString("Edate");
				etime=table.getString("Etime");
				if(stat.equals("0"))
				{
					Alert confirm =new Alert(AlertType.CONFIRMATION);
					confirm.setTitle("Detail");
					confirm.setContentText("Vehicle Already Exit");
					Optional<ButtonType> res=confirm.showAndWait();
					if(res.get()==ButtonType.OK)
					{
						txtVno.setText("");
					}
				}
				else
				{
				
				txtMobile.setText(m);
				ctype.setText(c);
				txtFloor.setText(fl);
				txtType.setText(type);	
				txtEdate.setText(edate);
				txtEtime.setText(etime);
				
				cd=(java.time.LocalDate.now());  
		    	  txtDate.setText(cd+"");
		    	     	  
		    	 ct=(java.time.LocalTime.now());  
		    	    txtTime.setText(ct+"");
				}
			}
			if(jasus==false)
			{
				System.out.println("Invalid number");
			}
				
			
		} 
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    	
    }

    @FXML
    void dpUpdate(ActionEvent event)
    {
    	
    	try 
    	{
    		String f=txtFloor.getText();
    		pst=con.prepareStatement("select Occupied from playout where Floor=?");
    		pst.setString(1, f);
    		ResultSet table=pst.executeQuery();
    		while(table.next())
    		{
    			occ=table.getInt("Occupied");
    		}
    		if(occ!=0)
    		{
    			pst=con.prepareStatement("update playout set Occupied=Occupied-1 where Floor=?");
    			pst.setString(1, f);
    			pst.executeUpdate();
    			pst=con.prepareStatement("update parking set Status=0 , Bill=? where Vno=?");
    			pst.setFloat(1,bi);
    			pst.setString(2,v);
    			pst.executeUpdate();
    			lbl.setText("Updated");
    		}
    		txtVno.setText("");
    		txtMobile.setText("");
			ctype.setText("");
			txtFloor.setText("");
			txtType.setText("");	
			txtEdate.setText("");
			txtEtime.setText(""); 
	    	txtDate.setText("");
	    	txtTime.setText("");
	    	txtAmount.setText("");
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
