package br.com.mmodeveloper.sgc.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.mmodeveloper.sgc.constants.Constants;
import br.com.mmodeveloper.sgc.model.Cliente;
import br.com.mmodeveloper.sgc.model.Municipio;
import br.com.mmodeveloper.sgc.model.UF;
import br.com.mmodeveloper.sgc.dao.UFDao;

public class ClienteDao {
	
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
		StringBuffer sql = new StringBuffer();
		Integer retorno = 1;		
		try {
			sql = new StringBuffer("SELECT (MAX(codigo_cliente)+1) AS CHAVE FROM CLIENTE");
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
	
	public void incluir(Cliente cliente) {
		StringBuffer sql = new StringBuffer();
		try {
			cliente.setId(this.gerarChave());
			sql = new StringBuffer("INSERT INTO CLIENTE(" +
					 "CODIGO_CLIENTE,NOME,ENDERECO,OBSERVACAO,DLATITUDE,DLONGITUDE,CODIGO_MUNICIPIO) VALUES (");
			if (cliente.getId() != null && cliente.getId() > 0)
				sql.append(cliente.getId() + ",");
			else
				sql.append("null,");
			if (cliente.getNome() != null && !cliente.getNome().equals(""))
				sql.append("'" + cliente.getNome() + "',");
			else
				sql.append("null,");
			if (cliente.getEndereco() != null && !cliente.getEndereco().equals(""))
				sql.append("'" + cliente.getEndereco() + "',");
			else
				sql.append("null,");
			if (cliente.getObservacao() != null && !cliente.getObservacao().equals(""))
				sql.append("'" + cliente.getObservacao() + "',");
			else
				sql.append("null,");
			if (cliente.getDlatitude() != null && cliente.getDlatitude() > 0)
				sql.append("'" + cliente.getDlatitude() + "',");
			else
				sql.append("null,");
			if (cliente.getDlongitude() != null && cliente.getDlongitude() > 0)
				sql.append("'" + cliente.getDlongitude() + "',");
			else
				sql.append("null,");
			if (cliente.getMunicipio() != null && cliente.getMunicipio().getId() > 0)
				sql.append("'" +  cliente.getMunicipio().getId() + "')");
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
	
	public void atualizar(Cliente cliente) {
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer("UPDATE CLIENTE SET ");			
			if (cliente.getNome() != null && !cliente.getNome().equals("")){
				sql.append("NOME ='" + cliente.getNome() + "'");
			}	
			if (cliente.getEndereco() != null && !cliente.getEndereco().equals("")) {
				sql.append(",ENDERECO ='" + cliente.getEndereco() + "'");
			}	
			if (cliente.getObservacao() != null && !cliente.getObservacao().equals("")) {
				sql.append(",OBSERVACAO ='" + cliente.getObservacao() + "'");
			}	
			if (cliente.getDlatitude() != null && cliente.getDlongitude() > 0) {
				sql.append(",DLATITUDE ='" + cliente.getDlatitude() + "'");
			}		
			if (cliente.getDlongitude() != null && cliente.getDlongitude() > 0) {
				sql.append(",DLONGITUDE ='" + cliente.getDlongitude() + "'");
			}		
			if (cliente.getMunicipio() != null && cliente.getMunicipio().getId() > 0) {
				sql.append(",CODIGO_MUNICIPIO ='" + cliente.getMunicipio().getId() + "'");
			}	
			sql.append(" WHERE CODIGO_CLIENTE = " + cliente.getId());
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
	
	
	
	protected Cliente resultadoParaBean(ResultSet resultado) throws Exception {
		Cliente retorno = new Cliente();
		Municipio municipio = new Municipio();
		MunicipioDao municipioDao = new MunicipioDao();
		try {
			retorno.setId(resultado.getInt("CODIGO_CLIENTE"));
			retorno.setNome(resultado.getString("NOME"));
			retorno.setEndereco(resultado.getString("ENDERECO"));
			retorno.setObservacao(resultado.getString("OBSERVACAO"));		
			retorno.setDlatitude(resultado.getDouble("DLATITUDE"));
			retorno.setDlongitude(resultado.getDouble("DLONGITUDE"));
			municipio = new Municipio();
			municipio.setId(resultado.getInt("CODIGO_MUNICIPIO"));
			retorno.setMunicipio(municipioDao.consulta(municipio).get(0));
			retorno.setUf(retorno.getMunicipio().getUf());
		} catch (Exception e) {
			System.out.println(e.getMessage());		
		}			
		return retorno;
	}
	
	public ArrayList<Cliente> consulta(Cliente cliente) {
		ArrayList <Cliente> retorno = new ArrayList<Cliente>();
		StringBuffer sql = new StringBuffer();
		try {
			Cliente clienteAux = new Cliente();
			sql = new StringBuffer("SELECT * FROM CLIENTE WHERE 1=1");
			if (cliente.getId() != null && cliente.getId() > 0) {
				sql.append(" AND CODIGO_CLIENTE = " +cliente.getId());
			}
			if (cliente.getNome() != null && !cliente.getNome().equals("")){
				sql.append(" AND NOME like " + "'%" + cliente.getNome() + "%'");
			}
			if (cliente.getMunicipio() != null && cliente.getMunicipio().getId() > 0) {
				sql.append(" AND CODIGO_MUNICIPIO = '" + cliente.getMunicipio().getId() + "'");
			}
			sql.append(" ORDER BY NOME");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				clienteAux = resultadoParaBean(resultado);
				retorno.add(clienteAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public ArrayList<Cliente> clientePorUF(UF uf) {
		ArrayList <Cliente> retorno = new ArrayList<Cliente>();
		StringBuffer sql = new StringBuffer();
		try {
			Cliente clienteAux = new Cliente();
			sql = new StringBuffer("SELECT cli.CODIGO_CLIENTE,cli.NOME,cli.ENDERECO,cli.OBSERVACAO,"
					+ "cli.DLATITUDE,cli.DLONGITUDE,cli.CODIGO_MUNICIPIO"
					+ " FROM CLIENTE cli,MUNICIPIO mun");	
			sql.append(" WHERE cli.CODIGO_MUNICIPIO = mun.CODIGO_MUNICIPIO");
			if (uf != null && uf.getId() > 0) {
				sql.append(" AND mun.CODIGO_UF = '" + uf.getId() + "'");
			}
			sql.append(" ORDER BY cli.NOME");
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				clienteAux = resultadoParaBean(resultado);
				retorno.add(clienteAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}			
		return retorno; 
	}

	
	
	public ArrayList<Cliente> todos(String ord) {
		ArrayList <Cliente> retorno = new ArrayList<Cliente>();
		StringBuffer sql = new StringBuffer();
		try {
			Cliente clienteAux = new Cliente();
			sql = new StringBuffer("SELECT * FROM CLIENTE");
			if (ord != null && !ord.equals("")) {
				sql.append(" ORDER BY " + ord);
			}
			// Conecta com o banco de dados
			this.conectar();
			Statement stmt = con.createStatement();
			ResultSet resultado = stmt.executeQuery(sql.toString());
			while (resultado.next()) {
				clienteAux = resultadoParaBean(resultado);
				retorno.add(clienteAux);				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - " + sql.toString());
		} finally {
			this.desconectar();	
		}			
		return retorno;
	}
	
	public void excluir(Cliente cliente) {
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer("DELETE FROM CLIENTE");
			sql.append(" WHERE CODIGO_CLIENTE =" + cliente.getId());
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
	
	
	
}
