
package org.viirya.gclient

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler 

import android.util.Log 

class GCXMLParser extends DefaultHandler {
   
    val TAG: String = "GCXMLParser" 
    var currentTag: String = ""
//    var parsedData: GCQueryData = null

    var canonicalImages: List[String] = List()
    var allFollowImages: List[List[String]] = List()
    var followImages: List[String] = List()
    var clusterTags: List[String] = List()

    override def startDocument() {
//        parsedData = new GCQueryData()
    }

    override def endDocument() {
        if (!followImages.isEmpty) {
            allFollowImages = followImages :: allFollowImages
            followImages = Nil
        }
    }

    override def startElement(namespaceURI: String, localName: String, qName: String, atts: Attributes) {
        currentTag = localName
        Log.i(TAG, "currentTag: " + currentTag)

        currentTag match {
            case "canonical" =>
            case "follow" =>
            case _ =>
        }
    }

    override def endElement(namespaceURI: String, localName: String, qName: String) {
        currentTag = ""
    }

    override def characters(ch: Array[char], start: Int, length: Int) {
        var content = new String(ch, start, length)
        Log.i(TAG, "content: " + content)

        currentTag match {
            case "canonical" =>
                canonicalImages = content :: canonicalImages
                if (!followImages.isEmpty) {
                  allFollowImages = followImages :: allFollowImages
                  followImages = Nil
                }
            case "follow" =>
                followImages = content :: followImages
            case "tag" =>
                clusterTags = content :: clusterTags
            case _ =>
        }

    }
}

