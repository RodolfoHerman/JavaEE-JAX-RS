package br.com.rodolfo.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
* Classe responsável por criar um servidor do Grizzly
*/
public class Servidor {

    public static void main(String[] args) throws IOException {
        
        HttpServer server = inicializaServidor();

        System.out.println("Servidor rodando");

        System.in.read();
        server.shutdown();

    }


    public static HttpServer inicializaServidor() {

        //Configuração dos recursos. Tudo que estiver dentro do pacote "br.com.rodolfo.loja" deverá ser gerenciado. Informamos ao servidor que nossa aplicação é baseada em JAX-RS e não em servlet-api
        ResourceConfig config = new ResourceConfig().packages("br.com.rodolfo.loja");

        //Mapeando a URI, informando qual a porta abrir o servidor
        URI uri = URI.create("http://localhost:8080/");

        //Criando o servidor do Grizzly compatível com JAX-RS, servlet api e outros. necessário passar o parâmetro de configuração para o servidor 
        //entender quais as classes do JAX-RS
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);

        return server;
    }

}