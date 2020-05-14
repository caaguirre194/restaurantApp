package com.e.restaurant.ui.carta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.e.restaurant.AdminActivity;
import com.e.restaurant.Connection;
import com.e.restaurant.MainActivity;
import com.e.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CartaFragment extends Fragment {

    private CartaViewModel cartaViewModel;
    private ListView listViewAlimentos;
    private ListView listViewBebidas;
    //dirección ip predeterminada del emulador de Android Studio.
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private String[] bebidas;
    private String[] alimentos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartaViewModel =
                ViewModelProviders.of(this).get(CartaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_carta, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        cartaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                listViewAlimentos = (ListView)getView().findViewById(R.id.listAlimentos);
                listViewBebidas = (ListView)getView().findViewById(R.id.listBebidas);

                Connection con = new Connection();
                try {
                    String response = con.execute("http://" + IP + "/" + SITIO + "/consultarBebida.php").get();
                    JSONArray jsonArrayBebidas = new JSONArray(response);

                    bebidas = new String[jsonArrayBebidas.length()];

                    for(int i=0; i<jsonArrayBebidas.length();i++){
                        JSONObject json = jsonArrayBebidas.getJSONObject(i);
                        String nombre = json.getString("nombre");
                        String tipo = json.getString("tipo");
                        bebidas[i] = nombre + " --- "+ tipo;
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Connection con2 = new Connection();
                try {
                    String response = con2.execute("http://" + IP + "/" + SITIO + "/consultarAlimento.php").get();
                    JSONArray jsonArrayAlimentos = new JSONArray(response);

                    alimentos = new String[jsonArrayAlimentos.length()];

                    for(int i=0; i<jsonArrayAlimentos.length();i++){
                        JSONObject json = jsonArrayAlimentos.getJSONObject(i);
                        String nombre = json.getString("nombre");
                        String tipo = json.getString("tipo");
                        alimentos[i] = nombre + " --- "+ tipo;
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listViewAlimentos.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, alimentos) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        return textView;
                    }
                });

                listViewBebidas.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, bebidas) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        return textView;
                    }
                });

            }

        });
        return root;
    }
}