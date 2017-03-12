package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dpach on 11.01.2017.
 */

public class URLUtils
{

    public static String addParameter(String URL, String value)
    {
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
//        char sep = qpos == -1 ? '?' : '&';
        String sep = "/";
        String seg = sep + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    public static String addParameter(String valueString, String name, String value) {
    	if(valueString.equals("")) {
    		valueString = name + "=" + value;
    	} else {
    		valueString += "&" + name + "=" + value;
    	}
    	
    	return valueString;
    }
    
    public static String encodeUrl(String url)
    {
        try
        {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new IllegalArgumentException(uee);
        }
    }

    /**
     * 
     * @param POST_URL
     * @param contentType - if set to null than default content-type set to "application/json"
     * @param params - if no params set to null
     * 
     * @throws IOException 
     */
    public static void postRequestToGcm(String POST_URL, String contentType, Map<String, String> params) throws IOException {
    	String FMCurl = "https://fcm.googleapis.com/fcm/send"; 

    	String packageName = "threewe.arinterface.sharedspaceclient";
    	
    	   URL url = new URL(FMCurl);
    	   HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

    	   conn.setUseCaches(false);
    	   conn.setDoInput(true);
    	   conn.setDoOutput(true);

    	   conn.setRequestMethod("POST");
    	   conn.setRequestProperty("Authorization","key="+apiKey);
    	   conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
    	   
    	   	HashMap<String, Object> data = new HashMap<String, Object>();
	   		data.put("Hello", "World!");
	   		data.put("Marco", "Polo");
	   		data.put("Foo", "Bar");
    	   
//	   		List<String> targets = new ArrayList<String>();
//	   		targets.add("31826831111");
	   		
	   		Notification notification = new Notification();
	   		notification
	   			.to(userDeviceIdKey.trim())
	   			.collapse_key("a_collapse_key")
				.priority(1)
//				.registration_ids(targets)
				.delay_while_idle(true)
				.time_to_live(3600)
				.restricted_package_name(packageName.trim())
				.dry_run(false);
//				.data(data)
//				.title("Testing")
//				.body("Hello World!dasdasdsadsa");
	   		
	   		if(params.containsKey("title")) {
	   			notification.title(params.get("title"));
	   		}
	   		if(params.containsKey("body")) {
	   			notification.body(params.get("body"));
	   		}
	   		
	   		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
			System.out.println(notification.toJSON());
			writer.write(notification.toJSON());
			writer.close();
			wr.close();

			wr.flush();
			wr.close();
	   		
			conn.getResponseCode();
    	   
			System.out.println(url.getHost());
			System.out.println(conn.getURL().toString());
    	   System.out.println("KOD: " + conn.getResponseCode());
    	   System.out.println(conn.getResponseMessage());
    	   
    	   FcmResponse response = new FcmResponse(conn);
    	   System.out.println(response.getResponseMessage());
    	   System.out.println(response.getSuccessResponseMessage());
    }
    
    public static String getRequest(String GET_URL) {
        String getResponse = "";
        URL getUrl = null;
        try {
            getUrl = new URL(GET_URL);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getUrl.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    getResponse += line;
                }
            }
        } catch (MalformedURLException e) {
            return "Error code: 74392763";
        } catch (IOException e) {
            return "Error code: 17474037";
        }

        return getResponse;
    }

    public static String getRequest(String GET_URL, List<String> params) {
        String getResponse = "";
        for(String param : params) {
            GET_URL = URLUtils.addParameter(GET_URL, param);
        }
        URL getUrl = null;
        try {
            getUrl = new URL(GET_URL);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getUrl.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    getResponse += line;
                }
            }
        } catch (MalformedURLException e) {
            return "Error code: 74392763";
        } catch (IOException e) {
            return "Error code: 17474037";
        }

        return getResponse;
    }


}

