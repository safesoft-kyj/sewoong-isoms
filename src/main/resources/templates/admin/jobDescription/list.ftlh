<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > ROLE 관리</title>
</head>
<body>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Role 관리</h3>
        <div class="panel-control" style="float:left;">
            <ul class="nav nav-tabs">
                <li class='active'>
                    <a href="/admin/role">Role 관리</a>
                </li>
                <li>
                    <a href="/admin/role/user">사용자별 Role 관리</a>
                </li>
            </ul>
        </div>
    </div>
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body" style="margin-top:45px;">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <a class="btn btn-primary" href="/admin/role?action=new#edit0"><i class="pli-pencil"></i>
                        ROLE 추가</a>
                </div>
                <div class="col-sm-6 table-toolbar-right">
                    <#--                    <div class="form-group">-->
                    <#--                        <input id="demo-input-search2" type="text" placeholder="Search" class="form-control" autocomplete="off">-->
                    <#--                    </div>-->
                    <#--                    <div class="btn-group">-->
                    <#--                        <button class="btn btn-default"><i class="fa fa-search fa-lg"></i></button>-->
                    <#--                    </div>-->
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <form name="f" method="post" action="/admin/role">
                <input type="hidden" name="action" value="${action}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th class="text-center" style="width:70px;">No.</th>
                        <th style="width:240px;">ROLE</th>
                        <th>ROLE Alias</th>
                        <th class="text-center" style="width:300px;">Manager</th>
                        <th class="text-center" style="width:300px;">Status</th>
                        <th style="width:180px">#actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if jobDescriptions?has_content>
                        <#list jobDescriptions as role>
                            <tr>
                                <td class="text-center">${role?index + 1}
                                    <a name="edit${role?index}"></a>
                                    <#if action == "edit" && id == role.id>
                                        <@spring.formHiddenInput "jobDescription.id"/>
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == role.id>
                                        <@spring.formInput "jobDescription.shortName" "class='form-control'"/>
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        ${role.shortName}
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == role.id>
                                        <@spring.formInput "jobDescription.title" "class='form-control'"/>
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        ${role.title}
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if action == "edit" && id == role.id>
                                        <@spring.formCheckbox "jobDescription.manager" "class="/>
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        <span class="label label-${role.manager?then('success', 'warning')}">${role.manager?then('Y', 'N')}</span>
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if action == "edit" && id == role.id>
                                        <@spring.formCheckbox "jobDescription.enabled" />
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        <span class="label label-${role.enabled?then('success', 'warning')}">${role.enabled?then('Active', 'Inactive')}</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == role.id>
                                        <button class="btn btn-sm btn-primary" type="submit"><i
                                                    class="fa fa-check"></i> 저장
                                        </button>
                                        <a class="btn btn-sm btn-warning" href="?action=list"><i
                                                    class="fa fa-times"></i> 취소</a>
                                    <#else>
                                        <a class="btn btn-sm btn-mint"
                                           href="/admin/role?action=edit&id=${role.id}#edit${role?index}"><i
                                                    class="fa fa-pencil"> 수정</i></a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                    <#else>
                        <tr>
                            <td colspan="5" class="text-center">등록된 ROLE 정보가 없습니다.</td>
                        </tr>
                    </#if>
                    <#if action == "new">
                        <tr>
                            <td class="text-center">#<a name="edit0"></a></td>
                            <td>
                                <@spring.formInput "jobDescription.shortName" "class='form-control' placeholder='ROLE'"/>
                                <@spring.showErrors "" "color:red;"/></td>
                            <td>
                                <@spring.formInput "jobDescription.title" "class='form-control' placeholder='ROLE Alias'"/>
                                <@spring.showErrors "" "color:red;"/></td>
                            <td class="text-center">
                                <@spring.formCheckbox "jobDescription.manager" />
                                <@spring.showErrors "" "color:red;"/>
                            </td>
                            <td class="text-center">
                                <@spring.formCheckbox "jobDescription.enabled" "checked"/>
                                <@spring.showErrors "" "color:red;"/></td>
                            <td>
                                <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i>
                                    저장
                                </button>
                                <a class="btn btn-sm btn-warning" href="?action=list"><i
                                            class="fa fa-times"></i> 취소</a>
                            </td>
                        </tr>
                    </#if>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
    <!--===================================================-->
    <!--End Data Table-->
</div><!-- end panel-->
</body>
</html>