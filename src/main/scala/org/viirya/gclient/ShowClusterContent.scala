
package org.viirya.gclient

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.view.View
import android.widget.Toast
import android.widget.EditText
import android.content.Intent
import android.util.Log
import android.widget.SimpleAdapter
import android.widget.ListView
import android.widget.GridView

import scala.util.parsing.json._
//import scala.collection.mutable.HashMap

import java.net.{URLConnection, URL}
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
//import java.util.List
//import java.util.Map
import java.util.ArrayList
import java.util.HashMap

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


class ShowClusterContent extends Activity {
    var TAG = "ShowClusterContent"
    var followImages: ArrayList[String] = new ArrayList[String]

    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)    
        setContentView(R.layout.cluster_content)

        getContent()

        val listView: ListView = findViewById(R.id.clusterview).asInstanceOf[ListView]
        val imageAdapter: ClusterContentAdapter = new ClusterContentAdapter(this, followImages)
        listView.setAdapter(imageAdapter)
        
    }

    def getContent() {
        val bundleObject = this.getIntent().getExtras()
        followImages = bundleObject.getStringArrayList("IMAGES")
    }
}
