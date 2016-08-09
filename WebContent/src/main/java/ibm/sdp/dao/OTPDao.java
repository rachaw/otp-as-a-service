package com.ibm.sdp.rest.dao;
import com.ibm.sdp.rest.dao.SQLDB;
import com.ibm.sdp.rest.util.OTPUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.PrintWriter;
public class OTPDao {
	
	public static Connection getConnection(){
		String methodName = "OTPDao :: getConnection: "; 
		System.out.println(methodName);
		Connection con  = SQLDB.getConnection();
		return con;
	}
	
	public static void saveOtp(String appid, String msisdn, String otp){
		String methodName = "OTPDao :: saveOtp: "; 
		System.out.println(methodName);
		Statement stmt = null;
		String tableName = "";
		String sqlStatement = "";
		Connection con = null;
		//String schemaName = "USER02067";
		long timeinSecond=0;
		PrintWriter writer = null;
		try{
			timeinSecond = OTPUtil.getCurrentTimeinSecound(); 
			writer = new PrintWriter(System.out);
		 con = getConnection();
		stmt = con.createStatement();
		// Create the CREATE SCHEMA SQL statement and execute it
		sqlStatement = "INSERT INTO LALBIHARI_IN_IBM_COM.OTPTABLE1 VALUES ('"+appid+"','"+ msisdn + "','" + otp+"'," + timeinSecond + ")";
		System.out.println("insert statement : " +sqlStatement );
		//writer.println("Executing: " + sqlStatement);
		System.out.println("Executing: " + sqlStatement);
		int temp = stmt.executeUpdate(sqlStatement);
		System.out.println("out put of executeUpdate : " + temp);
		}catch(Exception e){
			System.out.println(methodName + "Error connecting to database");
			System.out.println(methodName + "SQL Exception: " + e);
			 e.printStackTrace(System.out);
		}finally{
			try{
			stmt.close();
			con.close();
			}
			catch(Exception e){
				System.out.println("Exception while closing connection: " + e.getMessage()); 
			}
		}
		
	}
	
	public static boolean validateOtp(String appid, String msisdn, String otp){
		String methodName = "OTPDao :: validateOtp: "; 
		System.out.println(methodName);
		Statement stmt = null;
		
		String tableName = "";
		String sqlStatement = "";
		//String schemaName = "USER02067";
		PrintWriter writer = null;
		int counter = 0;
		long timefromdb = 0;
		try{
			writer = new PrintWriter(System.out);
		Connection con = getConnection();
		stmt = con.createStatement();
		// Create the CREATE SCHEMA SQL statement and execute it
		sqlStatement =  "SELECT * FROM LALBIHARI_IN_IBM_COM.OTPTABLE1 WHERE OTP ='"+ otp+"'";
		System.out.println("Executing: " + sqlStatement);
		ResultSet rs = stmt.executeQuery(sqlStatement);
		
		while (rs.next()) {
			
			String appidfromdb = rs.getString(1);
			String msisdnfromdb = rs.getString(2);
			timefromdb = (long)rs.getInt(4);
			if(appidfromdb.equalsIgnoreCase(appid) && msisdnfromdb.equalsIgnoreCase(msisdn)){
				counter++;
				System.out.println(" Valid otp with counter: " + counter);
			}
			System.out.println("  Found appid: " + appidfromdb + "  msisdn: " + msisdnfromdb + " timefromdb: " + timefromdb);
		}
		}catch(Exception e){
			System.out.println("Error connecting to database");
			System.out.println("SQL Exception: " + e);
			e.printStackTrace(System.out);
		}
		if(counter>0 && OTPUtil.validateOtpTime(timefromdb)){
			return true;
		}else{
			return false;
		}
	}
	
	//public static void getOtpFrom database
	
}
