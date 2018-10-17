package model.produto;

import excecoes.ProdutoExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.Produto;

/**
 *
 * @author Luciano
 */
public class ProdutoBO {
    
    
    private ProdutoDAO dao;
    
    public ProdutoBO(){
        dao = new ProdutoDAO();
    }
    
    /**
     * Faz as vericações de negocio e manda salvar no banco de 
     * dados
     * @param p Produto a ser salvo
     */
    public void salvar(Produto p) 
            throws SQLException,
            ProdutoExistenteException
    {
        
        //Verificar se existe um produto com o mesmo código
        if( dao.buscarPeloCodigo(p.getCodigo()) != null ){
            //TODO lançar exceção
            //mensagem que já existe o produto com o codigo
            throw new ProdutoExistenteException("Já "
                    + "existe produto cadastrado com mesmo"
                    + "código");
            
        }else{
            //mandar salvar no banco de dados
            dao.salvar(p);
        }
        
    }
    
    public ArrayList<Produto> listar() throws SQLException{
        
        return dao.listar();
        
    }
    
    public void excluir(Produto p) throws SQLException{
        
        //Mandar o dao excluir
        dao.excluir(p);
    }
    
}
