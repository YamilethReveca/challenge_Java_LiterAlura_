package com.alura.literatura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") Integer death_year

) {

    @Override
    public String toString(){
        return "Nombre: "+ nombre + ", fecha de nacimiento: "+ fechaDeNacimiento + "Death year: "+ death_year;
    }
}

