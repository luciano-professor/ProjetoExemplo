package model.entidades;

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
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.validade = validade;
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
