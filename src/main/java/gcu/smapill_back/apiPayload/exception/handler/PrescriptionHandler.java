package gcu.smapill_back.apiPayload.exception.handler;

import gcu.smapill_back.apiPayload.code.BaseErrorCode;

public class PrescriptionHandler extends GeneralException {
    public PrescriptionHandler(BaseErrorCode code) {
        super(code);
    }
}
