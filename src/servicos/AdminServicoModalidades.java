package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminServicoModalidades
 */
@WebServlet("/AdminServicoModalidades")
public class AdminServicoModalidades extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminServicoModalidades() {
        super();
        // TODO Auto-generated constructor stub
    }


    
    private int util_id = 0;
    private int form_mod = 0;
    private int aut_nivel = 1;
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
					//Utilizador n�o � administrador logo a inscri��o precisa de aprova��o
					setAut_nivel(1); //estado=1 - PENDENTE
				}
				if(Integer.parseInt(rs.getString("aut_nivel")) == 1){
					//Utilizador � administrador logo a inscri��o n�o precisa de aprova��o
					setAut_nivel(2); //estado=2 - INSCRITO
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
		if(getAut_nivel() == 1){
			//Utilizador n�o � administrdor
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
		}else{
			//Utilizador � um administrador
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
		setUtil_id(Integer.parseInt(request.getParameter("form_util")));
		
		//Verificar se o utilizador se quer inscrever ou Remover inscri��o
		String opcao = request.getParameter("botao");
		if(opcao.equals("Inscrever")){
			//Utilizador pretende inscrever-se
			boolean nivel = getAutNivel();
			if(nivel == false){
				PrintWriter out = response.getWriter();  
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Erro: Verifica��o do n�vel de autentica��o!');"); 
				out.println("location='administracaoA.html';");
				out.println("</script>");
			}else{
				boolean verificacao = verificaInscricao();
				if(verificacao == true){
					//Utilizador j� est� inscrito na modalidade
					boolean inscricao = inscreveMod(true);//UPDATE
					if(inscricao == false){
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Erro: Inscri��o na modalidade!');"); 
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}else{
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Inscrito na modalidade com sucesso!');");
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}
				}else{
					//Utilizador ainda n�o est� inscrito logo pode inscrever-se
					boolean inscricao = inscreveMod(false);//INSERT
					if(inscricao == false){
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Erro: Inscri��o na modalidade!');"); 
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}else{
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Inscrito na modalidade com sucesso!');");
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}
				}
			}
		}else{
			//Utilizador pretende remover inscri��o
			boolean nivel = getAutNivel();
			if(nivel == false){
				PrintWriter out = response.getWriter();  
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Erro: Verifica��o do n�vel de autentica��o!');"); 
				out.println("location='administracaoA.html';");
				out.println("</script>");
			}else{
				boolean verificacao = verificaInscricao();
				if(verificacao == false){
					//Utilizador n�o est� inscrito logo n�o pode remover inscri��o
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('N�o est� inscrito na modalidade!');"); 
					out.println("location='administracaoA.html';");
					out.println("</script>");
				}else{
					//Utilizador est� inscrito na modalidade logo pode ser removida a inscri��o
					boolean verificaRemocao = removeInscricao();
					if(verificaRemocao == false){
						//Erro ao remover
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Erro:Imposs�vel de remover inscri��o!');"); 
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}else{
						//Remo��o correta
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Inscri��o removida com sucesso!');"); 
						out.println("location='administracaoA.html';");
						out.println("</script>");
					}
				}
			}
		}
	}

}
