package com.cauh.iso.domain.constant;

//Audited Table의 Rev Type enum 클래스
public enum RevisionType {
    INSERT(0, "추가"),
    UPDATE(1, "수정"),
    DELETE(2, "삭제");

    private Integer value;
    private String name;

    RevisionType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue(){return value;}
    public String getName(){return name;}
}
