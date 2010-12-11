
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
import android.widget.GridView
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


class ClusterContentAdapter(context: Context, followImages: ArrayList[String]) extends BaseAdapter {
    var TAG = "ClusterContentAdapter"
    var mContext: Context = null

    mContext = context

    def getCount(): Int = {
        followImages.size()    
    }
    
    def getItem(position: Int): Object = {
        int2Integer(position)
    }

    def getItemId(position: Int): Long = {
        position
    }
    
    override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
        var imageView: ImageView = null
        if (imageView == null) {
            Log.i(TAG, "creating a ImageView")
            imageView = new ImageView(mContext)
            //imageView.setLayoutParams(new LayoutParams(85, 85))
            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE)
            imageView.setPadding(10, 10, 10, 10)
            imageView.setMaxHeight(50)
            imageView.setMaxWidth(50)
            //imageView.setMinimumHeight(50)
            //imageView.setMinimumWidth(50)
        }
        else {
            imageView = convertView.asInstanceOf[ImageView] 
        }

        try {
            var bm: Bitmap = null
            var imageURL: URL = new URL(followImages.get(position))
            var bis: BufferedInputStream = new BufferedInputStream(imageURL.openStream(), 1024)
            bm = BitmapFactory.decodeStream(bis)
            bis.close()
            if (bm != null)
                imageView.setImageBitmap(bm)
        } catch {
            case e: Exception => Log.i(TAG, "Error when retriving image")
        }

        imageView
    }

}


