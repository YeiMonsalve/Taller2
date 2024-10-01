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
        addData.enterData();

        Scanner scanner = new Scanner(System.in);

        PersonaDao personaDao = new PersonaDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        LibroDao libroDao = new LibroDao();
        PrestamoDao prestamoDao = new PrestamoDao();

        System.out.println("¿DESEA AGREGAR UNA PERSONA A LA BASE DE DATOS? (si/no)");

        String createPerson = scanner.nextLine().toLowerCase();

        if (createPerson.equals("si")) {

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


            Usuario usuario = new Usuario();
            System.out.println("USUARIO DE LA PERSONA A CREAR.");
            System.out.println("Ingrese el rol del usuario:");
            String rol = scanner.nextLine();
            usuario.setRol(rol);

            usuario.setIdPersona(persona);

            personaDao.crearPersona(persona);
            usuarioDao.crearUsuario(usuario);

            System.out.println("Tanto la persona y el usuario fueron creados exitosamente.");
        } else {
            System.out.println("No se creo ninguna persona o usuario nueva a la base de datos.");
        }

        /////////////////////////////////////////////

        System.out.println("¿DESEA AGREGAR UN LIBRO? (si/no)");
        String createBook = scanner.nextLine().toLowerCase();

        if (createBook.equals("si")) {
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

            libroDao.crearLibro(libro);

            System.out.println("Libro creado exitosamente.");
        } else {
            System.out.println("No se creo ningún libro.");
        }

        /////////////////////////////////////////////

        System.out.println("¿DESEA HACER UN PRÉSTAMO? (si/no)");
        String hacerPrestamo = scanner.nextLine().toLowerCase();

        if (hacerPrestamo.equals("si")) {
            Prestamo prestamo = new Prestamo();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

            System.out.println("Fecha en que se hizo el préstamo del libro (año-mes-día):");
            String fechaPrestamo = scanner.nextLine();
            prestamo.setFechaPrestamo(dateFormat.parse(fechaPrestamo));

            System.out.println("Ingrese la fecha de devolución del libro (año-mes-día):");
            String devolucion = scanner.nextLine();
            prestamo.setFechaDevolucion(dateFormat.parse(devolucion));

            prestamo.setActivo(true);

            System.out.println("Id del usuario al que se le prestó el libro:");
            int userBook = scanner.nextInt();
            scanner.nextLine();

            Usuario obtenerIdUsuario = usuarioDao.obtenerUsuarioId(userBook);

            prestamo.setIdUsuario(obtenerIdUsuario);

            System.out.println("Ingrese el Id del libro préstado.");
            int prestarBook = scanner.nextInt();
            scanner.nextLine();

            Libro obtenerIdLibro = libroDao.obtenerLibroId(prestarBook);
            prestamo.setIdLibro(obtenerIdLibro);

            prestamoDao.crearPrestamo(prestamo);

            System.out.println("Préstamo realizado exitosamente.");
        } else {
            System.out.println("No se hizo ningún préstamo.");
        }

        /////////////////////////////////////////////

        System.out.println("¿DESEA DEVOLVER UN LIBRO?");
        String returnLibro = scanner.nextLine().toLowerCase();

        if (returnLibro.equals("si")) {
            System.out.println("Ingrese el Id del préstamo hecho para ese libro.");
            int returnBook = scanner.nextInt();
            scanner.nextLine();

            Prestamo obtenerIdPrestamo = prestamoDao.obtenerPrestamoId(returnBook);

            if (obtenerIdPrestamo != null && obtenerIdPrestamo.isActivo()) {

                obtenerIdPrestamo.setActivo(false);
                obtenerIdPrestamo.setIdLibro(null);

                prestamoDao.actualizarPrestamo(obtenerIdPrestamo);

                System.out.println("El libro ha sido devuelto correctamente.");
            } else {
                System.out.println("No se encontró un préstamo activo con ese ID.");
            }
        } else {
            System.out.println("No se ha devuelto ningún libro.");
        }

        /////////////////////////////////////////////

        System.out.println("¿DESEA ACTUALIZAR UN LIBRO? (si/no)");
        String updateLibro = scanner.nextLine().toLowerCase();

        if (updateLibro.equals("si")) {
            System.out.println("Ingrese el id del libro a actualizar:");
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
        }

        /////////////////////////////////////////////

        System.out.println("¿DESEA ELIMINAR UN LIBRO? (si/no)");
        String delete = scanner.nextLine().toLowerCase();
        if (delete.equals("si")) {
            System.out.println("Ingrese el ID del libro a eliminar:");
            String libroIdStr2 = scanner.nextLine();
            int libroId2 = Integer.parseInt(libroIdStr2);

            // Verificar si el libro existe.
            Libro libroExistente = libroDao.obtenerLibroId(libroId2);

            if (libroExistente == null) {
                System.out.println("No se encontró ningún libro con el ID especificado.");
            } else {
                // Verificar si el libro tiene un préstamo activo.
                Prestamo prestamoActivo = prestamoDao.obtenerPrestamoPorLibroId(libroId2);

                if (prestamoActivo != null && prestamoActivo.isActivo()) {
                    System.out.println("No se puede eliminar el libro porque tiene un préstamo activo.");
                } else {
                    libroDao.eliminarLibro(libroId2);
                    System.out.println("El libro ha sido eliminado correctamente.");
                }
            }
        } else {
            System.out.println("Ningún libro será eliminado.");
        }

    }
}
