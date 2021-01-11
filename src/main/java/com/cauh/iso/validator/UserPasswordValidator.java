package com.cauh.iso.validator;

import com.cauh.common.entity.Account;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPasswordValidator implements Validator {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account user = (Account) o;
        Optional<Account> currentUser = userRepository.findById(user.getId());

        //현재 비밀번호가 입력되지 않았을 경우,
        if(StringUtils.isEmpty(user.getPassword())){
            errors.rejectValue("password", "message.required", "비밀번호가 입력되지 않았습니다.");
        }

        //현재 비밀번호가 일치하지 않을 경우
        if(currentUser.isPresent()){
            if(!passwordEncoder.matches(user.getPassword(), currentUser.get().getPassword())){
                errors.rejectValue("password", "message.invalid", "현재 비밀번호와 일치하지 않습니다.");
            }
        }
    }
}
