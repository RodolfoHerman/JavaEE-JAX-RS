package br.com.rodolfo.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.rodolfo.loja.models.Carrinho;
import br.com.rodolfo.loja.models.Produto;

public class ClienteTest {
    
    private HttpServer server;
    private Client cliente;
    private WebTarget target;

    @Before
    public void startaServidor() {

        server = Servidor.inicializaServidor();

        //cliente HTTP que faz as requisições para o servidor
        this.cliente = ClientBuilder.newClient();

        //Criar um alvo 'link' para realizar os trabalhos
        this.target = this.cliente.target("http://localhost:8080");
    }

    @After
    public void mataServidor() {
        
        server.shutdown();
    }
        
    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

        //Faz a requisição ao servidor e passa como parâmetro o formato 'String' que é o que esperamos
        String conteudo = this.target.path("/carrinhos/xml/1").request().get(String.class);

        //Deserializar o XML para o objeto Carrinho
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);

        Assert.assertEquals("Rua aguas de marco, 210", carrinho.getRua());
    }

    @Test
    public void testaQueInserirUmCarrinhoRetornaSuccess() {

        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(100l, "Carrinho Azul", 55.89, 1));
        carrinho.setCidade("Belo Horizonte");
        carrinho.setRua("Ipe do Campo");

        String xml = carrinho.toXML();

        //Agora que temos o XML e sabemos que o media type que enviaremos é application/xml, 
        //precisamos representar isso de alguma maneira. Utilizamos a 
        //classe Entity do próprio JAX-RS, para criar tal representação - o conteúdo e o media type.
        //A Entity é utilizada para representar o que será enviado
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = this.target.path("/carrinhos").request().post(entity);

        //Status 201 é o código do CREATED
        Assert.assertEquals(201, response.getStatus());

        //Pegar o HEADER LOCATION 'http:localhost:8080/carrinhos/xml/{id}'
        String location = response.getHeaderString("Location");

        String conteudo = cliente.target(location).request().get(String.class);

        Assert.assertTrue(conteudo.contains("Carrinho Azul"));

        //Assert.assertEquals("<status>success</status>", response.readEntity(String.class));
    }

    @Test
    public void testaQueFormatarUmaStringRetornaOEsperado() {

        String forma = String.format("[Produto : %d %s %d %.2f]", 1l, "Carro Verde", 1, 1.00);

        Assert.assertEquals("[Produto : 1 Carro Verde 1 1,00]", forma);

    }

}