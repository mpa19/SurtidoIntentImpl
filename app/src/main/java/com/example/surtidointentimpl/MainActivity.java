package com.example.surtidointentimpl;


import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	TextView tv1;
	ImageView foto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	    Button btn1 = findViewById(R.id.button1);
	    Button btn2 = findViewById(R.id.button2);
	    Button btn3 = findViewById(R.id.button3);
	    Button btn4 = findViewById(R.id.button4);
	    Button btn5 = findViewById(R.id.button5);
	    Button btn6 = findViewById(R.id.button6);
		Button btn7 = findViewById(R.id.buttonM);
		Button btn8 = findViewById(R.id.buttonSms);
		Button btn9 = findViewById(R.id.button7);
		Button btn10 = findViewById(R.id.button8);
		Button btn11 = findViewById(R.id.button9);






		btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    btn3.setOnClickListener(this);
	    btn4.setOnClickListener(this);
	    btn5.setOnClickListener(this);
	    btn6.setOnClickListener(this);
	    btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);
		btn11.setOnClickListener(this);


		tv1 = findViewById(R.id.textView2);
		foto = findViewById(R.id.imageView);

		if (Build.VERSION.SDK_INT >= 23)
	        if (! ckeckPermissions())
		   requestPermissions();	  
	}

	public void onClick (View v) {
		Intent in;
		final String lat = "41.60788";
		final String lon = "0.623333";
		final String url = "http://www.eps.udl.cat/";
		final String adressa = "Carrer de Jaume II, 69, Lleida";
		final String textoABuscar = "escuela politecnica superior";

		switch (v.getId()) {
			case R.id.button1:
				Toast.makeText(this, getString(R.string.opcio1), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
				startActivity(in);
				break;
			case R.id.button2:
				Toast.makeText(this, getString(R.string.opcio2), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + adressa));
				startActivity(in);
				break;
			case R.id.button3:
				Toast.makeText(this, getString(R.string.opcio3), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
				break;
			case R.id.button4:
				Toast.makeText(this, getString(R.string.opcio4), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + "escola politecnica superior UdL"));
				in = new Intent(Intent.ACTION_WEB_SEARCH);
				in.putExtra(SearchManager.QUERY, textoABuscar);
				startActivity(in);
				break;
			case R.id.button5:
				Toast.makeText(this, getString(R.string.opcio5), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
				break;
			case R.id.button6:
				Toast.makeText(this, getString(R.string.opcio6), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW);
				in.setData(ContactsContract.Contacts.CONTENT_URI);
				startActivity(in);
				break;
			case R.id.buttonM:
				in = new Intent(Intent.ACTION_DIAL);
				in.setData(Uri.parse("tel:"));
				startActivity(in);
				break;
			case R.id.buttonSms:
				in = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + getText((R.string.telef))));
				in.putExtra("sms_body", textoABuscar);
				startActivity(in);
				break;
			case R.id.button7:
				in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(in, 2);
				break;
			case R.id.button8:
				in = new Intent(Intent.ACTION_SEND);
				in.putExtra(Intent.EXTRA_EMAIL, "marcperezarnaiz@gmail.com");
				in.putExtra(Intent.EXTRA_SUBJECT, "demo");
				in.putExtra(Intent.EXTRA_TEXT, "test email");
				in.setType("message/rfc822");
				startActivity(in);
				break;
			case R.id.button9:
				in = new Intent(Intent.ACTION_PICK);
				in.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				startActivityForResult(in, 3);

				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode,resultCode,data);
		if(resultCode == RESULT_OK && requestCode == 2){
			foto.setImageURI(data.getData());
		} else if(resultCode == RESULT_OK && requestCode == 3){
			Uri contactUri = data.getData();
			//String phoneNo = null;
			String name = null;

			Cursor cursor = null;
			cursor = getContentResolver().query(contactUri, null, null, null, null);
			cursor.moveToFirst();
			//int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
			//phoneNo = cursor.getString(phoneIndex);
			name = cursor.getString(nameIndex);

			tv1.setText(name);



		}
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (ActivityCompat.checkSelfPermission(getApplicationContext(),
					Manifest.permission.CALL_PHONE) ==
					PackageManager.PERMISSION_GRANTED)
				return true;
			else
				return false;
		    }
		else
			return true;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.CALL_PHONE},
				0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
