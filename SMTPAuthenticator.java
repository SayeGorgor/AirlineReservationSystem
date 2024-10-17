package com.example.airlinereservationsystem;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator{
        public SMTPAuthenticator() {

            super();
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "skgairlines@gmail.com";
            String password = "tlhclpsuoqszcyqo";
            if ((username != null) && (username.length() > 0) && (password != null)
                    && (password.length() > 0)) {

                return new PasswordAuthentication(username, password);
            }

            return null;
        }
}
