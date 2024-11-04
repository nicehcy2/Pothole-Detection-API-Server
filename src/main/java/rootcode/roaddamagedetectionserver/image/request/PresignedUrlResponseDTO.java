package rootcode.roaddamagedetectionserver.image.request;

public record PresignedUrlResponseDTO(
        String presignedUrl,
        String fileUrl
) {
}
