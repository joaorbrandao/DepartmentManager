package servicos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServicoDestroiCookies
 */
@WebServlet("/ServicoDestroiCookies")
public class ServicoDestroiCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServicoDestroiCookies() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		
		if(cookies != null){
			for(int i = 0; i < cookies.length; i++){
				cookie = cookies[i];
				if((cookie.getName( )).compareTo("util_id") == 0 || (cookie.getName( )).compareTo("code") == 0){
					cookie.setMaxAge(0);
					cookie.setValue("0");
		            response.addCookie(cookie);
				}
			}
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print("{\"cookie\":true}");
		}else{
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print("{\"cookie\":false}");
		}
	}

}
