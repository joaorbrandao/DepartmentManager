package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class AdminServicoProvaPedido
 */
@WebServlet("/AdminServicoProvaPedido")
public class AdminServicoProvaPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminServicoProvaPedido() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    private int util_id = 0;
    private String util_nome = null;
    private int prova_id = 0;
    private String prova_nome = null;
	
    public int getUtil_id() {
		return util_id;
	}
	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}
	public String getUtil_nome() {
		return util_nome;
	}
	public void setUtil_nome(String util_nome) {
		this.util_nome = util_nome;
	}
	public int getProva_id() {
		return prova_id;
	}
	public void setProva_id(int prova_id) {
		this.prova_id = prova_id;
	}
	public String getProva_nome() {
		return prova_nome;
	}
	public void setProva_nome(String prova_nome) {
		this.prova_nome = prova_nome;
	}
    
	
	private boolean aceitaInscricao(boolean aceitaTrue_removeFalse ,JSONArray array){
    	try{
    		JSONObject obj = (JSONObject)array.get(0);
    		setUtil_nome((String) obj.get("name"));
    		setProva_nome((String) obj.get("value"));
    		
    		//Obter util_id com base no util_nome
    		String cmd = "SELECT * FROM utilizadores WHERE util_nome=\""+getUtil_nome()+"\";";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				setUtil_id(Integer.parseInt(rs.getString("util_id")));
			}
			rs.close();
			s.close ();
			conn.close();
			
    		//Obter mod_id com base no mod_nome
			String cmd1 = "SELECT * FROM provas WHERE prova_nome=\""+getProva_nome()+"\";";
			String bd1 = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn1 = DriverManager.getConnection(bd1);
			Statement s1 = conn1.createStatement();
			s1.executeQuery(cmd1);
			ResultSet rs1 = s1.getResultSet();
			
			while(rs1.next()){
				setProva_id(Integer.parseInt(rs1.getString("prova_id")));
			}
			rs1.close();
			s1.close ();
			conn1.close();
			
    		//Fazer Update à inscrição
			boolean flag = false;
			if(aceitaTrue_removeFalse == true){
				//Aceita inscrição
				String cmd2 = "UPDATE utilizadores_provas SET estado=2 WHERE util_id="+getUtil_id()+" and prova_id="+getProva_id()+";";
				String bd2 = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn2 = DriverManager.getConnection(bd2);
				Statement s2 = conn2.createStatement();
				int res2 = s2.executeUpdate(cmd2);
				
				s2.close ();
				conn2.close();
				
				flag = true;
			}else{
				//Recusa inscrição
				String cmd2 = "UPDATE utilizadores_provas SET estado=0 WHERE util_id="+getUtil_id()+" and prova_id="+getProva_id()+";";
				String bd2 = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn2 = DriverManager.getConnection(bd2);
				Statement s2 = conn2.createStatement();
				int res2 = s2.executeUpdate(cmd2);
				
				s2.close ();
				conn2.close();
				
				flag = true;
			}
    		
			
			return flag;
			
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
    }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String form_prova = "["+request.getParameter("form_prova")+"]";
		String botao = request.getParameter("botao");
		
		if(form_prova.equals("[false]")){
			//Nenhum pedido de inscrição
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Nenhuma inscrição pendente!');");
			out.println("location='administracaoA.html';");
			out.println("</script>");
		}else{
			if(botao.equals("Aceitar")){
				//Aceitar inscrição
				Object obj = JSONValue.parse(form_prova);
				JSONArray array = (JSONArray)obj;
				boolean verificaInscricao = aceitaInscricao(true ,array);
				if(verificaInscricao == false){
					//Erro a aceitar inscricao
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Erro: Erro ao aceitar inscrição!');");
					out.println("location='administracaoA.html';");
					out.println("</script>");
				}else{
					//Inscricao aceite
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('"+getUtil_nome()+" aceite na prova "+getProva_nome()+"!');");
					out.println("location='administracaoA.html';");
					out.println("</script>");
				}
			}else{
				//Recusar inscrição
				Object obj = JSONValue.parse(form_prova);
				JSONArray array = (JSONArray)obj;
				boolean verificaInscricao = aceitaInscricao(false ,array);
				if(verificaInscricao == false){
					//Erro a recusar inscricao
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Erro: Erro ao recusar inscrição!');");
					out.println("location='administracaoA.html';");
					out.println("</script>");
				}else{
					//Inscricao recusada
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('"+getUtil_nome()+" recusado na prova "+getProva_nome()+"!');");
					out.println("location='administracaoA.html';");
					out.println("</script>");
				}
			}
		}
	}

}
