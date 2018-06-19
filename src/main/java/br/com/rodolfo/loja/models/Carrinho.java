package br.com.rodolfo.loja.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class Carrinho {
    
    private List<Produto> produtos = new ArrayList<>();
    private String rua;
    private String cidade;
    private Long id;

    public Carrinho adiciona(Produto produto) {
        produtos.add(produto);
        return this;
    }

    public Carrinho para(String rua, String cidade) {
        
        this.rua = rua;
        this.cidade = cidade;

        return this;
    }

    public Carrinho steId(Long id) {

        this.id = id;
        return this;
    }

    public long getId() {

		return id;
    }
    
    public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
    }
    
    public void setCidade(String cidade) {
		this.cidade = cidade;
    }
    
    public String getCidade() {
        
        return this.cidade;
    }
    
    public void remove(Long id) {
        
        for(Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {

            Produto produto = iterator.next();

            if(produto.getId() == id) {

                iterator.remove();
            }
        }
    }

    public void trocaQuantidade(Produto produto) {

        for(Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {

            Produto p = iterator.next();

            if(p.getId() == produto.getId()) {

                p.setQuantidade(produto.getQuantidade());
                return;
            }
        }
    }


    public List<Produto> getProdutos() {
        
        return this.produtos;
    }

    //Utilizar qualquer biblioteca, JAXB, XSTREAM, Concatenar String, qualquer biblioteca
    public String toXML() {

        //Transforma o objeto em XML
        return new XStream().toXML(this);
    }

}