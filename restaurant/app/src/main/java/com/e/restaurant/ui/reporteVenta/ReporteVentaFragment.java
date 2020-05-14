package com.e.restaurant.ui.reporteVenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.e.restaurant.Connection;
import com.e.restaurant.R;

import java.util.concurrent.ExecutionException;

public class ReporteVentaFragment extends Fragment {

    private ReporteVentaViewModel reporteVentaViewModel;
    private Button btnCrearReporte;
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private Toast toast;
    private EditText txtCantidad;
    private EditText txtFecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reporteVentaViewModel =
                ViewModelProviders.of(this).get(ReporteVentaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reporte_venta, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        reporteVentaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnCrearReporte = (Button) root.findViewById(R.id.button2);
        txtFecha = (EditText) root.findViewById(R.id.editTextFecha);
        txtCantidad = (EditText) root.findViewById(R.id.editTextCantidad);
        toast=Toast.makeText(root.getContext(),"",Toast.LENGTH_SHORT);
        toast.setGravity(20|20, 0, 0);

        btnCrearReporte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!txtCantidad.getText().toString().equals("") && !txtFecha.getText().toString().equals("")) {
                    Connection con = new Connection();
                    try {
                        String response = con.execute("http://" + IP + "/" + SITIO + "/insertarVenta.php?fecha=" + txtFecha.getText().toString() + "&cantidad=" + txtCantidad.getText().toString()).get();
                        if (response != null) {
                            toast.setText("Los datos se guardaron éxitosamente");
                            toast.show();
                            txtFecha.setText("");
                            txtCantidad.setText("");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        return root;
    }
}