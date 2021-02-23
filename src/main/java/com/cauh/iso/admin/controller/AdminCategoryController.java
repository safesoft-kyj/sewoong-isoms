package com.cauh.iso.admin.controller;

import com.cauh.iso.domain.Category;
import com.cauh.iso.service.CategoryService;
import com.cauh.iso.service.DocumentService;
import com.cauh.iso.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"Category", "CategoryList"})
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryValidator categoryValidator;
    private final DocumentService documentService;

    @GetMapping("/admin/sop/category")
    public String CategoryList(@RequestParam(value = "action", defaultValue = "list") String action,
                               @RequestParam(value = "id", required = false) String id, Model model) {
        List<Category> CategoryList = categoryService.getCategoryList();
        model.addAttribute("CategoryList", CategoryList);

        if("new".equals(action)) {
            Category Category = new Category();
            model.addAttribute("Category", Category);
        } else if ("edit".equals(action)) {
            long count = documentService.countByCategoryId(id);
            Category Category = categoryService.findById(id);
            Category.setReadonly(count > 0);
            model.addAttribute("Category", Category);
        }

        model.addAttribute("action", action);
        model.addAttribute("id", id);

        return "admin/sop/category/list";
    }

    @PostMapping("/admin/sop/category")
    public String editCategory(@RequestParam(value = "action", defaultValue = "list") String action,
                               @RequestParam(value = "id", required = false) String id,
                               @ModelAttribute("Category") Category Category,
                               BindingResult result,
                               SessionStatus status, Model model, RedirectAttributes attributes) {
        categoryValidator.validate(Category, result);

        if(result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("action", action);
            return "admin/sop/category/list";
        }
        categoryService.save(Category);
        status.setComplete();
        attributes.addFlashAttribute("message", "저장 되었습니다.");
        return "redirect:/admin/sop/category";
    }

    @DeleteMapping("/admin/sop/category")
    public String removeCategory(@RequestParam("id") String id, RedirectAttributes attributes) {

        long categoryCnt = documentService.countByCategoryId(id);

        if(categoryCnt > 0) {
            attributes.addFlashAttribute("messageType", "danger");
            attributes.addFlashAttribute("message", "삭제 불가 :: 해당 카테고리로 사용중인 문서가 있습니다.");
            return "redirect:/admin/sop/category";
        }

        Category category = categoryService.findById(id);

        if(ObjectUtils.isEmpty(category)) {
            attributes.addFlashAttribute("messageType", "danger");
            attributes.addFlashAttribute("message", "삭제하려는 Category가 존재하지 않거나 이미 삭제되었습니다.");
        } else {
            category.setDeleted(true);
            Category savedCategory = categoryService.save(category);
            log.debug("삭제 대상 Category : {}", savedCategory);

            attributes.addFlashAttribute("message", "[" + category.getShortName() + "] 카테고리가 삭제되었습니다.");
        }
        return "redirect:/admin/sop/category";
    }



}
