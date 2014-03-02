package com.crime.crimebusters.login;

import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class Login {
	private final static String SOAP_ACTION = "http://tempuri.org/ValidateUser";
	private final static String OPERATION_NAME = "ValidateUser";
	private final static String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
	private final static String SOAP_ADDRESS = "http://www.chrisababan.com/CS411/WebService/CS428Test.asmx";

	public Login() {

	}

	public String validateCredentials(String username, String password) throws InterruptedException, ExecutionException {
		AsyncTask<String, Void, String> task = 
				new LoginTask().execute(username, password);
		return task.get();
	}

	private class LoginTask extends AsyncTask<String, Void, String> {
		
		protected String doInBackground(String... params) {
			SoapObject request = 
					new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
			request.addProperty("username", params[0]);
			request.addProperty("password", params[1]);

			SoapSerializationEnvelope envelope = 
					new SoapSerializationEnvelope(SoapEnvelope.VER12);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			Object response = null;
			try {
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse();
			} catch (Exception ex) {
				return ex.getMessage();
			}
			return response.toString();
		}

		protected void onPostExecute(String result) {
			// TODO: Redirect to main page perhaps? Else, just delete this method.
		}
	}
}
