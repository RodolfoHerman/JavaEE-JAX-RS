package br.com.rodolfo.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rodolfo.loja.dao.CarrinhoDao;
import br.com.rodolfo.loja.models.Carrinho;

/**
* Classe que representa um recurso WEB, ou seja, que retorna o XML
*/
//Praticamente todo resource será anotado com '@Path' que indica qual será a URI para acessar no servidor
@Path("carrinhos")
public class CarrinhoResource {

    //Informar que o método é acessado através do método GET
    @GET
    //Temos que informar com a anotação 'Produces' o que o método retorna, String pura ? XML ? JSON ? HTML ?
    @Produces(MediaType.APPLICATION_XML)
    public String busca() {
        
        //Acessa o banco de dados e busca o carrinho
        Carrinho carrinho = new CarrinhoDao().busca(1l);

        //pode-se utilizar qualquer biblioteca de serialização/deserialização, ex.: JSON, HTML, XML .....
        return carrinho.toXML();
    }

}