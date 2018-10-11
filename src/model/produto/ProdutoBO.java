package model.produto;

import java.sql.SQLException;
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
    public void salvar(Produto p) throws SQLException{
        
        //Verificar se existe um produto com o mesmo código
        if( dao.buscarPeloCodigo(p.getCodigo()) != null ){
            //TODO lançar exceção
            //mensagem que já existe o produto com o codigo
            
        }else{
            //mandar salvar no banco de dados
            dao.salvar(p);
        }
        
    }
    
}
