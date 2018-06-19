package br.com.rodolfo.loja;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.thoughtworks.xstream.XStream;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.rodolfo.loja.models.Projeto;

public class ProjetoTest {
    
    
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
    public void testaAcessoAoRecursoProjetosNoServidor() {

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://localhost:8080");

        String conteudo = target.path("/projetos").request().get(String.class);

        Assert.assertTrue(conteudo.contains("<nome>Loja Nova"));

    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {

        Client cliente = ClientBuilder.newClient();

        WebTarget target = cliente.target("http://localhost:8080");

        String conteudo = target.path("/projetos").request().get(String.class);

        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

        Assert.assertEquals("Loja Nova", projeto.getNome());
    }

}