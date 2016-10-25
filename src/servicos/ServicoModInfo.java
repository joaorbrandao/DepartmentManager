package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


class Mod{
	int mod_id, ativo, estado;
	String mod_nome, timestamp;
}


/**
 * Servlet implementation class ServicoModInfo
 */
@WebServlet("/ServicoModInfo")
public class ServicoModInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoModInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private int util_id = 0;
    
    
    
	public int getUtil_id() {
		return util_id;
	}
	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}


	private List<Mod> getMod(){
		List<Mod> listaFinal = new ArrayList<Mod>();
		try{
			String cmd = "SELECT * FROM modalidades WHERE ativo=1";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			
			while(rs.next()){
				Mod mod = new Mod();
				mod.mod_id = Integer.parseInt(rs.getString("mod_id"));
				mod.mod_nome = rs.getString("mod_nome");
				mod.timestamp = null;
				mod.ativo = Integer.parseInt(rs.getString("ativo"));
				mod.estado = 0;
				
				listaFinal.add(mod);
			}
			rs.close();
			s.close ();
			conn.close();
			
			return listaFinal;			
		} catch(Exception e){
			e.printStackTrace();
		}
		return listaFinal;
	}
	
	private List<Mod> getUtilMod(List<Mod> listaFinal){
		try{
			String cmd = "SELECT * FROM utilizadores_modalidades WHERE util_id="+getUtil_id();
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				int i = 0;
				for(i = 0; i < listaFinal.size(); i++){
					if((Integer.parseInt(rs.getString("mod_id"))) == listaFinal.get(i).mod_id){
						listaFinal.get(i).estado = Integer.parseInt(rs.getString("estado"));
					}
					listaFinal.get(i).timestamp = rs.getString("timestamp");
				}
			}
			rs.close();
			s.close ();
			conn.close();
			
			return listaFinal;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return listaFinal;
	}
	
	private String listToJson(List<Mod> listaMod){
		List<String> listaFinal = new ArrayList<String>();

		int i = 0;
		for(i = 0; i < listaMod.size(); i++){
			JSONObject obj = new JSONObject();
			
			obj.put("mod_id", listaMod.get(i).mod_id);
			obj.put("mod_nome", listaMod.get(i).mod_nome);
			obj.put("timestamp", listaMod.get(i).timestamp);
			obj.put("ativo", listaMod.get(i).ativo);
			obj.put("estado", listaMod.get(i).estado);
			
			listaFinal.add(JSONValue.toJSONString(obj));
		}
		
		String resultadoJSON = JSONValue.toJSONString(listaFinal);
		return resultadoJSON;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Ler o util_id dos cookies
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		Date date = new Date();
		boolean flag = false;
		for(int i = 0; i < cookies.length; i++){
			cookie = cookies[i];
			if((cookie.getName()).compareTo("util_id") == 0 ){
				//set util_id
				setUtil_id(Integer.parseInt(cookie.getValue()));
				break;
			}
		}
		
		//Obter lista de modalidades ativas
		List<Mod> listaMod = new ArrayList<Mod>();
		listaMod = getMod();
		
		if(listaMod.isEmpty()){
			//Não há modalidades ativas
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(false);
		}else{
			//Há pelo menos uma modalidade ativa
			
			//Obter as modalidades em que o utilizador está inscrito
			listaMod = getUtilMod(listaMod);
			String listaModJson = listToJson(listaMod);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(listaModJson);
		}
	}

}
