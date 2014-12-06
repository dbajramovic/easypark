package model;

import java.util.ArrayList;
import java.util.List;

public class History {

	public History(){
		this.lista=new ArrayList<Temp>();
	}
	public List<Temp> lista;
	public List<Temp> getLista() {
		return lista;
	}
	public void setLista(List<Temp> lista) {
		this.lista = lista;
	}
	public void Dodaj(Temp t){this.lista.add(t);}
}
