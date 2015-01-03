package com.jb.workflow.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;




public class RestwfClient {
	
	  public static void main(String[] args) throws Exception, IOException {
		  String url = "http://localhost:8080/kft-activiti-demo/rest/runtime/tasks";
		  String url2 = "http://localhost:8080/kft-activiti-demo/workflow/task/todo/list";
		  
		  String result = getTest(url);
		  
	        JSONObject object = new JSONObject(result);
	        if (object != null) {
	            JSONArray arr = (JSONArray) object.get("data");
	            for (int i = 0; i < arr.length(); i++) {
	            	JSONObject obj = (JSONObject) arr.get(i);
	            	System.out.println(obj.toString());
	            }
	        }
		  
		 }
	  
	  public static String getTest(String url) throws Exception, IOException {
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        try {
	            httpclient.getCredentialsProvider().setCredentials(
	            		AuthScope.ANY,
	                    new UsernamePasswordCredentials("kefu01", "000000"));

	            HttpGet httpget = new HttpGet(url);

	            System.out.println("executing request" + httpget.getRequestLine());
	            HttpResponse response = httpclient.execute(httpget);
	            
	            StringBuffer res = new StringBuffer();
	            
			    BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				 
			    String output;
			    System.out.println("Output from Server .... \n");
			    while ((output = br.readLine()) != null) {
			      System.out.println(output);
			      res.append(output);
			    }
			    return res.toString();
			    
	        } finally {
	            // When HttpClient instance is no longer needed,
	            // shut down the connection manager to ensure
	            // immediate deallocation of all system resources
	            httpclient.getConnectionManager().shutdown();
	        }
	  }
	  
		/**
		 * 将一个InputStream流转换成字符串
		 * 
		 * @param is
		 * @return
		 */
		public static String toConvertString(InputStream is) {
			StringBuffer res = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader read = new BufferedReader(isr);
			try {
				String line;
				line = read.readLine();
				while (line != null) {
					res.append(line);
					line = read.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != isr) {
						isr.close();
						isr.close();
					}
					if (null != read) {
						read.close();
						read = null;
					}
					if (null != is) {
						is.close();
						is = null;
					}
				} catch (IOException e) {
				}
			}
			return res.toString();
		}
}
