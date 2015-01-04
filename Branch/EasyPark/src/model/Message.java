package model;

public class Message {
	public Message(){};
	private int _messageID;
	private int _personID;
	private int _parkingID;
	private String _dateOdMes;
	private String _message;
	private int _isSolved;
	
	
	public int get_messageID() {
		return _messageID;
	}
	public void set_messageID(int _messageID) {
		this._messageID = _messageID;
	}
	public int get_personID() {
		return _personID;
	}
	public void set_personID(int _personID) {
		this._personID = _personID;
	}
	public int get_parkingID() {
		return _parkingID;
	}
	public void set_parkingID(int _parkingID) {
		this._parkingID = _parkingID;
	}
	public String get_dateOdMes() {
		return _dateOdMes;
	}
	public void set_dateOdMes(String _dateOdMes) {
		this._dateOdMes = _dateOdMes;
	}
	public String get_message() {
		return _message;
	}
	public void set_message(String _message) {
		this._message = _message;
	}
	public int get_isSolved() {
		return _isSolved;
	}
	public void set_isSolved(int _isSolved) {
		this._isSolved = _isSolved;
	}


	
	
}
