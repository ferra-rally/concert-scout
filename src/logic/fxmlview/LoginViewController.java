package logic.fxmlview;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.scene.control.ComboBox;

import logic.bean.ArtistBean;
import logic.bean.GeneralUserBean;
import logic.bean.UserBean;
import logic.login.*;
import logic.utils.FileManager;
import logic.utils.SessionUser;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import java.io.FileInputStream;
import javafx.beans.value.ObservableValue;
import logic.exceptions.*;

public class LoginViewController {
	
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordBox;
	@FXML
	private Label registerLabel;
	@FXML
	private TextField emailField;
	@FXML
	private TextField usernameRegField;
	@FXML
	private PasswordField passwordRegField;
	@FXML
	private TextField bandNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private ComboBox<String> typeOfUserField;
	@FXML
	private Label errorLabel1;
	@FXML
	private Label errorLabel2;
	@FXML
	private Label imageLabel;
	
	private static final String ARTIST  = "Artist";
	private static final String USER = "User";
	
	private static final Logger logger = Logger.getLogger(LoginViewController.class.getName());
	
	private File imageFile=null;
	
	@FXML
	public void selectImage(ActionEvent event) {
		final FileChooser fc=new FileChooser();
		fc.setTitle("Select image");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG","*.jpg"),
										new FileChooser.ExtensionFilter("PNG","*.png"));
		this.imageFile=fc.showOpenDialog(new Stage());
		if(this.imageFile!=null)this.imageLabel.setText(this.imageFile.getName());
	}
	
	//NO IMAGE SELECTED STRING
	
	@FXML
	public void loginButtonAction(ActionEvent event){
		
		GeneralUserBean gub = new GeneralUserBean();
		gub.setUsername(this.usernameTextField.getText());
		gub.setPassword(this.passwordBox.getText());
		
		LoginController controller = new LoginController();
		GeneralUserBean gu;
		try {	
    		gu=controller.login(gub);
    		if(gu==null) {
    			this.errorLabel1.setText("Wrong username");
    			this.errorLabel2.setText("or password");
    			this.usernameTextField.setText("");
    			this.passwordBox.setText("");
    		}else {
        		String role=gu.getRole();
        		
        		//SET SESSION GENERAL USER
        		SessionUser su=SessionUser.getInstance();
        		su.setSession(gu);
        		
        		switch(role) {
        		case "user":
        			UserGraphicChange.getInstance().toHomepage(this.usernameTextField.getScene());    			
        			break;
        		case "artist":
        			//set artist homepage controller
        			ArtistGraphicChange.getInstance().toHomepage(this.usernameTextField.getScene());
        			break;
        		case "admin":
        			//set admin controller
        			AdminGraphicChange.getInstance().toHomepage(this.usernameTextField.getScene());
        			break;
        		default:
        			break;
        		}
    		}
		}
    	catch(LoginEmptyFieldException e) {
    		this.errorLabel1.setText(e.getMessage());
    		this.errorLabel2.setText("");
    	}
    }
    	
	
	@FXML
	public void registerButtonAction(ActionEvent event) {
		LoginController controller = new LoginController();
		Boolean regResult = false;
		String email = "";
		String username = "";
		String password = "";
		String userType = "";
		
		email=emailField.getText();
		
		username=this.usernameRegField.getText();
		
		password=this.passwordRegField.getText();
		
		userType=this.typeOfUserField.getValue();
		
		String fileName;
		String newFileName;
		
		if(this.imageFile==null) {
			fileName="";
			newFileName="";
		}else {
			fileName=this.imageFile.getName();
			newFileName=username+fileName;
		}
		
		
		if(userType.equals(USER)) {
			String firstName=this.firstNameField.getText();
			String lastName=this.lastNameField.getText();
			UserBean u = new UserBean();
			u.setUsername(username);
			u.setPassword(password);
			u.setEmail(email);
			u.setName(firstName);
			u.setSurname(lastName);
			u.setProfilePicture(newFileName);
			regResult = controller.createUser(u);	
		}else if(userType.contentEquals(ARTIST)) {
			String bandName=this.bandNameField.getText();
			ArtistBean a = new ArtistBean();
			a.setUsername(username);
			a.setPassword(password);
			a.setBandName(bandName);
			a.setProfilePicture(newFileName);
			a.setEmail(email);
			regResult = controller.createArtist(a);
		}
		
		if(Boolean.TRUE.equals(regResult)) {
			this.registerLabel.setText("Registration successfull");
			if(this.imageFile!=null) {
				String path = FileManager.PROFILE;
			    File file = new File(path, fileName);
			    File newFile = new File(path, newFileName);
			    try (InputStream input = new FileInputStream(this.imageFile)) {
			    		Files.copy(input, file.toPath());
			    } catch (Exception e) {
			    	logger.log(Level.WARNING, e.toString());
			    }
			    
			    if(!file.renameTo(newFile)) {
			    	logger.log(Level.WARNING, "Unable to rename: {0}", fileName);
			    }
			}
		}
		else {
			this.registerLabel.setText("Registration unsuccessfull");
		}
		
		this.emailField.setText("");
		this.usernameRegField.setText("");
		this.passwordRegField.setText("");
		this.bandNameField.setText("");
		this.firstNameField.setText("");
		this.lastNameField.setText("");
		this.imageFile=null;
		this.imageLabel.setText("No image selected");
		
	}
	
	
	public void init() { 
		String[] kinds= {USER,ARTIST};
		this.typeOfUserField.setItems(FXCollections.observableArrayList(kinds));
		this.typeOfUserField.getSelectionModel().selectFirst();
		this.typeOfUserField.valueProperty().addListener(
				(ObservableValue<? extends String> composant, String oldValue, String newValue) -> {
			if (newValue.equals(USER)) {
				bandNameField.setDisable(true);
				firstNameField.setDisable(false);
				lastNameField.setDisable(false);
				bandNameField.setText("");
			} else if (newValue.equals(ARTIST)) {
				bandNameField.setDisable(false);
				firstNameField.setDisable(true);
				lastNameField.setDisable(true);
				firstNameField.setText("");
				lastNameField.setText("");
			}
		});
		this.bandNameField.setDisable(true);
	}

}
