package pkg_genoma;
import javafx.*;

import java.io.FileNotFoundException;
import java.lang.System;



import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
	private ListView<Gene> lvGene;
	private PasswordField pfPass;
	
	
	@Override
	public void start(Stage primaryStage)throws FileNotFoundException {
		Genoma g = new Genoma();
		GridPane grid = new GridPane();
		//root.setAlignment(Pos.BASELINE_RIGHT);
		grid.setPadding(new Insets(25,25,25,25));
		grid.setVgap(200);
		grid.setHgap(400);

		// Painel do usuario/senha
		Text scenetitle = new Text("Arquivo: sequence.txt");
		scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 16));
		grid.add(scenetitle, 0, 0);
		Label lbUser = new Label("Genes:");
		grid.add(lbUser, 0, 1);
		lvGene = new ListView<Gene>();
		lvGene.setItems(g.getGenes());
		grid.add(lvGene, 1, 1);
		Label lbPass = new Label("Senha:");
		grid.add(lbPass, 0, 2);
		pfPass = new PasswordField();
		grid.add(pfPass, 1, 2);

		// Painel dos botoes
		HBox hbBut = new HBox();
		hbBut.setAlignment(Pos.BOTTOM_CENTER);
		hbBut.setPadding(new Insets(10,10,10,10));
		Button btOk = new Button("Ok");
		Button btCancela = new Button("Cancela");
		hbBut.getChildren().add(btOk);
		hbBut.getChildren().add(btCancela);

		//Painel geral
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(grid);
		root.getChildren().add(hbBut);

		/*btOk.setOnAction(e->{
			if (lvGene.getText().equals("Zezinho")&&
					pfPass.getText().equals("segredo")){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acesso liberado");
				alert.setHeaderText(null);
				alert.setContentText("Usuario e senha corretos !!");
				alert.show();
			}else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Oops!!");
				alert.setHeaderText(null);
				alert.setContentText("Usuario ou senha incorretos !!");           		
				alert.show();
			}
		});*/

		Scene scene = new Scene(root);
		primaryStage.setTitle("Projeto GENOMA");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


