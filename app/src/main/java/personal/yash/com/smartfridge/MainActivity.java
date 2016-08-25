package personal.yash.com.smartfridge;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ListActivity {

	static String[] MOBILE_OS =
               new String[] { "Android", "iOS", "WindowsMobile", "Blackberry"};


	// URL to get contacts JSON
	private static String url = "http://192.168.1.106:9000/getquantity";



	// contacts JSONArray
	JSONArray contacts = null;

	ProgressDialog pDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setListAdapter(new MobileArrayAdapter(this, MOBILE_OS));
//
		new GetQuantities().execute();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetQuantities extends AsyncTask<Void, Void, Void> {

		String itemsArray[];
		int quantArray[];

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
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);


			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {

				try {
					JSONArray jsonarray = new JSONArray(jsonStr);
					int num_items = jsonarray.length();
					itemsArray = new String[num_items];
					quantArray = new int[num_items];
					Log.d("ABC", "" + num_items);

					for(int i = 0; i<num_items; i++) {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						String name = jsonobject.getString("item");
						int quantity = jsonobject.getInt("quantity");
						itemsArray[i] = name;
						quantArray[i] = quantity;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			Log.d("ABC", jsonStr);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			setListAdapter(new MobileArrayAdapter(MainActivity.this, itemsArray, quantArray));
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

		}

	}



}