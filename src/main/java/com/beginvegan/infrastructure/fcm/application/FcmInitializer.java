package com.beginvegan.infrastructure.fcm.application;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FcmInitializer {

    @Value("${firebase.key-path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void getFcmCredential(){
        try {
            InputStream refreshToken = new ClassPathResource("firebase/" + firebaseConfigPath).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

            FirebaseApp.initializeApp(options);
            log.info("Fcm Setting Completed");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
