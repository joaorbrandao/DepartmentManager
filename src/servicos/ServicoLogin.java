package servicos;

import java.sql.*;

public class ServicoLogin {

	public boolean autenticacao(String form_email, String form_pass){
		try{
			String cmd = "SELECT util_id FROM utilizadores WHERE util_email=\""+form_email+"\";";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				int util_id = rs.getInt("util_id");
				
				if(util_id != 0){
					//Verificar Password
					String cmd_aut = "SELECT aut_password FROM autenticacao WHERE util_id=\""+util_id+"\";";
					Statement s_aut = conn.createStatement();
					s_aut.executeQuery(cmd_aut);
					ResultSet rs_aut = s_aut.getResultSet();
					
					while(rs_aut.next()){
						String aut_pass = rs_aut.getString("aut_password");
						if(aut_pass.equals(form_pass)){
							return true;
						}
					}
					rs_aut.close();
					s_aut.close();
				}
				else{
					return false;
				}
			}
			rs.close();
			s.close ();
			conn.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
