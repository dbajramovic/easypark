package model;

public class Device {
public Device(){}

public String name;
public int registratedby;
public String username;
public String password;
public String serialNumber;
public int ordinalNumber;
public String type;
public String regDate;
public String regTime;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getRegistratedby() {
	return registratedby;
}
public void setRegistratedby(int registratedby) {
	this.registratedby = registratedby;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getSerialNumber() {
	return serialNumber;
}
public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
}
public int getOrdinalNumber() {
	return ordinalNumber;
}
public void setOrdinalNumber(int ordinalNumber) {
	this.ordinalNumber = ordinalNumber;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getRegDate() {
	return regDate;
}
public void setRegDate(String regDate) {
	this.regDate = regDate;
}
public String getRegTime() {
	return regTime;
}
public void setRegTime(String regTime) {
	this.regTime = regTime;
}


}
