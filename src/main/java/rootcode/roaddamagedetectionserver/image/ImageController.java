package rootcode.roaddamagedetectionserver.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rootcode.roaddamagedetectionserver.image.request.PresignedUrlResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeImagesResponseDTO;

import java.util.List;

@Tag(description = "이미지 관련 API", name = "Image")
@RequiredArgsConstructor
@RestController
public class ImageController {
    private final FileService fileService;
    private final ImageService imageService;
    private final int EDGE_TO_SECONDARY_DATA = 1;
    private final int SECONDARY_TO_FINAL_DATA = 2;

    @Operation(summary = "이미지 업로드를 위한 presigned url 생성")
    @GetMapping("/presigned-url")
    public PresignedUrlResponseDTO getPresignedUrl() {
        // FIXME: 현실에서 코드 이렇게 쓰면 큰일 납니다. 아무 권한 체크 없이 모든 이미지 업로드 요청에 대해 presigned url을 생성해주는 것은 위험합니다.
        return fileService.createPresignedUrl(EDGE_TO_SECONDARY_DATA);
    }

    @Operation(summary = "2차 서버에서 검증된 포트홀 이미지 업로드를 위한 presigned url 생성")
    @GetMapping("/presigned-validate-url")
    public PresignedUrlResponseDTO getValidatePresignedUrl() {
        // FIXME: 현실에서 코드 이렇게 쓰면 큰일 납니다. 아무 권한 체크 없이 모든 이미지 업로드 요청에 대해 presigned url을 생성해주는 것은 위험합니다.
        return fileService.createPresignedUrl(SECONDARY_TO_FINAL_DATA);
    }

    @Operation(summary = "신고를 위한 포트홀 이미지 업로드를 위한 presigned url 생성")
    @GetMapping("/presigned-report-url")
    public PresignedUrlResponseDTO getReportPresignedUrl() {
        return fileService.createPresignedUrl(3);
    }


    @Operation(summary = "2차 검증이 되지 않은 포트홀 정보 조회")
    @GetMapping("/images/unverified")
    public PotholeImagesResponseDTO getUnverifiedPotholes() {
        List<Image> images = imageService.findAllUnverifiedPotholeImages();
        return PotholeImagesResponseDTO.of(images);
    }

}
