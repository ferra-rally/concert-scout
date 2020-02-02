package logic.view;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.ArtistBean;
import logic.bean.GeneralUserBean;
import logic.bean.MusicEventBean;
import logic.buyticket.BuyTicketController;
import logic.utils.ControllerCreator;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(SearchServlet.class.getName());
	private static final long serialVersionUID = 102831973239L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		BuyTicketController btc = ControllerCreator.getInstance().getBuyTicketController();
		int i;
		
		GeneralUserBean gu = (GeneralUserBean) session.getAttribute("user");
		List<MusicEventBean> musicEvents = null;
		List<ArtistBean> artists = null;
		session.setAttribute("origin", "SearchServlet");
		
		String searchString = request.getParameter("searchString");
		
		if(searchString == null) {
			searchString = (String) request.getAttribute("searchString");
		}
		
		request.setAttribute("searchString", searchString);
		
		musicEvents = btc.getSearchMusicEvent(searchString);
		artists = btc.getSearchArtist(searchString);
		
		session.setAttribute("searchString", searchString);
		request.setAttribute("musicEventList", musicEvents);
		request.setAttribute("artistList", artists);
		rd = request.getRequestDispatcher("searchResult.jsp");
		
		try {
			rd.forward(request, response);
		} catch(Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
	}
}
