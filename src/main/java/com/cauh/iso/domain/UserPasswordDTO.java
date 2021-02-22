package com.cauh.iso.domain;

import com.cauh.common.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPasswordDTO {

    private Account currentUser;

    private Account newPwUser;

}
