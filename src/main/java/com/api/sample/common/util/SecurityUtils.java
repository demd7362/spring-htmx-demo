package com.api.sample.common.util;

import com.api.sample.common.security.PrincipalDetails;
import com.api.sample.entity.user.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {
    public User currentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof PrincipalDetails principalDetails){
            return principalDetails.user();
        }
        return null;
    }
}
