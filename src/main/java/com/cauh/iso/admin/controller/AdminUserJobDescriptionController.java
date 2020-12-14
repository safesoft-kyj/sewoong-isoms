package com.cauh.iso.admin.controller;

import com.cauh.common.entity.Account;
import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.QAccount;
import com.cauh.common.entity.UserJobDescription;
import com.cauh.common.repository.UserRepository;
import com.cauh.iso.admin.service.UserJobDescriptionService;
import com.cauh.iso.admin.validator.UserJobDescriptionValidator;
import com.cauh.iso.service.JobDescriptionService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"user", "jdMap"})
@RequestMapping("/admin")
public class AdminUserJobDescriptionController {

    private final UserRepository userRepository;
    private final JobDescriptionService jobDescriptionService;
    private final UserJobDescriptionService userJobDescriptionService;
    private final UserJobDescriptionValidator userJobDescriptionValidator;

    @GetMapping("/user/jobDescription")
    public String userJobDescription(
            @PageableDefault(size = 15, sort = {"lev", "teamCode", "name"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        QAccount qAccount = QAccount.account;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAccount.enabled.eq(true));
        model.addAttribute("userJobDescriptions", userRepository.findAll(builder, pageable));
        return "admin/jobDescription/userJobDescriptions";
    }

    @GetMapping("/user/jobDescription/{username}")
    public String getUserJobDescription(@PathVariable("username") String username, Model model) {
        List<JobDescription> jobDescriptionList = jobDescriptionService.getJobDescriptionList();
        Map<String, String> jdMap = jobDescriptionList.stream()
                .collect(Collectors.toMap(s -> Integer.toString(s.getId()), s -> s.getStatusTitle()));
        model.addAttribute("jdMap", jdMap);
        model.addAttribute("user", userRepository.findByUsername(username).get());
        return "admin/jobDescription/userJobDescription";
    }

    @PostMapping("/user/jobDescription/{username}")
    public String getUserJobDescription(@PathVariable("username") String username,
                                        @ModelAttribute("user") Account user, BindingResult result,
                                        SessionStatus status, HttpServletRequest request) throws Exception {
        boolean isRemove = WebUtils.hasSubmitParameter(request, "remove");
        boolean isAdd = WebUtils.hasSubmitParameter(request, "add");
        if(isRemove) {
            int index = ServletRequestUtils.getIntParameter(request, "remove");
//
            if(ObjectUtils.isEmpty(user.getUserJobDescriptions().get(index).getId())) {
                user.getUserJobDescriptions().remove(index);
            } else {
                user.getUserJobDescriptions().get(index).setDelete(true);
            }

            return "admin/jobDescription/userJobDescription";
        } else if(isAdd) {
            user.getUserJobDescriptions().add(new UserJobDescription());
            return "admin/jobDescription/userJobDescription";
        }

        userJobDescriptionValidator.validate(user, result);
        if(result.hasErrors()) {
            return "admin/jobDescription/userJobDescription";
        }

        userJobDescriptionService.saveAll(user);
        status.setComplete();
        return "redirect:/admin/user/jobDescription";
    }

}
