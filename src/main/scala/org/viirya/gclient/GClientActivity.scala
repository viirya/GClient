
package org.viirya.gclient

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.LinearLayout
import android.widget.Button
import android.view.View
import android.widget.Toast
import android.widget.EditText
import android.content.Intent

class GClientActivity extends Activity {

    lazy val onSearchClicked = new View.OnClickListener {
        def onClick (view: View) {
            //Toast.makeText(getApplicationContext(), R.string.onsearch, Toast.LENGTH_SHORT).show() 

            val intent = new Intent()
            intent.setClass(getApplicationContext(), classOf[ShowCluster])

            val textView = findViewById(R.id.query).asInstanceOf[EditText] 
            
            val bundle = new Bundle()
            bundle.putString("TXT_QUERY", textView.getText().toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)    
        setContentView(R.layout.main)

        val searchButton: Button = findViewById(R.id.search).asInstanceOf[Button]
        searchButton.setOnClickListener(onSearchClicked)
    }
}
