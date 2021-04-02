package it.polito.tdp.corsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
    public List<Corso>corsiDiUnPeriodo(Integer periodo) {
    	String sql="SELECT * FROM corso WHERE pd=?";
    	List<Corso>stampa=new ArrayList<Corso>();
    	try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, periodo);          //serve per specificare l'attributo con ?;1 è perchè e il primo(e unico )attributo
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				stampa.add(c);
			}
			
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return stampa; }
    
    public Map<Corso,Integer>corsiPiuIscritti(Integer periodo){
    	String sql="SELECT c.codins,c.crediti,c.nome,c.pd,COUNT(*) AS tot "
    			+ "FROM corso c,iscrizione i "
    			+ "WHERE c.codins=i.codins AND c.pd=? "
    			+ "GROUP BY c.codins,c.crediti,c.nome,c.pd ";
    	Map<Corso,Integer>stampa=new HashMap<Corso,Integer>();
    	try {
    	Connection conn=DBConnect.getConnection();
    	PreparedStatement st=conn.prepareStatement(sql);
    	st.setInt(1, periodo);
    	ResultSet rs=st.executeQuery();
    	while(rs.next()) {
    		Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
			Integer n=rs.getInt("tot");
			stampa.put(c,n);
    		
    	}
    	
    	conn.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return stampa;
    }
    
    public List <Studente> studentiCorso(Corso corso){
    	String sql="SELECT s.matricola,s.cognome,s.nome,s.CDS "
    			+ "FROM iscrizione i,studente s "
    			+ "WHERE i.matricola=s.matricola AND i.codins=? ";
    	List<Studente> stampa=new ArrayList<Studente>();
    	
    	try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Studente s=new Studente(rs.getInt("matricola"),rs.getString("cognome"),rs.getString("nome"),rs.getString("CDS"));
				stampa.add(s);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return stampa;
    }
    
    
    public boolean esisteCorso(Corso c) {
    	String sql=" SELECT * FROM corso WHERE codins=?";
    	boolean esiste=false;
    	try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,c.getCodins());
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				conn.close();
				esiste=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return esiste;
    }
    
    public Map<String,Integer> numeroStudentiCorso(String codice) {
    	String sql="SELECT s.CDS,COUNT(*) AS tot "
    			+ "FROM studente s,iscrizione i "
    			+ "WHERE s.matricola=i.matricola AND s.CDS <>' ' AND i.codins=? "
    			+ "GROUP BY s.CDS ";
    	Map <String,Integer> stampa= new HashMap<String,Integer>();
    	try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,codice);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				stampa.put(rs.getString("CDS"),rs.getInt("tot"));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return stampa;
    }
}
