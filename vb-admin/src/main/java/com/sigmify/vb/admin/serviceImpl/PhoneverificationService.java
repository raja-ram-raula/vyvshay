package com.sigmify.vb.admin.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigmify.vb.admin.entity.Twilioproperties;
import com.sigmify.vb.admin.repositories.UserContactDetailsRepository;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@Service
public class PhoneverificationService {
  private final Twilioproperties twilioproperties;
  
  @Autowired
  private UserContactDetailsRepository ucRepo;
  @Autowired
  public PhoneverificationService(Twilioproperties twilioproperties) {
    this.twilioproperties=twilioproperties;
  }
   

  //method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator(twilioproperties.getServiceId(), phone, "sms").create();
            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
      return new VerificationResult(verification.getSid());
      }
        } catch (ApiException exception) {
            return new VerificationResult(new String[] {exception.getMessage()});
        }
        return null;
    }
    //mehtod to verifiy the otp
    public VerificationResult checkverification(String phone, String code) {
    	//System.out.println("PhoneService :: "+phone);
        try {
            VerificationCheck verification = VerificationCheck.creator(twilioproperties.getServiceId(), code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
}
