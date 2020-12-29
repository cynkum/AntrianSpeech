package com.antrianservice.constant;

public class Constant {
    public final static String SERVICE_NAME = "PROJECT_SPEECH";
    static public class ErrorDictionary{
        public final static String ERROR_MSG_EN_KEY = "errorMsgEn";
        public final static String ERROR_MSG_IN_KEY = "errorMsgIn";
        public final static String ERROR_CODE_KEY = "errorCode";
    }
    static public class ErrorCode {
        public static final String SUCCESS = "00";

        public static final String GENERAL_FAILED = "100";
        public static final String GENERAL_BACKEND_FAILED = "300";

        public static final String TIMEOUT = "690";
        public static final String BACKEND_FAILED = "698";
        public static final String VALIDATION_FAILED = "699";
    }
    static public class ErrorMessage{

        static public class Id{
            public static final String SUCCESS = "Berhasil";
            public static final String GENERAL_FAILED = "Transaksi tidak dapat dilakukan";
            public static final String GENERAL_BACKEND_FAILED = "Transaksi tidak dapat dilakukan";
        }
        static public class En{
            public static final String SUCCESS = "Success";
            public static final String GENERAL_FAILED = "Transaction Cannot be Done";
            public static final String GENERAL_BACKEND_FAILED = "Transaction Cannot be Done";
        }

    }
}
