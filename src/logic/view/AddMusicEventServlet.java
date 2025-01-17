package logic.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import logic.addmusicevent.AddMusicEventController;
import logic.bean.GeneralUserBean;
import logic.bean.MusicEventBean;
import logic.exceptions.DateException;
import logic.utils.FileManager;

@MultipartConfig

public class AddMusicEventServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(AddMusicEventServlet.class.getName());
	private static final long serialVersionUID = 102831973239L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("artistHome.jsp");
		AddMusicEventController controller = new AddMusicEventController();
		GeneralUserBean gu = (GeneralUserBean) session.getAttribute("user");
		String res = null;
		String name = request.getParameter("name");
		String location = request.getParameter("location");
		String date = request.getParameter("date");
		String ticketone = request.getParameter("ticketone");
		String newFileName;
		String fileName = "";
		boolean result = false;
		
		Part filePart = null; // Retrieves <input type="file" name="file">
		try {
			filePart = request.getPart("file");
		} catch (Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
		
		if(filePart != null) {
			fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		}
		
		newFileName = FileManager.generateNewFileName(fileName, true, gu.getUsername());
		
		MusicEventBean meb = new MusicEventBean();
		meb.setArtistId(gu.getUsername());
		meb.setName(name);
		meb.setCoverPath(newFileName);
		meb.setLocation(location);
		meb.setTicketone(ticketone);
		meb.setDate(date);
		
		try {
			result = controller.addMusicEvent(meb);
			if (result) {
				res = "added";
			} else {
				res = "notAdded";
			}
		} catch (DateException de) {
			res = de.getMessage();
		}
		
		if(!fileName.equals("") && result && filePart != null){
			String path = FileManager.EVENT;
		    File file = new File(path, fileName);
		    File newFile = new File(path, newFileName);

		    try (InputStream input = filePart.getInputStream()) {
		    		Files.copy(input, file.toPath());
		    } catch (Exception e) {
		    	logger.log(Level.WARNING, e.toString());
		    }
		    
		    if(file.renameTo(newFile)) {
		    	logger.log(Level.WARNING, "Unable to rename: {0}", fileName);
		    }
		    
		}
		request.setAttribute("result", res);
		
		try {
			rd.forward(request, response);
		} catch(Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
	}
}