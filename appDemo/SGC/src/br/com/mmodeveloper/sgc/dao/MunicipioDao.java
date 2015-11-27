package br.com.mmodeveloper.sgc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.mmodeveloper.sgc.constants.Constants;
import br.com.mmodeveloper.sgc.model.Municipio;
import br.com.mmodeveloper.sgc.model.UF;
import br.com.mmodeveloper.sgc.dao.UFDao;

public class MunicipioDao {
	
	Connection con = null;

	private void conectar() {
		// Disconecta primerio
		this.desconectar();
		try {
			// Conecta com banco
			Class.forName(Constants.className).newInstance();
			con = DriverManager.getConnection(Constants.getUrlConnection(),
					Constants.getUSER_KEY(), Constants.getKEY());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Fecha a conexão
	private void desconectar() {
		if (con != null) {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public Integer gerarChave() {
		Integer retorno = 1;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer("SELECT (MAX(codigo_municipio)+1) AS CHAVE FROM MUNICIPIO");
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				if (resultado.getString("CHAVE") != null)
					retorno = resultado.getInt("CHAVE");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}				
		return retorno;
	}
	
	public void incluir(Municipio municipio) {
		StringBuffer sql = new StringBuffer();
		municipio.setId(this.gerarChave());		
		try {
			sql = new StringBuffer("INSERT INTO MUNICIPIO(" +
					 "CODIGO_MUNICIPIO,NOME,POPULACAO,CODIGO_UF) VALUES (");
			if (municipio.getId() != null && municipio.getId() > 0)
				sql.append(municipio.getId() + ",");
			else
				sql.append("null,");
			if (municipio.getNome() != null && !municipio.getNome().equals(""))
				sql.append("'" + municipio.getNome() + "',");
			else
				sql.append("null,");
			if (municipio.getPopulacao() != null && municipio.getPopulacao() > 0)
				sql.append("'" + municipio.getPopulacao() + "',");
			else
				sql.append("null,");
			if (municipio.getUf() != null && municipio.getUf().getId() > 0)
				sql.append("'" +  municipio.getUf().getId() + "')");
			else
				sql.append("null)");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}		
	}
	
	public void atualizar(Municipio municipio) {
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer("UPDATE MUNICIPIO SET ");			
			if (municipio.getNome() != null && !municipio.getNome().equals("")){
				sql.append("NOME =' " + municipio.getNome() + "'");
			}	
			if (municipio.getPopulacao() != null && municipio.getPopulacao() > 0) {
				sql.append(",POPULACAO =' " + municipio.getPopulacao() + "'");
			}	
			if (municipio.getUf() != null && municipio.getUf().getId() > 0) {
				sql.append(",CODIGO_UF =' " + municipio.getUf().getId() + "'");
			}	
			sql.append(" WHERE CODIGO_MUNICIPIO = " + municipio.getId());
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}		
	}
	
	
	
	protected Municipio resultadoParaBean(ResultSet resultado) throws Exception {
		Municipio retorno = new Municipio();
		UF uf = new UF();
		UFDao ufDao = new UFDao();	
		try {
			retorno.setId(resultado.getInt("CODIGO_MUNICIPIO"));
			retorno.setNome(resultado.getString("NOME"));
			retorno.setPopulacao(resultado.getLong("POPULACAO"));
			uf = new UF();
			uf.setId(resultado.getInt("CODIGO_UF"));
			retorno.setUf(ufDao.consulta(uf).get(0));
		} catch (Exception e) {
			System.out.println(e.getMessage());		
		}			
		return retorno;
	}
	
	public ArrayList<Municipio> consulta(Municipio municipio) {
		StringBuffer sql = new StringBuffer();
		ArrayList <Municipio> retorno = new ArrayList<Municipio>();
		try {
			Municipio municipioAux = new Municipio();
			sql = new StringBuffer("SELECT * FROM MUNICIPIO WHERE 1=1");
			if (municipio.getId() != null && municipio.getId() > 0) {
				sql.append(" AND CODIGO_MUNICIPIO = " +municipio.getId());
			}
			if (municipio.getNome() != null && !municipio.getNome().equals("")){
				sql.append(" AND NOME like " + "'%" + municipio.getNome() + "%'");
			}
			if (municipio.getUf() != null && municipio.getUf().getId() > 0) {
				sql.append(" AND CODIGO_UF = '" + municipio.getUf().getId() + "'");
			}
			sql.append(" ORDER BY NOME");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				municipioAux = resultadoParaBean(resultado);
				retorno.add(municipioAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public ArrayList<Municipio> todos(String ord) {
		ArrayList <Municipio> retorno = new ArrayList<Municipio>();
		StringBuffer sql = new StringBuffer();
		try {
			Municipio municipioAux = new Municipio();
			sql = new StringBuffer("SELECT * FROM MUNICIPIO");
			if (ord != null && !ord.equals("")) {
				sql.append(" ORDER BY " + ord);
			}
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				municipioAux = resultadoParaBean(resultado);
				retorno.add(municipioAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public void excluir(Municipio municipio) {
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer("DELETE FROM MUNICIPIO");
			sql.append(" WHERE CODIGO_MUNICIPIO =" + municipio.getId());
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}		
	}	
	
}
