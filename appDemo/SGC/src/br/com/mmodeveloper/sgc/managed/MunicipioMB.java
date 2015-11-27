package br.com.mmodeveloper.sgc.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mmodeveloper.sgc.dao.MunicipioDao;
import br.com.mmodeveloper.sgc.model.Municipio;


@SessionScoped
@ManagedBean(name="municipioMB")
public class MunicipioMB extends AbstractMB {
	
	private Municipio municipio;
	private List<Municipio> listagemResultadoBusca;
	protected MunicipioDao municipioDao = new MunicipioDao();
    
	// Inicializando
	
	public MunicipioMB() {
		municipio = new Municipio();
		listagemResultadoBusca = new ArrayList<Municipio>();
		total = 0;
	}
    
	public String init() {
		try {
			municipio = new Municipio();
			listagemResultadoBusca = new ArrayList<Municipio>();
			total = 0;
			carregarListaUfSelect();			
			this.consultar();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "/paginas/municipio/Municipio.xhtml?faces-redirect=true";
	}		
    
	// Opcional ver validacao de tela
	
	protected boolean validarMunicipio(Municipio municipio) throws Exception {
		boolean retorno = true;
		if (municipio != null) {
			if (municipio.getNome().equals("")) {
				retorno = false;
				addErrorMessage(getMessage("nomeCampoObrigatorio"));
			}
			if (municipio.getPopulacao() < 1) {
				retorno = false;
				addErrorMessage(getMessage("populacaoCampoObrigatorio"));
			}
			if (municipio.getUf() == null && municipio.getId() < 0) {
				retorno = false;
				addErrorMessage(getMessage("ufCampoObrigatorio"));
			}			
		}
		return retorno;
	}
    
	//Pessistencia
	
	public String salvar() {
		try {
			if (validarMunicipio(municipio)){// Opcional ver validacao de tela
				getMunicipioDao().incluir(municipio);	
				municipio = new Municipio();
				this.consultar();
			    addInfoMessage("Operação efetuada com sucesso");
			    return "/paginas/municipio/Municipio.xhtml?faces-redirect=true";
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
			return null;
		}
	}

	public String atualizar() {
		try {
			if (validarMunicipio(municipio)){
				getMunicipioDao().atualizar(municipio);
				municipio = new Municipio();
				this.consultar();
				addInfoMessage("Operação efetuada com sucesso");
				return "/paginas/municipio/Municipio.xhtml?faces-redirect=true";
			} else {
				return null;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
			return "*";
		}
	}

	public String excluir() {
		try {
			getMunicipioDao().excluir(municipio);
			this.consultar();			
			municipio = new Municipio();
			addInfoMessage("Operação efetuada com sucesso");			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return null;
	}
	
	public String consultar() {
		listagemResultadoBusca = getMunicipioDao().consulta(municipio);
		total = listagemResultadoBusca.size();
		if (total == 0) {
			addInfoMessage("Nenhum registro encontrado");
		}		
		municipio = new Municipio();
		return null;		
	}
	
	
	// Navegação
	
	public String incluir() {
		municipio = new Municipio();
		return "/paginas/municipio/MunicipioIncluir.xhtml?faces-redirect=true";
	}
	
	public String editar() {
		return "/paginas/municipio/MunicipioEditar.xhtml?faces-redirect=true";
	}


	public String cancelar() {
		return "/paginas/municipio/Municipio.xhtml?faces-redirect=true";
	}

	public void relatorio() {
		HashMap paramRel = new HashMap();
		List<Municipio> listaRel = getMunicipioDao().consulta(municipio);	
		String nomeRelatorio = "relMunicipio";		
		gerarRelatorio(nomeRelatorio, paramRel,listaRel);
	}
	
	public void relatorioGrafico() {
		HashMap paramRel = new HashMap();
		List<Municipio> listaRel = getMunicipioDao().consulta(municipio);		
		String nomeRelatorio = nomeRelatorio = "relMunicipioGrafico";		
		gerarRelatorio(nomeRelatorio, paramRel,listaRel);		
	}
	
	public void relatorioSQL() {
		HashMap paramRel = new HashMap();
		String nomeRelatorio = "relMunicipioSQL";		
		gerarRelatorio(nomeRelatorio, paramRel);		
	}

	public void relatorioCrossTab() {
		HashMap paramRel = new HashMap();
		List<Municipio> listaRel = getMunicipioDao().consulta(municipio);	
		String nomeRelatorio = "relMunicipioCT";		
		gerarRelatorio(nomeRelatorio, paramRel,listaRel);
	}
	// gets e setters	
	
	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Municipio> getListagemResultadoBusca() {
		return listagemResultadoBusca;
	}

	public void setListagemResultadoBusca(List<Municipio> listagemResultadoBusca) {
		this.listagemResultadoBusca = listagemResultadoBusca;
	}

	public MunicipioDao getMunicipioDao() {
		return municipioDao;
	}

	public void setMunicipioDao(MunicipioDao municipioDao) {
		this.municipioDao = municipioDao;
	}	
	
	
}
