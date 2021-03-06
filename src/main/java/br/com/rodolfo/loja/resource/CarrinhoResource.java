package br.com.rodolfo.loja.resource;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    //Temos que informar com a anotação 'Produces' o que o método retorna, String pura ? XML ? JSON ? HTML ?. Idempotente
    @Produces(MediaType.APPLICATION_XML)
    //Para realizar a leitura de parâmetros do GET utilizamos a anotação 'QueryParam' e acessando 'http://localhost:8080/carrinhos?id=1'. Conforme o padrão REST um recurso é acessado via URI e não através de parâmetros 'http://localhost:8080/carrinhos/1'. Por isso utilizamos a anotação 'PathParam'
    //public String busca(@QueryParam("id") Long id) {
    //public String busca(@PathParam("id") Long id, @PathParam("teste") Long teste) {
    //public String buscaXML(@PathParam("id") Long id) {
    public Carrinho buscaXML(@PathParam("id") Long id) {
        
        //Acessa o banco de dados e busca o carrinho
        Carrinho carrinho = new CarrinhoDao().busca(id);

        //System.out.println(teste);

        //pode-se utilizar qualquer biblioteca de serialização/deserialização, ex.: JSON, HTML, XML .....
        //return carrinho.toXML();

        //Utilizando o JAXB
        return carrinho;
    }

    @POST
    //POST não PRODUZ e sim CONSOME um RECURSO. Não retornamos nada, somente o HEAD sem conteúdo nenhum. É opcional devolver o conteúdo, no camso podemos devolver o XML junto. Não é idempotente
    //@Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    //public Response adiciona(String conteudo) {
    public Response adiciona(Carrinho carrinho) {

        //Caso ocorra erro de "Security framework of XStream"
        //XStream xStream = new XStream();
        //Class<?>[] classes = new Class[]{CarrinhoResource.class, Carrinho.class, Produto.class, CarrinhoDao.class, Servidor.class};
        //xStream.allowTypes(classes);
        
        //usando o JAXB
        //Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDao().adiciona(carrinho);

        //Criando a URI para o LOCATION do HEAD
        URI location = URI.create("/carrinhos/xml/" + carrinho.getId());

        //Retorna no HEAD o STATUS 201 (created) e o LOCATION a uri
        return Response.created(location).build();

        //return "<status>success</status>";
    }

    @Path("json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJSON(@PathParam("id") Long id) {

        Carrinho carrinho = new CarrinhoDao().busca(id);

        return carrinho.toJSON();
    }

    //O verbo DELETE não recebe e nem envia a representação. Idempotente
    //No parâmetro do 'Path' temos uma URI que aponta para um subrecurso (um recurso dentro de outro)
    @Path("xml/{id}/produtos/{idProduto}")
    @DELETE
    public Response remove(@PathParam("id") Long id, 
                           @PathParam("idProduto") Long idProduto) {

        Carrinho carrinho = new CarrinhoDao().busca(id);
        carrinho.remove(idProduto);

        return Response.ok().build();
    }

    //O PUT recebe PARTES/CAMPOS de um recurso para realizar a atualização. Aqui é Atualizado um subrecurso. Idempotente
    //Se é o recurso inteiro, utilize a URI que o representa (xml/{id}/produtos/{idProduto}), caso contrário, utilize uma nova URI que representa a parte a ser trocada (xml/{id}/produtos/{idProduto}/quantidade).
    @Path("xml/{id}/produtos/{idProduto}/quantidade")
    @PUT
    //public Response trocaProdutoQuantidade(String conteudo,
    public Response trocaProdutoQuantidade(Produto produto,
                                 @PathParam("id") Long id,
                                 @PathParam("idProduto") Long idProduto) {

        Carrinho carrinho = new CarrinhoDao().busca(id);

        //Utilizando o JAXB em PRODUTO
        //Produto produto = (Produto) new XStream().fromXML(conteudo);

        //Passamos para o PUT as partes que desejamos ATUALIZAR, no caso passamos no XML apenas o ID e a QUANTIDADE
        carrinho.trocaQuantidade(produto);

        return Response.ok().build();
    }


}