package br.com.mmodeveloper.sgc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.mmodeveloper.sgc.constants.Constants;
import br.com.mmodeveloper.sgc.model.UF;

public class UFDao {
	
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
		StringBuffer sql = new StringBuffer("SELECT (MAX(codigo_uf)+1) AS CHAVE FROM UF");
		try {			
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
	
	public void incluir(UF uf) {
		uf.setId(this.gerarChave());
		StringBuffer sql = new StringBuffer("INSERT INTO UF(");
		try {
			sql.append("CODIGO_UF,NOME,SIGLA) VALUES (");
			if (uf.getId() != null && uf.getId() > 0)
				sql.append(uf.getId() + ",");
			else
				sql.append("null,");
			if (uf.getNome() != null && !uf.getNome().equals(""))
				sql.append("'" + uf.getNome().trim() + "',");
			else
				sql.append("null,");
			if (uf.getSigla() != null && !uf.getSigla().equals(""))
				sql.append("'" + uf.getSigla().trim() + "')");
			else
				sql.append("null)");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}		
	}
	
	public void atualizar(UF uf) {
		StringBuffer sql = new StringBuffer("UPDATE UF SET ");
		try {						
			if (uf.getNome() != null && !uf.getNome().equals("")){
				sql.append("NOME ='" + uf.getNome().trim() + "'");
			}	
			if (uf.getSigla() != null && !uf.getSigla().equals("")) {
				sql.append(",SIGLA ='" + uf.getSigla().trim() + "'");
			}	
			sql.append(" WHERE CODIGO_UF = " + uf.getId());
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}		
	}
	
	
	
	protected UF resultadoParaBean(ResultSet resultado) throws Exception {
		UF retorno = new UF();
		try {
			retorno.setId(resultado.getInt("CODIGO_UF"));
			retorno.setNome(resultado.getString("NOME"));
			retorno.setSigla(resultado.getString("SIGLA"));
		} catch (Exception e) {
			System.out.println(e.getMessage());		
		}			
		return retorno;
	}
	
	public ArrayList<UF> consulta(UF uf) {
		ArrayList <UF> retorno = new ArrayList<UF>();
		try {
			UF ufAux = new UF();

			StringBuffer sql = new StringBuffer("SELECT * FROM  UF WHERE 1=1");
			if (uf.getId() != null && uf.getId() > 0){
				sql.append(" AND CODIGO_UF = " +uf.getId());
			}
			if (uf.getNome() != null && !uf.getNome().equals("")){
				sql.append(" AND NOME like " + "'%" + uf.getNome() + "%'");
			}
			if (uf.getSigla() != null && !uf.getSigla().equals("")) {
				sql.append(" AND SIGLA like " + "'%" + uf.getSigla() + "%'");
			}
			sql.append(" ORDER BY NOME");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				ufAux = resultadoParaBean(resultado);
				retorno.add(ufAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public ArrayList<UF> todos(String ord) {
		ArrayList <UF> retorno = new ArrayList<UF>();
		try {
			UF ufAux = new UF();

			StringBuffer sql = new StringBuffer("SELECT * FROM UF");
			if (ord != null && !ord.equals("")) {
				sql.append(" ORDER BY " + ord);
			}
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				ufAux = resultadoParaBean(resultado);
				retorno.add(ufAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public void excluir(UF uf) {
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM UF");
			sql.append(" WHERE CODIGO_UF =" + uf.getId());
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();		
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			this.desconectar();	
		}		
	}	
	
	
	
}
