package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Kayttaja;
import model.RegisterSystem;
import view.MainApp;

public class RekisteroidyController {
	private MainApp app;
	@FXML
	private TextField etu;
	@FXML
	private TextField suku;
	@FXML
	private TextField email;
	@FXML
	private TextField puhelinnumero;
	@FXML
	private PasswordField salasana;
	@FXML
	private PasswordField csalasana;
	@FXML
	private Text tippi;
	@FXML
	private Text emailtip;
	String locale = Locale.getDefault().getLanguage();

	@FXML
	void initialize() {
		Tooltip toolpwd = new Tooltip();
		Tooltip toolpwd2 = new Tooltip();
		Tooltip toolemail = new Tooltip();
		if(locale.equals("en")) {
			toolpwd.setText("Password length at least 6");
			toolpwd2.setText("Password length at least 6");
			toolemail.setText("Enter Email for example JohnDoe@Hotmail.com");
		}else {
			toolpwd.setText("Salasanan pituus vähintään 6");
			toolpwd2.setText("Salasanan pituus vähintään 6");
			toolemail.setText("Syötä Sähköposti esimerkiksi JohnDoe@Hotmail.com");
		}
		salasana.setTooltip(toolpwd);
		csalasana.setTooltip(toolpwd);
		email.setTooltip(toolemail);
	}

	@FXML
	void Rekistyröidy(ActionEvent event) {
		boolean test = validointi();
		if (test == true) {
			try {
				Kayttaja käyttäjä = new Kayttaja();
				käyttäjä.setEtunimi(etu.getText());
				käyttäjä.setSukunimi(suku.getText());
				käyttäjä.setPuhelinumero(puhelinnumero.getText());
				käyttäjä.setSähköposti(email.getText());
				käyttäjä.setSalasana(salasana.getText());
				RegisterSystem register = new RegisterSystem();
				boolean tulos = register.register(käyttäjä);
				if (tulos == true) {
					//Ilmoitetaan rekisteröitymisen onnistumisesta
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					if(locale.equals("en")) {
						alert.setTitle("Notification");
						alert.setContentText("Registered succesfully!");
					}else {
						alert.setTitle("Imoitus");
						alert.setContentText("Rekisteröityminen onnistui!");
					}
					
					alert.showAndWait();
					//Viedään kirjautumissivulle
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
					BorderPane kirjaudu = (BorderPane) loader.load();
					Scene kirjauduNäkymä = new Scene(kirjaudu);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(kirjauduNäkymä);
					window.show();
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					if(locale.equals("en")) {
						alert.setTitle("Information");
						alert.setContentText("Your email has already been registered");
					}else {
						alert.setTitle("Tiedoksi");
						alert.setContentText("Sähköpostillasi on jo rekistyröidytty");
					}
					alert.show();
					email.setStyle("-fx-border-color:red");
				}
			} catch (NumberFormatException e) {
				System.out.println(e);
				return;
			} catch (Exception e) {
				System.out.println(e);
				return;
			}
		}
	}

	@FXML
	void ViewNäkymäLogin(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle);
		BorderPane kirjaudu = (BorderPane) loader.load();
		Scene kirjauduNäkymä = new Scene(kirjaudu);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(kirjauduNäkymä);
		window.show();
	}

	@FXML
	void vieVierasNäkymä(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Vieras.fxml"));
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle);
		BorderPane register = (BorderPane) loader.load();
		Scene rekistyröintiNäkymä = new Scene(register);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rekistyröintiNäkymä);
		window.show();
	}

	boolean validointi() {
		boolean test = true;
		etu.setStyle("-fx-border-color:#0589ff");
		suku.setStyle("-fx-border-color:#0589ff");
		salasana.setStyle("-fx-border-color:#0589ff");
		csalasana.setStyle("-fx-border-color:#0589ff");
		email.setStyle("-fx-border-color:#0589ff");
		puhelinnumero.setStyle("-fx-border-color:#0589ff");
		etu.setStyle("-fx-border-color:#0589ff");
		tippi.setText("");
		emailtip.setText("");
		if (etu.getText() == "") {
			etu.setStyle("-fx-border-color:red");
			test = false;
		}
		if (suku.getText() == "") {
			suku.setStyle("-fx-border-color:red");
			test = false;
		}
		if (email.getText() == "") {
			email.setStyle("-fx-border-color:red");
			test = false;
		}
		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email.getText());
		boolean validEmail = matcher.matches();
		if (validEmail == false) {
			email.setStyle("-fx-border-color:red");
			emailtip.setText("Muoto väärä");
			test = false;
		}
		if (puhelinnumero.getText() == "") {
			puhelinnumero.setStyle("-fx-border-color:red");
			test = false;
		}
		if (salasana.getText() == "" || csalasana.getText() == "") {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			test = false;
		}
		boolean compare = salasana.getText().equals(csalasana.getText());
		if (compare != true) {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			tippi.setText("Salasanat eivät täsmää");
			test = false;
		}
		if (salasana.getText().length() < 6 || csalasana.getText().length() < 6) {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			tippi.setText("Salasanan pituus vähintään 6");
			test = false;
		}
		return test;
	}

	public void setMainApp(MainApp mainApp) {
		this.app=mainApp;
	}
}
