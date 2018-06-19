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

    @Before
    public void startaServidor() {

        server = Servidor.inicializaServidor();
    }

    @After
    public void mataServidor() {
        
        server.shutdown();
    }
        
    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

        //cliente HTTP que faz as requisições para o servidor
        Client cliente = ClientBuilder.newClient();

        //Criar um alvo 'link' para realizar os trabalhos
        WebTarget target = cliente.target("http://localhost:8080");

        //Faz a requisição ao servidor e passa como parâmetro o formato 'String' que é o que esperamos
        String conteudo = target.path("/carrinhos/1").request().get(String.class);

        //Deserializar o XML para o objeto Carrinho
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);

        Assert.assertEquals("Rua aguas de marco, 210", carrinho.getRua());
    }

    @Test
    public void testaQueInserirUmCarrinhoRetornaSuccess() {

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://localhost:8080");

        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(100l, "Carrionho Azul", 55.89, 1));
        carrinho.setCidade("Belo Horizonte");
        carrinho.setRua("Ipe do Campo");

        String xml = carrinho.toXML();

        //Agora que temos o XML e sabemos que o media type que enviaremos é application/xml, 
        //precisamos representar isso de alguma maneira. Utilizamos a 
        //classe Entity do próprio JAX-RS, para criar tal representação - o conteúdo e o media type.
        //A Entity é utilizada para representar o que será enviado
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);

        Assert.assertEquals("<status>success</status>", response.readEntity(String.class));
    }

}