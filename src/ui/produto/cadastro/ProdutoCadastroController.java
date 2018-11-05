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
import excecoes.ValorInvalidoException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
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

        //Configurando a largura das colunas da tabela
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colCodigo.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colPreco.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colQtde.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colValidade.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabela.getColumns().addAll(colCodigo, colNome, colPreco,
                colQtde, colValidade);

    }

    /**
     * Vai carregar os dados na tabela
     */
    private void carregarDados() {

        try {
            //Convertendo o ArrayList no ObservableList com os dados do Banco
            dados = FXCollections.observableArrayList(pBO.listar());

            //Joga os dados na tabela para exibir
            tabela.setItems(dados);

        } catch (SQLException ex) {
            mensagemDeErroBD();
        } catch (ValorInvalidoException ex) {
            mensagemDeErro(ex.getMessage());
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
                mensagemDeSucesso("Produto salvo com sucesso!");

            } else {//no caso de editar

                //Pegando o botao que foi pressionado
                Optional<ButtonType> btn = mensagemDeConfirmacao("Deseja mesmo salvar as alterações?", "EDITAR");

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
                    mensagemDeSucesso("Produto salvo com sucesso!");

                } else {

                    //Limpando os campos apos salvar
                    limparCampos();

                }

            }

        } catch (SQLException e) {
            mensagemDeErroBD();
        } catch (ProdutoExistenteException e) {
            mensagemDeErro(e.getMessage());
        } catch (ParseException ex) {
            mensagemDeErro("Problema na conversão do preço ou da quantidade.");
        } catch (ValorInvalidoException e) {
            mensagemDeErro(e.getMessage());
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

            //Pegando o botao que foi pressionado
            Optional<ButtonType> btn = mensagemDeConfirmacao("Deseja excluir?", "EXCLUIR");

            //Verificar qual botão foi pressionado
            if (btn.get() == ButtonType.OK) { //vai excluir

                try {

                    //Manda a classe de negocio excluir
                    pBO.excluir(p);

                    //Atualizar a tabela
                    carregarDados();

                    //Mensagem de excluído com sucesso                    
                    mensagemDeSucesso("Produto excluído com sucesso");

                } catch (SQLException ex) {
                    mensagemDeErroBD();
                }

            }

        } else {
            mensagemDeErro("Selecione um produto.");
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

        //Marcando o primeiro ja como selecionado
        comboBusca.getSelectionModel().selectFirst();

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
            mensagemDeErro("Selecione um produto.");
        }

    }

    @FXML
    private void filtrar(ActionEvent event) {

        //Pegar o que esta selecionado no comboBox
        String campo = comboBusca.getValue();

        if (campo != null) {//a pessoa selecionou um campo

            //pegar o que estiver escrito no campo de pesquisa
            String pesquisar = txtPesquisar.getText();

            if (campo.equals("Código")) {

                try {

                    //Convertendo o ArrayList no ObservableList com os dados do Banco
                    dados = FXCollections.observableArrayList(pBO.filtrarPeloCodigo(pesquisar));
                    //Joga os dados na tabela para exibir
                    tabela.setItems(dados);

                } catch (SQLException ex) {
                    mensagemDeErroBD();
                } catch (ValorInvalidoException ex) {
                    mensagemDeErro(ex.getMessage());
                }

            } else {//No caso de ser nome

                //Buscar pelo Nome
                try {

                    //Convertendo o ArrayList no ObservableList com os dados do Banco
                    dados = FXCollections.observableArrayList(pBO.filtrarPeloNome(pesquisar));
                    //Joga os dados na tabela para exibir
                    tabela.setItems(dados);

                } catch (SQLException ex) {
                    mensagemDeErroBD();
                } catch (ValorInvalidoException ex) {
                    mensagemDeErro(ex.getMessage());
                }
            }

        } else {
            mensagemDeErro("Favor Selecionar um campo para busca.");
        }

    }

    private void mensagemDeErro(String mensagem) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
        


    }

    private void mensagemDeErroBD() {
        mensagemDeErro("Erro de comunicação com "
                + "o Banco de Dado procure o administrador "
                + "do sistema");
    }
    
    private void mensagemDeSucesso(String mensagem){
        Alert m = new Alert(Alert.AlertType.INFORMATION);
        m.setTitle("Sucesso");
        m.setHeaderText(null);
        m.setContentText(mensagem);
        m.showAndWait();
    }
    
    private Optional<ButtonType> mensagemDeConfirmacao(String mensagem, String titulo){
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle(titulo);
        conf.setHeaderText(null);
        conf.setContentText(mensagem);
        return conf.showAndWait();
    }

}
