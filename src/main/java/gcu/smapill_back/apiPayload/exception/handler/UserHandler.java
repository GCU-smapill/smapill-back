package gcu.smapill_back.apiPayload.exception.handler;

import gcu.smapill_back.apiPayload.code.BaseErrorCode;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
