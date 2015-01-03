package model;

import java.sql.Date;

public class Reservation {
	private int _reservationID;
	private int _parkingID;
	private int _numberofreserved;
	private Date _dateofStart;
	private Date _dateofEnd;
	private int _personID;
	private String _note;
	private String _isdeleted;
	public int get_reservationID() {
		return _reservationID;
	}
	public void set_reservationID(int _reservationID) {
		this._reservationID = _reservationID;
	}
	public int get_parkingID() {
		return _parkingID;
	}
	public void set_parkingID(int _parkingID) {
		this._parkingID = _parkingID;
	}
	public int get_numberofreserved() {
		return _numberofreserved;
	}
	public void set_numberofreserved(int _numberofreserved) {
		this._numberofreserved = _numberofreserved;
	}
	public Date get_dateofStart() {
		return _dateofStart;
	}
	public void set_dateofStart(Date _dateofStart) {
		this._dateofStart = _dateofStart;
	}
	public Date get_dateofEnd() {
		return _dateofEnd;
	}
	public void set_dateofEnd(Date _dateofEnd) {
		this._dateofEnd = _dateofEnd;
	}
	public int get_personID() {
		return _personID;
	}
	public void set_personID(int _personID) {
		this._personID = _personID;
	}
	public String get_note() {
		return _note;
	}
	public void set_note(String _note) {
		this._note = _note;
	}
	public String get_isdeleted() {
		return _isdeleted;
	}
	public void set_isdeleted(String _isdeleted) {
		this._isdeleted = _isdeleted;
	}
}
