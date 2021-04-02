package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.CorsoDAO;

public class Model {
	private CorsoDAO corsoDAO;
	public Model() {
		corsoDAO=new CorsoDAO();
	}
	
	public List<Corso>elencoCorsiPeriodo(Integer periodo){
		return corsoDAO.corsiDiUnPeriodo(periodo);
	}
	
	public Map<Corso,Integer>elencoCorsiPeriodoEStudenti(Integer periodo){
		return corsoDAO.corsiPiuIscritti(periodo);
	}
	
	public List<Studente>studentiDelCorso(String codice){
		return corsoDAO.studentiCorso(new Corso(codice,null,null,null));
	}
	
	public boolean esisteIlCorso(String codice) {
		return corsoDAO.esisteCorso(new Corso(codice,null,null,null));
	}
	
	public Map<String,Integer>totaleStudenti(String codice){
		return corsoDAO.numeroStudentiCorso(codice);
	}
}
