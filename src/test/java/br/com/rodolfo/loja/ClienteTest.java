package br.com.rodolfo.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.thoughtworks.xstream.XStream;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.rodolfo.loja.models.Carrinho;

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

}