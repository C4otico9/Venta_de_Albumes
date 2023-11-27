package com.example.sistemaventas.modelo.dao;

import com.example.sistemaventas.modelo.dominio.Album;
import com.example.sistemaventas.modelo.dominio.Producto;
import com.example.sistemaventas.util.ConexionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AlbumDao {
        private final ConexionDB conexionDB;
        private PreparedStatement preparedStatement;

        public AlbumDao() {
            this.conexionDB = new ConexionDB();
        }

        public void creaTablaAlbum() {
            String sqlTablaAlbum = "create table if not exists album( id int primary key, codigo text,nombre text, artista text,fecha_creacion DATE, id_genero int , precio real)";

            try {
                preparedStatement = conexionDB.connection.prepareStatement(sqlTablaAlbum);
                preparedStatement.execute();
                System.out.println("Tabla album creada o actualizada correctamente");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Album> listarAlbumes() {
            creaTablaAlbum();
            List<Album> albumes = new ArrayList<>();
            String consultaListarAlbumes = "select a.id, a.codigo,a.nombre,a.artista,a.fecha_creacion,g.codigo,g.genero, a.precio from album a left join genero g on a.id_genero = g.id";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaListarAlbumes);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Album album = new Album();
                    album.setIdAlbum(resultSet.getInt(1));
                    album.setCodigoAlbum(resultSet.getString(2));
                    album.setNombreAlbum(resultSet.getString(3));
                    album.setArtistaAlbum(resultSet.getString(4));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    album.setFechaCreacionAlbum(formatter.parse(resultSet.getString(5)));
                    album.setCodigo(resultSet.getString(6));
                    album.setGenero(resultSet.getString(7));
                    album.setPrecioAlbum(resultSet.getDouble(8));
                    albumes.add(album);
                }
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("Listar album correctamente");
            return albumes;
        }

        public List<Album> listarAlbumesxNombre(String nombre) {
            creaTablaAlbum();
            nombre = "%"+nombre+"%";
            List<Album> albumes = new ArrayList<>();
            String consultaListarAlbumes = "select a.id, a.codigo,a.nombre,a.artista,a.fecha_creacion,g.codigo,g.genero, a.precio from album a left join genero g on a.id_genero = g.id where a.nombre like ?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaListarAlbumes);
                preparedStatement.setString(1, nombre);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Album album = new Album();
                    album.setIdAlbum(resultSet.getInt(1));
                    album.setCodigoAlbum(resultSet.getString(2));
                    album.setNombreAlbum(resultSet.getString(3));
                    album.setArtistaAlbum(resultSet.getString(4));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    album.setFechaCreacionAlbum(formatter.parse(resultSet.getString(5)));
                    album.setCodigo(resultSet.getString(6));
                    album.setGenero(resultSet.getString(7));
                    album.setPrecioAlbum(resultSet.getDouble(8));
                    albumes.add(album);
                }
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("Listar album correctamente");
            return albumes;
        }

        public boolean insertarAlbum(Album album) {
            System.out.println(album.toString());
            creaTablaAlbum();
            String consultaInsertarAlbum = "INSERT INTO album(codigo, nombre, artista, fecha_creacion, id_genero,precio) VALUES(?,?,?,?,?,?)";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarAlbum);
                preparedStatement.setString(1, album.getCodigoAlbum());
                preparedStatement.setString(2, album.getNombreAlbum());
                preparedStatement.setString(3, album.getArtistaAlbum());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                preparedStatement.setString(4, formatter.format(album.getFechaCreacionAlbum()));
                preparedStatement.setInt(5, album.getId());
                preparedStatement.setDouble(6, album.getPrecioAlbum());
                preparedStatement.execute();
                System.out.println("Insertar album correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public Album albumPorId(Integer id) {
            creaTablaAlbum();
            String consultarAlbumPorId = "select a.id, a.codigo, a.nombre, a.artista, g.codigo, g.genero, a.id_genero,a.precio\n" +
                    "from album a\n" +
                    "         left join genero g on a.id_genero = g.id where a.id=?";
            Album album = new Album();
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultarAlbumPorId);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    album.setIdAlbum(resultSet.getInt(1));
                    album.setCodigoAlbum(resultSet.getString(2));
                    album.setNombreAlbum(resultSet.getString(3));
                    album.setArtistaAlbum(resultSet.getString(4));
                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    // producto.setFechaCreacionProducto(formatter.parse(resultSet.getString(5)));
                    album.setCodigo(resultSet.getString(5));
                    album.setId(resultSet.getInt(7));
                    album.setGenero(resultSet.getString(6));
                    album.setPrecioAlbum(resultSet.getDouble(7));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("albumPorId de id = " + id + " devuelto correctamente");
            return album;
        }

        public boolean actulizarAlbum(Album album) {
            creaTablaAlbum();
            String consultaInsertarAlbum = "UPDATE album SET codigo=?, nombre=?, artista=?, id_genero=?, precio=? WHERE id=?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarAlbum);
                preparedStatement.setString(1, album.getCodigoAlbum());
                preparedStatement.setString(2, album.getNombreAlbum());
                preparedStatement.setString(3, album.getGeneroAlbum());
                preparedStatement.setInt(4, album.getId());
                preparedStatement.setInt(5, album.getIdAlbum());
                preparedStatement.setDouble(6, album.getPrecioAlbum());
                preparedStatement.execute();
                System.out.println("Actualizar album correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void eliminarAlbumPorId(Integer id) {
            creaTablaAlbum();
            String consultaAlbumPorId = "delete  from album where id=?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaAlbumPorId);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("eliminacion correcta");


        }
    }

