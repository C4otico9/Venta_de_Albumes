package com.example.sistemaventas.controlador;

import com.example.sistemaventas.modelo.dao.CategoriaDao;
import com.example.sistemaventas.modelo.dao.GeneroDao;
import com.example.sistemaventas.modelo.dominio.Categoria;
import com.example.sistemaventas.modelo.dominio.Genero;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GeneroController implements Initializable {
        @FXML
        private TableView<Genero> tablaGeneros;
        @FXML
        private TableColumn<Genero, String> colCodigo;
        @FXML
        public TableColumn<Genero, String> colGenero;
        @FXML
        public TableColumn<Genero, String> colFechaCreacion;
        @FXML
        private TextField codigoText;
        @FXML
        private TextField generoText;

        @FXML
        public Button guardarBtn;
        Integer idGenero = 0;
        private ObservableList<Genero> generosObservableList = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            listarGeneros();

        }

        public void listarGeneros() {
            tablaGeneros.getItems().clear();

            GeneroDao generoDao = new GeneroDao();
            List<Genero> generos = generoDao.listarGeneros();
            generosObservableList.addAll(generos);
            colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
            colGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            colFechaCreacion.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFechaCreacion())));
            tablaGeneros.setItems(generosObservableList);

            tablaGeneros.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            seleccionarGenero(newValue);
                        }
                    }
            );

        }
        public void onInsertarButtonClick(ActionEvent actionEvent) {
            GeneroDao generoDao = new GeneroDao();
            Genero genero = new Genero();
            if (idGenero == 0) {
                genero.setCodigo(codigoText.getText());
                genero.setGenero(generoText.getText());
                genero.setFechaCreacion(new Date());
                generoDao.insertarGenero(genero);
            } else {
                genero.setId(idGenero);
                genero.setCodigo(codigoText.getText());
                genero.setGenero(generoText.getText());
                generoDao.actualizarGenero(genero);


            }
            idGenero = 0;
            onLimpiarButtonClick(null);
            listarGeneros();
            guardarBtn.setText("Guardar");
        }


        public void onLimpiarButtonClick(ActionEvent actionEvent) {
            codigoText.clear();
            generoText.clear();
        }
        public void onSeleccionarButtonClick(ActionEvent actionEvent) {
            GeneroDao generoDao = new GeneroDao();
            Genero genero = generoDao.generoPorId(idGenero);
            codigoText.setText(genero.getCodigo());
            generoText.setText(genero.getGenero());
            guardarBtn.setText("Guardar Editar");
        }

        private void seleccionarGenero(Genero genero) {
            System.out.println(genero.getId());
            idGenero = genero.getId();
        }
        public void eliminarButtonClick(ActionEvent actionEvent) {
            GeneroDao generoDao = new GeneroDao();
            generoDao.eliminarGeneroPorId(idGenero);
            listarGeneros();
        }

    }

