import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
	ArrayList<String> al = new ArrayList<String>();
	String url = "https://api.themoviedb.org/3/search/person?api_key=dae6cd32450211d689ce9fc4fec840a2&query=Gal+Gadot";
    JSONObject json = readJsonFromUrl(url);
    System.out.println(json.toString());
    JSONArray ja = json.getJSONArray("results");
    for (int i = 0; i < ja.length(); i++)
    {
    	JSONObject jo = ja.getJSONObject(i);
    	JSONArray ja2 = jo.getJSONArray("known_for");
    	for (int j = 0; j < ja2.length(); j++)
    	{
    		JSONObject jo2 = ja2.getJSONObject(j);
    		System.out.println(jo2);
    		String s = "http://image.tmdb.org/t/p/w185";
    		s = s + jo2.getString("poster_path");
    		al.add(s);
    	}
    }
    
    for (int i = 0; i < al.size(); i++)
    {
    	System.out.println(al.get(i));
    }
  }
}