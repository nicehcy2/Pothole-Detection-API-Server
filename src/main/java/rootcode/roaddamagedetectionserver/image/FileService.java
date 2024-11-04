package rootcode.roaddamagedetectionserver.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rootcode.roaddamagedetectionserver.image.request.PresignedUrlResponseDTO;

import java.net.URL;

@RequiredArgsConstructor
@Service
public class FileService {
    private static final String POTHOLE_FILE_STORE_PATH = "pothole/";
    private static final String REPORT_FILE_STORE_PATH = "report/";
    private static final String FILE_VALIDATE_STORE_PATH = "validate-pothole/";
    private final FileNameCreator fileNameCreator;
    private final FileStorageService fileStorageService;

    public PresignedUrlResponseDTO createPresignedUrl(int level) {
        String fileName = fileNameCreator.createFileName();
        String filePath = getFilePathBasedOnLevel(level, fileName);

        URL url = fileStorageService.getPresignedUrl(filePath);

        return new PresignedUrlResponseDTO(
                url.toString(),
                url.toString().split("\\?")[0]
        );
    }

    private String getFilePathBasedOnLevel(int level, String fileName) {
        switch (level) {
            case 1:
                return createFilePath(fileName);
            case 2:
                return createValidateFilePath(fileName);
            case 3:
                return createReportImageFilePath(fileName);
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }

    private String createReportImageFilePath(String fileName) {
        return REPORT_FILE_STORE_PATH + fileName + ".jpg";
    }

    private String createFilePath(String fileName) {
        return POTHOLE_FILE_STORE_PATH + fileName + ".jpg";
    }

    private String createValidateFilePath(String fileName) {
        return FILE_VALIDATE_STORE_PATH + fileName + ".jpg";
    }
}
