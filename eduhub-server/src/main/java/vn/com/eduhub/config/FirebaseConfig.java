package vn.com.eduhub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
public class FirebaseConfig {

    @Value("${firebase.app-name}")
    private String appName;
    
    @Value("${firebase.bucket-name}")
    private String bucketName;
    
    @Value("${firebase.class-path}")
    private String path;
    
    @SuppressWarnings("deprecation")
    @Bean
    public FirebaseApp initFirebase() throws IOException {
        appName = appName.concat(String.valueOf(System.currentTimeMillis()));
        FileInputStream serviceAccount = new FileInputStream(path);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(bucketName)
                .build();
        FirebaseApp.initializeApp(options, appName);
        return FirebaseApp.getInstance(appName);
    }

}
