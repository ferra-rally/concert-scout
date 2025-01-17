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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import logic.addnews.AddNewsController;
import logic.bean.GeneralUserBean;
import logic.bean.NewsBean;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;

@WebServlet("/AddNewsServlet")
@MultipartConfig
public class AddNewsServlet extends HttpServlet{
	
	private static final Logger logger = Logger.getLogger(AddNewsServlet.class.getName());
	private static final long serialVersionUID = 102831973239L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("addNews.jsp");
		GeneralUserBean gu = (GeneralUserBean) session.getAttribute("user");
		String resString = "";
		String fileName = "";
		String newFileName = null;
		Part filePart = null;
		
		AddNewsController controller = new AddNewsController();
		
		String text = request.getParameter("text");
		NewsBean nb = new NewsBean();
		
		// Retrieves <input type="file" name="file">
		try {
			filePart = request.getPart("file");
		} catch(Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
		if(filePart != null) {
			fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		}
		
		newFileName = FileManager.generateNewFileName(fileName, true, gu.getUsername());
		
		nb.setText(text);
		nb.setArtistId(gu.getUsername());
		nb.setPicturePath(newFileName);

		boolean result;
		try {
			result = controller.addNews(nb);
			if(result) {
				resString = "added";
			} else {
				resString = "notAdded";
			}
		} catch (FieldTooLongException fe) {
			result = false;
			resString = fe.getMessage();
		}
		
		if(!fileName.equals("") && result && filePart != null){
			String path = FileManager.NEWS;
		    File file = new File(path, fileName);
		    File newFile = new File(path, newFileName);
		    try (InputStream input = filePart.getInputStream()) {
		    		Files.copy(input, file.toPath());
		    } catch (Exception e) {
		    	logger.log(Level.WARNING, e.toString());
		    }
		    if(!file.renameTo(newFile)) {
		    	logger.log(Level.WARNING, "Unable to rename file {0}", fileName);
		    }
		}
		
		request.setAttribute("result", resString);
		
		try {
			rd.forward(request, response);
		} catch(Exception e) {
			logger.log(Level.WARNING, e.toString());
		}
	}
}
