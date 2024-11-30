package com.fiec.provafinal;

import com.fiec.provafinal.models.Sapato;
import com.fiec.provafinal.models.SapatoRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@WebServlet("/sapatos")
public class SapatoServlet extends HttpServlet {

    private SapatoRepositorio sapatoRepositorio;

    private EntityManager em;

    public SapatoServlet(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("minhaUnidadeDePersistencia");
        this.em = entityManagerFactory.createEntityManager();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String preco = request.getParameter("preco");
        String imagem = request.getParameter("imagem");
        String tamanho = request.getParameter("tamanho");
        String marca = request.getParameter("marca");
        Sapato sapato = Sapato.builder()
                .nome(nome)
                .preco(Double.parseDouble(preco))
                .imagem(imagem)
                .tamanho(parseInt(tamanho))
                .marca(marca)
                .build();

        response.setContentType("text/html");
        response.getWriter().println("Produto Salvo");
    }
    // Read  /produtos
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        List<Sapato> sapatos = sapatoRepositorio.ler();

        response.getWriter().println(sapatos.stream().map(Sapato::toString).collect(Collectors.toList()));

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = getId(request);
        Sapato atual = em.find(Sapato.class, id);
        String nome = request.getParameter("nome");
        String preco = request.getParameter("preco");
        String imagem = request.getParameter("imagem");
        String tamanho = request.getParameter("tamanho");
        String marca = request.getParameter("marca");
        em.getTransaction().begin();
        if(atual != null){
            atual.setNome(nome);
            atual.setPreco(Double.parseDouble(preco));
            atual.setImagem(imagem);
            atual.setTamanho(parseInt(tamanho));
            atual.setMarca(marca);
            em.merge(atual);
        }

        em.getTransaction().commit();

        response.setContentType("text/html");
        response.getWriter().println("Produto Atualizado");

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = getId(request);

        em.getTransaction().begin();
        Sapato s = em.find(Sapato.class, id);
        em.remove(s);
        em.getTransaction().commit();
        response.setContentType("text/html");
        response.getWriter().println("Produto Deletado");
    }

    private static String getId(HttpServletRequest req){
        String path = req.getPathInfo();
        String[] paths = path.split("/");
        String id = paths[paths.length - 1];
        return id;
    }
}
