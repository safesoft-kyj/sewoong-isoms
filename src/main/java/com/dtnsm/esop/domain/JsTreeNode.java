package com.dtnsm.esop.domain;

import lombok.Data;

import java.util.*;

@Data
public class JsTreeNode {
//    private String icon = "company";
    private String type = "company";
//    private String deptCode;
//    private String deptName;
//    private String teamCode;
//    private String teamName;
//    private String empNo;
    private String code;
    private String text;
    private Map<String, Object> data = new HashMap<>();
    private JsTreeState state = new JsTreeState();
    private List<JsTreeNode> children = new ArrayList<>();

    public JsTreeNode(String code, String text) {
        this.code = code;
        this.text = text;
    }


    public JsTreeNode(String code, String text, String deptCode, String deptName, String teamCode, String teamName, String empNo, String username, JsTreeIcon icon) {
        this.code = code;
        this.text = text;
        this.type = icon.name();
        data.put("type", icon.name());
        data.put("deptCode", deptCode);
        data.put("deptName", deptName);

        if(icon != JsTreeIcon.dept) {
            data.put("teamCode", teamCode);
            data.put("teamName", teamName);

            if (icon == JsTreeIcon.user_female || icon == JsTreeIcon.user_male || icon == JsTreeIcon.user_mng_female || icon == JsTreeIcon.user_mng_male) {
                data.put("empNo", empNo);
                data.put("empName", text);
                data.put("username", username);
                data.put("type", "user");
            }
        }

    }

    public Map<String, Object> getData() {
        data.put("children", getChildren());
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsTreeNode that = (JsTreeNode) o;

//        log.debug("code[{}] == that.code[{}]", code, that.code);
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
