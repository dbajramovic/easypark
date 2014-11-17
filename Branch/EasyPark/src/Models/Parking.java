package Models;

public class Parking {
	public Parking() {
	}

	private int _parkingID;
	private int _locationID;
	private int _creatorID;
	private int _totalnumber;
	private int _price;
	private String _note;
	private int _pictureID;

	public int get_parkingID() {
		return _parkingID;
	}

	public void set_parkingID(int _parkingID) {
		this._parkingID = _parkingID;
	}

	public int get_locationID() {
		return _locationID;
	}

	public void set_locationID(int _locationID) {
		this._locationID = _locationID;
	}

	public int get_creatorID() {
		return _creatorID;
	}

	public void set_creatorID(int _creatorID) {
		this._creatorID = _creatorID;
	}

	public int get_totalnumber() {
		return _totalnumber;
	}

	public void set_totalnumber(int _totalnumber) {
		this._totalnumber = _totalnumber;
	}

	public int get_price() {
		return _price;
	}

	public void set_price(int _price) {
		this._price = _price;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}

	public int get_pictureID() {
		return _pictureID;
	}

	public void set_pictureID(int _pictureID) {
		this._pictureID = _pictureID;
	}
}
