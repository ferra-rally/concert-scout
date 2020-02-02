package logic.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.ArtistBean;
import logic.bean.GeneralUserBean;
import logic.bean.MusicEventBean;
import logic.buyticket.BuyTicketController;
import logic.utils.ControllerCreator;

public class ButtonHandler  extends HttpServlet{
	private static final Logger logger = Logger.getLogger(ButtonHandler.class.getName());
	private static final long serialVersionUID = 102831973239L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		BuyTicketController btc = ControllerCreator.getInstance().getBuyTicketController();
		GeneralUserBean gu = (GeneralUserBean) session.getAttribute("user");

		if(request.getParameter("m") != null) {
			String id = request.getParameter("Mevent");
			MusicEventBean meb = btc.getMusicEvent(id, gu.getUsername());
			session.setAttribute("Mevent", meb);
			boolean isPart = btc.isParticipating(gu, meb);
			request.setAttribute("isPart", isPart);
			rd = request.getRequestDispatcher("musicEventDetail.jsp");
		} else if(request.getParameter("a") != null) {
			String username = request.getParameter("artist");
			System.out.println(username);
			ArtistBean ab = btc.getArtist(username);
			request.setAttribute("artist", ab);
			rd = request.getRequestDispatcher("artistDetail.jsp");
		} else if(request.getParameter("addPart") != null) {
			MusicEventBean meb = (MusicEventBean) session.getAttribute("Mevent");
			boolean isPart = btc.isParticipating(gu, meb);
			if(isPart){
				btc.removeParticipation(gu, meb);
			} else {
				btc.addParticipation(gu, meb);
			}
			request.setAttribute("isPart", !isPart);
			rd = request.getRequestDispatcher("musicEventDetail.jsp");
		} else if(request.getParameter("back") != null) {
			String origin = (String) session.getAttribute("origin");
			String searchString = (String) session.getAttribute("searchString");
			request.setAttribute("searchString", searchString);
			rd = request.getRequestDispatcher(origin);
		} else if(request.getParameter("follow") != null) {
			String target = request.getParameter("artist");
			
			rd = request.getRequestDispatcher("artistDetail.jsp");
		}
		
		try {
			rd.forward(request, response);
		} catch(Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
	}
}

