package com.swyp.glint.auth.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KaKaoAccountResponse {

    private String name_needs_agreement;
    private String name;
    private Boolean has_email;
    private String email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    private String has_phone_number;
    private Boolean phone_number_needs_agreement;
    private String phone_number;
    private String has_birthdate;
    private Boolean has_birthyear;
    private Boolean birthyear_needs_agreement;
    private String birthyear;
    private Boolean has_birthday;
    private Boolean birthday_needs_agreement;
    private String birthday;
    private String birthday_type;
    private Boolean has_gender;
    private Boolean gender_needs_agreement;
    private String gender;

}
