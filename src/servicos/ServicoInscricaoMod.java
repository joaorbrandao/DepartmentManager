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

/**
 * Servlet implementation class ServicoInscricaoMod
 */
@WebServlet("/ServicoInscricaoMod")
public class ServicoInscricaoMod extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServicoInscricaoMod() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    private int util_id = 0;
    private int form_mod = 0;
    private int aut_nivel = 0;
    private int estado = 1;
    
    public int getUtil_id() {
		return util_id;
	}
	public void setUtil_id(int util_id) {
		this.util_id = util_id;
	}
	public int getForm_mod() {
		return form_mod;
	}
	public void setForm_mod(int form_mod) {
		this.form_mod = form_mod;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getAut_nivel() {
		return aut_nivel;
	}
	public void setAut_nivel(int aut_nivel) {
		this.aut_nivel = aut_nivel;
	}
	
	
	
	
	private boolean getAutNivel(){
		try{
			String cmd = "SELECT aut_nivel FROM autenticacao WHERE util_id="+getUtil_id();
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			while(rs.next()){
				if(Integer.parseInt(rs.getString("aut_nivel")) == 0){
					//Utilizador não é administrador logo a inscrição precisa de aprovação
					setAut_nivel(0); //estado=1 - PENDENTE
				}
				if(Integer.parseInt(rs.getString("aut_nivel")) == 1){
					//Utilizador é administrador logo a inscrição não precisa de aprovação
					setAut_nivel(1); //estado=2 - INSCRITO
				}
			}
			rs.close();
			s.close ();
			conn.close();
			
			return true;
			
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean verificaInscricao(){
		try{
			String cmd = "SELECT * FROM utilizadores_modalidades WHERE util_id="+getUtil_id()+" and mod_id="+getForm_mod()+";";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			s.executeQuery(cmd);
			ResultSet rs = s.getResultSet();
			
			boolean flag = false;
			while(rs.next()){
				flag = true;
			}
			rs.close();
			s.close ();
			conn.close();
			
			return flag;
			
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
    }
	
	private boolean inscreveMod(boolean op){
		if(getAut_nivel() == 0){
			//Utilizador não é administrdor
			if(op == true){
				//UPDATE
				try{
					String cmd = "UPDATE utilizadores_modalidades SET estado=1 WHERE util_id="+getUtil_id()+" and mod_id="+getForm_mod()+";";
					String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(bd);
					Statement s = conn.createStatement();
					int res = s.executeUpdate(cmd);
					
					s.close ();
					conn.close();
					
					return true;
					
				} catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}else{
				//INSERT
				try{
					String cmd = "INSERT INTO utilizadores_modalidades(util_id,mod_id,estado) VALUES ("+getUtil_id()+","+getForm_mod()+",1);";
					String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(bd);
					Statement s = conn.createStatement();
					int res = s.executeUpdate(cmd);
					
					s.close ();
					conn.close();
					
					return true;
					
				} catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
		}else{
			//Utilizador é um administrador
			if(op == true){
				//UPDATE
				try{
					String cmd = "UPDATE utilizadores_modalidades SET estado=2 WHERE util_id="+getUtil_id()+" and mod_id="+getForm_mod()+";";
					String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(bd);
					Statement s = conn.createStatement();
					int res = s.executeUpdate(cmd);
					
					s.close ();
					conn.close();
					
					return true;
					
				} catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}else{
				//INSERT
				try{
					String cmd = "INSERT INTO utilizadores_modalidades(util_id,mod_id,estado) VALUES ("+getUtil_id()+","+getForm_mod()+",2);";
					String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(bd);
					Statement s = conn.createStatement();
					int res = s.executeUpdate(cmd);
					
					s.close ();
					conn.close();
					
					return true;
					
				} catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
		}
    }
	
	private boolean removeInscricao(){
		try{
			String cmd = "UPDATE utilizadores_modalidades SET estado=0 and timestamp=now() WHERE util_id="+getUtil_id()+" and mod_id="+getForm_mod()+";";
			String bd = "jdbc:mysql://localhost/desportivobd?user=root&password=root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(bd);
			Statement s = conn.createStatement();
			int res = s.executeUpdate(cmd);
			
			s.close ();
			conn.close();
			
			return true;
			
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Obter Modalidade a que se pretende inscrever
		
		setForm_mod(Integer.parseInt(request.getParameter("form_mod")));
		
		//Obter util_id dos cookies
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
		
		//Verificar se o utilizador se quer inscrever ou Remover inscrição
		String opcao = request.getParameter("botao");
		if(opcao.equals("Inscrever")){
			//Utilizador pretende inscrever-se
			boolean nivel = getAutNivel();
			if(nivel == false){
				if(getAut_nivel() == 0){
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Erro: Verificação do nível de autenticação!');"); 
					out.println("location='home.html';");
					out.println("</script>");
				}else{
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Erro: Verificação do nível de autenticação!');"); 
					out.println("location='homeA.html';");
					out.println("</script>");
				}
			}else{
				boolean verificacao = verificaInscricao();
				if(verificacao == true){
					//Utilizador já está inscrito na modalidade
					boolean inscricao = inscreveMod(true);//UPDATE
					if(inscricao == false){
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro: Inscrição na modalidade!');"); 
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro: Inscrição na modalidade!');"); 
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}else{
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrito na modalidade com sucesso!');");
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrito na modalidade com sucesso!');");
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}
				}else{
					//Utilizador ainda não está inscrito logo pode inscrever-se
					boolean inscricao = inscreveMod(false);//INSERT
					if(inscricao == false){
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro: Inscrição na modalidade!');"); 
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro: Inscrição na modalidade!');"); 
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}else{
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrito na modalidade com sucesso!');");
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrito na modalidade com sucesso!');");
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}
				}
			}
		}else{
			//Utilizador pretende remover inscrição
			boolean nivel = getAutNivel();
			if(nivel == false){
				if(getAut_nivel() == 0){
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Erro: Verificação do nível de autenticação!');"); 
					out.println("location='home.html';");
					out.println("</script>");
				}else{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Erro: Verificação do nível de autenticação!');");
					out.println("location='homeA.html';");
					out.println("</script>");
				}
			}else{
				boolean verificacao = verificaInscricao();
				if(verificacao == false){
					//Utilizador não está inscrito logo não pode remover inscrição
					if(getAut_nivel() == 0){
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Não está inscrito na modalidade!');"); 
						out.println("location='home.html';");
						out.println("</script>");
					}else{
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Não está inscrito na modalidade!');");
						out.println("location='homeA.html';");
						out.println("</script>");
					}
				}else{
					//Utilizador está inscrito na modalidade logo pode ser removida a inscrição
					boolean verificaRemocao = removeInscricao();
					if(verificaRemocao == false){
						//Erro ao remover
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro:Impossível de remover inscrição!');"); 
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Erro:Impossível de remover inscrição!');"); 
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}else{
						//Remoção correta
						if(getAut_nivel() == 0){
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrição removida com sucesso!');"); 
							out.println("location='home.html';");
							out.println("</script>");
						}else{
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  
							out.println("<script type=\"text/javascript\">");  
							out.println("alert('Inscrição removida com sucesso!');"); 
							out.println("location='homeA.html';");
							out.println("</script>");
						}
					}
				}
			}
		}
	}

}
