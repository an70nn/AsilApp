package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

public class PlaceInterestFragment extends Fragment {
    private final Double LATITUDE = 41.1172;
    private final Double LONGITUDE = 16.8719; //Coordinate di Bari
    private ImageButton placeZoomIn, placeZoomOut;
    private ImageButton placeClinic, placeTownHall, placePostOffice, placeWorship, placeLanguageSchool, placePharmacy;
    private MapView mapView;
    private String categoryCurrent = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_placeinterest, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        placeZoomIn         = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_ZoomIn);
        placeZoomOut        = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_ZoomOut);
        placeClinic         = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_Clinic);
        placeTownHall       = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_TownHall);
        placePostOffice     = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_PostOffice);
        placeWorship        = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_PlacesWorship);
        placeLanguageSchool = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_LanguageSchool);
        placePharmacy       = view.findViewById(R.id.ImageButton_Patient_PlaceofInterest_Pharmacy);
        mapView             = view.findViewById(R.id.MapView_Patient_PlaceofInterest);
    }

    @Override
    public void onStart() {
        super.onStart();

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(LATITUDE, LONGITUDE );
        mapView.getController().setZoom(15);
        mapView.getController().setCenter(startPoint);

        // Aggiungi listener per i bottoni
        placeZoomIn.setOnClickListener(v -> mapView.getController().zoomIn());
        placeZoomOut.setOnClickListener(v -> mapView.getController().zoomOut());

        placeClinic.setOnClickListener(v -> onCategoriaButtonClick("Ambulatorio"));
        placeTownHall.setOnClickListener(v -> onCategoriaButtonClick("Comune"));
        placePostOffice.setOnClickListener(v -> onCategoriaButtonClick("Posta"));
        placeWorship.setOnClickListener(v -> onCategoriaButtonClick("LuoghiCulto"));
        placeLanguageSchool.setOnClickListener(v -> onCategoriaButtonClick("ScuolaItaliano"));
        placePharmacy.setOnClickListener(v -> onCategoriaButtonClick("Farmacia"));
    }

    /**
     * Aggiunge un marker alla mappa alle coordinate specificate con un titolo personalizzato.
     *
     * @param latitude La latitudine del punto in cui posizionare il marker.
     * @param longitude La longitudine del punto in cui posizionare il marker.
     * @param title Il titolo da visualizzare quando si clicca sul marker.
     */
    private void addMarker(double latitude, double longitude, String title) {
        Marker marker = new Marker(mapView);
        GeoPoint point = new GeoPoint(latitude, longitude);
        marker.setPosition(point);
        marker.setTitle(title);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.invalidate(); // Aggiorna la mappa per mostrare il marker aggiunto
    }

    /**
     * Gestisce il clic su un ImageButton per visualizzare i marker specifici per la categoria selezionata.
     *
     * @param categoria La categoria selezionata.
     */
    private void onCategoriaButtonClick(String categoria) {
        // Cancella tutti i marker dalla mappa prima di aggiungere i nuovi
        mapView.getOverlays().clear();
        mapView.invalidate();

        // Imposta la categoria corrente
        categoryCurrent = categoria;

        // Aggiungi i marker specifici per la categoria selezionata
        switch (categoria) {
            case "Ambulatorio":
                addMarker(41.1168, 16.8727, "Ospedale Policlinico di Bari");
                addMarker(41.1179, 16.8534, "Ospedale Giovanni XXIII");
                addMarker(41.1240, 16.8588, "Ospedale F. Perinei");
                addMarker(41.1173, 16.8833, "Ospedale San Paolo");
                addMarker(41.1245, 16.8773, "Ospedale C. de Bellis");
                break;
            case "Comune":
                addMarker(41.1286, 16.8694, "Comune di Bari");
                break;
            case "Posta":
                addMarker(41.1232, 16.8728, "Ufficio Postale di Bari");
                break;
            case "LuoghiCulto":
                addMarker(41.1253, 16.8635, "Basilica di San Nicola");
                addMarker(41.1261, 16.8687, "Cattedrale di San Sabino");
                addMarker(41.1207, 16.8682, "Chiesa di San Giacomo");
                break;
            case "ScuolaItaliano":
                addMarker(41.1184, 16.8649, "Scuola di Italiano a Bari");
                break;
            case "Farmacia":
                addMarker(41.1224, 16.8746, "Farmacia Centrale di Bari");
                break;
            default:
                break;
        }
    }
}
