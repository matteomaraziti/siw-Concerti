package it.uniroma3.siw.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@ManyToOne
	private Concerto concerto;
	
	@ManyToOne
	private Artista artista;
	
	public Canzone(){
		
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

	public Concerto getConcerto() {
		return concerto;
	}

	public void setConcerto(Concerto concerto) {
		this.concerto = concerto;
	}

	public String getAnnoDiRealizzazione() {
		return annoDiRealizzazione;
	}

	public void setAnnoDiRealizzazione(String annoDiRealizzazione) {
		this.annoDiRealizzazione = annoDiRealizzazione;
	}

		
	
}
