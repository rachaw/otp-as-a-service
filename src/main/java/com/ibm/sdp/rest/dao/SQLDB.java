package com.ibm.sdp.rest.dao;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import com.ibm.nosql.json.api.BasicDBList;
import com.ibm.nosql.json.api.BasicDBObject;
import com.ibm.nosql.json.util.JSON;

public class SQLDB {
	
	/*{
  "sqldb": [
    {
      "name": "SQL Database-x6",
      "label": "sqldb",
      "plan": "sqldb_small",
      "credentials": {
        "port": 50000,
        "db": "I5299272",
        "username": "ijzalaqr",
        "ssljdbcurl": "jdbc:db2://192.155.240.194:50001/I5299272:sslConnection=true;",
        "host": "192.155.240.194",
        "dsn": "DATABASE=I5299272;HOSTNAME=192.155.240.194;PORT=50000;PROTOCOL=TCPIP;UID=ijzalaqr;PWD=jtspeh91grm7;",
        "hostname": "192.155.240.194",
        "jdbcurl": "jdbc:db2://192.155.240.194:50000/I5299272",
        "ssldsn": "DATABASE=I5299272;HOSTNAME=192.155.240.194;PORT=50001;PROTOCOL=TCPIP;UID=ijzalaqr;PWD=jtspeh91grm7;Security=SSL;",
        "uri": "db2://ijzalaqr:jtspeh91grm7@192.155.240.194:50000/I5299272",
        "password": "jtspeh91grm7"
      }
    }
  ]
}

		}*/

	
	
	
	// set defaults
		private static String databaseHost = "192.155.240.194";
		private static int port = 50000;
		private static String databaseName = "sqldb";
		private static String user = "ijzalaqr";
		private static String password = "jtspeh91grm7";
		private static String url = "jdbc:db2://192.155.240.194:50000/I5299272";
		
		public static void main(String[] args){
			PrintWriter writer = new PrintWriter(System.out);
			processVCAP(writer);
			
		}
		
		private static boolean processVCAP(PrintWriter writer) {
			String methodName = "SDLDB :: processVCAP ";
			System.out.println(methodName);
			// VCAP_SERVICES is a system environment variable
			// Parse it to obtain the for DB2 connection info
			String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
//			VCAP_SERVICES = "{\"cleardb\": [{\"name\": \"ClearDB MySQL Database-kg\",\"label\": \"cleardb\",\"plan\": \"spark\",\"credentials\": {\"jdbcUrl\": \"jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_487f252eccae32e?user=b6db71d551de03&password=61dda630\",\"uri\": \"mysql://b6db71d551de03:61dda630@us-cdbr-iron-east-04.cleardb.net:3306/ad_487f252eccae32e?reconnect=true\",\"name\": \"ad_487f252eccae32e\",\"hostname\": \"us-cdbr-iron-east-04.cleardb.net\",\"port\": \"3306\",\"username\": \"b6db71d551de03\",\"password\": \"61dda630\"}}]}";
			System.out.println("VCAP_SERVICES content: " + VCAP_SERVICES);
			writer.println("VCAP_SERVICES content: " + VCAP_SERVICES);
			if (VCAP_SERVICES != null) {
				try {
					// parse the VCAP JSON structure
					BasicDBObject obj = (BasicDBObject) JSON.parse(VCAP_SERVICES);
					String thekey = null;
					Set<String> keys = obj.keySet();
					writer.println("Searching through VCAP keys");
					// Look for the VCAP key that holds the SQLDB information
					for (String eachkey : keys) {
						writer.println("Key is: " + eachkey);
						// Just in case the service name gets changed to lower case in the future, use toUpperCase
						if (eachkey.toUpperCase().contains("CLEARDB")) {
							thekey = eachkey;
						}
					}
					if (thekey == null) {
						writer.println("Cannot find any CLEARDB service in the VCAP; exiting");
						return false;
					}
					BasicDBList list = (BasicDBList) obj.get(thekey);
					obj = (BasicDBObject) list.get("0");
					writer.println("Service found: " + obj.get("name"));
					// parse all the credentials from the vcap env variable
					obj = (BasicDBObject) obj.get("credentials");
//				databaseHost = (String) obj.get("host");
					databaseHost = (String) obj.get("hostname");
//				databaseName = (String) obj.get("db");
					databaseName = (String) obj.get("name");
//				port = (int)obj.getInt("port");
					port = (int)obj.getInt("port");				
					user = (String) obj.get("username");
					password = (String) obj.get("password");
//				url = (String) obj.get("jdbcurl");
					url = (String) obj.get("jdbcUrl");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.out);
				}
			} else {
				writer.println("VCAP_SERVICES is null");
				return false;
			}
			writer.println();
			writer.println("database host: " + databaseHost);
			writer.println("database port: " + port);
			writer.println("database name: " + databaseName);
			writer.println("username: " + user);
			writer.println("password: " + password);
			writer.println("url: " + url);
			System.out.println("database host: " + databaseHost);
			System.out.println("database port: " + port);
			System.out.println("database name: " + databaseName);
			System.out.println("username: " + user);
			System.out.println("password: " + password);
			System.out.println("url: " + url);
			return true;
		}
		
		public static Connection getConnection(){
			String methodName = "SDLDB :: getConnection";
			System.out.println(methodName);
			PrintWriter writer = new PrintWriter(System.out);
			writer.println("IBM SQL Database, OTPApi Application using DB2 drivers");
			writer.println("SQLDB: " + SQLDB.class.getName());
			writer.println();
			try {
				writer.println("Host IP:" + InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(System.out);
			}
			Connection con = null;
			if (processVCAP(writer)) {
				System.out.println(methodName + "inside processVCAP..");
				
				// Connect to the Database
				
				try {
//					writer.println();
//					writer.println("Connecting to the database");
//					System.out.println(methodName + "Connecting to the database");
//					DB2SimpleDataSource dataSource = new DB2SimpleDataSource();
//					dataSource.setServerName(databaseHost);
//					dataSource.setPortNumber(port);
//					dataSource.setDatabaseName(databaseName);
//					dataSource.setUser(user);
//					dataSource.setPassword (password);
//					dataSource.setDriverType(4);
//					con=dataSource.getConnection();
//					writer.println();
//					con.setAutoCommit(true);

					writer.println();
					writer.println("Connecting to the database");
					System.out.println(methodName + "Connecting to the database");
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection(url);
					writer.println();
					con.setAutoCommit(true);					
					
				} catch (SQLException e) {
					System.out.println(methodName + "Error connecting to database");
					System.out.println(methodName +"SQL Exception: " + e);
					e.printStackTrace(System.out);
					return null;
				} catch (ClassNotFoundException e){
					System.out.println(methodName + "Cannot find class");
					e.printStackTrace(System.out);
					return null;
					
				}
				
			}
			return con;
		}
}
