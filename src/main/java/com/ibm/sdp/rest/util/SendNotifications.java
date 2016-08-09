package com.ibm.sdp.rest.util;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.instance.Sms;

import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class SendNotifications {
	/* Twilio REST API version */
    public static final String ACCOUNTSID = "AC6474365a204197e741c4aa45c736751e";
    public static final String AUTHTOKEN = "26ff00c79833a693b9c493d7b6069da9";
    
    public static String sendSMS(String msisdn, int otp)throws TwilioRestException{
    	
    	
	    TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);

	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", "Hello from Java"));
	    params.add(new BasicNameValuePair("To", "+919811021169"));
	    params.add(new BasicNameValuePair("From", "+12015524807"));

	    MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    Message message = messageFactory.create(params);
	    System.out.println(message.getSid());
	    return message.getSid();
    	
    	
//    	String methodName = "SendNotifications ::  sendSMS: ";
//    	String smsid =null;
//        /* Instantiate a new Twilio Rest Client */
//        TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
//
//        // Get the account and call factory class
//        Account acct = client.getAccount();
//        SmsFactory smsFactory = acct.getSmsFactory();
//
//        //build map of server admins
//        Map<String,String> admins = new HashMap<String,String>();
//        admins.put("919811021169", "Ramit");
//       // admins.put("4158675310", "Helen");
//        //admins.put("4158675311", "Virgil");
//       
//        String fromNumber = "2066419136";
//
//    	// Iterate over all our server admins
//        for (String toNumber : admins.keySet()) {
//            
//            //build map of post parameters 
//            Map<String,String> params = new HashMap<String,String>();
//            params.put("From", fromNumber);
//            params.put("To", toNumber);
//            params.put("Body", "Dear Customer, you have initiated an online shopping transaction of Rs 30000 that need an OTP. DON'T SHARE THE OTP WITH ANYONE. OTP is "  + admins.get(toNumber) + ".");
//
//            try {
//            	System.out.println("Sending a sms...");
//                // send an sms a call  
//                // ( This makes a POST request to the SMS/Messages resource)
//                Sms sms = smsFactory.create(params);
//                smsid = sms.getSid();
//                System.out.println("Success sending SMS: " + smsid );
//            }
//            catch (TwilioRestException e) {
//                e.printStackTrace();
//            }
//        }
//        return smsid;
    }       

}
