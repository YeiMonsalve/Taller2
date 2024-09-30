package org.example;

import org.example.connection.DatabaseConnection;
import org.example.controllers.LibroDao;
import org.example.controllers.PersonaDao;
import org.example.controllers.PrestamoDao;
import org.example.controllers.UsuarioDao;
import org.example.data.Data;
import org.example.entities.Libro;
import org.example.entities.Persona;
import org.example.entities.Prestamo;
import org.example.entities.Usuario;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConnection dbconnect = new DatabaseConnection();
        dbconnect.connectDb();

        Data addData = new Data();
        //addData.enterData();

        Scanner scanner = new Scanner(System.in);

        System.out.println("AGREGAR UNA PERSONA A LA BASE DE DATOS.");
        Persona persona = new Persona();

        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();
        persona.setNombre(nombre);

        System.out.println("Ingrese el apellido:");
        String apellido = scanner.nextLine();
        persona.setApellido(apellido);

        System.out.println("Ingrese el sexo:");
        String sexo = scanner.nextLine();
        persona.setSexo(sexo);

        PersonaDao personaDao = new PersonaDao();
        personaDao.crearPersona(persona);

        System.out.println("Persona guardada exitosamente en la base de datos.");

        /////////////////////////////////////////////

        System.out.println("CREAR EL USUARIO DE LA PERSONA.");
        Usuario usuario = new Usuario();

        System.out.println("Ingrese el rol del usuario:");
        String rol = scanner.nextLine();
        usuario.setRol(rol);

        usuario.setIdPersona(persona);

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.crearUsuario(usuario);

        System.out.println("Usuario creado exitosamente.");

        /////////////////////////////////////////////

        System.out.println("CREAR UN LIBRO.");
        Libro libro = new Libro();

        System.out.println("Ingrese el título del libro:");
        String titulo = scanner.nextLine();
        libro.setTitulo(titulo);

        System.out.println("Ingrese el autor del libro:");
        String autor = scanner.nextLine();
        libro.setAutor(autor);

        System.out.println("Ingrese el isbn del libro:");
        String isbn = scanner.nextLine();
        libro.setIsbn(isbn);

        LibroDao libroDao = new LibroDao();
        libroDao.crearLibro(libro);

        System.out.println("Libro creado exitosamente.");

        /////////////////////////////////////////////

        System.out.println("DATOS PARA HACER EL PRÉSTAMO DEL LIBRO AL USUARIO:");
        Prestamo prestamo = new Prestamo();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        System.out.println("Fecha en que se hizo el préstamo del libro (año-mes-día):");
        String fechaPrestamo = scanner.nextLine();
        prestamo.setFechaPrestamo(dateFormat.parse(fechaPrestamo));

        System.out.println("Ingrese la fecha de devolución del libro (año-mes-día):");
        String devolucion = scanner.nextLine();
        prestamo.setFechaDevolucion(dateFormat.parse(devolucion));

        //Presar un libro a un usuario.

        System.out.println("Id del usuario al que se le prestó el libro:");
        int userBook = scanner.nextInt();
        scanner.nextLine();

        Usuario obtenerIdUsuario = usuarioDao.obtenerUsuarioId(userBook);

        prestamo.setIdUsuario(obtenerIdUsuario);

        //Libro que se prestó.
        System.out.println("Ingrese el Id del libro préstado.");
        int prestarBook = scanner.nextInt();
        scanner.nextLine();

        Libro obtenerIdLibro = libroDao.obtenerLibroId(prestarBook);
        prestamo.setIdLibro(obtenerIdLibro);



        System.out.println("Préstamo realizado exitosamente.");

        /////////////////////////////////////////////

        //Devolver un libro.
        System.out.println("¿Desea devolver el libro?");
        String answer = scanner.nextLine();

        if (answer.equals("si")) {
            prestamo.setActivo(true);
            prestamo.setIdLibro(null);
        } else {
            prestamo.setActivo(false);
        }

        // Crear instancia del DAO y guardar el préstamo.
        PrestamoDao prestamoDao = new PrestamoDao();
        prestamoDao.crearPrestamo(prestamo);
        /////////////////////////////////////////////


        System.out.println("INGRESE EL ID DEL LIBRO A ACTUALIZAR.");
        int libroId = scanner.nextInt();
        scanner.nextLine();

        Libro actualizarLibro = libroDao.obtenerLibroId(libroId);

        if (actualizarLibro != null) {
            System.out.println("Ingrese el nuevo título del libro:");
            String nuevoTitulo = scanner.nextLine();
            if (!nuevoTitulo.isEmpty()) {
                actualizarLibro.setTitulo(nuevoTitulo);
            }

            System.out.println("Ingrese el autor del libro:");
            String nuevoAutor = scanner.nextLine();
            if (!nuevoAutor.isEmpty()) {
                actualizarLibro.setAutor(nuevoAutor);
            }

            System.out.println("Ingrese el ISBN del libro:");
            String nuevoIsbn = scanner.nextLine();
            if (!nuevoIsbn.isEmpty()) {
                actualizarLibro.setIsbn(nuevoIsbn);
            }

            libroDao.actualizarLibro(actualizarLibro);

            System.out.println("El libro se ha actualizado exitosamente.");
        } else {
            System.out.println("No se encontró el libro con el ID especificado.");
        }

        /////////////////////////////////////////////

        // Eliminar libro.
        System.out.println("Ingrese el ID del libro a eliminar:");
        String libroIdStr2 = scanner.nextLine();
        int libroId2 = Integer.parseInt(libroIdStr2);

        LibroDao libroDao2 = new LibroDao();
        libroDao.eliminarLibro(libroId2);
    }
}
