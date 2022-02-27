package AllParking;

import java.sql.Date;
import java.sql.Time;

public class BeanParking
{

	int RID;
	String ctype;
	String vtype;
	String floor;
	int status;
	Date edate;
	Time etime;
	String mob;
	public BeanParking(){}
	public BeanParking(int rID, String ctype, String vtype, String floor, int status, Date edate,
			Time etime, String mob) 
	{
		super();
		RID = rID;
		this.ctype = ctype;
		this.vtype = vtype;
		this.floor = floor;
		this.status = status;
		this.edate = edate;
		this.etime = etime;
		this.mob = mob;
	}
	public int getRID() {
		return RID;
	}
	public void setRID(int rID) {
		RID = rID;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getVtype() {
		return vtype;
	}
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public Time getEtime() {
		return etime;
	}
	public void setEtime(Time etime) {
		this.etime = etime;
	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
}
