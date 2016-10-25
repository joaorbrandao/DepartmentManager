package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class ServicoUtilizadores
 */
@WebServlet("/ServicoUtilizadores")
public class ServicoUtilizadores extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoUtilizadores() {
        super();
        // TODO Auto-generated constructor stub
    }

  //Função que devolve uma lista de objetos [{"mod_id":1,"mod_nome":Karting},...]
    private List<String> listaDeObjetos(List<String> utilId, List<String> utilNome){
    	List<String> listaFinal = new ArrayList<String>();
    	int i = 0;
    	
    	for(i = 0; i < utilId.size(); i++){
    		JSONObject obj = new JSONObject();
    		obj.put("util_id", utilId.get(i));
    		obj.put("util_nome", utilNome.get(i));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
    	}
    	
    	return listaFinal;
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String cmd = "SELECT * FROM utilizadores WHERE ativo=1;";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> resultsUtilId = new ArrayList<String>();
			List<String> resultsUtilNome = new ArrayList<String>();

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			
			while(rs.next()){
				resultsUtilId.add(rs.getString("util_id"));
				resultsUtilNome.add(rs.getString("util_nome"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			//Devolve lista de objetos JSON [{"util_id":1,"util_nome":João Rito Brandão},...]
			List<String> resultadoObj = listaDeObjetos(resultsUtilId, resultsUtilNome);
			
			
			String resultadoJSON = JSONValue.toJSONString(resultadoObj);
			out.print(resultadoJSON);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
