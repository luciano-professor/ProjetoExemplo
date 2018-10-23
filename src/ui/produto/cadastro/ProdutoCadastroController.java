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
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    //Dados da Tabela
    private ObservableList<Produto> dados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pBO = new ProdutoBO();

        carregarComboBusca();

        configurarTabela();

        carregarDados();

    }

    /**
     * Faz a configuração da tabela e das colunas
     */
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Produto, String> colNome = new TableColumn("Nome");
        TableColumn<Produto, String> colCodigo = new TableColumn("Código");
        TableColumn<Produto, Double> colPreco = new TableColumn("Preço R$");
        TableColumn<Produto, Double> colQtde = new TableColumn("Quantidade");
        TableColumn<Produto, String> colValidade = new TableColumn("Validade");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoFormatado"));
        colQtde.setCellValueFactory(new PropertyValueFactory<Produto, Double>("quantidadeFormatada"));
        colValidade.setCellValueFactory(new PropertyValueFactory<Produto, String>("validadeFormatada"));

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabela.getColumns().addAll(colCodigo, colNome, colPreco,
                colQtde, colValidade);

    }

    /**
     * Vai carregar os dados na tabela
     */
    private void carregarDados() {

        try {
            //Cnvertendo o ArrayList no ObservableList com os dados do Banco
            dados = FXCollections.observableArrayList(pBO.listar());

            //Joga os dados na tabela para exibir
            tabela.setItems(dados);

        } catch (SQLException ex) {
            //Mensagem de Erro
            ex.printStackTrace();
        }

    }

    /**
     * Salvar o produto no bd
     *
     * @param event
     */
    @FXML
    private void salvar(ActionEvent event) {

        try {

            if (id.getText().isEmpty()) {//no caso de inserir

                //Pegando os dados da tela e criando um produto
                Produto p = new Produto(
                        "0",
                        nome.getText(),
                        preco.getText(),
                        codigo.getText(),
                        quantidade.getText(),
                        validade.getValue()
                );

                //Mandando a classe de negocio salvar o produto
                pBO.salvar(p);

                //Atualizando os dados da tabela
                carregarDados();
                //dados.add(p); (outra opção que adiciona no fim da tabela)

                //Limpando os campos apos salvar
                limparCampos();

                //Mensagem de Sucesso
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Sucesso");
                a.setHeaderText(null);
                a.setContentText("Produto salvo com sucesso!");
                a.showAndWait();

            } else {//no caso de editar

                //Configurando a caixa de confirmacao
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText("");
                conf.setContentText("Deseja mesmo salvar as alterações?");

                //Pegando o botao que foi pressionado
                Optional<ButtonType> btn = conf.showAndWait();

                if (btn.get() == ButtonType.OK) {//quer mesmo salvar
                    //Pegando os dados da tela e criando um produto
                    Produto p = new Produto(
                            id.getText(),
                            nome.getText(),
                            preco.getText(),
                            codigo.getText(),
                            quantidade.getText(),
                            validade.getValue()
                    );

                    pBO.editar(p);

                    //Atualizando os dados da tabela
                    carregarDados();
                    //dados.add(p); (outra opção que adiciona no fim da tabela)

                    //Limpando os campos apos salvar
                    limparCampos();

                    //Mensagem de Sucesso
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Sucesso");
                    a.setHeaderText(null);
                    a.setContentText("Produto salvo com sucesso!");
                    a.showAndWait();

                } else {

                    //Limpando os campos apos salvar
                    limparCampos();

                }

            }

        } catch (SQLException e) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Erro de comunicação com "
                    + "o Banco de Dado procure o administrador "
                    + "do sistema");
            a.showAndWait();

        } catch (ProdutoExistenteException e) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.showAndWait();

        } catch (ParseException ex) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Problema na conversão do preço ou da quantidade.");
            a.showAndWait();

        }

    }

    /**
     *
     */
    private void limparCampos() {
        nome.setText(null);
        preco.setText(null);
        codigo.setText(null);
        quantidade.setText(null);
        validade.setValue(null);
    }

    /**
     * Exclui um produto do banco de dados
     *
     * @param event
     */
    @FXML
    private void excluir(ActionEvent event) {

        //Pegar o produto selecionado (pode ser null)
        Produto p = tabela.getSelectionModel().getSelectedItem();

        //Verificar se tem algum produto selecionado na tabela
        if (p != null) { //tem produto selecionado 

            //Configurando a caixa de confirmacao
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText("");
            conf.setContentText("Deseja excluir?");

            //Pegando o botao que foi pressionado
            Optional<ButtonType> btn = conf.showAndWait();

            //Verificar qual botão foi pressionado
            if (btn.get() == ButtonType.OK) { //vai excluir

                try {

                    //Manda a classe de negocio excluir
                    pBO.excluir(p);

                    //Atualizar a tabela
                    carregarDados();

                    //Mensagem de excluído com sucesso
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Sucesso");
                    m.setHeaderText(null);
                    m.setContentText("Produto excluído com sucesso");
                    m.showAndWait();

                } catch (SQLException ex) {
                    //Mensagem de erro
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERRO");
                    a.setHeaderText(null);
                    a.setContentText("Erro de comunicação com "
                            + "o Banco de Dado procure o administrador "
                            + "do sistema");
                    a.showAndWait();
                }

            }

        } else {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Selecione um produto ");
            a.showAndWait();

        }

    }

    private void carregarComboBusca() {

        //Criar uma lista
        ObservableList<String> lista
                = FXCollections.observableArrayList(
                        "Código", "Nome"
                );

        //Jogar a lista no combo
        comboBusca.getItems().addAll(lista);

    }

    @FXML
    private void editar(ActionEvent event) {

        //Pegar o produto selecionado (pode ser null)
        Produto p = tabela.getSelectionModel().getSelectedItem();

        if (p != null) {//tem produto selecionado

            //Jogar os dados do produto nos campos
            id.setText(String.valueOf(p.getId()));
            nome.setText(p.getNome());
            codigo.setText(p.getCodigo());
            preco.setText(p.getPrecoFormatado());
            quantidade.setText(p.getQuantidadeFormatada());
            validade.setValue(p.getValidade());

        } else {//Não tem produto selecionado

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Selecione um produto ");
            a.showAndWait();
        }

    }

}
