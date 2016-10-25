package registo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servicos.ServicoRegisto;


@WebServlet("/Registo")
public class RegistoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegistoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String form_email, form_pass, form_nome, form_data_nascimento, form_telefone, form_cartao_cidadao;
		int form_mod;
		char form_sexo;
		
		form_email = request.getParameter("form_email");
		form_pass = request.getParameter("form_pass");
		form_nome = request.getParameter("form_nome");
		form_data_nascimento = request.getParameter("form_data_nascimento");
		form_sexo = request.getParameter("form_sexo").charAt(0);
		form_telefone = request.getParameter("form_telefone");
		form_cartao_cidadao = request.getParameter("form_cartao_cidadao");
		form_mod = Integer.parseInt(request.getParameter("form_mod"));
		
		//Passar informações introduzidas pelo Utilizador para a Class ServicoRegisto
		ServicoRegisto servicoRegisto = new ServicoRegisto();
		servicoRegisto.setForm_email(form_email);
		servicoRegisto.setForm_pass(form_pass);
		servicoRegisto.setForm_nome(form_nome);
		servicoRegisto.setForm_data_nascimento(form_data_nascimento);
		servicoRegisto.setForm_sexo(form_sexo);
		servicoRegisto.setForm_telefone(form_telefone);
		servicoRegisto.setForm_cartao_cidadao(form_cartao_cidadao);
		servicoRegisto.setForm_mod(form_mod);
		
		boolean registo = servicoRegisto.registo();
		
		if(registo == true){
			//Registo com sucesso
			/*PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Registo com sucesso!');");  
			out.println("</script>");
			
			response.sendRedirect("login.html");*/
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Registo com sucesso!');"); 
			out.println("location='login.html';");
			out.println("</script>");
		}else{
			//Registo sem sucesso
			/*PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<html><body><script type=\"text/javascript\">");  
			out.println("alert('Erro ao registar...por favor tente mais tarde ou contacte um administrador!');");  
			out.println("</script></body></html>");
			
			response.sendRedirect("registo.html");*/
			
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Erro ao registar...por favor tente mais tarde ou contacte um administrador!');"); 
			out.println("location='registo.html';");
			out.println("</script>");
		}
	}

}
