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
 * Servlet implementation class AdminServicoProvasInfo
 */
@WebServlet("/AdminServicoProvasInfo")
public class AdminServicoProvasInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminServicoProvasInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    private List<List<String>> getPedidosUtil(){
		List<List<String>> resultado = new ArrayList<List<String>>();
		try{
			String cmd = "SELECT * FROM utilizadores_provas WHERE estado=1";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> resultsUtilId = new ArrayList<String>();
			List<String> resultsProvaId = new ArrayList<String>();
			
			while(rs.next()){
				resultsUtilId.add(rs.getString("util_id"));
				resultsProvaId.add(rs.getString("prova_id"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			
			resultado.add(resultsUtilId);
			resultado.add(resultsProvaId);
			
			return resultado;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return resultado;
	}
    
	private List<List<String>> getNomes(List<List<String>> pedidos){
		try{
			String cmd = "SELECT * FROM utilizadores";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			List<String> listaUtilId = new ArrayList<String>();
			List<String> listaUtilNome = new ArrayList<String>();
			
			while(rs.next()){
				listaUtilId.add(rs.getString("util_id"));
				listaUtilNome.add(rs.getString("util_nome"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			
			int i = 0;int j = 0;
			//Ciclo que troca os util_id pelos respetivos util_nome
			for(i = 0; i < pedidos.get(0).size(); i++){
				for(j = 0; j < listaUtilId.size(); j++){
					if(pedidos.get(0).get(i).equals(listaUtilId.get(j))){
						pedidos.get(0).set(i, listaUtilNome.get(j));
						break;
					}
				}
			}
			
			String cmd1 = "SELECT * FROM provas";
			String bd1 = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn1 = DriverManager.getConnection(bd1);
			Statement s1 = conn1.createStatement();
			s1.executeQuery(cmd1);
			ResultSet rs1 = s1.getResultSet();
			
			List<String> listaProvaId = new ArrayList<String>();
			List<String> listaProvaNome = new ArrayList<String>();
			
			while(rs1.next()){
				listaProvaId.add(rs1.getString("prova_id"));
				listaProvaNome.add(rs1.getString("prova_nome"));
			}
			rs1.close();
			s1.close ();
			conn1.close();
			
			//Ciclo que troca os mod_id pelos respetivos mod_nome
			for(i = 0; i < pedidos.get(1).size(); i++){
				for(j = 0; j < listaUtilId.size(); j++){
					if(pedidos.get(1).get(i).equals(listaProvaId.get(j))){
						pedidos.get(1).set(i, listaProvaNome.get(j));
						break;
					}
				}
			}
			
			return pedidos;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return pedidos;
	}
	
	//Função que devolve uma lista de objetos [{"name":"João Rito Brandão","value":CNU - Karting},...]
    private List<String> listaDeObjetos(List<List<String>> pedidos){
    	List<String> listaFinal = new ArrayList<String>();
    	int i = 0;
    	
    	for(i = 0; i < pedidos.get(0).size(); i++){
			JSONObject obj = new JSONObject();
    		obj.put("name", pedidos.get(0).get(i));
    		obj.put("value", pedidos.get(1).get(i));
    		
    		listaFinal.add(JSONValue.toJSONString(obj));
    	}
    	
    	return listaFinal;
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<List<String>> pedidos = getPedidosUtil();
		
		if(pedidos.get(0).isEmpty() || pedidos.get(1).isEmpty()){
			//Não há pedidos para inscrição
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print("false");
		}else{
			//Há pedidos para inscrição
			pedidos = getNomes(pedidos);
			
			List<String> resultadoObj = listaDeObjetos(pedidos);
			
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String resultadoJSON = JSONValue.toJSONString(resultadoObj);
			out.print(resultadoJSON);
		}
	}

}
