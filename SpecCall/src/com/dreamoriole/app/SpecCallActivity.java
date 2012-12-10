package com.dreamoriole.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SpecCallActivity extends Activity {
	private EditText etPhoneNumber;
	private Button btnSubmit;
	private SharedPreferences preferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences("SpecCall", MODE_PRIVATE);
		String pn = preferences.getString("SpecCallNumber", null);
		boolean pnExist = false;
		if (pn != null && !"".equals(pn)) {
			pnExist = true;
			Intent intent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + pn));
			startActivity(intent);
		}
		setContentView(R.layout.main);

		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		if (pnExist) {
			etPhoneNumber.setText(pn);
		}
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pn = etPhoneNumber.getText().toString();
				if (pn == null || "".equals(pn)) {
					Toast.makeText(SpecCallActivity.this,
							R.string.nullPhoneNumberTip, 3).show();
					return;
				}
				Editor editor = preferences.edit();
				editor.putString("SpecCallNumber", pn);
				editor.commit();
				Toast.makeText(SpecCallActivity.this,
						R.string.savePhoneNumberSuccess, 3).show();
			}
		});
	}
}