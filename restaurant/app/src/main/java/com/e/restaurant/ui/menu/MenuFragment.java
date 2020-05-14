package com.e.restaurant.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

public class MenuFragment extends Fragment {

    private MenuViewModel MenuViewModel;
    private Button btnCrear;
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private Toast toast;
    private EditText txtAlimento;
    private EditText txtBebida;
    private Switch txtTipoAlimento;
    private Switch txtTipoBebida;
    private EditText txtPrecioAlimento;
    private EditText txtPrecioBebida;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        MenuViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnCrear = (Button) root.findViewById(R.id.btnCrear);
        txtAlimento = (EditText) root.findViewById(R.id.editTextAlimento);
        txtBebida = (EditText) root.findViewById(R.id.editTextBebida);
        txtPrecioAlimento = (EditText) root.findViewById(R.id.editTextPrecioAlimento);
        txtPrecioBebida = (EditText) root.findViewById(R.id.editTextPrecioBebida);
        txtTipoAlimento = (Switch) root.findViewById(R.id.switchAlimento);
        txtTipoBebida = (Switch) root.findViewById(R.id.switchBebida);
        toast=Toast.makeText(root.getContext(),"",Toast.LENGTH_SHORT);
        toast.setGravity(20|20, 0, 0);

        btnCrear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!txtAlimento.getText().toString().equals("") && !txtBebida.getText().toString().equals("") && !txtPrecioAlimento.getText().toString().equals("") && !txtPrecioBebida.getText().toString().equals("")) {
                    Connection con = new Connection();
                    try {
                        String tipoBebida = "Fria";
                        if (txtTipoBebida.isChecked()) {
                            tipoBebida = "Caliente";
                        }
                        String response = con.execute("http://" + IP + "/" + SITIO + "/insertarBebida.php?tipo=" + tipoBebida + "&nombre=" + txtBebida.getText().toString() + "&precio=" + txtPrecioBebida.getText().toString()).get();
                        if (response != null) {
                            toast.setText("Los datos se guardaron éxitosamente");
                            toast.show();
                            txtBebida.setText("");
                            txtPrecioBebida.setText("");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Connection con2 = new Connection();
                    try {
                        String tipoAlimento = "Frio";
                        if (txtTipoBebida.isChecked()) {
                            tipoAlimento = "Caliente";
                        }
                        String response = con2.execute("http://" + IP + "/" + SITIO + "/insertarAlimento.php?tipo=" + tipoAlimento + "&nombre=" + txtAlimento.getText().toString() + "&precio=" + txtPrecioAlimento.getText().toString()).get();
                        if (response != null) {
                            toast.setText("Los datos se guardaron éxitosamente");
                            toast.show();
                            txtAlimento.setText("");
                            txtPrecioAlimento.setText("");
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