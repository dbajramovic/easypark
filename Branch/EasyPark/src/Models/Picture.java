package Models;

import java.sql.Blob;

public class Picture {
	public Picture() {
	};

	private int _pictureID;
	private String _note;
	private Blob _picture;

	public int get_pictureID() {
		return _pictureID;
	}

	public void set_pictureID(int _pictureID) {
		this._pictureID = _pictureID;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}

	public Blob get_picture() {
		return _picture;
	}

	public void set_picture(Blob _picture) {
		this._picture = _picture;
	}
}
