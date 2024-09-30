package org.example.data;

import org.example.entities.Libro;
import org.example.entities.Persona;
import org.example.entities.Prestamo;
import org.example.entities.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class Data {
    private SessionFactory sessionFactory;
    private boolean dataInit;

    public Data() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void enterData() throws ParseException {
        Session session = sessionFactory.openSession();
        try {

            long countPersonas = (long) session.createQuery("SELECT COUNT(p.id) FROM Persona p").uniqueResult();
            if (countPersonas > 0) {
                System.out.println("Los datos ya existen en la base de datos.");
                dataInit = true;
                session.close();
                return;
            }

            session.beginTransaction();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");

            // Personas.
            Persona persona1 = new Persona();
            persona1.setNombre("Yeison");
            persona1.setApellido("Monsalve");
            persona1.setSexo("Masculino");

            Persona persona2 = new Persona();
            persona2.setNombre("Julian");
            persona2.setApellido("Casablancas");
            persona2.setSexo("Masculino");

            Persona persona3 = new Persona();
            persona3.setNombre("Avril");
            persona3.setApellido("Lavigne");
            persona3.setSexo("Femenino");

            Persona persona4 = new Persona();
            persona4.setNombre("Alex");
            persona4.setApellido("Turner");
            persona4.setSexo("Masculino");

            Persona persona5 = new Persona();
            persona5.setNombre("Hayley");
            persona5.setApellido("Williams");
            persona5.setSexo("Femenino");

            // Usuarios.
            Usuario usuario1 = new Usuario();
            usuario1.setIdPersona(persona1);
            usuario1.setRol("Bibliotecario");

            Usuario usuario2 = new Usuario();
            usuario2.setIdPersona(persona2);
            usuario2.setRol("Cliente");

            Usuario usuario3 = new Usuario();
            usuario3.setIdPersona(persona3);
            usuario3.setRol("Cliente");

            Usuario usuario4 = new Usuario();
            usuario4.setIdPersona(persona4);
            usuario4.setRol("Bibliotecario");

            Usuario usuario5 = new Usuario();
            usuario5.setIdPersona(persona5);
            usuario5.setRol("Cliente");

            // Libros.
            Libro libro1 = new Libro();
            libro1.setTitulo("Bajo la misma estrella");
            libro1.setAutor("John Green");
            libro1.setIsbn("2356");

            Libro libro2 = new Libro();
            libro2.setTitulo("Looking for Alaska");
            libro2.setAutor("John Green");
            libro2.setIsbn("1223");

            Libro libro3 = new Libro();
            libro3.setTitulo("Las ventajas de ser invisible");
            libro3.setAutor("Stephen Chbosky");
            libro3.setIsbn("4343");

            Libro libro4 = new Libro();
            libro4.setTitulo("Matar un ruiseñor");
            libro4.setAutor("Harper Lee");
            libro4.setIsbn("7612");

            Libro libro5 = new Libro();
            libro5.setTitulo("Cuentos del escondite secreto");
            libro5.setAutor("Anne Frank");
            libro5.setIsbn("1944");

            // Prestamos.
            Prestamo prestamo1 = new Prestamo();
            prestamo1.setIdUsuario(usuario1);
            prestamo1.setIdLibro(libro1);
            prestamo1.setFechaPrestamo(formato.parse("2024-08-20"));
            prestamo1.setFechaDevolucion(formato.parse("2024-10-20"));
            prestamo1.setActivo(true);

            Prestamo prestamo2 = new Prestamo();
            prestamo2.setIdUsuario(usuario2);
            prestamo2.setIdLibro(null);
            prestamo2.setFechaPrestamo(formato.parse("2024-02-28"));
            prestamo2.setFechaDevolucion(formato.parse("2024-03-28"));
            prestamo2.setActivo(false);

            Prestamo prestamo3 = new Prestamo();
            prestamo3.setIdUsuario(usuario3);
            prestamo3.setIdLibro(libro3);
            prestamo3.setFechaPrestamo(formato.parse("2024-08-30"));
            prestamo3.setFechaDevolucion(formato.parse("2024-12-30"));
            prestamo3.setActivo(true);

            Prestamo prestamo4 = new Prestamo();
            prestamo4.setIdUsuario(usuario4);
            prestamo4.setIdLibro(null);
            prestamo4.setFechaPrestamo(formato.parse("2023-12-01"));
            prestamo4.setFechaDevolucion(formato.parse("2024-01-12"));
            prestamo4.setActivo(false);

            Prestamo prestamo5 = new Prestamo();
            prestamo5.setIdUsuario(usuario5);
            prestamo5.setIdLibro(libro5);
            prestamo5.setFechaPrestamo(formato.parse("2024-06-28"));
            prestamo5.setFechaDevolucion(formato.parse("2024-11-11"));
            prestamo5.setActivo(true);

            List<Object> entities = Arrays.asList(persona1, persona2, persona3, persona4, persona5, usuario1, usuario2, usuario3, usuario4, usuario5, libro1, libro2, libro3, libro4, libro5, prestamo1, prestamo2, prestamo3, prestamo4, prestamo5);

            for (Object entity : entities) {
                session.persist(entity);
            }

            session.getTransaction().commit();
            dataInit = true;
            System.out.println("Los datos han sido agregados correctamente.");
        } catch (HibernateException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}