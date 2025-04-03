package com.airbnb.service;

import org.springframework.stereotype.Service;
 import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

        @Value("${twilio.account-sid}")
        private String accountSid;

        @Value("${twilio.auth-token}")
        private String authToken;

        @Value("${twilio.phone-number}")
        private String fromPhoneNumber;

        public String sendSms(String to, String message) {
            Twilio.init(accountSid, authToken);

            Message sentMessage = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(fromPhoneNumber),
                    message
            ).create();

            return "SMS sent successfully with SID: " + sentMessage.getSid();
        }
    }




