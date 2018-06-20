package br.com.rodolfo.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.rodolfo.loja.dao.ProjetoDao;
import br.com.rodolfo.loja.models.Projeto;

@Path("projetos")
public class ProjetoResource {
    
    @Path("xml/{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    //public String buscaXML(@PathParam("id") Long id) {
    public Projeto buscaXML(@PathParam("id") Long id) {
        
        Projeto projeto = new ProjetoDao().busca(id);

        //utilizando o JAXB
        //return projeto.toXML();

        return projeto;
    }

    @Path("json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJSON(@PathParam("id") Long id) {

        Projeto projeto = new ProjetoDao().busca(id);

        return projeto.toJSON();
    }

    @POST
    //@Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    //public Response adiciona(String conteudo) {
    public Response adiciona(Projeto projeto) {

        //Utilizando o JAXB
        //Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        
        new ProjetoDao().adiciona(projeto);

        URI location = URI.create("/projetos/xml/" + projeto.getId());

        return Response.created(location).build();

        //return "<status>success</status>";
    }

    @Path("xml/{id}")
    @DELETE
    public Response remove(@PathParam("id") Long id) {

        new ProjetoDao().remove(id);

        return Response.ok().build();
    }

}