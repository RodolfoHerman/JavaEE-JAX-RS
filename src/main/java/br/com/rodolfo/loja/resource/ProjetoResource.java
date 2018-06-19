package br.com.rodolfo.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rodolfo.loja.dao.ProjetoDao;
import br.com.rodolfo.loja.models.Projeto;

@Path("projetos")
public class ProjetoResource {
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String name(@PathParam("id") Long id) {
        
        Projeto projeto = new ProjetoDao().busca(id);

        return projeto.toXML();
    }

}