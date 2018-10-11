package excecoes;

/**
 * Representa a exceção quando um produto tentará ser 
 * cadastrado e existe já um outro produto com o mesmo código
 * @author Luciano
 */
public class ProdutoExistenteException extends Exception {
    
    public ProdutoExistenteException(String m){
        super(m);
    }
    
}
