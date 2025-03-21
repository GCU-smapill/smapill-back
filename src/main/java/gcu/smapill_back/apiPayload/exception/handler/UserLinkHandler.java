package gcu.smapill_back.apiPayload.exception.handler;


import gcu.smapill_back.apiPayload.code.BaseErrorCode;

public class UserLinkHandler extends GeneralException {
    public UserLinkHandler(BaseErrorCode code) {
        super(code);
    }
}