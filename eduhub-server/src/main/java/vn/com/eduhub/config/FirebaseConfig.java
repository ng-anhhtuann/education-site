package vn.com.eduhub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.class-path:eduhub-key.json}")
    private String firebaseClassPath;

    @Value("${firebase.database-url}")
    private String firebaseDatabaseUrl;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        InputStream serviceAccount = resolveServiceAccount(firebaseClassPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(firebaseDatabaseUrl)
            .build();

        return FirebaseApp.initializeApp(options);
    }

    private InputStream resolveServiceAccount(String classPathOrFile) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(classPathOrFile);
        if (classPathResource.exists()) {
            return classPathResource.getInputStream();
        }

        return new FileInputStream(classPathOrFile);
    }
}

