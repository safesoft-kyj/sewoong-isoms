package com.cauh.iso.domain.constant;

import lombok.Getter;

@Getter
public enum TrainingLogType {
    ISO_TRAINING_LOG("USER ISO", "<div class='text-center'>사용자 ISO 교육 이력<br />User ISO Training Log</div>", "mint"),
    SOP_TRAINING_LOG("USER SOP", "<div class='text-center'>사용자 SOP 교육 이력<br />User SOP Training Log</div>",  "primary"),
    ISO_ADMIN_NOT_COMPLETE_LOG("ADMIN ISO COMP", "<div class='text-center'>관리자 ISO 미이수 내역<br />(Admin ISO No Complete Log)</div>",  "dark"),
    ISO_ADMIN_COMPLETE_LOG("ADMIN ISO NO COMP", "<div class='text-center'>관리자 ISO 이수 내역<br />(Admin ISO Complete Log)</div>",  "info"),
    SOP_ADMIN_NOT_COMPLETE_LOG("ADMIN SOP COMP", "<div class='text-center'>관리자 SOP 이수 내역<br />(Admin SOP No Complete Log)</div>",  "warning"),
    SOP_ADMIN_COMPLETE_LOG("ADMIN SOP NO COMP", "<div class='text-center'>관리자 SOP 이수 내역<br />(Admin SOP Complete Log)</div>",  "success");


    String label;
    String detail;
    String className;

    TrainingLogType(String label, String detail, String className){
        this.label = label;
        this.detail = detail;
        this.className = className;
    }
}
