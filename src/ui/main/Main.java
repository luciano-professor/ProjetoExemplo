package ui.main;

//https://stackoverflow.com/questions/36197764/javafx-stage-resizeable-false-and-maximized-true-hides-the-taskbar
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe principal do projeto
 * @author Luciano
 */
public class Main extends Application {
    
    public void start(Stage stage) throws IOException{
        
        //Leitura do FXML
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        
        //Titulo da Janela
        stage.setTitle("Sistema Projeto Exemplo");
        
        //Icone da janela
        stage.getIcons().add(new Image("/imagens/icone.png"));
        
        //Criação da cena
        Scene scene = new Scene(root);
                
        //Configuração da janela
        stage.setScene(scene);
        stage.show();
        
        
    }
    //Sempre fixo chamando a execucao do javafx
    public static void main(String[] args) {
        launch(args);
    } 
    
}
