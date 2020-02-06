package logic.fxmlView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.File;

import logic.login.*;
import logic.utils.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import logic.bean.*;
import logic.buyticket.BuyTicketController;
import logic.utils.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import logic.bean.*;

public class ArtistController {
	@FXML
	private Button artBtn;
	@FXML
	private Button imageBtn;
	
	private ArtistBean myArtist;
	
	private String from;
	private String searchString;
	
	@FXML
	public void openArtist(ActionEvent e) throws IOException{
		ArtDetailsController adc=new ArtDetailsController();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("ArtDetails.fxml"));
		loader.setController(adc);
		this.artBtn.getScene().setRoot(loader.load());
		adc.init(this.myArtist,this.from, this.searchString);
	}
	
	public void init(ArtistBean ev,String from,String searchString) {
		
		this.from=from;
		this.searchString=searchString;
		this.myArtist=ev;
		String path=System.getProperty("user.home")+ File.separator
				+ "Desktop" + File.separator + "LIVEtheMUSIC" + File.separator
				+ "trunk" + File.separator + "WebContent" + File.separator
				+ "img" + File.separator + "profilePictures"+File.separator+this.myArtist.getProfilePicture();
		
		File file = new File(path);
		Image image = new Image(file.toURI().toString());
		ImageView iv3 = new ImageView(image);
		iv3.setFitHeight(170);
        iv3.setFitWidth(110);
		this.imageBtn.setGraphic(iv3);
		
		
		
		
		
		this.artBtn.setText(this.myArtist.getBandName());
		}
		
	
}