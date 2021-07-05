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
import javax.persistence.OneToMany;
@Entity
public class Concerto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String descrizione;
	
	@OneToMany(mappedBy="concerto")
	private List<Canzone> canzoni;
	
	@ManyToOne
	private Sponsor sponsor;
	@ManyToMany
	private List<User> iscritti;
	
	public Concerto() {
		canzoni=new ArrayList<Canzone>();
		this.iscritti=new ArrayList<>();
	}
	
	public List<User> getIscritti() {
		return iscritti;
	}

	public void setIscritti(List<User> iscritti) {
		this.iscritti = iscritti;
	}
	public void addIscritto(User u) {
		this.iscritti.add(u);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Canzone> getCanzoni() {
		return canzoni;
	}

	public void setCanzoni(List<Canzone> opere) {
		this.canzoni = opere;
	}
	
	public void addCanzone(Canzone o) {
		this.canzoni.add(o);
	}
	
	public void rimuoviCanzone(Canzone o) {
		this.canzoni.remove(o);
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
	
}
