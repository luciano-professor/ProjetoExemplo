/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.produto.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import model.entidades.Produto;

/**
 * FXML Controller class
 *
 * @author Luciano
 */
public class ProdutoCadastroController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField codigo;
    @FXML
    private JFXTextField nome;
    @FXML
    private JFXTextField preco;
    @FXML
    private JFXTextField quantidade;
    @FXML
    private JFXDatePicker validade;
    @FXML
    private JFXComboBox<String> comboBusca;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private TableView<Produto> tabela;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Salvar o produto no bd
     * @param event 
     */
    @FXML
    private void salvar(ActionEvent event) {
        
        double precoDouble = Double.parseDouble(preco.getText());
        double qtdeDouble = Double.parseDouble(quantidade.getText());
        
        //Pegando os dados da tela e criando um produto
        Produto p = new Produto(
                0,
                nome.getText(),
                precoDouble,
                codigo.getText(),
                qtdeDouble,
                validade.getValue()
        );
        
    }
    
}
