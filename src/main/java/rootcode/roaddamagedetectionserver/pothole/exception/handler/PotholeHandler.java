package rootcode.roaddamagedetectionserver.pothole.exception.handler;

import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.error.GeneralException;

public class PotholeHandler extends GeneralException {

    public PotholeHandler(ResponseCode errorCode) { super(errorCode); }
}
