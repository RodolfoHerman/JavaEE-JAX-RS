package br.com.rodolfo.loja.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

//A anotação 'XmlRootElement' informa que esta classe é um elemento válido do XML do JAXB
//A anotação 'XmlAccessorType' com o parâmetro 'XmlAccessType.FIELD' informa que todos os atributos serão serializados por padrão
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Carrinho {
    
    private List<Produto> produtos = new ArrayList<>();
    private String rua;
    private String cidade;
    private Long id;

    //Neecssário para o JAXB
    public Carrinho() {}

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

    public String toJSON() {
        
        return new Gson().toJson(this);
    }

}