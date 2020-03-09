package pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class servlet1
 */
@WebServlet("/servlet1")
public class servlet1 extends HttpServlet {
		private static final long serialVersionUID = 1L;
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
							throws ServletException, IOException {
			// Se leen los parámetros
			String user = request.getParameter("User");		
			String contraseña = request.getParameter("Password");

			// Se crea el objeto usuario (se supone que existe la clase Usuario)
			Usuario usuario = new Usuario(user, contraseña);

			// Y se guarda en una base de datos (igualmente se supone implementada)
			UsuarioDB basededatos = new UsuarioDB( );
			basededatos.add (usuario);

			//A continuación se guarda en la sesión el mismo objeto que en la base de datos
			HttpSession session = request.getSession( );
			session.setAttribute ("usuario",usuario);
			
			//Y se almacena el usuario en una cookie para poder identificar en el futuro
			//al usuario mediante su email cuando vuelva a navegar por el sitio web
			Cookie c = new Cookie("emailCookie", user);
			c.setMaxAge(60*60*24*365*2);
			c.setPath("/");
			response.addCookie(c);
			
			
			
			
		}
}
		
		