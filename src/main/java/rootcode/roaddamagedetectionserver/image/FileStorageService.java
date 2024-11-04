package rootcode.roaddamagedetectionserver.image;

import java.net.URL;

public interface FileStorageService {
    URL getPresignedUrl(String filePath);
}
