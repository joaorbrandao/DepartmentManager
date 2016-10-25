package login;

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

import servicos.ServicoLogin;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String baseDeDados = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
    
    private int getUtilId(String form_email){
    	try{
    		int util_id = 0;
			String cmd = "SELECT util_id FROM utilizadores WHERE util_email=\""+form_email+"\";";
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			
			while(rs.next()){
				util_id = Integer.parseInt(rs.getString("util_id"));
			}
			rs.close();
			s.close ();
			conn.close();
			
			return util_id;
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
    }
    private int getAutNivel(int util_id){
    	try{
    		int aut_nivel = 0;
			String cmd = "SELECT aut_nivel FROM autenticacao WHERE util_id=\""+util_id+"\";";
			String bd = baseDeDados;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			
			while(rs.next()){
				aut_nivel = Integer.parseInt(rs.getString("aut_nivel"));
			}
			
			rs.close();
			s.close ();
			conn.close();
			
			return aut_nivel;
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
    }
    
    private void setCookies(int util_id, HttpServletRequest request, HttpServletResponse response){
    	Date date = new Date();
    	//Cria Cookie
        Cookie util_id_cookie = new Cookie("util_id", Integer.toString(util_id));
        Cookie code_cookie = new Cookie("code", Long.toString(date.getTime()));

        //Prazo de validade do Cookie 24h
        util_id_cookie.setMaxAge(60*60*5);//segundos*minutos*horas
        code_cookie.setMaxAge(60*60*5);

        //Adicionar Cookie no cabeçalho da resposta HTTP
        response.addCookie(util_id_cookie);
        response.addCookie(code_cookie);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String form_email, form_pass;
		
		form_email = request.getParameter("form_email");
		form_pass = request.getParameter("form_pass");
		
		ServicoLogin servicoLogin = new ServicoLogin();
		boolean autRes = servicoLogin.autenticacao(form_email, form_pass);
		
		if(autRes){
			//Login correto
			int util_id = getUtilId(form_email);
			int aut_nivel = getAutNivel(util_id);
			//Criar Cookies de sessão para o utilizador
			setCookies(util_id, request, response);
			//Navegar para o perfil
			if(aut_nivel == 0){
				//Utilizador Normal
				response.sendRedirect("home.html");
			}else{
				//Administrador
				response.sendRedirect("homeA.html");
			}
			
		}
		else{
			//Login errado
			//Apresentar Login e msg de erro
			//response.sendRedirect("login.html");
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Erro: Credenciais inválidas!');"); 
			out.println("location='login.html';");
			out.println("</script>");
		}
	}

}
