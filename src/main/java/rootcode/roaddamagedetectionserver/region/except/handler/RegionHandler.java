package rootcode.roaddamagedetectionserver.region.except.handler;

import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.error.GeneralException;

public class RegionHandler extends GeneralException {

    public RegionHandler(ResponseCode errorCode) { super(errorCode); }
}
