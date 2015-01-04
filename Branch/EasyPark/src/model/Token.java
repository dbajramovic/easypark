package model;

public class Token {
	public Token(){};
	private int _tokenID;
	private String _tokenBody;
	private String _date;
	private String _email;
	private int _sent;
	private int _typeOfPerson;
	
	
	public int get_tokenID() {
		return _tokenID;
	}
	public void set_tokenID(int _tokenID) {
		this._tokenID = _tokenID;
	}
	public String get_tokenBody() {
		return _tokenBody;
	}
	public void set_tokenBody(String _tokenBody) {
		this._tokenBody = _tokenBody;
	}
	public String get_date() {
		return _date;
	}
	public void set_date(String _date) {
		this._date = _date;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public int get_sent() {
		return _sent;
	}
	public void set_sent(int _sent) {
		this._sent = _sent;
	}
	public int get_typeOfPerson() {
		return _typeOfPerson;
	}
	public void set_typeOfPerson(int _typeOfPerson) {
		this._typeOfPerson = _typeOfPerson;
	}

}
