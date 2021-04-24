package com.github.fagner.crud;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @GET
    public List<Produto> buscarTodosProdutos(){
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void cadastroProdutos(CadastrarProdutoDTO cadastrarProdutoDTO){
        Produto produto = new Produto();
        produto.nome = cadastrarProdutoDTO.nome;
        produto.valor = cadastrarProdutoDTO.valor;
        produto.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProdutos(@PathParam("id") Long id,CadastrarProdutoDTO cadastrarProdutoDTO){
        Optional<Produto> produtoOP = Produto.findByIdOptional(id);

        if(produtoOP.isPresent()){

            Produto produto = produtoOP.get();
            produto.nome = cadastrarProdutoDTO.nome;
            produto.valor = cadastrarProdutoDTO.valor;
            produto.persist();

        } else {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void atualizarProdutos(@PathParam("id") Long id){

        Optional<Produto> produtoOP = Produto.findByIdOptional(id);
        produtoOP.ifPresentOrElse(Produto::delete, () -> {throw new NotFoundException();});  
            
    }
}
