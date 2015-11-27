package br.com.mmodeveloper.sgc.model;



public class UF extends AbstractModel<Integer> {

	private Integer id;	
	private String nome;	
	private String sigla;
	private static final long serialVersionUID = 1L;
	
	public UF() { 

	} 

	public UF(Integer id, String nome) { 
		this.id = id; 
		this.nome = nome; 
	} 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
