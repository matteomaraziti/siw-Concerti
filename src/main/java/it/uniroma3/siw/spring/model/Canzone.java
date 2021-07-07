package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "findAllOperas", query = "SELECT o FROM Canzone o")
public class Canzone {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String titolo;
	
	@Column(nullable=false, length = 2000)
	private String testo;
	
	private String annoDiRealizzazione;
	
	@ManyToMany
	private List<Concerto> concerti;
	
	@ManyToOne
	private Artista artista;
	
	public Canzone(){
		concerti=new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String descrizione) {
		this.testo = descrizione;
	}


	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public List<Concerto> getConcerti() {
		return concerti;
	}

	public void setConcerti(List<Concerto> concerti) {
		this.concerti = concerti;
	}
	public void addConcerto(Concerto c) {
		this.concerti.add(c);
	}
	public void removeConcerto(Concerto c) {
		this.concerti.remove(c);
	}
	public String getAnnoDiRealizzazione() {
		return annoDiRealizzazione;
	}

	public void setAnnoDiRealizzazione(String annoDiRealizzazione) {
		this.annoDiRealizzazione = annoDiRealizzazione;
	}

		
	
}
