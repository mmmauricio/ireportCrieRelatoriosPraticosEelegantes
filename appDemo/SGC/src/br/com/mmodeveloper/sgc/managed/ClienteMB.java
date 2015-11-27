package br.com.mmodeveloper.sgc.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mmodeveloper.sgc.dao.ClienteDao;
import br.com.mmodeveloper.sgc.model.Cliente;
import br.com.mmodeveloper.sgc.model.Municipio;

@SessionScoped
@ManagedBean(name="clienteMB")
public class ClienteMB extends AbstractMB {
	
	
	private Cliente cliente;
	private List<Cliente> listagemResultadoBusca;
	
	
	public ClienteMB() {
		cliente = new Cliente();
		listagemResultadoBusca = new ArrayList<Cliente>();
		total = 0;		
	}
 
 	public String init() {
		cliente = new Cliente();
		listagemResultadoBusca = new ArrayList<Cliente>();
		total = 0;
		carregarListaUfSelect();
		return "/paginas/cliente/Cliente.xhtml?faces-redirect=true";
	}
	
 	public void limpar() {
		cliente = new Cliente();
		listagemResultadoBusca = new ArrayList<Cliente>();
		total = 0;		
	}
	
 	
	//Pessistencia
	public String salvar() {
		try {
			   
				getClienteDao().incluir(cliente);					
				cliente = new Cliente();
				this.consultar();	
				addInfoMessage("Operação efetuada com sucesso.");			    
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "/paginas/cliente/Cliente.xhtml?faces-redirect=true";
	}

	public String atualizar() {
		try {		
			getClienteDao().atualizar(cliente);				
			cliente = new Cliente();
			this.consultar();	
			addInfoMessage("Operação efetuada com sucesso.");
			return "/paginas/cliente/Cliente.xhtml?faces-redirect=true";
						
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
			return null;
		}
	}

	public String  excluir() {
		try {
			getClienteDao().excluir(cliente);
			this.consultar();		
			listagemResultadoBusca.remove(cliente);
			cliente = new Cliente();
			addInfoMessage("Operação efetuada com sucesso.");			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return null;
	}
	
	public String consultar() {
		listagemResultadoBusca = getClienteDao().consulta(cliente);
		total = listagemResultadoBusca.size();
		if (total == 0) {
			addInfoMessage("Nenhum Registro foi localizado.");
		}		
		return null;
	}	
		
	public List<Municipio> getListaMunicipioUFSelect() {
		List<Municipio> retorno = new ArrayList<Municipio>();
		if ((cliente.getUf() != null)&& (cliente.getUf().getId()> 0)) {
			    Municipio municipioAux = new Municipio();
				municipioAux.setUf(cliente.getUf());
				retorno = getMunicipioDao().consulta(municipioAux); 
		}
		return retorno;
	}
		
	public String incluir() {
		cliente = new Cliente();		
		return "/paginas/cliente/ClienteIncluir.xhtml?faces-redirect=true";
	}	
   
	public String editar() {	
		return "/paginas/cliente/ClienteEditar.xhtml?faces-redirect=true";
	}

	public String cancelar() {
		return "/paginas/cliente/Cliente.xhtml?faces-redirect=true";
	}
	
	
	public void relatorio() {		
		if (cliente != null && cliente.getId() > 0) {
			listagemResultadoBusca = getClienteDao().consulta(cliente);		
			HashMap paramRel = new HashMap();
			String nomeRelatorio = "relCliMap";		
			gerarRelatorio(nomeRelatorio, paramRel,listagemResultadoBusca);
		} else {
			addInfoMessage("Informe o código do cliente");
		}
	}
    
	// gets e setters
		
	public List<Cliente> getListagemResultadoBusca() {
		return listagemResultadoBusca;
	}

	
	public Cliente getcliente() {
		return cliente;
	}

	public void setcliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setListagemResultadoBusca(List<Cliente> listagemResultadoBusca) {
		this.listagemResultadoBusca = listagemResultadoBusca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	} 	

}
