package Models;

public class Parking {
	public Parking() {
	}

	private int _parkingID;
	private float _longtitude;
	private float _latitude;
	private int _creatorID;
	private int _totalnumber;
	private int _price;
	private String _note;
	private int _pictureID;
	private int _gradeUser;
	private Boolean _isthereLight;
	private Boolean _isthereRoad;
	private Boolean _isthereCamera;
	private Boolean _isthereGuard;
	private Boolean _isthereGoodEntrance;
	private Boolean _isthereRoof;
	public int get_parkingID() {
		return _parkingID;
	}

	public void set_parkingID(int _parkingID) {
		this._parkingID = _parkingID;
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
	public int get_gradeUser() {
		return _gradeUser;
	}

	public void set_gradeUser(int _gradeUser) {
		this._gradeUser = _gradeUser;
	}

	public Boolean get_isthereLight() {
		return _isthereLight;
	}

	public void set_isthereLight(Boolean _isthereLight) {
		this._isthereLight = _isthereLight;
	}

	public Boolean get_isthereRoad() {
		return _isthereRoad;
	}

	public void set_isthereRoad(Boolean _isthereRoad) {
		this._isthereRoad = _isthereRoad;
	}

	public Boolean get_isthereCamera() {
		return _isthereCamera;
	}

	public void set_isthereCamera(Boolean _isthereCamera) {
		this._isthereCamera = _isthereCamera;
	}

	public Boolean get_isthereGuard() {
		return _isthereGuard;
	}

	public void set_isthereGuard(Boolean _isthereGuard) {
		this._isthereGuard = _isthereGuard;
	}

	public Boolean get_isthereGoodEntrance() {
		return _isthereGoodEntrance;
	}

	public void set_isthereGoodEntrance(Boolean _isthereGoodEntrance) {
		this._isthereGoodEntrance = _isthereGoodEntrance;
	}

	public float get_latitude() {
		return _latitude;
	}

	public void set_latitude(float _latitude) {
		this._latitude = _latitude;
	}

	public float get_longtitude() {
		return _longtitude;
	}

	public void set_longtitude(float _longtitude) {
		this._longtitude = _longtitude;
	}

	public Boolean get_isthereRoof() {
		return _isthereRoof;
	}

	public void set_isthereRoof(Boolean _isthereRoof) {
		this._isthereRoof = _isthereRoof;
	}
}
