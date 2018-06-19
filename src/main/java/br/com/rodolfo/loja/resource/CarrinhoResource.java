package br.com.rodolfo.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    //Informa que na URI receberemos parâmetros de identificação do recurso
    //@Path("{id}/{teste}")
    @Path("{id}")
    //Informar que o método é acessado através do método GET
    @GET
    //Temos que informar com a anotação 'Produces' o que o método retorna, String pura ? XML ? JSON ? HTML ?
    @Produces(MediaType.APPLICATION_XML)
    //Para realizar a leitura de parâmetros do GET utilizamos a anotação 'QueryParam' e acessando 'http://localhost:8080/carrinhos?id=1'. Conforme o padrão REST um recurso é acessado via URI e não através de parâmetros 'http://localhost:8080/carrinhos/1'. Por isso utilizamos a anotação 'PathParam'
    //public String busca(@QueryParam("id") Long id) {
    //public String busca(@PathParam("id") Long id, @PathParam("teste") Long teste) {
    public String busca(@PathParam("id") Long id) {
        
        //Acessa o banco de dados e busca o carrinho
        Carrinho carrinho = new CarrinhoDao().busca(id);

        //System.out.println(teste);

        //pode-se utilizar qualquer biblioteca de serialização/deserialização, ex.: JSON, HTML, XML .....
        return carrinho.toXML();
    }

}