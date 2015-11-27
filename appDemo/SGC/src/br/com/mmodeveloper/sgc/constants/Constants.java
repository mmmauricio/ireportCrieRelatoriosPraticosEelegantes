package br.com.mmodeveloper.sgc.constants;



/**
 * Muito importante a manutencao dessa classe.<BR/>
 * Os valores das constantes devem corresponder EXATAMENTE ao código do modulo
 * cadastrado no sistema de seguranca.<BR/>
 * A inclusao de um novo modulo de seguranca no istema de seguranca também deve
 * ser incluido nessa classe com seu respectivo codigo. Alem de ter que ser
 * adicionado um get na classe {@link SegurancaController}. <BR/>
 * <BR/>
 * Essa classe tambem eh necessaria na classe {@link SegurancaController} para
 * a busca das permissoes do usuario
 * 
 * 
 */
public final class Constants {

	
	
	
	
	
	public static final String NOME_SISTEMA = "SISTEMA DE GERENCIAMENTO DE CLIENTE";
	public final static String MSG_SUCESSO = "Operação efetuada com sucesso.";
	public final static String MSG_ERRO = "Erro na operação, contacte o administrador do sistema.";
	public final static String MSG_NENHUM_REGISTRO = "Nenhum Registro foi localizado.";
	public final static String SLG_SISTEMA = "SGC";
	public static final String className = "org.postgresql.Driver"; 
	
	static String USER_KEY; //Usuario do bd
	static String KEY;  //Senha do usuario do bd
	static String urlConnection; //Endereço de conexão  
	
	public static String getUrlConnection()  
	{
		if (urlConnection == null)
			urlConnection = "jdbc:postgresql://localhost:5432/sgc"; //Utilizado para conexao local		
		return urlConnection;
	}
	
	public static String getUSER_KEY() 
	{
		 if (null == USER_KEY)			 
			 USER_KEY = "postgres"; 
			 return USER_KEY;
	}	
	public static String getKEY() 
	{
		if (null == KEY)
			KEY = "123456"; 
		return KEY;
	}	

}