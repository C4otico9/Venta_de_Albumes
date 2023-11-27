package com.example.sistemaventas.modelo.dao;

import com.example.sistemaventas.modelo.dominio.Categoria;
import com.example.sistemaventas.modelo.dominio.Genero;
import com.example.sistemaventas.util.ConexionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GeneroDao {
        private final ConexionDB conexionDB;
        private PreparedStatement preparedStatement;

        public GeneroDao() { this.conexionDB = new ConexionDB(); }

        public void creaTablaGenero() {
            String sqlTablaGenero = "CREATE TABLE IF NOT EXISTS genero (id integer primary key,codigo text,genero text,fecha_creacion DATE)";

            try {
                preparedStatement = conexionDB.connection.prepareStatement(sqlTablaGenero);
                preparedStatement.execute();
                System.out.println("Tabla genero creada o actualizada correctamente");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Genero> listarGeneros() {
            creaTablaGenero();
            List<Genero> generos = new ArrayList<>();
            String consultaListarGeneros = "select id, codigo, genero, fecha_creacion from genero";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaListarGeneros);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Genero genero = new Genero();
                    genero.setId(resultSet.getInt(1));
                    genero.setCodigo(resultSet.getString(2));
                    genero.setGenero(resultSet.getString(3));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    genero.setFechaCreacion(formatter.parse(resultSet.getString(4)));
                    generos.add(genero);
                }
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("Listar genero correctamente");
            return generos;
        }
        public boolean insertarGenero(Genero genero) {
            creaTablaGenero();
            String consultaInsertarGenero = "INSERT INTO genero(codigo,genero,fecha_creacion) VALUES(?,?,?)";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarGenero);
                preparedStatement.setString(1, genero.getCodigo());
                preparedStatement.setString(2, genero.getGenero());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                preparedStatement.setString(3, formatter.format(genero.getFechaCreacion()));
                preparedStatement.execute();
                System.out.println("Insertar genero correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public Genero generoPorId(Integer id) {
            creaTablaGenero();
            String consultaGeneroPorId = "select id, codigo, genero, fecha_creacion from genero where id =?";
            Genero genero = new Genero();
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaGeneroPorId);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    genero.setId(resultSet.getInt(1));
                    genero.setCodigo(resultSet.getString(2));
                    //categoria.setCodigo(resultSet.getString(2));
                    genero.setGenero(resultSet.getString(3));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("generoPorId de id = " + id + " devuelto correctamente");
            return genero;
        }

        public boolean actualizarGenero(Genero genero) {
            creaTablaGenero();
            String consultaInsertarGenero = "UPDATE genero SET codigo =?,genero=? WHERE id=?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarGenero);
                preparedStatement.setString(1, genero.getCodigo());
                preparedStatement.setString(2, genero.getGenero());
                preparedStatement.setInt(3, genero.getId());
                preparedStatement.execute();
                System.out.println("Actualizar genero correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void eliminarGeneroPorId(Integer id) {
            creaTablaGenero();
            String consultaGeneroPorId = "delete from genero where id=?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaGeneroPorId);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("eliminacion correcta");


        }

    }


