package com.e.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsuario;
    private EditText txtContra;
    private Button btnLogin;
    public static String idUser;

    //dirección ip predeterminada del emulador de Android Studio.
    public static final String IP = "10.0.2.2";
    //Nombre del sitio “es una Carpeta” donde se almacenan los archivos PHP del lado del servidor.
    public static final String SITIO = "delicias";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText) findViewById(R.id.editText2);
        txtContra = (EditText) findViewById(R.id.editText4);
        toast=Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setGravity(20|20, 0, 0);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection con = new Connection();
                try {
                    String response = con.execute("http://" + IP + "/" + SITIO + "/consultarUsuario.php").get();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        String nombre = json.getString("nombre");
                        String clave = json.getString("clave");
                        if(txtUsuario.getText().toString().equals(nombre) & txtContra.getText().toString().equals(clave)){
                            String perfil = json.getString("perfil");
                            String apellido = json.getString("apellido");
                            idUser = json.getString("id");
                            Intent in;
                            if(perfil.equals("Administrador")){
                                in = new Intent(getApplicationContext(), AdminActivity.class);
                            }else{
                                in = new Intent(getApplicationContext(), MainActivity.class);
                            }
                            in.putExtra("nombre", nombre);
                            in.putExtra("apellido", apellido);
                            toast.setText("Login exitoso");
                            toast.show();
                            startActivity(in);
                            break;
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
