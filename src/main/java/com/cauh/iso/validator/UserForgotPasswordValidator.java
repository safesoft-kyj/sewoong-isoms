package com.cauh.iso.validator;

import com.cauh.common.entity.Account;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.component.CurrentUserComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserForgotPasswordValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account user = (Account)o;

        //CASE 1. 입력한 username정보가 없으면
        if(ObjectUtils.isEmpty(user.getUsername())) {
            errors.rejectValue("username", "message.required", "ID를 입력해 주세요.");
        }

        //CASE 2. 입력한 email정보가 없으면
        if(ObjectUtils.isEmpty(user.getEmail())) {
            errors.rejectValue("email", "message.required", "이메일 주소를 입력해 주세요.");
        }

        Optional<Account> searchUserOptional = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        
        //CASE 3. 입력한 username, email로 계정이 확인 되지 않으면,
        if(!searchUserOptional.isPresent()){
            errors.rejectValue("email", "message.mismatch", "입력한 ID와 이메일을 가진 계정이 존재하지 않습니다.");
        } else {
            Account searchUser = searchUserOptional.get();

            //CASE 4. 이미 임시 비밀번호가 발급되었거나, 비밀번호 유효기한이 지났을 경우
            if(!searchUser.isCredentialsNonExpired()) {
                errors.rejectValue("email", "message.duplicate", "이미 임시비밀번호를 발급 받았거나, 비밀번호를 기한이 지났습니다.");
            }
        }
    }
}
