package com.example.sistemaventas.controlador;

import com.example.sistemaventas.modelo.dao.AlbumDao;
import com.example.sistemaventas.modelo.dao.CategoriaDao;
import com.example.sistemaventas.modelo.dao.GeneroDao;
import com.example.sistemaventas.modelo.dao.ProductoDao;
import com.example.sistemaventas.modelo.dominio.Album;
import com.example.sistemaventas.modelo.dominio.Categoria;
import com.example.sistemaventas.modelo.dominio.Genero;
import com.example.sistemaventas.modelo.dominio.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AlbumController implements Initializable {
        @FXML
        private TableView<Album> tablaAlbumes;
        @FXML
        private TableColumn<Album, String> colCodigoAlbum;
        @FXML
        public TableColumn<Album, String> colNombreAlbum;
        @FXML
        public TableColumn<Album, String> colArtistaAlbum;
        @FXML
        public TableColumn<Album, String> colPrecioAlbum;
        @FXML
        public TableColumn<Album, String> colFechaCreacionAlbum;
        @FXML
        public TableColumn<Album, String> colCodigoGenero;
        @FXML
        public TableColumn<Album, String> colGeneroGenero;
        @FXML
        private TextField codigoText;
        @FXML
        private TextField nombreText;
        @FXML
        private TextField artistaText;
        @FXML
        private TextField precioText;
        @FXML
        private ComboBox generoBox;

        @FXML
        public Button guardarBtn;
        Integer idAlbum = 0;
        private ObservableList<Album> albumesObservableList = FXCollections.observableArrayList();
        private ObservableList<Genero> generosObservableList = FXCollections.observableArrayList();
        Genero generoSeleccionada = new Genero();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            listarAlbumes();
            listarGeneros();

        }

        public void listarGeneros() {

            GeneroDao generoDao = new GeneroDao();
            List<Genero> generos = generoDao.listarGeneros();
            generosObservableList.addAll(generos);
            generoBox.setPromptText("Selecciona una opción");
            generoBox.setItems(generosObservableList);
            generoBox.setConverter(new StringConverter<Genero>() {
                @Override
                public String toString(Genero genero) {
                    return genero.getGenero();
                }


                @Override
                public Genero fromString(String string) {
                    return null;
                }
            });
            generoBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    seleccionarGenero((Genero) newValue);
                }
            });


        }

        private void seleccionarGenero(Genero newValue) {
            generoSeleccionada = newValue;
        }

        public void listarAlbumes() {
            tablaAlbumes.getItems().clear();

            AlbumDao albumDao = new AlbumDao();
            List<Album> albumes = albumDao.listarAlbumes();
            albumesObservableList.addAll(albumes);
            colCodigoAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoAlbum()));
            colNombreAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAlbum()));
            colArtistaAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtistaAlbum()));

            colCodigoGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
            colGeneroGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            colFechaCreacionAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFechaCreacionAlbum())));
            tablaAlbumes.setItems(albumesObservableList);

            tablaAlbumes.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            seleccionarAlbum(newValue);
                        }
                    }
            );

        }

        public void onInsertarButtonClick(ActionEvent actionEvent) {
            System.out.println("click boton guardar");
            AlbumDao albumDao = new AlbumDao();
            Album album = new Album();
            if (idAlbum == 0) {
                album.setCodigoAlbum(codigoText.getText());
                album.setNombreAlbum(nombreText.getText());
                album.setArtistaAlbum(artistaText.getText());
                album.setFechaCreacionAlbum(new Date());
                album.setId(generoSeleccionada.getId());
                album.setPrecioAlbum(Double.valueOf(precioText.getText()));
                albumDao.insertarAlbum(album);
            } else {
                album.setIdAlbum(idAlbum);
                album.setCodigoAlbum(codigoText.getText());
                album.setNombreAlbum(nombreText.getText());
                album.setArtistaAlbum(artistaText.getText());
                album.setFechaCreacionAlbum(new Date());
                album.setId(generoSeleccionada.getId());
                album.setPrecioAlbum(Double.valueOf(precioText.getText()));
                albumDao.actulizarAlbum(album);


            }
            idAlbum = 0;
            onLimpiarButtonClick(null);
            listarAlbumes();
            guardarBtn.setText("Guardar");
        }


        public void onLimpiarButtonClick(ActionEvent actionEvent) {
            codigoText.clear();
            nombreText.clear();
            artistaText.clear();
            precioText.clear();
            generoBox.getSelectionModel().clearSelection();
        }

        public void onSeleccionarButtonClick(ActionEvent actionEvent) {
            AlbumDao albumDao = new AlbumDao();
            Album album = albumDao.albumPorId(idAlbum);
            codigoText.setText(album.getCodigoAlbum());
            nombreText.setText(album.getNombreAlbum());
            artistaText.setText(album.getArtistaAlbum());
            precioText.setText(Double.toString(album.getPrecioAlbum()));
            Genero genero = new Genero();
            genero.setId(genero.getId());
            genero.setGenero(genero.getGenero());
            generoBox.getSelectionModel().select(genero);
            guardarBtn.setText("Guardar Editar");
        }

        private void seleccionarAlbum(Album album) {
            idAlbum = album.getIdAlbum();
        }

        public void eliminarButtonClick(ActionEvent actionEvent) {
            AlbumDao albumDao = new AlbumDao();
            albumDao.eliminarAlbumPorId(idAlbum);
            listarAlbumes();
        }

    }

