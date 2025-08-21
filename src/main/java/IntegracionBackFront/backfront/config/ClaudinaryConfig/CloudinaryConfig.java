package IntegracionBackFront.backfront.config.ClaudinaryConfig;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary claudinary(){
        Dotenv dotenv = Dotenv.load();

        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLOUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLOUDINARY_APY_SECRET"));
        return new Cloudinary(config);
    }
}
