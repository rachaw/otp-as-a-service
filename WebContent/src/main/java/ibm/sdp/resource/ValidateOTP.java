package com.ibm.sdp.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ibm.sdp.rest.util.OTPUtil;

@Path("/validateotp")
public class ValidateOTP {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String validateOtp(@QueryParam("appid") String appID, @QueryParam("msisdn") String msisdn, @QueryParam("otp") String otp){
		String methodName = "ValidateOTP :: validateOtp: ";
		System.out.println(methodName);
		boolean status = false;
		String finalStauts = null;
		System.out.println("AppId: "+ appID + "  Msisdn: "+ msisdn);
		
		try{
			
			if(null == appID || null == msisdn || null == otp){
				return "Mendetory paramter missing";
			}
			
			if(null != msisdn && (msisdn.length() == 10 || msisdn.length() == 12)){
				if(msisdn.length() == 12){
					System.out.println(methodName + " validating msisdn");
					msisdn = OTPUtil.trimMsisdn(msisdn);
				}
				System.out.println(methodName + " validating otp...");
				status = OTPUtil.validateOTP(appID, msisdn, otp);
				if(status == false){
					finalStauts  = "FAIL";
				}else{
					finalStauts  = "SUCCESS";
				}
			}else{
				return "Invalid Mobile Number";
			}
			
		}catch(Exception e){
			System.out.println("Somethin goes wrong in " + methodName);
			return null;
		}
		
		return finalStauts;
	}
	

}
