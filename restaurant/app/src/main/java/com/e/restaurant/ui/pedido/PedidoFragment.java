package com.e.restaurant.ui.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.e.restaurant.Connection;
import com.e.restaurant.LoginActivity;
import com.e.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PedidoFragment extends Fragment {

    private PedidoViewModel pedidoViewModel;
    private Button btnCrearPedido;
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private Toast toast;
    private ListView listPedidoAlimentos;
    private ListView listPedidoBebidas;
    private String[] alimentos;
    private String[] bebidas;
    private int positionAlimento;
    private int positionBebida;
    private JSONArray jsonArrayAlimentos;
    private JSONArray jsonArrayBebidas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pedidoViewModel =
                ViewModelProviders.of(this).get(PedidoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedido, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        pedidoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnCrearPedido = (Button) root.findViewById(R.id.btnCrearPedido);
        toast=Toast.makeText(root.getContext(),"",Toast.LENGTH_SHORT);
        toast.setGravity(20|20, 0, 0);
        listPedidoAlimentos = (ListView) root.findViewById(R.id.listPedidoAlimento);
        listPedidoBebidas = (ListView) root.findViewById(R.id.listPedidoBebida);
        positionAlimento = -1;
        positionBebida = -1;

        Connection con = new Connection();
        try {
            String response = con.execute("http://" + IP + "/" + SITIO + "/consultarBebida.php").get();
            jsonArrayBebidas = new JSONArray(response);

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
            jsonArrayAlimentos = new JSONArray(response);

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

        listPedidoAlimentos.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, alimentos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                return textView;
            }
        });

        listPedidoBebidas.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, bebidas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                return textView;
            }
        });

        listPedidoAlimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item= String.valueOf(adapterView.getItemAtPosition(i));
               System.out.println(selected_item);
               positionAlimento = i;
            }
        });

        listPedidoBebidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item= String.valueOf(adapterView.getItemAtPosition(i));
                System.out.println(selected_item);
                positionBebida = i;
            }
        });

        btnCrearPedido.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(positionBebida!= -1 && positionAlimento!= -1) {
                    Connection con = new Connection();
                    try {
                        String response = con.execute("http://" + IP + "/" + SITIO + "/insertarPedido.php?bebida=" + jsonArrayBebidas.getJSONObject(positionBebida).getString("id") + "&alimento=" + jsonArrayAlimentos.getJSONObject(positionAlimento).getString("id")+ "&usuario=" + LoginActivity.idUser).get();
                        if (response != null) {
                            toast.setText("Los datos se guardaron éxitosamente");
                            toast.show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        return root;
    }
}