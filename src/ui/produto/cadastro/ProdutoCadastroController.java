/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.produto.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import excecoes.ProdutoExistenteException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import model.entidades.Produto;
import model.produto.ProdutoBO;

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

    //A classe de negocio do produto
    private ProdutoBO pBO;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pBO = new ProdutoBO();
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
        
        
        try{
            //Mandando a classe de negocio salvar o produto
            pBO.salvar(p);
            
            //Limpando os campos apos salvar
            limparCampos();
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Sucesso");
            a.setHeaderText(null);
            a.setContentText("Produto salvo com sucesso!");
            a.showAndWait();
            
            
        }catch(SQLException e){
            
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Erro de comunicação com "
                    + "o Banco de Dado procure o administrador "
                    + "do sistema");
            a.showAndWait();
            
        }catch(ProdutoExistenteException e){
            
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.showAndWait();
            
        }
        
    }
    
    /**
     * 
     */
    private void limparCampos(){
        nome.setText(null);
        preco.setText(null);
        codigo.setText(null);
        quantidade.setText(null);
        validade.setValue(null);
    }
    
}
