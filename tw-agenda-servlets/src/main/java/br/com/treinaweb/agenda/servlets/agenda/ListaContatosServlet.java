package br.com.treinaweb.agenda.servlets.agenda;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.impl.ContatoRepositorioJDBC;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/agenda/listar"})
public class ListaContatosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3704698039659899753L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AgendaRepositorio<Contato> agendaRepositorio = new ContatoRepositorioJDBC();
		try {
			List<Contato> contatos = agendaRepositorio.selecionar();
			req.setAttribute("listaContatos", contatos);
		} catch (SQLException e) {
			req.setAttribute("mensagemErro", e.getMessage());
		}
		Object mensagemErro = req.getSession().getAttribute("mensagemErro");
		if(mensagemErro != null) {
			req.setAttribute("mensagemErro", mensagemErro.toString());
			req.getSession().removeAttribute("mensagemErro");
		}
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/paginas/agenda/listaContatos.jsp");
		dispatcher.forward(req, resp);
	}
}
