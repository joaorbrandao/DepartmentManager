package servicos;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;

@WebServlet("/ServicoModalidades")
public class ServicoModalidades extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public ServicoModalidades() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //Função que devolve uma lista de objetos [{"mod_id":1,"mod_nome":Karting},...]
    private List<String> listaDeObjetos(List<String> modId, List<String> modNome){
    	List<String> listaFinal = new ArrayList<String>();
    	int i = 0;
    	
    	for(i = 0; i < modId.size(); i++){
    		JSONObject obj = new JSONObject();
    		obj.put("mod_id", modId.get(i));
    		obj.put("mod_nome", modNome.get(i));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
    	}
    	
    	return listaFinal;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
		try{
			String cmd = "SELECT * FROM modalidades WHERE ativo=1;";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> resultsModId = new ArrayList<String>();
			List<String> resultsModNome = new ArrayList<String>();

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			
			while(rs.next()){
				resultsModId.add(rs.getString("mod_id"));
				resultsModNome.add(rs.getString("mod_nome"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			//Devolve lista de objetos JSON [{"mod_id":1,"mod_nome":Karting},...]
			List<String> resultadoObj = listaDeObjetos(resultsModId, resultsModNome);
			
			
			String resultadoJSON = JSONValue.toJSONString(resultadoObj);
			out.print(resultadoJSON);
			
		} catch(Exception e){
			e.printStackTrace();
		}
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
