package AllCustomers;

public class BeanCustomers 
{
	String Mobile_No;
	String Name;
	String Address;
	String City;
	
	public BeanCustomers(){}
	public BeanCustomers(String mobile_No, String name, String address, String city) {
		super();
		Mobile_No = mobile_No;
		Name = name;
		Address = address;
		City = city;
	}
	public String getMobile_No() {
		return Mobile_No;
	}
	public void setMobile_No(String mobile_No) {
		Mobile_No = mobile_No;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	

}
