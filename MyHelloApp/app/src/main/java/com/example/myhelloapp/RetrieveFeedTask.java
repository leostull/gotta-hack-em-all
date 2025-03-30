//package com.example.myhelloapp;
//
//import android.os.AsyncTask;
//
//import android.util.Log;
//
//
//public class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {
//
//
//
//    private Exception exception;
//
//
//
//    public RetrieveFeedTask() {
//
//    }
//
//
//
//    protected RSSFeed doInBackground(String... urls) {
//
//        try {
//
//            URL url = new URL(urls[0]);
//
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//
//            SAXParser parser = factory.newSAXParser();
//
//            XMLReader xmlreader = parser.getXMLReader();
//
//            RssHandler theRSSHandler = new RssHandler();
//
//            xmlreader.setContentHandler(theRSSHandler);
//
//            InputSource is = new InputSource(url.openStream())
//
//            xmlreader.parse(is);
//
//
//
//            return theRSSHandler.getFeed();
//
//        } catch (Exception e) {
//
//            System.out.println("Error: " + e);
//
//        } finally {
//
//            is.close();
//
//        }
//
//    }
//
//
//
//    protected void onPostExecute(RSSFeed feed) {
//
//        // TODO: check this.exception
//
//        // TODO: do something with the feed
//
//    }
//
//
//
//}