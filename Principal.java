package principal;

import com.aluracursos.demo.servicio.ConvierteDatos;
import com.aluracursos.demo.servicio.consumeAPI;
import modelo.DatosEpisodios;
import modelo.DatosSerie;
import modelo.DatosTemporadas;
import modelo.Episodios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private consumeAPI consumoAPI = new consumeAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b8eead10";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();
        var json = consumeAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //bUSCA LOS DATOS DE LAS TEMPORADAS
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumeAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);
        //mostrar solo el titulo de las temporadas
        //for (int i = 0; i < datos.totalDeTemporadas() ; i++) {
        //List<DatosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
        //for (int j = 0; j <episodiosTemporada.size() ; j++) {
        //System.out.println(episodiosTemporada.get(j).titulo());

        // }

        //}
        //forma simple de mostrar los datos
        // temporadas.forEach(t -> t.episodios().forEach(e-> System.out.println(e.titulo())));
        // convertir todas la informacion en una lista del tipo datos episodios
        List<DatosEpisodios> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());





        //top 5 mejores episodios
        //System.out.println("Top 5 Episodios");
        //datosEpisodios.stream()
           //     .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
             //   .peek(e-> System.out.println("Primer filtro (N/A)" + e))
              //  .sorted(Comparator.comparing(DatosEpisodios::evaluacion).reversed())
              //  .peek(e-> System.out.println("Segundo filtro ordenacion (M>m) " + e))
               // .map(e-> e.titulo().toUpperCase())
                //.peek(e-> System.out.println("Tercer filtro Mayuscula (m>M)" + e))
                //.limit(5)
               // .forEach(System.out::println);









        //Convirtiendo los datos a una lista del tipo episodio
        List<Episodios> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodios(t.numero(), d)))
                .collect(Collectors.toList());

       // episodios.forEach(System.out::println);

        //Busqueda de episodios apartir de x año
       // System.out.println("Por davor indica el año a partir de que fecha desas ver");
        //var fecha = teclado.nextInt();
       // teclado.nextLine();

        //LocalDate fechaDebusqueda = LocalDate.of(fecha,1,1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

       // episodios.stream()
             //   .filter(e-> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaDebusqueda))
          //      .forEach(e-> System.out.println(
            //         "Temporada " + e.getTemporada() +
            //                 "Episodio " + e.getTitulo() +
             //                "Fecha de lanzamiento" + e.getFechaDeLanzamiento().format(dtf)
        //   ));

        //Busca de episodios por pedazo de titulo
       // System.out.println("Ingrese el nombre del episodio");
        //var pedazoTitulo = teclado.nextLine();
       // Optional<Episodios> EpisodioBuscado = episodios.stream()
             //   .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
           //     .findFirst();
       // if (EpisodioBuscado.isPresent()){
           // System.out.println("Episodio encontrado");
           // System.out.println("Los datos son: " + EpisodioBuscado.get());
        //} else {
         //   System.out.println("Episodio no encontrado");
        //}
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e-> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodios::getTemporada, Collectors.averagingDouble(Episodios::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e-> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodios::getEvaluacion));
        System.out.println("Media de las evaluaciones: " + est.getAverage());
        System.out.println("Episodios Mejor Evaluados: " + est.getMax());
        System.out.println("Episodios Peor evaluados: " + est.getMin());
    }
}

