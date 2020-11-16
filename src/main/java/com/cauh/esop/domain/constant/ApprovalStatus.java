package com.cauh.esop.domain.constant;

public enum ApprovalStatus {
    temp("임시보관함", "보관", "mint", true, true),
    request("요청함", "요청", "info", true, true),
    progress("진행함", "진행", "purple", false, false),
    approved("완료함", "완료", "success", false, false),
//    COMPLETED("완료함", "완료"),
    rejected("반려함", "반려", "danger", false, false),
    deleted("삭제함", "삭제", "warning", false, false);

    private String box;
    private String label;
    private String className;
    private boolean edit;
    private boolean delete;

    ApprovalStatus(String box, String label, String className, boolean edit, boolean delete) {
        this.box = box;
        this.label = label;
        this.className = className;
        this.edit = edit;
        this.delete = delete;
    }

    public String getBox() {
        return box;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return className;
    }

    public boolean isEdit() {
        return edit;
    }

    public boolean isDelete() {
        return delete;
    }
}
