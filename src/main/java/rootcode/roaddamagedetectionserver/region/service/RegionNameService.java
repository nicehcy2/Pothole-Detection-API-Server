package rootcode.roaddamagedetectionserver.region.service;

public interface RegionNameService {

    String getAddressFromCoordinates(double latitude, double longitude);

    String extractRegionBeforeSecondSpace(String fullRegionName);
}
