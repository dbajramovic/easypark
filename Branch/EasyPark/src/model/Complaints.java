package model;

public class Complaints {
	public Complaints(){};
	private int _complaintsID;
	private int _ownerID;
	private int _accussedID;
	private int _parkingID;
	private int _numOfReserved;
	private int _isSolved;
	
	
	
	public int get_complaintsID() {
		return _complaintsID;
	}
	public void set_complaintsID(int _complaintsID) {
		this._complaintsID = _complaintsID;
	}
	public int get_ownerID() {
		return _ownerID;
	}
	public void set_ownerID(int _ownerID) {
		this._ownerID = _ownerID;
	}
	public int get_accussedID() {
		return _accussedID;
	}
	public void set_accussedID(int _accussedID) {
		this._accussedID = _accussedID;
	}
	public int get_parkingID() {
		return _parkingID;
	}
	public void set_parkingID(int _parkingID) {
		this._parkingID = _parkingID;
	}
	public int get_numOfReserved() {
		return _numOfReserved;
	}
	public void set_numOfReserved(int _numOfReserved) {
		this._numOfReserved = _numOfReserved;
	}
	public int get_isSolved() {
		return _isSolved;
	}
	public void set_isSolved(int _isSolved) {
		this._isSolved = _isSolved;
	}

	
	
	
}
