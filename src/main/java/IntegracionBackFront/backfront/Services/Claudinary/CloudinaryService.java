package IntegracionBackFront.backfront.Services.Claudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final String[]Allowed_EXTENTIONS = {".jpg", ".png",".jpeg"};

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
        Map<?,?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String Folder) throws IOException{
        validateImage(file);
        String originalfilename = file.getOriginalFilename();
        String fileExtension = originalfilename.substring(originalfilename.lastIndexOf(".")).toLowerCase();
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", Folder,
                "public_id", uniqueFilename,
                "use_filename", false,
                "unique_Filename", false,
                "overwrite", false,
                "resource_type", "auto",
                "quality" , "auto:good"
        );
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty() )throw  new IllegalArgumentException("el archivo no puede estar vacio");
        if (file.getSize() > MAX_FILE_SIZE)throw new IllegalArgumentException("el tamaño de la imagen debe ser menos de 5mb");
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IllegalArgumentException("nombre del archivo no valido");
        String extenciones = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(Allowed_EXTENTIONS).contains(extenciones)) throw new IllegalArgumentException("solo se permiten archivos jpg, png o jpeg");
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imagen valda");
    }
}
