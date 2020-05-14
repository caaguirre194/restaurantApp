package com.e.restaurant.ui.reportePedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.e.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ReportePedidoFragment extends Fragment {

    private ReportePedidoViewModel reportePedidoViewModel;
    private ListView listPedidos;
    //dirección ip predeterminada del emulador de Android Studio.
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private String[] pedidos;
    private Toast toast;
    private JSONArray jsonArrayPedidos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportePedidoViewModel =
                ViewModelProviders.of(this).get(ReportePedidoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reporte_pedido, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        reportePedidoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                listPedidos = (ListView)getView().findViewById(R.id.listPedidosReporte);
                toast=Toast.makeText(getView().getContext(),"",Toast.LENGTH_SHORT);
                toast.setGravity(20|20, 0, 0);
                Connection con = new Connection();
                try {
                    String response = con.execute("http://" + IP + "/" + SITIO + "/consultarPedido.php").get();
                    jsonArrayPedidos = new JSONArray(response);

                    pedidos = new String[jsonArrayPedidos.length()];

                    for(int i=0; i<jsonArrayPedidos.length();i++){
                        JSONObject json = jsonArrayPedidos.getJSONObject(i);
                        String id = json.getString("id");
                        String usuario = json.getString("nombreUsuario");
                        String alimento = json.getString("nombreAlimento");
                        String bebida = json.getString("nombreBebida");
                        String total = json.getString("total");
                        pedidos[i] = id + "- "+ usuario+ ": $"+total;
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listPedidos.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, pedidos) {
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