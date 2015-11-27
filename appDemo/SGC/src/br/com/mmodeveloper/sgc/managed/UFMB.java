package br.com.mmodeveloper.sgc.managed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mmodeveloper.sgc.model.Cliente;
import br.com.mmodeveloper.sgc.model.UF;
@SessionScoped
@ManagedBean(name="ufMB")
public class UFMB extends AbstractMB {
	private UF uf;
	private List<UF> listagemResultadoBusca;
	
	public void limpar() {
		uf = new UF();
		listagemResultadoBusca = new ArrayList<UF>();
		total = 0;		
	}
		
	public UFMB() {
		limpar();
	}
 
 	public String init() {
 		limpar();
		return "/paginas/uf/UF.xhtml?faces-redirect=true";
	}
	 	
	//Pessistencia
	public String salvar() {
		try {
				getUfDao().incluir(uf);				
				this.consultar();				
				uf = new UF();
				addInfoMessage("Operação efetuada com sucesso.");			    
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "/paginas/uf/UF.xhtml?faces-redirect=true";
	}

	
	public String atualizar() {
		try {			
			getUfDao().atualizar(uf);
			this.consultar();
			uf = new UF();
			addInfoMessage("Operação efetuada com sucesso.");
			return "/paginas/uf/UF.xhtml?faces-redirect=true";
						
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public String  excluir() {
		try {
			getUfDao().excluir(uf);
			this.consultar();
			uf = new UF();
			addInfoMessage("Operação efetuada com sucesso.");			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return null;
	}
	
	
	public String consultar() {
		listagemResultadoBusca =  getUfDao().todos("sigla");
		total = listagemResultadoBusca.size();
		if (total == 0) {
			addInfoMessage("Nenhum Registro foi localizado.");
		}		
		return null;
	}	
	
	
	public String incluir() {
		uf = new UF();		
		return "/paginas/uf/UFIncluir.xhtml?faces-redirect=true";
	}	
   
	public String editar() {
		return "/paginas/uf/UFEditar.xhtml?faces-redirect=true";
	}

	public String cancelar() {
		return "/paginas/uf/UF.xhtml?faces-redirect=true";
	}
	
	
   public void relatorio() {		
		listagemResultadoBusca =  getUfDao().consulta(uf);		
		HashMap paramRel = new HashMap();
		String nomeRelatorio = "relUF";		
		gerarRelatorio(nomeRelatorio, paramRel,listagemResultadoBusca);		
	}
    
    public void relatorioSQLCP() {
    	if (uf != null && uf.getId() > 0) {
    		HashMap paramRel = new HashMap();
    		paramRel.put("codigo",uf.getId());
    		String nomeRelatorio = "relUFSQLCP";
    		gerarRelatorio(nomeRelatorio, paramRel);	
    	} else {
			addInfoMessage("Informe o código do estado");
		}	
	}
    
    public void relatorioSQL() {
		HashMap paramRel = new HashMap();
		String nomeRelatorio = "relUFSQL";
		gerarRelatorio(nomeRelatorio, paramRel);		
	}
    
    public void relatorioSubRel() {
    	if (uf != null && uf.getId() > 0) {
    		listagemResultadoBusca =  getUfDao().consulta(uf);
    		List<Cliente> listagemSubRel = getClienteDao().clientePorUF(uf);
    		HashMap paramRel = new HashMap();
    		String nomeRelatorio = "relUFCSREL";
    		String subNomeRelatorio = "subRelCliente";
    		gerarRelatorioSub(nomeRelatorio, paramRel,listagemResultadoBusca,listagemSubRel,subNomeRelatorio);	
    	} else {
			addInfoMessage("Informe o código do estado");
		}	
	}
		
	// gets e setters
		
	public List<UF> getListagemResultadoBusca() {
		return listagemResultadoBusca;
	}

	
	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}
	
	public void setListagemResultadoBusca(List<UF> listagemResultadoBusca) {
		this.listagemResultadoBusca = listagemResultadoBusca;
	}	

}
