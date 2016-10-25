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
 * Servlet implementation class ServicoProvas
 */
@WebServlet("/ServicoProvas")
public class ServicoProvas extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoProvas() {
        super();
        // TODO Auto-generated constructor stub
    }

    
  //Função que devolve uma lista de objetos [{"mod_id":1,"mod_nome":Karting},...]
    private List<String> listaDeObjetos(List<String> provaId, List<String> provaNome){
    	List<String> listaFinal = new ArrayList<String>();
    	int i = 0;
    	
    	for(i = 0; i < provaId.size(); i++){
    		JSONObject obj = new JSONObject();
    		obj.put("prova_id", provaId.get(i));
    		obj.put("prova_nome", provaNome.get(i));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
    	}
    	
    	return listaFinal;
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String cmd = "SELECT * FROM provas WHERE ativo=1;";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> resultsProvaId = new ArrayList<String>();
			List<String> resultsProvaNome = new ArrayList<String>();

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			
			while(rs.next()){
				resultsProvaId.add(rs.getString("prova_id"));
				resultsProvaNome.add(rs.getString("prova_nome"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			//Devolve lista de objetos JSON [{"mod_id":1,"mod_nome":Karting},...]
			List<String> resultadoObj = listaDeObjetos(resultsProvaId, resultsProvaNome);
			
			
			String resultadoJSON = JSONValue.toJSONString(resultadoObj);
			out.print(resultadoJSON);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
