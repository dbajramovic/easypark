package Models;



public class Temp {

	private int idDevice=0;
	private int idTemp=0;
	private int UserId=0;
	private String Name="";
	private int OrdinalNumber=0;
	private float Lat=0;
	private float Long=0;
	private String Time="";
	private String Date="";
	private String Comment="";
	private double Speed=0;
	private String Message="";

	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getIdTemp() {
		return idTemp;
	}
	public void setIdTemp(int idTemp) {
		this.idTemp = idTemp;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getOrdinalNumber() {
		return OrdinalNumber;
	}
	public void setOrdinalNumber(int ordinalNumber) {
		OrdinalNumber = ordinalNumber;
	}
	public float getLat() {
		return Lat;
	}
	public void setLat(float lat) {
		Lat = lat;
	}
	public float getLong() {
		return Long;
	}
	public void setLong(float l) {
		Long = l;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public double getSpeed() {
		return Speed;
	}
	public void setSpeed(double speed) {
		Speed = speed;
	}
	public int getIdDevice() {
		return idDevice;
	}
	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

	public Temp(){
	}
   /*
	@Override
	    public String toString() {
	        return new StringBuffer("\"idTemp\":").append(this.idTemp).append(",")
	                .append("\"DeviceId\":").append(this.idDevice).append(",")
	                .append("\"UserId\":").append(this.UserId).append(",")
	                .append("\"Name\":\"").append(this.Name).append("\",")
	                .append("\"OrdinalNumber\":").append(this.OrdinalNumber).append(",")
	                .append("\"Lat\":").append(this.Lat).append(",")
	                .append("\"Long\":").append(this.Long).append(",")
	                .append("\"Time\":\"").append(this.Time).append("\",")
	                .append("\"Date\":\"").append(this.Date).append("\",")
	                .append("\"Comment\":\"").append(this.Comment).append("\",")
	                .append("\"Speed\":").append(this.Speed).append(",")
	                .append("\"Message:\"").append(this.Message).append("\"")
	                .toString();
	    }*/
}
