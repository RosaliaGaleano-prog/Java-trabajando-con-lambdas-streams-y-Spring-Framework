package com.aluracursos.demo;

import com.aluracursos.demo.servicio.ConvierteDatos;
import com.aluracursos.demo.servicio.consumeAPI;
import modelo.DatosEpisodios;
import modelo.DatosSerie;
import modelo.DatosTemporadas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import principal.EjemplosStreams;
import principal.Principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraElMenu();
       // EjemplosStreams ejemplosStreams = new EjemplosStreams();
        //ejemplosStreams.muestraEjemplo();
    }
}
