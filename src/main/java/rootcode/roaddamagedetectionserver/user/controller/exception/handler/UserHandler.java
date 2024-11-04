package rootcode.roaddamagedetectionserver.user.controller.exception.handler;

import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.error.GeneralException;

public class UserHandler extends GeneralException {

    public UserHandler(ResponseCode errorCode) { super(errorCode); }
}
