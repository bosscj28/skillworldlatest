package com.shanks.myapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallService extends AsyncTask<Void, String, String> {

	OnServicecall OnServicecall;
	Context context;
	String urlStr;

	String mehtod = "";
	private ProgressDialog progressDialog;
    boolean shouldShowProgress = false;

	public interface OnServicecall {
		public void onServicecall(String response);

	}

	public CallService(Context context, String urlStr, String mehtod, boolean shouldShowProgress,
					   OnServicecall OnServicecall) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.urlStr = urlStr;
		this.OnServicecall = OnServicecall;
		this.mehtod = mehtod;
        this.shouldShowProgress = shouldShowProgress;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
        if(shouldShowProgress)
        progressDialog = ProgressDialog.show(context,"Loading ..","Please wait");

	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub


            return getData(mehtod);


	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
        if(shouldShowProgress)
        progressDialog.dismiss();
		OnServicecall.onServicecall(result);
	}

	private String getData(String mehtod) {

		try {
			urlStr = urlStr.replace(" ","%20");
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(45000);
			conn.setRequestMethod(mehtod);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));

			String data = null;
			String webPage = "";
			while ((data = reader.readLine()) != null) {
				webPage += data + "\n";
			}
			return webPage;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

}
