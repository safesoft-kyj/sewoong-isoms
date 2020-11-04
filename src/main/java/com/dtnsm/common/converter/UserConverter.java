package com.dtnsm.common.converter;

import com.dtnsm.common.component.ApplicationContextProvider;
import com.dtnsm.common.entity.Account;
import com.dtnsm.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;
import java.util.Optional;

//@Converter
@Slf4j
public class UserConverter implements AttributeConverter<Account, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Account u) {
        return ObjectUtils.isEmpty(u) ? null : u.getId();
    }

    @Override
    public Account convertToEntityAttribute(Integer id) {
//        log.warn("id : {}", id);
        if(ObjectUtils.isEmpty(id) || id == 0) {
            return null;
        } else {
            ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
            if (ObjectUtils.isEmpty(applicationContext)) {
                log.error("applicationContext is null");
                return null;
            } else {
                UserRepository userRepository = (UserRepository) applicationContext.getBean("userRepository");
                if (ObjectUtils.isEmpty(userRepository)) {
                    log.error("ApplicationContext 에서 UserRepository 정보를 가져오지 못했습니다.");
                    return null;
                } else {
                    Optional<Account> optionalUser = userRepository.findById(id);
                    if (optionalUser.isPresent()) {
                        return optionalUser.get();
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
