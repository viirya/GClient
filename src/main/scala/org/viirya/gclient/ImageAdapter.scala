
package org.viirya.gclient

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.view.ViewGroup.LayoutParams
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.EditText
import android.content.Intent
import android.util.Log
import android.widget.SimpleAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.ImageView
import android.widget.TextView
import android.graphics.BitmapFactory
import android.graphics.Bitmap

import scala.util.parsing.json._
//import scala.collection.mutable.HashMap

import java.net.{URLConnection, URL}
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedInputStream
import java.lang.StringBuilder
//import java.util.List
//import java.util.Map
import java.util.ArrayList
import java.util.HashMap

import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

import org.xml.sax.InputSource
import org.xml.sax.XMLReader


class ImageAdapter(context: Context, canonicalImages: List[String], clusterTags: List[String]) extends BaseAdapter {
    var TAG = "ImageAdapter"
    var canonicalMapList: ArrayList[HashMap[String, String]] = new ArrayList[HashMap[String, String]]()
    var mContext: Context = null

    mContext = context

    canonicalImages.foreach{cImage =>
        //println(cImage)
        if (cImage != null) {
            val canonicalMap: HashMap[String, String] = new HashMap[String, String]()
            canonicalMap.put("image", "http://denali.csie.ntu.edu.tw/~itv/thumbnails/" + cImage)
            if (canonicalMapList.size < clusterTags.length)
                canonicalMap.put("tag", clusterTags(canonicalMapList.size))
            else
                canonicalMap.put("tag", "")
            canonicalMapList.add(canonicalMap)
        }
    }

    def getCount(): Int = {
        canonicalMapList.size()    
    }
    
    def getItem(position: Int): Object = {
        int2Integer(position)
    }

    def getItemId(position: Int): Long = {
        position
    }
    
    override def getView(position: Int, cellRenderer: View, parent: ViewGroup): View = {
        var cellRendererView: CellRendererView = null
        if (cellRenderer == null) {
            Log.i(TAG, "creating a CellRendererView object")
            cellRendererView = new CellRendererView()
        }
        else {
            cellRendererView = cellRenderer.asInstanceOf[CellRendererView] 
        }
        cellRendererView.display(position)
        cellRendererView
    }

    class CellRendererView extends TableLayout(mContext) {
        var clusterName: TextView = null
        var clusterIcon: ImageView = null

        setColumnShrinkable(1, true)
        setColumnStretchable(1, true)
        setPadding(10, 10, 10, 10)

        var row: TableRow = new TableRow(mContext)
        row.setLayoutParams(new TableLayout.LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT
        ))

        clusterName = new TextView(mContext)
        clusterName.setPadding(10, 10, 10, 10)
        clusterName.setMaxWidth(100)
        clusterName.setLayoutParams(new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ))

        clusterIcon = new ImageView(mContext)
        clusterIcon.setLayoutParams(new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ))
        clusterIcon.setAdjustViewBounds(true)
        clusterIcon.setScaleType(ImageView.ScaleType.FIT_CENTER)
        clusterIcon.setMaxHeight(100)
        clusterIcon.setMaxWidth(100)

        row.addView(clusterName)
        row.addView(clusterIcon)

        addView(row)

        def display(position: Int) {
            if (position < clusterTags.length) 
                clusterName.setText(clusterTags(position))
            try {
                var bm: Bitmap = null
                var imageURL: URL = new URL(canonicalMapList.get(position).get("image"))
                var bis: BufferedInputStream = new BufferedInputStream(imageURL.openStream(), 1024)
                bm = BitmapFactory.decodeStream(bis)
                bis.close()
                if (bm != null)
                    clusterIcon.setImageBitmap(bm)
            } catch {
                case e: Exception => Log.i(TAG, "Error when retriving image")
            }
        }

    }
}


