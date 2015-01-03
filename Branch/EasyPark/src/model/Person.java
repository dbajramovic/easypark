package model;

public class Person {
	public Person(){};
	private int _personID;
	private String _firstname;
	private String _lastname;
	private String _address;
	private String _phonenumber;
	private String _password;
	private String _email;
	private int _type;
	private String _accountNumber;
	private String _companyName;
	private String _token;
	
	
	public String get_token() {
		return _token;
	}
	public void set_token(String _token) {
		this._token = _token;
	}
	public String get_companyName() {
		return _companyName;
	}
	public void set_companyName(String _companyName) {
		this._companyName = _companyName;
	}
	public String get_accountNumber() {
		return _accountNumber;
	}
	public void set_accountNumber(String _accountNumber) {
		this._accountNumber = _accountNumber;
	}
	public int get_type() {
		return _type;
	}
	public void set_type(int _type) {
		this._type = _type;
	}
	public int get_personID() {
		return _personID;
	}
	public void set_personID(int _personID) {
		this._personID = _personID;
	}
	public String get_firstname() {
		return _firstname;
	}
	public void set_firstname(String _firstname) {
		this._firstname = _firstname;
	}
	public String get_lastname() {
		return _lastname;
	}
	public void set_lastname(String _lastname) {
		this._lastname = _lastname;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _city) {
		this._address = _city;
	}
	public String get_phonenumber() {
		return _phonenumber;
	}
	public void set_phonenumber(String _u) {
		this._phonenumber = _u;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	@Override
	public String toString() {
		return "Person [_personID=" + _personID + ", _firstname=" + _firstname
				+ ", _lastname=" + _lastname + ", _address=" + _address
				+ ", _phonenumber=" + _phonenumber + ", _password=" + _password
				+ ", _email=" + _email + "]";
	}

}
