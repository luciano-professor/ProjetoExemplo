package model.entidades;

import excecoes.ValorInvalidoException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Luciano
 */
public class Produto {
  
    private int id;
    private String nome;
    private double preco;
    private String codigo;
    private double quantidade;
    private LocalDate validade; 

    public Produto(int id, String nome, double preco, String codigo, double quantidade, LocalDate validade) throws ValorInvalidoException {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setCodigo(codigo);
        setQuantidade(quantidade);
        setValidade(validade);
    }
    
    public Produto(String id, String nome, String preco, String codigo, String quantidade, LocalDate validade) throws ParseException, ValorInvalidoException {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setCodigo(codigo);
        setQuantidade(quantidade);
        setValidade(validade);           
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ValorInvalidoException {
        
        if(!nome.matches("^[\\wÀ-ú][\\wÀ-ú\\s]*")){
           throw new ValorInvalidoException("O campo nome é obrigatório e só aceita caracteres alfanuméricos e espaços.");
        }
        
        this.nome = nome;
        
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setPreco(String preco) throws ParseException, ValorInvalidoException{
        
        if(!preco.matches("^[0-9]+([,][0-9]+)?$")){
           throw new ValorInvalidoException("O campo preço é obrigatório só pode conter dígitos e separador de decimal (,).");
        }
        
        NumberFormat nf = NumberFormat.getInstance();
        this.preco = nf.parse(preco).doubleValue();
    }
    
    public void setQuantidade(String quantidade) throws ParseException, ValorInvalidoException{
        
        if(!quantidade.matches("\\d+")){
           throw new ValorInvalidoException("O campo quantidade  é obrigatório só pode conter dígitos.");
        }
        
        NumberFormat nf = NumberFormat.getInstance();
        this.quantidade = nf.parse(quantidade).doubleValue();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) throws ValorInvalidoException {
        
        if(!codigo.matches("^[\\wÀ-ú]+")){
           throw new ValorInvalidoException("O campo código  é obrigatório e só aceita caracteres alfanuméricos sem espaços.");
        }
        
        this.codigo = codigo;
        
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }
    
    /**
     * Formatar a data de LocalDate para String no formato
     * brasileiro
     * @return 
     */
    public String getValidadeFormatada(){
        DateTimeFormatter formatador 
                = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return validade.format(formatador);
    }
    
    /**
     * Formata o preço para o padrão de moeda brasileira
     * @return 
     */
    public String getPrecoFormatado(){
        DecimalFormat formatador = new DecimalFormat("#0.00");
        return formatador.format(preco);
    }
    
    /**
     * Formata a quantidade pra aparecer a virgula
     * @return 
     */
    public String getQuantidadeFormatada(){
        DecimalFormat formatador = new DecimalFormat();
        return formatador.format(quantidade);
    }

    public void setValidade(LocalDate validade) throws ValorInvalidoException {
        
        if(validade == null){
           throw new ValorInvalidoException("O campo validade  é obrigatório.");
        }
        
        this.validade = validade;
    }
    
}
