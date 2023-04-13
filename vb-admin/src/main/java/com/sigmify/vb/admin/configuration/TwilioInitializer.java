package com.sigmify.vb.admin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sigmify.vb.admin.entity.Twilioproperties;
import com.twilio.Twilio;

@Configuration
public class TwilioInitializer {

  
  private final Twilioproperties twilioproperties ;

  @Autowired
  public TwilioInitializer(Twilioproperties twilioproperties) {
    this.twilioproperties = twilioproperties;
    Twilio.init(twilioproperties.getAccountSid(), twilioproperties.getAuthToken());
    System.out.println("Twilio initialized with account-"+twilioproperties.getAccountSid());
  }
  
}
