package rootcode.roaddamagedetectionserver.pothole.controller;

import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeDetail;

import java.util.List;

public record PotholeListResponseDTO(
        List<PotholeDetail> potholes,
        int page,
        int totalPage
) {
}
