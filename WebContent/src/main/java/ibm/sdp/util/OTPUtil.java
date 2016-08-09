package com.ibm.sdp.rest.util;

import java.util.Random;
import com.ibm.sdp.rest.dao.OTPDao;
import org.apache.commons.lang.StringUtils;

public class OTPUtil {
	
	public static int generateOTP(String appid, String msisdn){
		String methodName = "OTPUtil: generateOTP: ";
		System.out.println(methodName);
		int otp = 0;
		try{
			System.out.println(methodName + " calling calculateOPT()");
			otp = calculateOTP();
			System.out.println(methodName + " otp: " + otp);
			System.out.println(methodName + " calling function to save otp in db");
			OTPDao.saveOtp(appid, msisdn, String.valueOf(otp));
			
		}catch(Exception e){
			
		}
		return otp;
	}
	
	private static int calculateOTP() throws Exception{
		String methodName = "OTPUtil: calculateOTP: ";
		System.out.println(methodName);
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		  
		int otp = generator.nextInt(99999) + 99999;
		if (otp < 100000 || otp > 999999) {
			otp = generator.nextInt(99999) + 99999;
			if (otp < 100000 || otp > 999999) {
				throw new Exception("Unable to generate PIN at this time..");
			}
		}
		return otp;
		
	}
	
	public static boolean validateOTP(String appid, String msisdn, String otp){
		String methodName = "OTPUtil: validateOTP: ";
		System.out.println(methodName);
		boolean varifyOtp = false;
		System.out.println(methodName + " calling OTPDao.validateOtp()...");
		varifyOtp = OTPDao.validateOtp(appid, msisdn, otp);
		
		return varifyOtp;
	}
	
	
	public static String trimMsisdn(String msisdn){
		String nMsisdn = null;
		System.out.println("trimMsisn():: msisdn is "+msisdn);
		if(!isNullOrEmpty(msisdn) && 12 == msisdn.length()){
			try{
				nMsisdn = msisdn.substring(2);
			}catch(Exception e){
				System.out.println("Exception caught while trimmimg msisdn "+e);
				nMsisdn = msisdn;
			}			
		}else{
			nMsisdn = msisdn;
		}
		return nMsisdn;
	}
	
	public static boolean isNullOrEmpty(String input){
		if(null == input || StringUtils.isEmpty(input)){
			return true;
		}else{
			return false;
		}
	}
	
	public static long getCurrentTimeinSecound(){
		String methodName = "PDUtil :: getCurrentTimeinSecound: ";
		long timeinSecoud=0;
		try{
			long temp = System.currentTimeMillis();
			timeinSecoud =(temp/1000);
			System.out.println(methodName + " Current time in second : " + timeinSecoud);
		}catch(Exception e){
			System.out.println(methodName + " Exception occur: " + e.getMessage());
			e.printStackTrace(System.out);
			
		}
		
		return timeinSecoud;
	}

	public static boolean validateOtpTime(long timefromdb){
		String methodName = "OTPUtil :: validateOtpTiem: ";
		long timeinSecoud=0;
		long timeDiffDefined = 0;
		int timeDiff = 0;
		try{
			
			timeDiffDefined = 5*60;
			timeinSecoud = getCurrentTimeinSecound();
			System.out.println(methodName + " timeDiffDefined: " + timeDiffDefined);
			System.out.println(methodName + " Current timeinSecoud: " + timeinSecoud);
			System.out.println(methodName + " Time from db: " + timefromdb);
			timeDiff = getTimeDiff(timeinSecoud, timefromdb);
			System.out.println(methodName + " Time Diff : " + timeDiff);
			if(timeDiffDefined >= timeDiff){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			System.out.println(methodName + " Exception occur: " + e.getMessage());
			e.printStackTrace(System.out);
			return false;
		}
		
	}
	
	private static int getTimeDiff(long currentTime, long timefromdb){
		String methodName = "OTPUtil :: getTimeDiff : ";
		int timeDiff = 0;
		try{
			timeDiff  = (int) (currentTime - timefromdb);
			System.out.println(methodName + " time differece is : " + timeDiff);
		}catch(Exception e){
			System.out.println(methodName + " Exception occur: " + e.getMessage());
			e.printStackTrace(System.out);
		}
		return timeDiff;
		
	}
	
}
