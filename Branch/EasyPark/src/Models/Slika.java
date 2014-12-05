package Models;

import java.awt.Image;
import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.ImageIcon;

public class Slika {
	public Slika() {
	};

	private int _pictureID;
	private String _note;
	private Blob _slika;
	private File img;

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

	public Blob get_slika() {
		return _slika;
	}

	public void set_slika(Blob _slika) {
		this._slika = _slika;
	}
	public void set_Image(File image) throws SQLException {
		this.img = image;
	}
}
