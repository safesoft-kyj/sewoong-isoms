package com.cauh.iso.validator;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.constant.UserType;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.domain.UserPasswordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPasswordChangeValidator implements Validator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPasswordDTO userPasswordDTO = (UserPasswordDTO) target;

        Account currentUser = userPasswordDTO.getCurrentUser();
        Account newPwUser = userPasswordDTO.getNewPwUser();

        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
        Matcher newPwMatcher = pattern.matcher(newPwUser.getNewPassword());

        boolean isEmpty = false;

        if(ObjectUtils.isEmpty(newPwUser.getPassword())) {
            errors.rejectValue("password", "message.password.required", "현재 비밀번호를 입력해주세요.");
        }else if(!passwordEncoder.matches(newPwUser.getPassword(), currentUser.getPassword())){
            errors.rejectValue("password", "message.password.mismatched", "현재 비밀번호가 잘못 입력되었습니다.");
        }

        //정규표현식 추가

        if(currentUser.getUserType() == UserType.ADMIN) {
            log.debug("관리자 권한에서는 정규표현식 없이 비밀번호 설정 가능");
            if(ObjectUtils.isEmpty(newPwUser.getNewPassword())) {
                errors.rejectValue("newPassword", "message.newPassword.required", "신규 비밀번호를 입력해주세요.");
                isEmpty = true;
            } else if(passwordEncoder.matches(newPwUser.getNewPassword(), currentUser.getPassword())) {
                errors.rejectValue("newPassword", "message.newPassword.origin.equals", "신규 비밀번호가 현재 비밀번호와 동일합니다.");
            }
        } else {
            if(ObjectUtils.isEmpty(newPwUser.getNewPassword())) {
                errors.rejectValue("newPassword", "message.newPassword.required", "신규 비밀번호를 입력해주세요.");
                isEmpty = true;
            } else if(passwordEncoder.matches(newPwUser.getNewPassword(), currentUser.getPassword())) {
                errors.rejectValue("newPassword", "message.newPassword.origin.equals", "신규 비밀번호가 현재 비밀번호와 동일합니다.");
            } else if(!newPwMatcher.matches()) {
                errors.rejectValue("newPassword", "message.newPassword.reqex.mismatched", "신규 비밀번호는 영문자,숫자,특수문자 포함 8글자 이상이어야 합니다.");
            }
        }

        if(ObjectUtils.isEmpty(newPwUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "message.confirmPassword.required", "비밀번호 확인란을 입력해주세요.");
            isEmpty = true;
        }

        if(!(isEmpty || newPwUser.getNewPassword().equals(newPwUser.getConfirmPassword()))) {
            errors.rejectValue("confirmPassword", "message.newPassword.mismatched", "신규 비밀번호와 비밀번호 확인란이 맞지 않습니다.");
        }
    }
}
