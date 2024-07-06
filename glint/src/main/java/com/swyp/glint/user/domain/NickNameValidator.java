package com.swyp.glint.user.domain;

public class NickNameValidator {


    public static boolean isValid(String nickName) {
        return nickNameLengthValidate(nickName);
    }

    private static boolean nickNameLengthValidate(String nickName) {
        return nickName.length() >= 3 && nickName.length() <= 15;
    }


}
