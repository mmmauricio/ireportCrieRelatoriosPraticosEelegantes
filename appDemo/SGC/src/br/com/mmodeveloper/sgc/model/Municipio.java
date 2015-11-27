package br.com.mmodeveloper.sgc.model;



public class Municipio extends AbstractModel<Integer> {

	
	private Integer id;
	private String nome;
	private UF uf;	
	private Long populacao;
	private static final long serialVersionUID = 1L;
	
	public Municipio() { 

	} 

	public Municipio(Integer id, String nome) { 
		this.id = id; 
		this.nome = nome; 
	} 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public Long getPopulacao() {
		return populacao;
	}

	public void setPopulacao(Long populacao) {
		this.populacao = populacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
