package servicos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoRegisto {

	//Variaveis do form
	private String form_email = null;
	private String form_pass = null;
	private String form_nome = null;
	private String form_data_nascimento = null;
	private char form_sexo;
	private String form_telefone = null;
	private String form_cartao_cidadao = null;
	private int form_mod = 0;
	
	private int util_id = 0;
	
	//Base de Dados
	private String baseDeDados = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
	
	
	public void setForm_email(String form_email) {
		this.form_email = form_email;
	}
	public void setForm_pass(String form_pass) {
		this.form_pass = form_pass;
	}
	public void setForm_nome(String form_nome) {
		this.form_nome = form_nome;
	}
	public void setForm_data_nascimento(String form_data_nascimento) {
		this.form_data_nascimento = form_data_nascimento;
	}
	public void setForm_sexo(char form_sexo) {
		this.form_sexo = form_sexo;
	}
	public void setForm_telefone(String form_telefone) {
		this.form_telefone = form_telefone;
	}
	public void setForm_cartao_cidadao(String form_cartao_cidadao) {
		this.form_cartao_cidadao = form_cartao_cidadao;
	}
	public void setForm_mod(int form_mod) {
		this.form_mod = form_mod;
	}
	
	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}
	public String getForm_email() {
		return form_email;
	}
	public String getForm_pass() {
		return form_pass;
	}
	public String getForm_nome() {
		return form_nome;
	}
	public String getForm_data_nascimento() {
		return form_data_nascimento;
	}
	public char getForm_sexo() {
		return form_sexo;
	}
	public String getForm_telefone() {
		return form_telefone;
	}
	public String getForm_cartao_cidadao() {
		return form_cartao_cidadao;
	}
	public int getForm_mod() {
		return form_mod;
	}
	public int getUtil_id() {
		return util_id;
	}

	
	private boolean verificaEmail(){
		try{
			String query_verificaEmail = "SELECT util_email FROM utilizadores;";
			String cmd = query_verificaEmail;
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> bd_emails = new ArrayList<String>();
			
			while(rs.next()){
				bd_emails.add(rs.getString("util_email"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			int i = 0;
			boolean flag = true;
			for(i=0; i<bd_emails.size(); i++){
				String auxiliar = bd_emails.get(i);
				if(auxiliar.equals(getForm_email())){
					flag = false;
					break;
				}
				else{
					flag = true;
				}
			}
			return flag;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean registaUtilizador(){
		try{
			String query_utilizadores = "INSERT INTO utilizadores(util_email,util_nome,util_data_nascimento,util_sexo,util_telefone,util_cartao_cidadao,mod_id) VALUES(\"" + getForm_email() + "\",\"" + getForm_nome() + "\",\"" + getForm_data_nascimento() + "\",\"" + getForm_sexo() + "\",\"" + getForm_telefone() + "\",\"" + getForm_cartao_cidadao() + "\"," + getForm_mod() + ");";
			String cmd = query_utilizadores;
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			int res = s.executeUpdate(cmd);

			s.close ();
			conn.close();
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean obterUtilizadorNovo(){
		try{
			String query_util_id = "SELECT util_id FROM utilizadores WHERE util_email=\"" + getForm_email() + "\";";
			String cmd = query_util_id;
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				setUtil_id(rs.getInt("util_id"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean registaAutenticacao(){
		try{
			String query_autenticacao = "INSERT INTO autenticacao(util_id,aut_password,aut_nivel) VALUES(" + getUtil_id() + ",\"" + getForm_pass() + "\",0);";
			String cmd = query_autenticacao;
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			int res = s.executeUpdate(cmd);

			s.close ();
			conn.close();
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean registaUtilizadorModalidade(){
		try{
			String query_utilizador_modalidade = "INSERT INTO utilizadores_modalidades(util_id,mod_id,estado) VALUES(" + getUtil_id() + "," + getForm_mod() + ",2);";
			String cmd = query_utilizador_modalidade;
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			int res = s.executeUpdate(cmd);

			s.close ();
			conn.close();
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean registo(){
		boolean flag = false;
		flag = verificaEmail();
		if(flag == true){
			flag = registaUtilizador();
			if(flag == true){
				flag = obterUtilizadorNovo();
				if(flag == true){
					flag = registaAutenticacao();
					if(flag == true){
						flag = registaUtilizadorModalidade();
						if(flag == true){
							return true;
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}	
}
