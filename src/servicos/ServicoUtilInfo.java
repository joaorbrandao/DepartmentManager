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
import org.json.simple.parser.JSONParser;

/**
 * Servlet implementation class ServicoUtilInfo
 */
@WebServlet("/ServicoUtilInfo")
public class ServicoUtilInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoUtilInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    /*
     * Declarar variáveis e respetivos SET/GET para guardar info dos utilizadores
     * 
     * */
    private int util_id = 0;
    private String util_email = null;
	private String util_pass = null;
	private String util_nome = null;
	private String util_data_nascimento = null;
	private char util_sexo;
	private String util_telefone = null;
	private String util_cartao_cidadao = null;
	private int mod_id = 0;
	
	
    
    public int getUtil_id() {
		return util_id;
	}

	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}

	public String getUtil_email() {
		return util_email;
	}

	public void setUtil_email(String util_email) {
		this.util_email = util_email;
	}

	public String getUtil_pass() {
		return util_pass;
	}

	public void setUtil_pass(String util_pass) {
		this.util_pass = util_pass;
	}

	public String getUtil_nome() {
		return util_nome;
	}

	public void setUtil_nome(String util_nome) {
		this.util_nome = util_nome;
	}

	public String getUtil_data_nascimento() {
		return util_data_nascimento;
	}

	public void setUtil_data_nascimento(String util_data_nascimento) {
		this.util_data_nascimento = util_data_nascimento;
	}

	public char getUtil_sexo() {
		return util_sexo;
	}

	public void setUtil_sexo(char util_sexo) {
		this.util_sexo = util_sexo;
	}

	public String getUtil_telefone() {
		return util_telefone;
	}

	public void setUtil_telefone(String util_telefone) {
		this.util_telefone = util_telefone;
	}

	public String getUtil_cartao_cidadao() {
		return util_cartao_cidadao;
	}

	public void setUtil_cartao_cidadao(String util_cartao_cidadao) {
		this.util_cartao_cidadao = util_cartao_cidadao;
	}

	public int getMod_id() {
		return mod_id;
	}

	public void setMod_id(int mod_id) {
		this.mod_id = mod_id;
	}

	
	//Função que devolve uma lista de objetos [{"mod_id":1,"mod_nome":Karting},...]
    private List<String> listaDeObjetos(List<String> utilInfo){
    	List<String> listaFinal = new ArrayList<String>();
    	int i = 0;
    	
    	for(i = 0; i < utilInfo.size(); i++){
    		JSONObject obj = new JSONObject();
    		obj.put("mod_id", utilInfo.get(i));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
    	}
    	
    	return listaFinal;
    }
	
	private void getUtilInfo(HttpServletRequest request, HttpServletResponse response){
    	try{
			String cmd = "SELECT * FROM utilizadores WHERE util_id="+getUtil_id();
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> resultsUtilInfo = new ArrayList<String>();

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			
			while(rs.next()){
				resultsUtilInfo.add(rs.getString("util_id"));
				resultsUtilInfo.add(rs.getString("util_email"));
				resultsUtilInfo.add(rs.getString("util_nome"));
				resultsUtilInfo.add(rs.getString("util_data_nascimento"));
				resultsUtilInfo.add(rs.getString("util_sexo"));
				resultsUtilInfo.add(rs.getString("util_telefone"));
				resultsUtilInfo.add(rs.getString("util_cartao_cidadao"));
				resultsUtilInfo.add(rs.getString("mod_id"));
				resultsUtilInfo.add(rs.getString("ativo"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			//Devolve lista de objetos JSON [{"mod_id":1,"mod_nome":Karting},...]
			//List<String> resultadoObj = listaDeObjetos(resultsUtilInfo);
			List<String> listaFinal = new ArrayList<String>();
			
			JSONObject obj = new JSONObject();
    		obj.put("util_id", resultsUtilInfo.get(0));
    		obj.put("util_email", resultsUtilInfo.get(1));
    		obj.put("util_nome", resultsUtilInfo.get(2));
    		obj.put("util_data_nascimento", resultsUtilInfo.get(3));
    		obj.put("util_sexo", resultsUtilInfo.get(4));
    		obj.put("util_telefone", resultsUtilInfo.get(5));
    		obj.put("util_cartao_cidadao", resultsUtilInfo.get(6));
    		obj.put("mod_id", resultsUtilInfo.get(7));
    		obj.put("ativo", resultsUtilInfo.get(8));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
			
			
			
			String resultadoJSON = JSONValue.toJSONString(listaFinal);
			out.print(resultadoJSON);
			
		} catch(Exception e){
			e.printStackTrace();
		}
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*JSONParser parser = new JSONParser();
		Object obj = JSONValue.parse(request.getParameter("toServer"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String jsonText = obj.toString();
		out.print(jsonText);*/
		
		/*
		 * Read cookies to get util_id
		 * Call function to read use util information (pass util_id)
		 * */
		
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
		//Obtém a informação do utilizador
		getUtilInfo(request, response);
	}
}
