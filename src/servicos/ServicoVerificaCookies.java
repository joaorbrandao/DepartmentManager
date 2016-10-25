package servicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class ServicoVerificaCookies
 */
@WebServlet("/ServicoVerificaCookies")
public class ServicoVerificaCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServicoVerificaCookies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		Date date = new Date();
		boolean flag = false;
		for(int i = 0; i < cookies.length; i++){
			cookie = cookies[i];
			if((cookie.getName()).compareTo("util_id") == 0 ){
				if((cookie.getValue().compareTo("0") == 0)){
					//Cookie já não é válido
					cookie.setMaxAge(0);
					cookie.setValue("0");
		            response.addCookie(cookie);
		            
		            response.setContentType("application/json");
		    		PrintWriter out = response.getWriter();
		    		
		    		JSONObject obj = new JSONObject();
		    		obj.put("cookie", false);
		    		
		    		/*StringWriter st = new StringWriter();
		    		obj.writeJSONString(st);*/
		    		String jsonText = JSONObject.toJSONString(obj);
		    		//String jsonText = st.toString();
		    		out.print(jsonText);
				}else{
					//Cookie ainda válido
					continue;
				}
			}
			if((cookie.getName()).compareTo("code") == 0 ){
				long currentTime = date.getTime();
				long cookieTime = Long.parseLong(cookie.getValue())+(1000*60*60*5);
				if(currentTime <= cookieTime){
					response.setContentType("application/json");
		    		PrintWriter out = response.getWriter();
		    		
		    		JSONObject obj = new JSONObject();
		    		obj.put("cookie", true);
		    		
		    		/*StringWriter st = new StringWriter();
		    		obj.writeJSONString(st);*/
		    		String jsonText = JSONObject.toJSONString(obj);
		    		//String jsonText = st.toString();
		    		out.print(jsonText);
				}else{
					response.setContentType("application/json");
		    		PrintWriter out = response.getWriter();
		    		
		    		JSONObject obj = new JSONObject();
		    		obj.put("cookie", false);
		    		
		    		/*StringWriter st = new StringWriter();
		    		obj.writeJSONString(st);*/
		    		String jsonText = JSONObject.toJSONString(obj);
		    		//String jsonText = st.toString();
		    		out.print(jsonText);
				}
			}
		}
	}

}
