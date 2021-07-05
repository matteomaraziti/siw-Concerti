package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "findAllCuratori", query = "SELECT c FROM Sponsor c")
public class Sponsor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	
	@OneToMany(mappedBy="sponsor")
	private List<Concerto> concerti;

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



	public List<Concerto> getConcerti() {
		return concerti;
	}

	public void setConcerti(List<Concerto> concerti) {
		this.concerti = concerti;
	}
	
	
	

}
