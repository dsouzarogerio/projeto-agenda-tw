package br.com.treinaweb.agenda.servlets.agenda;

import java.io.IOException;
import java.sql.SQLException;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.impl.ContatoRepositorioJDBC;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/agenda/incluir" })
public class InserirContatoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -781631099010348217L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getServletContext()
				.getRequestDispatcher("/WEB-INF/paginas/agenda/inserirContato.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AgendaRepositorio<Contato> agendaRepositorio = new ContatoRepositorioJDBC();
		Contato novoContato = new Contato();
		novoContato.setNome(req.getParameter("nome"));
		novoContato.setIdade(Integer.parseInt(req.getParameter("idade")));
		novoContato.setTelefone(req.getParameter("telefone"));
		try {
			agendaRepositorio.inserir(novoContato);
		} catch (SQLException e) {
			req.getSession().setAttribute("mensagemErro", e.getMessage());
		}
		resp.sendRedirect(req.getContextPath() + "/agenda/listar");
	}

}
