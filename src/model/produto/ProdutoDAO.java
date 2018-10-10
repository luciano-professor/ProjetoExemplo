package model.produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.conexao.ConnectionFactory;
import model.entidades.Produto;

/**
 *
 * @author Luciano
 */
public class ProdutoDAO {
    
    /**
     * Buscar um produto pelo código
     * @param c Código
     * @return 
     * @throws SQLException 
     */
    public Produto buscarPeloCodigo(String c) throws SQLException{
        
        //Comando
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os parametros
        ps.setString(1, c);
        
        //Executa consulta no bd
        ResultSet resultado = ps.executeQuery();
        
        //Verificar se tem algum resultado
        if(resultado.next()){
            //Cria o objeto com o resultado do BD
            
            Produto p = new Produto(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getDouble("preco"),
                    resultado.getString("codigo"),
                    resultado.getDouble("quantidade"),
                    resultado.getDate("validade")
            );
            
            return p;
        }
        
        return null;
        
    }
    
}
