
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
import android.widget.AdapterView
import android.widget.Adapter

import scala.util.parsing.json._
//import scala.collection.mutable.HashMap

import java.net.{URLConnection, URL, URLEncoder}
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


object GCservice {

  val TAG = "GCservice"

  def connect(query: String) = {
    val queryString = URLEncoder.encode(query, "UTF-8")
    val url = new URL("http://denali.csie.ntu.edu.tw/~itv/gc2009/service.php?query=" + queryString + "&num=100&algo=ap&demo=1&type=tag&rank_size=on")
    Log.i(TAG, "open URL: " + url)
    val conn = url.openConnection
    parseXML(conn.getInputStream)
    //inputStreamAsString(conn.getInputStream)
  }

  def parseXML(stream: InputStream): Tuple3[List[String], List[List[String]], List[String]] = {
    try {
        val spf: SAXParserFactory = SAXParserFactory.newInstance()
        val sp: SAXParser = spf.newSAXParser() 
        val xr: XMLReader = sp.getXMLReader(); 
        val xmlHandler: GCXMLParser = new GCXMLParser()
        xr.setContentHandler(xmlHandler)
        xr.parse(new InputSource(stream))

        return (xmlHandler.canonicalImages, xmlHandler.allFollowImages, xmlHandler.clusterTags)
    } catch {
        case e: Exception =>
            Log.e(TAG, "XMLParseError")
            return (Nil, Nil, Nil)
    }
  }

  def inputStreamAsString(stream: InputStream): String = {
    val br = new BufferedReader(new InputStreamReader(stream))
    val sb = new StringBuilder()
    var line: String = null

    while ({line = br.readLine(); line} != null) {
        sb.append(line + "\n");
    }

    sb.toString()
  }
}


class ShowCluster extends Activity {
    var TAG = "ShowCluster"
    var canonicalImages: List[String] = List()
    var allFollowImages: List[List[String]] = List()
    var clusterTags: List[String] = List()
    //var canonicalMapList: List[HashMap[String, String]] = List()
    var canonicalMapList: ArrayList[HashMap[String, String]] = new ArrayList[HashMap[String, String]]()

    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)    
        setContentView(R.layout.cluster_list)

        getClusters()

        val listView: ListView = findViewById(R.id.MyListView).asInstanceOf[ListView]

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            def onItemClick(parentView: AdapterView[_], childView: View, position: Int, id: Long): Unit = {
                Log.i(TAG, "pos: " + position)
                showClusterContent(position)                         
            }
        })

        //val imageAdapter: SimpleAdapter = new SimpleAdapter(this, canonicalMapList, R.layout.cluster, List("canonical").toArray, List(R.id.ItemImage).toArray)
        val imageAdapter: ImageAdapter = new ImageAdapter(this, canonicalImages, clusterTags)
        listView.setAdapter(imageAdapter)
        
    }

    def showClusterContent(position: Int) {
        val images: ArrayList[String] = new ArrayList[String]()
        allFollowImages(position).foreach{imageString =>
            Log.i(TAG, "converting following image " + imageString) 
            val imageURL = "http://denali.csie.ntu.edu.tw/~itv/thumbnails/" + imageString
            images.add(imageURL)    
        }

        val intent = new Intent()
        intent.setClass(getApplicationContext(), classOf[ShowClusterContent])
        
        val bundle = new Bundle()
        bundle.putStringArrayList("IMAGES", images)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    def getClusters() {
        val bundleObject = this.getIntent().getExtras()
        val queryString = bundleObject.getString("TXT_QUERY")

        Toast.makeText(getApplicationContext(), queryString, Toast.LENGTH_SHORT).show()

        val retTuple = GCservice.connect(queryString)
        canonicalImages = retTuple._1
        allFollowImages = retTuple._2
        clusterTags = retTuple._3
        

        canonicalImages.foreach{cImage =>
            println(cImage)
            //val canonicalMap = new HashMap[String, String]
            val canonicalMap: HashMap[String, String] = new HashMap[String, String]()
            //canonicalMap += "canonical" -> "http://denali.csie.ntu.edu.tw/~itv/thumbnails/" + cImage
            canonicalMap.put("canonical", "http://denali.csie.ntu.edu.tw/~itv/thumbnails/" + cImage)
            //canonicalMapList = canonicalMap :: canonicalMapList 
            canonicalMapList.add(canonicalMap)
        }

    }
}
