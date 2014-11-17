package Models;

public class Location {
	public Location() {
	};

	private int _locationID;
	private int _latitude;
	private int _longtitude;

	public int get_locationID() {
		return _locationID;
	}

	public void set_locationID(int _locationID) {
		this._locationID = _locationID;
	}

	public int get_latitude() {
		return _latitude;
	}

	public void set_latitude(int _latitude) {
		this._latitude = _latitude;
	}

	public int get_longtitude() {
		return _longtitude;
	}

	public void set_longtitude(int _longtitude) {
		this._longtitude = _longtitude;
	}
}
