package br.com.rodolfo.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.rodolfo.loja.models.Projeto;

public class ProjetoDao {
 
    private static Map<Long, Projeto> banco = new HashMap<>();
    private static AtomicLong contador = new AtomicLong(1l);

    static {
        banco.put(1l, new Projeto("Loja Nova", 1l, 2018));
        banco.put(2l, new Projeto("Casa de Praia", 2l, 2017));
    }

    public void adiciona(Projeto projeto) {
        
        Long id = contador.incrementAndGet();
        projeto.setId(id);
        banco.put(id, projeto);
    }

    public Projeto busca(long id) {

        return banco.get(id);
    }

    public Projeto remove(Long id) {

        return banco.remove(id);
    }

}