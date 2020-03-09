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
 * Servlet implementation class chequeaUsuario
 */
@WebServlet("/chequeaUsuario")
public class chequeaUsuario extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private chequeaUsuario(HttpServletRequest request,HttpServletResponse response){
       
	HttpSession session = request.getSession( );
	Usuario usuario = (Usuario) session.getAttribute ("usuario");
	String url = "";
	
	// Primero se comprueba si existe ya el usuario en la sesión
	if (usuario == null){
			//Si no existe el usuario se busca la cookie de email
			Cookie[ ] cookies = request.getCookies( );
			String cookieName ="emailCookie";
			String emailAddress = "";
			
			if (cookies != null){
				for (Cookie cookie: cookies){
				if (cookieName.equals(cookie.getName())) emailAddress =
				cookie.getValue();
				}
			}
			//Se comprueba si la cookie no existe:
			if (emailAddress.equals("")){
			url = "/registro.jsp"; //Se ofrecerá registrarse
			}
			else{
				// En este caso, se rellenan los datos del usuario ya registrado
				// buscando en la base de datos por el valor del email
				UsuarioDB basededatos = new UsuarioDB( );
				Usuario usuarioreg = basededatos.readUsuario(emailAddress);
				//Se añade a la sesión
				session.setAttribute("usuario", usuarioreg);
				url = "/welcome.jsp"; //Se dará la bienvenida al usuario
			}
	}
	else { url = "/welcome.jsp"; }
	return;
	}
}
