package filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;


//Essa classe filtra tudo o que est� vindo das telas

//toda a url de requisi��o/resposta vai passar pelo Filter (*)

@WebFilter(urlPatterns = {"/*"}) 
public class Filter implements javax.servlet.Filter{

	private static Connection connection;
	
	
	//� executado toda vez que vc clica em um bot�o na tela
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		try {
			arg2.doFilter(arg0, arg1);
			connection.commit();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				connection.rollback();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
//S� passa por aqui uma vez.
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}
	
}
