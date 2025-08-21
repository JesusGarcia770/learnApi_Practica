package IntegracionBackFront.backfront.Controller.Claudinary;

import IntegracionBackFront.backfront.Services.Claudinary.CloudinaryService;
import com.cloudinary.Cloudinary;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class CloudinaryController {
    @Autowired
    private final CloudinaryService service;


    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file){
        try{
            System.out.println(file);
            String imageUrl = service.uploadImage(file);
            System.out.printf(imageUrl);
            return ResponseEntity.ok(Map.of(
                    "message", "imagen subida exitosamente",
                    "url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("error al subir la imagen");
        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImagetoFolder(
            @RequestParam("image")MultipartFile file,
            @RequestParam String Folder
    ){
        try {
            String imageUrl = service.uploadImage(file, Folder);
            return ResponseEntity.ok(Map.of(
                    "message", "imagen subida exitosamente",
                    "url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("error al subir la imagen");
        }
    }
}
