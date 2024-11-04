package rootcode.roaddamagedetectionserver.alarm.exception.handler;

import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.error.GeneralException;

public class AlarmHandler extends GeneralException {

    public AlarmHandler(ResponseCode errorCode) { super(errorCode); }
}
