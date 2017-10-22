package currencyapplte.currencyvelikiprojekat;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class    MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    NumberFormat formatter = NumberFormat.getNumberInstance();

    // URL to get contacts JSON
    private static String url = "http://apilayer.net/api/live?access_key=3b2d1ba52e923bb1df84fa1f54025b10";

    ArrayList<HashMap<String, String>> currencyList;

    HttpHandler sh = new HttpHandler();

    // Making a request to url and getting response
    String jsonStr = sh.makeServiceCall(url);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //chart handles

        currencyList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new GetCurrency().execute();

    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        int imageResource = android.R.drawable.ic_dialog_alert;
        Drawable image = getResources().getDrawable(R.drawable.icon_xl);

        builder.setTitle("Exit").setMessage("Are you sure you want to exit?").setIcon(image).setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();

    }


    public void onClickGoConversion(View c)
    {
        setContentView(R.layout.conversion);
    }
    public void onClickGoBack(View d)
    {
        setContentView(R.layout.list_item);
        startActivity(new Intent(this, MainActivity.class));
    }


    public void onConversionClick(View v)
    {

        TextView AUDCur=(TextView) lv.findViewById(R.id.AUD);
        TextView EURCur=(TextView) lv.findViewById(R.id.EUR);
        TextView RSDCur=(TextView) lv.findViewById(R.id.RSD);
        TextView CHFCur=(TextView) lv.findViewById(R.id.CHF);
        TextView GBPCur=(TextView) lv.findViewById(R.id.GBP);
        EditText USDCur=(EditText) findViewById(R.id.amountUSD);


        Double AUDcurrency=Double.parseDouble(AUDCur.getText().toString());
        Double EURcurrency=Double.parseDouble(EURCur.getText().toString());
        Double RSDcurrency=Double.parseDouble(RSDCur.getText().toString());
        Double CHFcurrency=Double.parseDouble(CHFCur.getText().toString());
        Double GBPcurrency=Double.parseDouble(GBPCur.getText().toString());
        Double USDcurrency=Double.parseDouble(USDCur.getText().toString());


        EditText AUDValue = (EditText) findViewById(R.id.AUDamount);
        EditText EURValue = (EditText) findViewById(R.id.EURamount);
        EditText RSDValue = (EditText) findViewById(R.id.RSDAmount);
        EditText CHFValue = (EditText) findViewById(R.id.CHFamount);
        EditText GBPValue = (EditText) findViewById(R.id.GBPAmount);



        Double AUDresenje=AUDcurrency*USDcurrency;
        Double EURresenje=EURcurrency*USDcurrency;
        Double RSDresenje=RSDcurrency*USDcurrency;
        Double CHFresenje=CHFcurrency*USDcurrency;
        Double GBPresenje=GBPcurrency*USDcurrency;



        DecimalFormat df = new DecimalFormat("#.##");
        String AUD1=(df.format(AUDresenje));
        String EUR1=(df.format(EURresenje));
        String RSD1=(df.format(RSDresenje));
        String CHF1=(df.format(CHFresenje));
        String GBP1=(df.format(GBPresenje));



        AUDValue.setText(AUD1);
        EURValue.setText(EUR1);
        RSDValue.setText(RSD1);
        CHFValue.setText(CHF1);
        GBPValue.setText(GBP1);


    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetCurrency extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject currency = jsonObj.getJSONObject("quotes");


                        Double USDAUD = currency.getDouble("USDAUD");
                        Double USDEUR = currency.getDouble("USDEUR");
                        Double USDRSD = currency.getDouble("USDRSD");
                        Double USDCHF = currency.getDouble("USDCHF");
                        Double USDGBP = currency.getDouble("USDGBP");

                        String USDAUDReal=USDAUD.toString();
                        String USDEURReal=USDEUR.toString();
                        String USDRSDReal=USDRSD.toString();
                        String USDCHFReal=USDCHF.toString();
                        String USDGBPReal=USDGBP.toString();


                        // tmp hash map za jedan currency
                        HashMap<String, String> currencySingle = new HashMap<>();

                        // adding each child node to HashMap key => value
                    currencySingle.put("AUD", USDAUDReal);
                    currencySingle.put("EUR", USDEURReal);
                    currencySingle.put("RSD", USDRSDReal);
                    currencySingle.put("CHF", USDCHFReal);
                    currencySingle.put("GBP", USDGBPReal);

                        // adding contact to contact list
                        currencyList.add(currencySingle);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, currencyList,
                    R.layout.list_item, new String[]{"AUD", "EUR",
                    "RSD","CHF","GBP"}, new int[]{R.id.AUD,
                    R.id.EUR, R.id.RSD,R.id.CHF,R.id.GBP});


            lv.setAdapter(adapter);
        }

    }
}
