package model.entidades;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;

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

    public Produto(int id, String nome, double preco, String codigo, double quantidade, LocalDate validade) {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setCodigo(codigo);
        setQuantidade(quantidade);
        setValidade(validade);
    }
    
    public Produto(int id, String nome, String preco, String codigo, String quantidade, LocalDate validade) throws ParseException {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setPreco(String preco) throws ParseException{
        NumberFormat nf = NumberFormat.getInstance();
        this.preco = nf.parse(preco).doubleValue();
    }
    
    public void setQuantidade(String quantidade) throws ParseException{
        NumberFormat nf = NumberFormat.getInstance();
        this.quantidade = nf.parse(quantidade).doubleValue();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
    
}
