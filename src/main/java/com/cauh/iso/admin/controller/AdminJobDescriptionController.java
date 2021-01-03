package com.cauh.iso.admin.controller;

import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.QJobDescription;
import com.cauh.common.repository.JobDescriptionRepository;
import com.cauh.iso.service.JobDescriptionService;
import com.cauh.iso.validator.JobDescriptionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes({"jobDescriptions", "jobDescription"})
public class AdminJobDescriptionController {
    private final JobDescriptionRepository jobDescriptionRepository;
    private final JobDescriptionService jobDescriptionService;
    private final JobDescriptionValidator jobDescriptionValidator;

    @GetMapping("/role")
    public String jobDescription(@RequestParam(value = "action", defaultValue = "list") String action,
                                 @RequestParam(value = "id", required = false) Integer id, Model model) {
        QJobDescription qJobDescription = QJobDescription.jobDescription;
        Iterable<JobDescription> jobDescriptions = jobDescriptionRepository.findAll(qJobDescription.shortName.asc());
        model.addAttribute("jobDescriptions", jobDescriptions);

        if("new".equals(action)) {
            model.addAttribute("jobDescription", new JobDescription());
        } else if ("edit".equals(action)) {
            JobDescription jobDescription = jobDescriptionRepository.findById(id).get();
            model.addAttribute("jobDescription", jobDescription);
        }

        model.addAttribute("action", action);
        model.addAttribute("id", id);
//        model.addAttribute("jobDescriptions", jobDescriptionRepository.findAll());

        return "admin/jobDescription/list";
    }

    @PostMapping("/role")
    public String editJobDescription(@RequestParam(value = "action", defaultValue = "list") String action,
                                     @RequestParam(value = "id", required = false) Integer id,
                                     @ModelAttribute("jobDescription") JobDescription jobDescription, BindingResult result,
                                     SessionStatus status, Model model, RedirectAttributes attributes) {
        jobDescriptionValidator.validate(jobDescription, result);

        if(result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("action", action);
            return "admin/jobDescription/list";
        }

        jobDescriptionService.save(jobDescription);
        status.setComplete();
        attributes.addFlashAttribute("message", "ROLE 정보가 등록 되었습니다.");
        return "redirect:/admin/role";
    }
}