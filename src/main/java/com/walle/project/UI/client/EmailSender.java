package com.walle.project.UI.client;

import com.walle.project.server.entity.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void sendEmailToNewUser(Users user){

        final String username = "walletobe2019@gmail.com";
        final String password = "walle2019";
        String to = user.getEmail ();
        String name = user.getName ();
        Properties props = new Properties ( );
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.starttls.enable", "true");
        props.put ("mail.smtp.host", "smtp.gmail.com");
        props.put ("mail.smtp.port", "587");

        Session session = Session.getInstance (props,
                new javax.mail.Authenticator ( ) {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication (username, password);
                    }
                });

        try {

            Message message = new MimeMessage (session);
            message.setFrom (new InternetAddress (username));
            InternetAddress[] addresses = {new InternetAddress (to)};
            message.setRecipients (Message.RecipientType.TO, addresses);
            message.setSubject ("Authentication for ToBe Application");
            message.setText ("Dear " + name + ","
                    + "\n\n Welcome to our company\n\n"+"Login :  "+ user.getLogin ()+
                    "\nPassword : " + user.getPassword ());

            Transport transport = session.getTransport ("smtp");
            transport.connect ("smtp.gmail.com", username, password);
            transport.sendMessage (message, message.getAllRecipients ( ));
            transport.close ( );

            System.out.println ("Done");

        } catch (MessagingException e) {
            throw new RuntimeException (e);
        }
    }
}
