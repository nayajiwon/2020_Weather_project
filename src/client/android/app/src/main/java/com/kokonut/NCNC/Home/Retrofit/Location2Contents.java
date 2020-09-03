package com.kokonut.NCNC.Home.Retrofit;

public class Location2Contents {
    private SiGunGuListResponse SiGunGuListResponse;

    public SiGunGuListResponse getSiGunGuListResponse ()
    {
        return SiGunGuListResponse;
    }

    public void setSiGunGuListResponse (SiGunGuListResponse SiGunGuListResponse)
    {
        this.SiGunGuListResponse = SiGunGuListResponse;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SiGunGuListResponse = "+SiGunGuListResponse+"]";
    }

    public class SiGunGuListResponse
    {
        private CmmMsgHeader cmmMsgHeader;

        private SiGunGuList[] siGunGuList;

        private String[] content;

        public CmmMsgHeader getCmmMsgHeader ()
        {
            return cmmMsgHeader;
        }

        public void setCmmMsgHeader (CmmMsgHeader cmmMsgHeader)
        {
            this.cmmMsgHeader = cmmMsgHeader;
        }

        public SiGunGuList[] getSiGunGuList ()
        {
            return siGunGuList;
        }

        public void setSiGunGuList (SiGunGuList[] siGunGuList)
        {
            this.siGunGuList = siGunGuList;
        }

        public String[] getContent ()
        {
            return content;
        }

        public void setContent (String[] content)
        {
            this.content = content;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [cmmMsgHeader = "+cmmMsgHeader+", siGunGuList = "+siGunGuList+", content = "+content+"]";
        }
    }

    public class SiGunGuList
    {
        private String signguCd;

        public String getSignguCd ()
        {
            return signguCd;
        }

        public void setSignguCd (String signguCd)
        {
            this.signguCd = signguCd;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [signguCd = "+signguCd+"]";
        }
    }

    public class CmmMsgHeader
    {
        private String returnCode;

        private String responseTime;

        private String responseMsgId;

        private String errMsg;

        private String requestMsgId;

        private String successYN;

        public String getReturnCode ()
        {
            return returnCode;
        }

        public void setReturnCode (String returnCode)
        {
            this.returnCode = returnCode;
        }

        public String getResponseTime ()
        {
            return responseTime;
        }

        public void setResponseTime (String responseTime)
        {
            this.responseTime = responseTime;
        }

        public String getResponseMsgId ()
        {
            return responseMsgId;
        }

        public void setResponseMsgId (String responseMsgId)
        {
            this.responseMsgId = responseMsgId;
        }

        public String getErrMsg ()
        {
            return errMsg;
        }

        public void setErrMsg (String errMsg)
        {
            this.errMsg = errMsg;
        }

        public String getRequestMsgId ()
        {
            return requestMsgId;
        }

        public void setRequestMsgId (String requestMsgId)
        {
            this.requestMsgId = requestMsgId;
        }

        public String getSuccessYN ()
        {
            return successYN;
        }

        public void setSuccessYN (String successYN)
        {
            this.successYN = successYN;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [returnCode = "+returnCode+", responseTime = "+responseTime+", responseMsgId = "+responseMsgId+", errMsg = "+errMsg+", requestMsgId = "+requestMsgId+", successYN = "+successYN+"]";
        }
    }
}
