<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>CAUH ISO Management System > User > Profile</title>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li>
            <a href="/user/profile">Profile</a>
        </li>
        <li>
            <a href="/user/signature">Signature</a>
        </li>
        <li class="active">
            <a href="/user/profile/role">Role</a>
        </li>
        <li style="float:right;">
            <a href="/agreements-withdrawal">
                <i class="pli-file-trash"></i>
                개인정보 동의 철회 신청 바로가기
                <#--                <img class="menu-title" src="/static/img/withdrawal.jpg" alt="철회신청">-->
            </a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade active in">
            <div class="pad-btm form-inline">
                <div class="row">
                    <div class="col-sm-6 table-toolbar-left">
                        <a class="btn btn-primary" href="javascript:goToURL('/user/profile/role/new')"><i class="pli-pencil"></i> ROLE 변경 신청</a>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">Current Roles</label>
                <div>
                    <input type="text" class="form-control" value="${user.commaJobTitle!''}" readonly="readonly">
                </div>
            </div>
            <@pagination.bind roleList/>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="text-center" style="width: 70px">No.</th>
                        <th class="text-center">변경 전</th>
                        <th class="text-center">변경 후</th>
                        <th class="text-center">요청 일자</th>
                        <th class="text-center">Role 배정 일자</th>
                        <th class="text-center">사유</th>
                        <th class="text-center">상태</th>
                        <th class="text-center">#Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if roleList.content?has_content>
                        <#list roleList.content as role>
                            <tr>
                                <td class="text-center">${(roleList.totalElements - (roleList.size * roleList.number)) - role?index}</td>
                                <td class="text-center"><span class="label label-warning">${role.prevJobDescription}</span></td>
                                <td class="text-center"><span class="label label-purple">${role.nextJobDescription}</span></td>
                                <td class="text-center">${role.requestDate?string("yyyy-MM-dd")}</td>
                                <td class="text-center">${role.assignDate?has_content?then(role.assignDate?string("yyyy-MM-dd"), '')}</td>
                                <td class="text-center">${role.reason}</td>
                                <td class="text-center">
                                    <#if role.roleStatus == 'ACCEPTED'>
                                        <span class="label label-info">${role.roleStatus}</span>
                                    <#elseif role.roleStatus == 'REQUESTED'>
                                        <span class="label label-primary" >${role.roleStatus}</span>
                                    <#elseif role.roleStatus == 'REJECTED'>
                                        <span class="label label-danger" >${role.roleStatus}</span>
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if role.roleStatus == 'REQUESTED'>
                                        <a class="label label-mint" href="javascript:goToURL('/user/profile/role/${role.id}')"><i class="pli-pencil"></i> Edit</a>
                                    <#else>
                                        -
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                    <#else>
                        <tr>
                            <td colspan="8" class="text-center">ROLE 변경 이력이 없습니다.</td>
                        </tr>
                    </#if>
                    </tbody>
                </table>
            </div>
            <@pagination.default/>
        </div>
    </div>
</div>

</body>
</html>
