package com.cauh.esop.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.Signature;
import com.cauh.common.repository.SignatureRepository;
import com.cauh.common.repository.UserRepository;
import com.cauh.common.security.annotation.CurrentUser;
import com.cauh.common.security.authentication.CustomUsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final SignatureRepository signatureRepository;

    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/user/signature")
    public String signature(@CurrentUser Account user, Model model) {
        Optional<Signature> optionalSignature = signatureRepository.findById(user.getUsername());
        model.addAttribute("signature", optionalSignature.isPresent() ? optionalSignature.get() : new Signature());

        return "user/signature";
    }

    @PostMapping("/user/signature")
    public String updateSignature(@CurrentUser Account user, @RequestParam("base64signature") String base64signature, RedirectAttributes attributes) {
        Optional<Account> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isPresent()) {
            Account u = optionalUser.get();

            Signature signature = new Signature();
            signature.setBase64signature(base64signature);
            signature.setId(u.getUsername());

            signatureRepository.save(signature);

            user.setSignature(true);
            updateAuthentication(user);
        }

        attributes.addFlashAttribute("message", "서명 정보가 등록 되었습니다.");
        return "redirect:/user/signature";
    }

    public void updateAuthentication(Account userDetails) {
        Collection authorities = userDetails.getSopAuthorities();
        Authentication authentication = new CustomUsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
