package rootcode.roaddamagedetectionserver.image;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;


@RequiredArgsConstructor
@Service
public class S3FileStorageService implements FileStorageService {
    @Value("${cloud.s3.bucket}")
    private String bucket;

    private static final long EXPIRATION_TIME = 1000 * 60 * 2L; // 2분

    private final AmazonS3 amazonS3;


    @Override
    public URL getPresignedUrl(String filePath) {
        GeneratePresignedUrlRequest request = getGeneratePresignedUrlRequest(bucket, filePath);
        return amazonS3.generatePresignedUrl(request);
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String filePath) {
        var request = new GeneratePresignedUrlRequest(bucket, filePath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration());

        request.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return request;
    }

    private Date getExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + EXPIRATION_TIME);
        return expiration;
    }
}
