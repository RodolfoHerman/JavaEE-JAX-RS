package br.com.rodolfo.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.rodolfo.loja.models.Carrinho;
import br.com.rodolfo.loja.models.Produto;

public class CarrinhoDao {

    private static Map<Long, Carrinho> banco = new HashMap<>();
    private static AtomicLong contador = new AtomicLong(1);

    static {
        Produto videogame = new Produto(6237, "Videogame 4", 4000, 1);
        Produto esporte = new Produto(3467, "Jogo de esporte", 60, 2);

        Carrinho carrinho = new Carrinho()
                                .adiciona(videogame)
                                .adiciona(esporte)
                                .para("Rua aguas de marco, 210", "Belo Horizonte")
                                .steId(1l);

        banco.put(1l, carrinho);
    }
    
    public void adiciona(Carrinho carrinho) {

        Long id = contador.incrementAndGet();
        carrinho.steId(id);
        banco.put(id, carrinho);
    }

    public Carrinho busca(Long id) {

        return banco.get(id);
    }

    public Carrinho remove(Long id) {
        
        return banco.remove(id);
    } 

}