package br.com.rodolfo.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

public class ClienteTest {
    
    @Test
    public void testaQueAConexaoComOServidorFunciona() {
        
        //cliente HTTP que faz as requisições para o servidor
        Client client = ClientBuilder.newClient();

        //Criar um alvo 'link' para realizar os trabalhos
        WebTarget target = client.target("http://www.mocky.io");

        //Faz a requisição ao servidor e passa como parâmetro o formato 'String' que é o que esperamos
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);

        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));

    }

}