package it.uniroma3.siw.spring.model;

import java.sql.Date;
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
	
	@ManyToMany(mappedBy="concerti")
	private List<Canzone> canzoni;
	
	@Column(nullable=false)
	private Date dataConcerto;
	
	public Date getDataConcerto() {
		return dataConcerto;
	}

	public void setDataConcerto(Date data) {
		this.dataConcerto = data;
	}

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
	
	public void removeIscritto(User u) {
		this.iscritti.remove(u);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataConcerto == null) ? 0 : dataConcerto.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concerto other = (Concerto) obj;
		if (dataConcerto == null) {
			if (other.dataConcerto != null)
				return false;
		} else if (!dataConcerto.equals(other.dataConcerto))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
