package com.e.restaurant.ui.entrega;

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

import java.util.concurrent.ExecutionException;

public class EntregaFragment extends Fragment {

    private EntregaViewModel entregaViewModel;
    private ListView listPedidos;
    private Button btnCrearEntrega;
    //dirección ip predeterminada del emulador de Android Studio.
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private String[] pedidos;
    private int positionPedido;
    private Toast toast;
    private JSONArray jsonArrayPedidos;
    private EditText txtFecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        entregaViewModel =
                ViewModelProviders.of(this).get(EntregaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_entrega, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        entregaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                listPedidos = (ListView)getView().findViewById(R.id.listPedidos);
                btnCrearEntrega = (Button)getView().findViewById(R.id.button);
                txtFecha = (EditText)getView().findViewById(R.id.editText);
                toast=Toast.makeText(getView().getContext(),"",Toast.LENGTH_SHORT);
                toast.setGravity(20|20, 0, 0);
                positionPedido = -1;

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
                        pedidos[i] = id + "- "+ usuario+ "- "+ alimento+ "- "+bebida;
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

                listPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selected_item= String.valueOf(adapterView.getItemAtPosition(i));
                        System.out.println(selected_item);
                        positionPedido = i;
                    }
                });

                btnCrearEntrega.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        if(positionPedido!= -1 && !txtFecha.getText().toString().equals("")) {
                            Connection con = new Connection();
                            try {
                                String response = con.execute("http://" + IP + "/" + SITIO + "/insertarEntrega.php?pedido=" + jsonArrayPedidos.getJSONObject(positionPedido).getString("id") + "&fecha=" + txtFecha.getText().toString()).get();
                                if (response != null) {
                                    txtFecha.setText("");
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

            }
        });
        return root;
    }
}