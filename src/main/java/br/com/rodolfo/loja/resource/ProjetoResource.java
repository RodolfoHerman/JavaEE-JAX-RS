package br.com.rodolfo.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.rodolfo.loja.dao.ProjetoDao;
import br.com.rodolfo.loja.models.Projeto;

@Path("projetos")
public class ProjetoResource {
    
    @Path("xml/{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String buscaXML(@PathParam("id") Long id) {
        
        Projeto projeto = new ProjetoDao().busca(id);

        return projeto.toXML();
    }

    @Path("json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJSON(@PathParam("id") Long id) {

        Projeto projeto = new ProjetoDao().busca(id);

        return projeto.toJSON();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public String adiciona(String conteudo) {

        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDao().adiciona(projeto);

        return "<status>success</status>";
    }

}