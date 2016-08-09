package com.ibm.sdp.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ibm.sdp.rest.util.OTPUtil;
import com.ibm.sdp.rest.util.SendNotifications;

@Path("/generateotp")
public class GenerateOPT {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getOtp(@QueryParam("appid") String appID, @QueryParam("msisdn") String msisdn){
		String methodName = "GenerateOPT :: getOtp: ";
		System.out.println(methodName);
		System.out.println("AppId: " + appID + "Msisdn: " + msisdn);
		int otp = 0;
		String msgId = null;
		System.out.println("calling...OTPUtil.generateOTP");
		try{
			if(null != msisdn && (msisdn.length() == 10 || msisdn.length() == 12)){
				if(msisdn.length() == 12){
					msisdn = OTPUtil.trimMsisdn(msisdn);
				}
				otp = OTPUtil.generateOTP(appID, msisdn);
				
				if(otp != 0){
					System.out.println("calling...SendNotifications.sendSMS");
					msgId = SendNotifications.sendSMS(msisdn, otp);
					System.out.println("Message sent with msg id: " + msgId);
				}
				
				
				return ""+otp;
			}else{
				return "Invalid Mobile Number";
			}
			
		}catch(Exception e){
			System.out.println("Somethin goes wrong in " + methodName);
		}
		
		return null;
	}
	
	

}
