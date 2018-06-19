package br.com.rodolfo.loja.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import br.com.rodolfo.loja.Servidor;
import br.com.rodolfo.loja.dao.CarrinhoDao;
import br.com.rodolfo.loja.models.Carrinho;
import br.com.rodolfo.loja.models.Produto;

/**
* Classe que representa um recurso WEB, ou seja, que retorna o XML
*/
//Praticamente todo resource será anotado com '@Path' que indica qual será a URI para acessar no servidor
@Path("carrinhos")
public class CarrinhoResource {

    //Informa que na URI receberemos parâmetros de identificação do recurso
    //@Path("{id}/{teste}")
    @Path("xml/{id}")
    //Informar que o método é acessado através do método GET
    @GET
    //Temos que informar com a anotação 'Produces' o que o método retorna, String pura ? XML ? JSON ? HTML ?
    @Produces(MediaType.APPLICATION_XML)
    //Para realizar a leitura de parâmetros do GET utilizamos a anotação 'QueryParam' e acessando 'http://localhost:8080/carrinhos?id=1'. Conforme o padrão REST um recurso é acessado via URI e não através de parâmetros 'http://localhost:8080/carrinhos/1'. Por isso utilizamos a anotação 'PathParam'
    //public String busca(@QueryParam("id") Long id) {
    //public String busca(@PathParam("id") Long id, @PathParam("teste") Long teste) {
    public String buscaXML(@PathParam("id") Long id) {
        
        //Acessa o banco de dados e busca o carrinho
        Carrinho carrinho = new CarrinhoDao().busca(id);

        //System.out.println(teste);

        //pode-se utilizar qualquer biblioteca de serialização/deserialização, ex.: JSON, HTML, XML .....
        return carrinho.toXML();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public String adiciona(String conteudo) {

        //Caso ocorra erro de "Security framework of XStream"
        //XStream xStream = new XStream();
        //Class<?>[] classes = new Class[]{CarrinhoResource.class, Carrinho.class, Produto.class, CarrinhoDao.class, Servidor.class};
        //xStream.allowTypes(classes);
        
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDao().adiciona(carrinho);

        return "<status>success</status>";
    }



    @Path("json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJSON(@PathParam("id") Long id) {

        Carrinho carrinho = new CarrinhoDao().busca(id);

        return carrinho.toJSON();
    }

}