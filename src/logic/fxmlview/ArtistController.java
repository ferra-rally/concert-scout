package logic.fxmlview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.File;

import logic.bean.*;
import logic.utils.FileManager;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ArtistController {
	@FXML
	private Button artBtn;
	@FXML
	private Button imageBtn;
	
	private ArtistBean myArtist;
	
	private String from;
	private String searchString;
	
	private UserGraphicChange ucg;
	
	@FXML
	public void openArtist(ActionEvent e){		
		this.ucg.toArtistDetails(this.artBtn.getScene(), this.myArtist, this.from, this.searchString);
	}
	
	public void init(ArtistBean ev,String from,String searchString) {
		
		this.ucg=UserGraphicChange.getInstance();
		
		this.from=from;
		this.searchString=searchString;
		this.myArtist=ev;
		String path = FileManager.PROFILE + File.separator + this.myArtist.getProfilePicture();
		
		File file = new File(path);
		Image image = new Image(file.toURI().toString());
		ImageView iv3 = new ImageView(image);
		iv3.setFitHeight(170);
        iv3.setFitWidth(110);
		this.imageBtn.setGraphic(iv3);

		this.artBtn.setText(this.myArtist.getBandName());

	}

}
