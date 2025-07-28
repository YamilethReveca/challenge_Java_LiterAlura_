package com.alura.literatura.principal;
import com.alura.literatura.model.Datos;
import com.alura.literatura.model.DatosAutor;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConvertirJson;

import java.util.Scanner;


public class Principal {

    public static void main(String[] args) {

        int opcion = -1;
        Scanner scanner = new Scanner(System.in);
        final String URL_BASE= "https://gutendex.com/books/";

        while (opcion != 0) {
            System.out.println("\nElija la opción a través de su número:");
            System.out.println("1- Buscar libro por título");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idiomas");
            System.out.println("0- Salir");
            System.out.print("Opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro a buscar: ");
                    String titulo = scanner.nextLine();
                    String url= URL_BASE+ "?search="+titulo.replace(" ", "+");
                    String resultado = ConsumoApi.buscarLibroPorTitulo(url);
                    ConvertirJson convertirJson = new ConvertirJson();
                    var datosJson= convertirJson.convertirDatos(resultado, Datos.class);
                    System.out.println("Url: \n" + url);
                    System.out.println("Resultado: \n" + datosJson);
                    break;
                case 2:
                    // método para listar libros registrados
                    System.out.println("Listando libros registrados...");
                    String respuestaLibros = ConsumoApi.buscarLibroPorTitulo(URL_BASE); // llamada simple
                    ConvertirJson convertirJson1= new ConvertirJson();
                    var datosLibros = convertirJson1.convertirDatos(respuestaLibros, Datos.class);
                    datosLibros.resultados().forEach(System.out::println);
                    break;
                case 3:
                    // método para listar autores registrados
                    System.out.println("Listando autores registrados...");
                    String respuestaAutores = ConsumoApi.buscarLibroPorTitulo(URL_BASE);
                    ConvertirJson convertirJson2 = new ConvertirJson();
                    var datosAutores = convertirJson2.convertirDatos(respuestaAutores, Datos.class);

                    datosAutores.resultados().stream()
                            .flatMap(libro -> libro.autor().stream()) // ✅ se llama autor(), no autores()
                            .map(DatosAutor::nombre)
                            .distinct()
                            .forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Ingrese el año: ");
                    int anio = scanner.nextInt();
                    scanner.nextLine();
                    // método para listar autores vivos ese año
                    String respuestaAnio = ConsumoApi.buscarLibroPorTitulo(URL_BASE);
                    ConvertirJson convertirJson3 = new ConvertirJson();
                    var datosAnio = convertirJson3.convertirDatos(respuestaAnio, Datos.class);

                    System.out.println("Autores vivos en el año " + anio + ":");

                    datosAnio.resultados().stream()
                            .flatMap(libro -> libro.autor().stream())
                            .filter(autor -> autor.death_year()!=null && autor.death_year() > anio)
                            .map(DatosAutor::nombre)
                            .distinct()
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Ingrese el código de idioma (ej. 'es' para español): ");
                    String idioma = scanner.nextLine();
                    // método para listar libros por idioma
                    String urlIdioma = URL_BASE + "?languages=" + idioma;
                    String respuestaIdioma = ConsumoApi.buscarLibroPorTitulo(urlIdioma);
                    ConvertirJson convertirJson4= new ConvertirJson();
                    var datosIdioma = convertirJson4.convertirDatos(respuestaIdioma, Datos.class);
                    datosIdioma.resultados().forEach(System.out::println);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

}
