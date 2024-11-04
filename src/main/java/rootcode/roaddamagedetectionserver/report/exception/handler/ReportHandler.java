package rootcode.roaddamagedetectionserver.report.exception.handler;

import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.error.GeneralException;

public class ReportHandler extends GeneralException {

    public ReportHandler(ResponseCode errorCode) { super(errorCode); }
}
