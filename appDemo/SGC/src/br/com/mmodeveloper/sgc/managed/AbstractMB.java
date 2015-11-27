package br.com.mmodeveloper.sgc.managed;  

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.mmodeveloper.sgc.constants.Constants;
import br.com.mmodeveloper.sgc.dao.ClienteDao;
import br.com.mmodeveloper.sgc.dao.MunicipioDao;
import br.com.mmodeveloper.sgc.dao.UFDao;
import br.com.mmodeveloper.sgc.model.UF;

public abstract class AbstractMB {

	protected Integer total = 0;
	
	public abstract String salvar();
    public abstract String atualizar();
	public abstract String excluir();
    public abstract String consultar();	
	public abstract String incluir(); 
    public abstract String editar() ;
    public abstract String cancelar();
    
    protected UFDao ufDao = new UFDao();
    protected ClienteDao clienteDao = new ClienteDao();
	protected MunicipioDao municipioDao = new MunicipioDao();

	protected List<UF> listaUfSelect = new ArrayList<UF>();
    
    protected void carregarListaUfSelect() {
		try {
			listaUfSelect = ufDao.todos("sigla");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}
    
    //Metodos para enviar mensagem para usuario
	
	
    
    public static void addInfoMessage(String summary) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, "");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, facesMsg);
	}

	
	
	
	public static void addWarnMessage(String summary) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				summary, "");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, facesMsg);
	}

	
	
	public static void addErrorMessage(String summary) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				summary, "");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, facesMsg);
	}
	
		
	public String getMessage(String key){
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ResourceBundle rb = ResourceBundle.getBundle("messages",
			fc.getViewRoot().getLocale());
			String mensagem = rb.getString(key);
			return mensagem;
		} catch (Exception e) {
			return "";
		}
	}
	
	

	//Controle de acesso
	
	
	public Boolean getAcesso() {
		Boolean retorno = new Boolean(true);
		return retorno;
	}

	
	
	//Mensagem de rodape
	
	
	public String getRodapePag() {
		StringBuffer retorno = new StringBuffer("SGC - ");		
		retorno.append("Prof.Maurício Morais - ");
		retorno.append("Desenvolvimento Web Com ");
		retorno.append("iReport");
		return retorno.toString();
	}
		
	public void gerarRelatorio(String nomeRelatorio,HashMap paramRel,List listaRel){
		   FacesContext context = FacesContext.getCurrentInstance();
		   HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse(); 
		   ServletContext sc = (ServletContext) context.getExternalContext().getContext();   
		   String relPath = sc.getRealPath("/");  
		   String imagemLogo = relPath + "resources/imagens/logo_mmo.jpg";
	       paramRel.put("imagemLogo", imagemLogo);
		   paramRel.put("nmSistema", Constants.NOME_SISTEMA);
		   paramRel.put("REPORT_LOCALE", new Locale("pt", "BR"));
		   try  {
			   JasperPrint print = null;
			   JRBeanCollectionDataSource rel = new JRBeanCollectionDataSource(listaRel);
			   print = JasperFillManager.fillReport(relPath + "relatorios/"+nomeRelatorio+".jasper", paramRel,rel);				   
			   response.setContentType("application/pdf");
			   response.addHeader("Content-disposition", "attachment; filename=\"" + nomeRelatorio + ".pdf\"");
			   JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
			   ServletOutputStream responseStream = response.getOutputStream();
			   responseStream.flush();
			   responseStream.close();
			   FacesContext.getCurrentInstance().renderResponse();
			   FacesContext.getCurrentInstance().responseComplete();   
			} catch (Exception e) { 
				e.printStackTrace();				
			} 
		}
	
	public void gerarRelatorio(String nomeRelatorio,HashMap paramRel){
		   FacesContext context = FacesContext.getCurrentInstance();
		   HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse(); 
		   ServletContext sc = (ServletContext) context.getExternalContext().getContext();   
		   String relPath = sc.getRealPath("/");  
		   String imagemLogo = relPath + "resources/imagens/logo_mmo.jpg";
	       paramRel.put("imagemLogo", imagemLogo);
		   paramRel.put("nmSistema", Constants.NOME_SISTEMA);
		   paramRel.put("REPORT_LOCALE", new Locale("pt", "BR"));
		   try  {
			   JasperPrint print = null;
			   Class.forName("org.postgresql.Driver");
			   String url = "jdbc:postgresql://localhost:5432/sgc";
			   String user = "postgres";
			   String pass = "123456";
			   Connection connection = DriverManager.getConnection(url, user,pass);
			   print = JasperFillManager.fillReport(relPath + "relatorios/"+nomeRelatorio+".jasper", paramRel,connection);
			   response.setContentType("application/pdf");
			   response.addHeader("Content-disposition", "attachment; filename=\"" + nomeRelatorio + ".pdf\"");
			   JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
			   ServletOutputStream responseStream = response.getOutputStream();
			   responseStream.flush();
			   responseStream.close();
			   FacesContext.getCurrentInstance().renderResponse();
			   FacesContext.getCurrentInstance().responseComplete();   
			} catch (Exception e) { 
				e.printStackTrace();				
			} 
		}
	
	
	public void gerarRelatorioSub(String nomeRelatorio,HashMap paramRel,List listaRel,List listaRelSub,String subNomeRelatorio){
		   FacesContext context = FacesContext.getCurrentInstance();
		   HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse(); 
		   ServletContext sc = (ServletContext) context.getExternalContext().getContext();   
		   String relPath = sc.getRealPath("/");  
		   String imagemLogo = relPath + "resources/imagens/logo_mmo.jpg";
	       paramRel.put("imagemLogo", imagemLogo);
		   paramRel.put("nmSistema", Constants.NOME_SISTEMA);
		   paramRel.put("REPORT_LOCALE", new Locale("pt", "BR")); 
		   subNomeRelatorio = relPath + "relatorios/" + subNomeRelatorio+".jasper";		      
		   paramRel.put("subNomeRelatorio",subNomeRelatorio);	
		   try  {
			   JRBeanCollectionDataSource rel = new JRBeanCollectionDataSource(listaRel);
			   JRBeanCollectionDataSource relSub = new JRBeanCollectionDataSource(listaRelSub);
			   paramRel.put("relSub",relSub);
			   JasperPrint print = JasperFillManager.fillReport(relPath + "relatorios/"+nomeRelatorio+".jasper",paramRel,rel);
			   response.setContentType("application/pdf");
			   response.addHeader("Content-disposition", "attachment; filename=\"" + nomeRelatorio + ".pdf\"");
			   JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
			   ServletOutputStream responseStream = response.getOutputStream();
			   responseStream.flush();
			   responseStream.close();
			   FacesContext.getCurrentInstance().renderResponse();
			   FacesContext.getCurrentInstance().responseComplete();			   
		       } catch (Exception e) {
				    e.printStackTrace();					
			   }		
		} 

	
	
	
	public ClienteDao getClienteDao() {
		return clienteDao;
	}
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}
	public MunicipioDao getMunicipioDao() {
		return municipioDao;
	}
	public void setMunicipioDao(MunicipioDao municipioDao) {
		this.municipioDao = municipioDao;
	}
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	public UFDao getUfDao() {
		return ufDao;
	}
	public void setUfDao(UFDao ufDao) {
		this.ufDao = ufDao;
	}
	
	public List<UF> getListaUfSelect() {
		return listaUfSelect;
	}
	
	public void setListaUfSelect(List<UF> listaUfSelect) {
		this.listaUfSelect = listaUfSelect;
	}	
	
}
