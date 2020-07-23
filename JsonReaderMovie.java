import java.io.BufferedReader;
import java.io.File;
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
import org.apache.commons.io.FileUtils;

public class JsonReaderMovie {

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
	ArrayList<String[]> al = new ArrayList<String[]>();
	String url = "https://api.themoviedb.org/3/search/movie?api_key=dae6cd32450211d689ce9fc4fec840a2&query=Iron+Man";
    JSONObject json = readJsonFromUrl(url);
    JSONArray ja = json.getJSONArray("results");
    for (int i = 0; i < ja.length(); i++)
    {
    	JSONObject jo = ja.getJSONObject(i);
    	String[] vals = new String[3];
    	String s = "http://image.tmdb.org/t/p/w185";
    	vals[0] = jo.getString("title");
    	vals[1] = jo.getString("overview");
    	if (!(jo.isNull("poster_path")))
    	{
    		vals[2] = s + jo.getString("poster_path");
    	}
    	else
    	{
    		vals[2] = "";
    	}
    	al.add(vals);
    }
    
    File htmlTemplateFile = new File("D:/SpringBoot/gs-spring-boot-master/complete/src/main/webapp/WEB-INF/jsp/hello.jsp");
    String htmlString = FileUtils.readFileToString(htmlTemplateFile, Charset.defaultCharset());
    String longString = "";
    int rows = al.size()/5;
    int remain = al.size()%5;
    int counter = 0;
    for (int i = 0; i < rows; i++)
    {
    	
    	for (int j = 0; j < 5; j++)
    	{
    		longString=longString+"<div class = 'col'><div class = 'text-center'>";
    		String[] s = al.get(counter);
    		for (int k = 0; k < s.length; k++)
    		{
    			if (k == 0)
    			{
    				longString+="<h2>" + s[k] + "</h2></div>";
    			}
    			else if (k == s.length-1)
    			{
    				longString+="<div style = 'margin:auto; width:10%;'><img src = '" + s[k] + "'></div>";
    			}

    			else
    			{
    				longString+="<div class = 'text-center' style = 'padding:20px;'><h4>" + s[k] + "</h4></div>";
    			}
    		}
    		longString+="</div></div>";
    		counter++;
    	}
    }
    
    
    longString = longString + "<div class = 'row'>";
    for (int i = 0; i < remain; i++)
    {
		longString=longString+"<div class = 'col'><div class = 'text-center'>";
		String[] s = al.get(counter);
		for (int k = 0; k < s.length; k++)
		{
			if (k == 0)
			{
				longString+="<h2>" + s[k] + "</h2></div>";
			}
			else if (k == s.length-1)
			{
				longString+="<div style = 'display:block; margin:auto;'><img src = '" + s[k] + "'></div>";
			}

			else
			{
				longString+="<div class = 'text-center'><h5>" + s[k] + "</h5></div>";
			}
		}
		longString+="</div></div>";
		counter++;
    }
    
	longString+="</div>";
	
	
    htmlString = htmlString.replace("$content", longString);
    File newHtmlFile = new File("D:/SpringBoot/gs-spring-boot-master/complete/src/main/webapp/WEB-INF/jsp/hello.jsp");
    FileUtils.writeStringToFile(newHtmlFile, htmlString, Charset.defaultCharset());
    System.out.println("Done...");
  }
}