package AllCustomers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import AllCustomers.BeanCustomers;
import connectdb.DBconnection;

public class CustomerViewController
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<BeanCustomers> tbl;
    

    @FXML
    private ComboBox<String> comboMobile;
    
    PreparedStatement pst;
    Connection con;

    @FXML
    void doFetchAll(ActionEvent event)
    {
    	try{
    		pst=con.prepareStatement("select * from Customers");
    		fetch(pst);
    		tbl.setItems(list);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	void getcol()
    {
    	TableColumn<BeanCustomers, String> Mobile_No=new TableColumn<BeanCustomers, String>("Mobile No.");//Dikhava Title
    	Mobile_No.setCellValueFactory(new PropertyValueFactory<>("Mobile_No"));//bean field name, no link with table col name

    	TableColumn<BeanCustomers, String> Name=new TableColumn<BeanCustomers, String>("Name");//Dikhava Title
    	Name.setCellValueFactory(new PropertyValueFactory<>("Name"));//bean field name, no link with table col name

    	TableColumn<BeanCustomers, String> Address=new TableColumn<BeanCustomers, String>("Address");//Dikhava Title
    	Address.setCellValueFactory(new PropertyValueFactory<>("Address"));//bean field name, no link with table col name

    	TableColumn<BeanCustomers, String> City=new TableColumn<BeanCustomers, String>("City");//Dikhava Title
    	City.setCellValueFactory(new PropertyValueFactory<>("City"));//bean field name, no link with table col name
    	tbl.getColumns().clear();
    	tbl.getColumns().addAll(Mobile_No,Name,Address,City);
    	//tbl.setItems(list);
    }
    
    ObservableList<BeanCustomers> list;
    
    void fetch(PreparedStatement pst)
    {
    		list=FXCollections.observableArrayList();
    		try
    		{
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
				String m=table.getString("Mobile_No");
				String n=table.getString("name");
				String a=table.getString("Address");
				String c=table.getString("City");
				
				//System.out.println(m+"  "+n+"  "+a+"   "+c);
				BeanCustomers sb=new BeanCustomers(m,n,a,c);
				list.add(sb);	
			}
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			
		}
    }

  //  ObservableList<BeanCustomers> list3;
    @FXML
    void doSearch(ActionEvent event) throws SQLException 
    {
    	list= FXCollections.observableArrayList();
    	String m=comboMobile.getSelectionModel().getSelectedItem();
    	
    	pst=con.prepareStatement("select * from customers where Mobile_No=?");
    	pst.setString(1, m);
    	fetch(pst);
    	tbl.setItems(list);
    	
    }
    void dofill() throws SQLException
    {
    	ArrayList<String> list=new ArrayList<>();
    	
    	pst=con.prepareStatement("select Mobile_No from customers");
    	ResultSet table=pst.executeQuery();
    	
    	
    	while(table.next())
    	{
    		String mob=table.getString("Mobile_No");
    		list.add(mob);
    	}
    	comboMobile.getItems().addAll(list);
    }


    @FXML
    void doExport(ActionEvent event)
    {
    	 Writer writer = null;
         try {
         	FileChooser chooser=new FileChooser();
         	chooser.setTitle("Select Path:");
         	chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
         	 File file=chooser.showSaveDialog(null);
         	String filePath=file.getAbsolutePath();
         	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
         	{
         		filePath=filePath+".csv";
         	}
         	 file = new File(filePath);
             writer = new BufferedWriter(new FileWriter(file));
             String text="Mobile No.,Name,Address,City\n";
             writer.write(text);
             for (BeanCustomers p : list)
             {
 				text = p.getMobile_No()+ "," + p.getName()+ "," + p.getAddress()+ "," + p.getCity()+"\n";
                 writer.write(text);
             }
         }
         catch (Exception ex) 
         {
             ex.printStackTrace();
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
    void initialize() throws SQLException
    {
    	con=DBconnection.doconnect();
    	dofill();
    	getcol();
    }
}
