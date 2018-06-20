package br.com.rodolfo.loja;

import java.util.List;

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

import br.com.rodolfo.loja.models.Projeto;

public class ProjetoTest {
    
    
    private HttpServer server;
    private Client client;
    private WebTarget target;

    @Before
    public void startaServidor() {

        this.server = Servidor.inicializaServidor();
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080");
    }

    @After
    public void mataServidor() {
        
        server.shutdown();
    }
    
    
    @Test
    public void testaAcessoAoRecursoProjetosNoServidor() {

        String conteudo = target.path("/projetos/xml/1").request().get(String.class);

        Assert.assertTrue(conteudo.contains("<nome>Loja Nova"));

    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {

        String conteudo = target.path("/projetos/xml/1").request().get(String.class);

        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

        Assert.assertEquals("Loja Nova", projeto.getNome());
    }

    @Test
    public void testaQueInserirUmProjetoRetornaSuccess() {

        Projeto projeto = new Projeto("Codigo JAVA", 987, 2012);

        String xml = projeto.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);

        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");

        String conteudo = this.client.target(location).request().get(String.class);

        Assert.assertTrue(conteudo.contains("Codigo JAVA"));

        //Assert.assertEquals("<status>success</status>", response.readEntity(String.class));
    }

}