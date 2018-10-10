/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Luciano
 */
public class PrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void abrirJanelaCadastro(ActionEvent event) throws IOException {
        
        //Código para criar uma nova janela
        
        //Leitura do FXML
        Parent root = FXMLLoader.load(getClass().getResource("/ui/produto/cadastro/ProdutoCadastro.fxml"));

        //Criação da cena
        Scene scene = new Scene(root);

        //Criação do Stage (window)
        //Configurar o tipo da janela que vai abrir
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Produto");
        
        //Icone
        stage.getIcons().add(new Image("/imagens/icone.png"));
        
        //Adicionando a scene no stage
        stage.setScene(scene);
        
        //Modality para bloquear outras janelas
        stage.initModality(Modality.APPLICATION_MODAL);    
        
        //Mostrando a nova janela
        stage.show();
        
    }
    
}
