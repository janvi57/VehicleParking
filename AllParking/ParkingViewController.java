package AllParking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import connectdb.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import AllParking.BeanParking;

public class ParkingViewController
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboVehicle;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private TableView<BeanParking> tbl;
    
    @FXML
    private DatePicker dtpFrom;

    @FXML
    private DatePicker dtpTo;

    
    Connection con;
    PreparedStatement pst;
   
    @FXML
    void doClick(ActionEvent event)
    {
    	Status();
    }

    void Status()
    {

    	String sd=comboStatus.getSelectionModel().getSelectedItem();
    	try 
    	{
			pst=con.prepareStatement("select * from parking where Status=?");
			pst.setString(1, sd);
			fetchAll(pst);
			tbl.setItems(list2);
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    void fillComboStatus() 
    {
    	 String[] id={"0", "1"};
    	comboStatus.getItems().addAll(id);
    }
   
    @FXML
    void doSearch(ActionEvent event)
    {
    	try 
    	{
    		LocalDate fd=dtpFrom.getValue();
    		java.sql.Date db=	java.sql.Date.valueOf(fd);
    		LocalDate ls=dtpTo.getValue();
    		java.sql.Date lb=	java.sql.Date.valueOf(ls);
			pst=con.prepareStatement("select * from parking where Edate BETWEEN ? AND ? ");
			pst.setDate(1,db);
			pst.setDate(2,lb);
			fetchAll(pst);
			tbl.setItems(list2);
			
			
		} 
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}	
    }

    
    @FXML
    void doFind(ActionEvent event) 
    {
    	fetch();
    }
    void fetch()
    {
    	try 
    	{
    		
    		String c=comboVehicle.getSelectionModel().getSelectedItem();
			pst=con.prepareStatement("select * from parking where Vno=?",Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, c);
			fetchAll(pst);
			
			tbl.setItems(list2);
			
		} 
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}

    }
    
    ObservableList<BeanParking> list2;

	void fetchAll(PreparedStatement pst)
	{
		list2=FXCollections.observableArrayList();
		try
		{
		ResultSet table=pst.executeQuery();
		while(table.next())
			{
				int rid=table.getInt("RID");
				String ct=table.getString("Ctype");
				String vt=table.getString("Type");
				String f=table.getString("Floor");
				int s=table.getInt("Status");
				Date ed=table.getDate("Edate");
				Time et=table.getTime("Etime");
				String m = table.getString("Mobile_No");
				BeanParking bp=new BeanParking(rid,ct,vt,f,s,ed,et,m);
				list2.add(bp);

			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	  @SuppressWarnings("unchecked")
	void fillColumns()
	    {
	    	TableColumn<BeanParking, Integer> r=new TableColumn<BeanParking, Integer>("Customer ID");//Dikhava Title
	     	r.setCellValueFactory(new PropertyValueFactory<>("RID"));//bean field name, no link with table col name

	     	TableColumn<BeanParking, Float> ctype=new TableColumn<BeanParking, Float>("Customer Type");//Dikhava Title
	     	ctype.setCellValueFactory(new PropertyValueFactory<>("Ctype"));//bean field name, no link with table col name
	     	
	     	TableColumn<BeanParking, String> vt=new TableColumn<BeanParking, String>("Vehicle type");//Dikhava Title
	     	vt.setCellValueFactory(new PropertyValueFactory<>("vtype"));//bean field name, no link with table col name

	     	TableColumn<BeanParking, String> f=new TableColumn<BeanParking, String>("Floor");//Dikhava Title
	     	f.setCellValueFactory(new PropertyValueFactory<>("floor"));//bean field name, no link with table col name
	     	
	     	TableColumn<BeanParking, Integer> s=new TableColumn<BeanParking, Integer>("status of Vehicle");//Dikhava Title
	     	s.setCellValueFactory(new PropertyValueFactory<>("status"));//bean field name, no link with table col name
	     	
	     	TableColumn<BeanParking, String> ed=new TableColumn<BeanParking, String>("Entry Date");//Dikhava Title
	     	ed.setCellValueFactory(new PropertyValueFactory<>("edate"));//bean field name, no link with table col name
	     	
	     	TableColumn<BeanParking, String> et=new TableColumn<BeanParking, String>("Entry time");//Dikhava Title
	     	et.setCellValueFactory(new PropertyValueFactory<>("etime"));//bean field name, no link with table col name
	     	
	     	TableColumn<BeanParking, String> m=new TableColumn<BeanParking, String>("Mobile No.");//Dikhava Title
	     	m.setCellValueFactory(new PropertyValueFactory<>("mob"));//bean field name, no link with table col name
	     	
	     	tbl.getColumns().clear();
	     	tbl.getColumns().addAll(r,ctype,vt,f,s,ed,et,m);
	    }

	  
    void fillcombovehicle()
    {
    	ArrayList<String> list=new ArrayList<>();
    	try
    	{
			pst=con.prepareStatement("select Vno from parking");
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
				String v=table.getString("Vno");
				list.add(v);

			}
			comboVehicle.getItems().addAll(list);
			
			
		}
    	catch (SQLException e1)
    	{
			e1.printStackTrace();
		}
    	
    }
    
    @FXML
    void doExport(ActionEvent event)
    {
    	Writer writer=null;
    	FileChooser fl=new FileChooser();
    	fl.setTitle("Select Path:");
    	fl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
    	File file=fl.showSaveDialog(null);
    	String filepath=file.getAbsolutePath();
    	if(!(filepath.endsWith(".csv")||filepath.endsWith(".CSV")))
     	{
     		filepath=filepath+".csv";
     	}
     	 file = new File(filepath);
         try 
         {
			writer = new BufferedWriter(new FileWriter(file));
			String text="RID,ctype,vtype,floor,status,edate,etime,mob\n";
			writer.write(text);
			for(BeanParking p: list2)
			{
				text=p.getRID()+","+p.getCtype()+","+p.getVtype()+","+p.getFloor()+","+p.getStatus()+","+p.getEdate()+","+p.getEtime()+","+p.getMob()+"\n";
				writer.write(text);
			}
			
		 } 
         catch (IOException e)
         {
			e.printStackTrace();
		 }
         finally
         {
             try
             {
				writer.flush();
				writer.close();
			 } 
             catch (IOException e)
             {
				e.printStackTrace();
             }
         }
    }

	@FXML
    void initialize() 
    {
       con=DBconnection.doconnect();
       fillcombovehicle();
       fillColumns();
       fillComboStatus();
    }
}
