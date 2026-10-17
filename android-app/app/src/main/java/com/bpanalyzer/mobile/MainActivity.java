package com.bpanalyzer.mobile;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends Activity {
    private static final String STORAGE = "mediciones";
    private static final String[] DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    private Spinner dia;
    private EditText nombre;
    private EditText peso;
    private EditText edad;
    private EditText sistolica;
    private EditText diastolica;
    private LinearLayout lista;
    private TextView contador;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencias = getSharedPreferences("BPAnalyzer", MODE_PRIVATE);
        construirPantalla();
        actualizarLista();
    }

    private void construirPantalla() {
        ScrollView desplazamiento = new ScrollView(this);
        LinearLayout contenido = new LinearLayout(this);
        contenido.setOrientation(LinearLayout.VERTICAL);
        contenido.setPadding(dp(20), dp(20), dp(20), dp(24));
        contenido.setBackgroundColor(Color.rgb(244, 247, 248));
        desplazamiento.addView(contenido);

        TextView titulo = texto("Registro de presión arterial", 26, Color.rgb(18, 48, 74));
        titulo.setTypeface(null, 1);
        contenido.addView(titulo);
        contenido.addView(texto("Añade mediciones y consúltalas desde tu móvil.", 15, Color.rgb(92, 107, 115)));

        TextView formularioTitulo = texto("Nueva medición", 19, Color.rgb(18, 48, 74));
        formularioTitulo.setTypeface(null, 1);
        formularioTitulo.setPadding(0, dp(24), 0, dp(8));
        contenido.addView(formularioTitulo);

        dia = new Spinner(this);
        dia.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, DIAS));
        agregarCampo(contenido, "Día", dia);

        nombre = entrada("Nombre", false);
        peso = entrada("Peso en kg", true);
        edad = entrada("Edad", true);
        sistolica = entrada("Presión sistólica", true);
        diastolica = entrada("Presión diastólica", true);
        agregarCampo(contenido, "Nombre", nombre);
        agregarCampo(contenido, "Peso (kg)", peso);
        agregarCampo(contenido, "Edad", edad);
        agregarCampo(contenido, "Sistólica (mmHg)", sistolica);
        agregarCampo(contenido, "Diastólica (mmHg)", diastolica);

        Button agregar = new Button(this);
        agregar.setText("Agregar medición");
        agregar.setTextColor(Color.WHITE);
        agregar.setBackgroundColor(Color.rgb(22, 125, 141));
        agregar.setOnClickListener(v -> agregarMedicion());
        LinearLayout.LayoutParams botonParametros = new LinearLayout.LayoutParams(-1, dp(52));
        botonParametros.setMargins(0, dp(16), 0, dp(8));
        contenido.addView(agregar, botonParametros);

        Button limpiar = new Button(this);
        limpiar.setText("Limpiar campos");
        limpiar.setOnClickListener(v -> limpiarCampos());
        contenido.addView(limpiar, new LinearLayout.LayoutParams(-1, dp(48)));

        TextView listaTitulo = texto("Mediciones registradas", 19, Color.rgb(18, 48, 74));
        listaTitulo.setTypeface(null, 1);
        listaTitulo.setPadding(0, dp(28), 0, dp(8));
        contenido.addView(listaTitulo);

        contador = texto("", 14, Color.rgb(92, 107, 115));
        contenido.addView(contador);
        lista = new LinearLayout(this);
        lista.setOrientation(LinearLayout.VERTICAL);
        contenido.addView(lista);
        setContentView(desplazamiento);
    }

    private EditText entrada(String sugerencia, boolean numerica) {
        EditText campo = new EditText(this);
        campo.setHint(sugerencia);
        campo.setSingleLine(true);
        if (numerica) {
            campo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        return campo;
    }

    private void agregarCampo(LinearLayout contenido, String etiqueta, View campo) {
        TextView texto = texto(etiqueta, 13, Color.rgb(92, 107, 115));
        contenido.addView(texto, new LinearLayout.LayoutParams(-1, dp(24)));
        contenido.addView(campo, new LinearLayout.LayoutParams(-1, dp(52)));
    }

    private void agregarMedicion() {
        String nombreTexto = nombre.getText().toString().trim();
        try {
            double pesoNumero = Double.parseDouble(peso.getText().toString());
            int edadNumero = Integer.parseInt(edad.getText().toString());
            int sistolicaNumero = Integer.parseInt(sistolica.getText().toString());
            int diastolicaNumero = Integer.parseInt(diastolica.getText().toString());
            if (nombreTexto.isEmpty() || pesoNumero <= 0 || edadNumero <= 0 || sistolicaNumero <= 0 || diastolicaNumero <= 0) {
                throw new IllegalArgumentException();
            }

            JSONArray mediciones = cargarMediciones();
            JSONObject medicion = new JSONObject();
            medicion.put("dia", dia.getSelectedItem().toString());
            medicion.put("nombre", nombreTexto);
            medicion.put("peso", pesoNumero);
            medicion.put("edad", edadNumero);
            medicion.put("sistolica", sistolicaNumero);
            medicion.put("diastolica", diastolicaNumero);
            mediciones.put(medicion);
            guardarMediciones(mediciones);
            limpiarCampos();
            actualizarLista();
            Toast.makeText(this, "Medición guardada", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException | JSONException error) {
            Toast.makeText(this, "Completa todos los campos con valores positivos", Toast.LENGTH_LONG).show();
        }
    }

    private void actualizarLista() {
        lista.removeAllViews();
        JSONArray mediciones = cargarMediciones();
        contador.setText(String.format(Locale.getDefault(), "%d medición(es) registrada(s)", mediciones.length()));
        for (int indice = 0; indice < mediciones.length(); indice++) {
            try {
                JSONObject medicion = mediciones.getJSONObject(indice);
                TextView fila = texto(String.format(Locale.getDefault(), "%s  ·  %s\n%.1f kg  ·  %d años  ·  %d/%d mmHg",
                        medicion.getString("dia"), medicion.getString("nombre"), medicion.getDouble("peso"),
                        medicion.getInt("edad"), medicion.getInt("sistolica"), medicion.getInt("diastolica")),
                        16, Color.rgb(18, 48, 74));
                fila.setGravity(Gravity.CENTER_VERTICAL);
                fila.setPadding(dp(14), dp(12), dp(14), dp(12));
                fila.setBackgroundColor(Color.WHITE);
                LinearLayout registro = new LinearLayout(this);
                registro.setOrientation(LinearLayout.HORIZONTAL);
                registro.setGravity(Gravity.CENTER_VERTICAL);
                registro.setBackgroundColor(Color.WHITE);

                LinearLayout.LayoutParams textoParametros = new LinearLayout.LayoutParams(0, dp(76), 1);
                registro.addView(fila, textoParametros);

                Button eliminar = new Button(this);
                eliminar.setText("Eliminar");
                eliminar.setTextColor(Color.rgb(180, 45, 45));
                final int indiceRegistro = indice;
                eliminar.setOnClickListener(v -> eliminarMedicion(indiceRegistro));
                registro.addView(eliminar, new LinearLayout.LayoutParams(dp(100), dp(56)));

                LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(-1, dp(76));
                parametros.setMargins(0, dp(8), 0, 0);
                lista.addView(registro, parametros);
            } catch (JSONException ignorado) {
            }
        }
    }

    private void eliminarMedicion(int indice) {
        JSONArray mediciones = cargarMediciones();
        if (indice < 0 || indice >= mediciones.length()) {
            return;
        }
        mediciones.remove(indice);
        guardarMediciones(mediciones);
        actualizarLista();
        Toast.makeText(this, "Medición eliminada", Toast.LENGTH_SHORT).show();
    }

    private JSONArray cargarMediciones() {
        try {
            return new JSONArray(preferencias.getString(STORAGE, "[]"));
        } catch (JSONException error) {
            return new JSONArray();
        }
    }

    private void guardarMediciones(JSONArray mediciones) {
        preferencias.edit().putString(STORAGE, mediciones.toString()).apply();
    }

    private void limpiarCampos() {
        nombre.setText("");
        peso.setText("");
        edad.setText("");
        sistolica.setText("");
        diastolica.setText("");
    }

    private TextView texto(String contenido, int tamano, int color) {
        TextView vista = new TextView(this);
        vista.setText(contenido);
        vista.setTextSize(tamano);
        vista.setTextColor(color);
        return vista;
    }

    private int dp(int valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }
}
