package com.fiec.provafinal.models;


import jakarta.persistence.EntityManager;

public class SapatoRepositorio extends GenericRepositorioImpl<Sapato, String>{

    public SapatoRepositorio(EntityManager em){
        super(em);
    }


    @Override
    Class<Sapato> getMyClass() {
        return Sapato.class;
    }
}
