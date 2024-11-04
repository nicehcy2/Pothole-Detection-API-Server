package rootcode.roaddamagedetectionserver.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rootcode.roaddamagedetectionserver.image.request.PresignedUrlResponseDTO;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class FileServiceTest {
    @DisplayName("생성된 presigned url을 반환한다")
    @Test
    void presigned_url_test() {
        // given
        FileService fileService = new FileService(
                () -> "12345",
                fileName -> {
                    try {
                        return new URL("https://awss3.com/" + fileName + "?presignedquery=string&another=param");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        // when
        PresignedUrlResponseDTO response = fileService.createPresignedUrl(1);

        // then
        assertThat(response.presignedUrl())
                .isEqualTo("https://awss3.com/pothole/12345.jpg?presignedquery=string&another=param");
    }

    @DisplayName("생성된 presigned url에서 쿼리 스트링을 날린 url이 생성될 file의 url이다")
    @Test
    void presigned_file_url_test() {
        // given
        FileService fileService = new FileService(
                () -> "12345",
                fileName -> {
                    try {
                        return new URL("https://awss3.com/" + fileName + "?presignedquery=string&another=param");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        // when
        PresignedUrlResponseDTO response = fileService.createPresignedUrl(1);

        // then
        assertThat(response.fileUrl())
                .isEqualTo("https://awss3.com/pothole/12345.jpg");
    }
}