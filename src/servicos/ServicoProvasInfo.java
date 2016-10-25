package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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


class Prova{
	int prova_id, mod_id, ativo, estado;
	String prova_nome, prova_hora, prova_data, prova_local;
	char prova_sexo;	
}

/**
 * Servlet implementation class ServicoProvasInfo
 */
@WebServlet("/ServicoProvasInfo")
public class ServicoProvasInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoProvasInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    private int util_id = 0;
    private int prova_id = 0;
    private int mod_id = 0;
	private String prova_nome = null;
	private String prova_hora = null;
	private String prova_data = null;
	private String prova_local = null;
	private char prova_sexo;
	private int ativo = 0;
	
	
   	public int getUtil_id() {
		return util_id;
	}
	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}
	public int getProva_id() {
		return prova_id;
	}
	public void setProva_id(int prova_id) {
		this.prova_id = prova_id;
	}
	public int getMod_id() {
		return mod_id;
	}
	public void setMod_id(int mod_id) {
		this.mod_id = mod_id;
	}
	public String getProva_nome() {
		return prova_nome;
	}
	public void setProva_nome(String prova_nome) {
		this.prova_nome = prova_nome;
	}
	public String getProva_hora() {
		return prova_hora;
	}
	public void setProva_hora(String prova_hora) {
		this.prova_hora = prova_hora;
	}
	public String getProva_data() {
		return prova_data;
	}
	public void setProva_data(String prova_data) {
		this.prova_data = prova_data;
	}
	public String getProva_local() {
		return prova_local;
	}
	public void setProva_local(String prova_local) {
		this.prova_local = prova_local;
	}
	public char getProva_sexo() {
		return prova_sexo;
	}
	public void setProva_sexo(char prova_sexo) {
		this.prova_sexo = prova_sexo;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	
	
	private List<Prova> getProvas(){
		List<Prova> listaFinal = new ArrayList<Prova>();
		try{
			String cmd = "SELECT * FROM provas WHERE ativo=1";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			
			while(rs.next()){
				Prova prova = new Prova();
				prova.prova_id = Integer.parseInt(rs.getString("prova_id"));
				prova.mod_id = Integer.parseInt(rs.getString("mod_id"));
				prova.prova_nome = rs.getString("prova_nome");
				prova.prova_hora = rs.getString("prova_hora");
				prova.prova_data = rs.getString("prova_data");
				prova.prova_local = rs.getString("prova_local");
				prova.prova_sexo = rs.getString("prova_sexo").charAt(0);
				prova.ativo = Integer.parseInt(rs.getString("ativo"));
				prova.estado = 0;
				
				listaFinal.add(prova);
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
	
	
	private String listToJson(List<Prova> listaProvas){
		List<String> listaFinal = new ArrayList<String>();

		int i = 0;
		for(i = 0; i < listaProvas.size(); i++){
			JSONObject obj = new JSONObject();
			
			obj.put("prova_id", listaProvas.get(i).prova_id);
			obj.put("mod_id", listaProvas.get(i).mod_id);
			obj.put("prova_nome", listaProvas.get(i).prova_nome);
			obj.put("prova_hora", listaProvas.get(i).prova_hora);
			obj.put("prova_data", listaProvas.get(i).prova_data);
			obj.put("prova_local", listaProvas.get(i).prova_local);
			obj.put("prova_sexo", listaProvas.get(i).prova_sexo+"");
			obj.put("ativo", listaProvas.get(i).ativo);
			obj.put("estado", listaProvas.get(i).estado);
			
			listaFinal.add(JSONValue.toJSONString(obj));
		}
		
		String resultadoJSON = JSONValue.toJSONString(listaFinal);
		return resultadoJSON;
	}
	
	private List<Prova> getUtilProvas(List<Prova> listaFinal){
		try{
			String cmd = "SELECT * FROM utilizadores_provas WHERE util_id="+getUtil_id();
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				int i = 0;
				for(i = 0; i < listaFinal.size(); i++){
					if((Integer.parseInt(rs.getString("prova_id"))) == listaFinal.get(i).prova_id){
						listaFinal.get(i).estado = Integer.parseInt(rs.getString("estado"));
					}
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
		
		//Obter lista de provas ativas
		List<Prova> listaProvas = new ArrayList<Prova>();
		listaProvas = getProvas();
		
		if(listaProvas.isEmpty()){
			//Não há provas ativas
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(false);
		}else{
			//Há pelo menos uma prova ativa
			
			//Obter as provas em que o utilizador está inscrito
			listaProvas = getUtilProvas(listaProvas);
			String listaProvasJson = listToJson(listaProvas);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(listaProvasJson);
		}
	}

}
