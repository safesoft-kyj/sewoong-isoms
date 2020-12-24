<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > Authority Management > 사용자 관리</title>

    <!-- select2 -->
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
    <!-- select2 -->
    <script src="/static/plugins/select2/js/select2.min.js"></script>

    <style>
        .select2-container--open {
            z-index: 9999999
        }
    </style>

    <script>
        //Training 진행 여부
        $(document).ready(function () {

            $("button[name='trainingBtn']").click(function () {
                $.ajax({
                    url: '/admin/authority/users/' + $(this).data("id") + '/training',
                    method: 'post',
                    data: {"_method": "put", r: Math.random()},
                    success: function (res) {
                        if (res.result == 'success') {
                            $("#trainingBtn" + res.id).removeClass('btn-' + (res.className == 'default' ? 'success' : 'default'));
                            $("#trainingBtn" + res.id).addClass('btn-' + res.className);

                            $.niftyNoty({
                                type: 'info',
                                container: 'floating',
                                message: '[' + res.name + '] 교육 진행 상태가 변경 되었습니다.',
                                closeBtn: true,
                                floating: {
                                    position: 'top-right',
                                    animationIn: 'fadeInUp',
                                    animationOut: 'fadeOut'
                                },
                                focus: true,
                                timer: 1000
                            });
                        }
                    }
                });
            });

            //이메일 수신여부 확인
            $("button[name='receiveEmailBtn']").click(function () {
                $.ajax({
                    url: '/admin/authority/users/' + $(this).data("id") + '/receiveEmail',
                    method: 'post',
                    data: {"_method": "put", r: Math.random()},
                    success: function (res) {
                        if (res.result == 'success') {
                            $("#receiveEmailBtn" + res.id).removeClass('btn-' + (res.className == 'default' ? 'success' : 'default'));
                            $("#receiveEmailBtn" + res.id).addClass('btn-' + res.className);

                            $.niftyNoty({
                                type: 'info',
                                container: 'floating',
                                message: '[' + res.name + '] 이메일 수신 상태가 변경 되었습니다.',
                                closeBtn: true,
                                floating: {
                                    position: 'top-right',
                                    animationIn: 'fadeInUp',
                                    animationOut: 'fadeOut'
                                },
                                focus: true,
                                timer: 1000
                            });
                        }
                    }
                });
            });

            $("#deptSelect").change(function(){
                departmentSelect($(this).val());
            })

            $("#teamSelect").change(function(){
                let res = $(this).val();

                if(res == 'department') {
                    $("#department").val($("#deptSelect").val());
                }else {
                    $("#department").val(res);
                }
            })

            $(document).on("click", "#acceptBtn", function (e) {

                console.log("TEST");

                let username = $(this).data("value");
                let JDList = $(this).data("jds").split(',');
                let pid = $(this).data("pdeptid");
                let id = $(this).data("deptid");

                $("#accept-modal #userId").val(username);
                $("#accept-modal .userTitle").html("[" + username + "]'s Information");

                //Job Description Setting
                $("#jdIds").val(JDList).trigger("change");

                if(pid != ''){ //pid가 있는 경우,
                    $("#deptSelect").val(pid).trigger("change");
                    $("#teamSelect").val(id).trigger("change");
                } else { //pid가 없는 경우
                    $("#deptSelect").val(id).trigger("change");
                }
            });

            //거절 버튼 입력 시
            $(document).on("click", "#rejectBtn", function (e) {
                let username = $(this).data("value");
                if (!confirm("유저 [" + username + "]의 회원가입 신청을 거절합니다. 계속 진행하시겠습니까?")) {
                    e.preventDefault();
                }
            });

            $('#jdIds').select2({width: '100%'});
        });

        function departmentSelect(value) {
            $("#department").val(value);
            $.ajax({
                url: "/signUp/ajax/department",
                method: 'post',
                data: {id: value},
                async: false,
                success: function (data) {
                    let teamSelect = $("#teamSelect");

                    if(data != '') {
                        teamSelect.attr("disabled", false);
                        teamSelect.html(data);
                    } else {
                        teamSelect.attr("disabled", true);
                        teamSelect.html(data);
                    }
                }
            });
        }

    </script>
</head>
<body>

<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <@pagination.bind users/>
        <div class="pad-btm">
<#--            <div class="pull-right">-->
<#--                <form method="post" action="/admin/authority/users/sync">-->
<#--                <button class="btn btn-info"><i class="pli-sync icon-lg"></i> 조직도 동기화</button>-->
<#--                </form>-->
<#--            </div>-->
            <form name="f" id="f" class="form-inline">
<#--                <#if deptList?has_content>-->
<#--                    <#assign pDeptCode = RequestParameters['deptCode']?default('')/>-->
<#--                    <div class="box-inline mar-btm">-->
<#--                        &lt;#&ndash;                        Team :&ndash;&gt;-->
<#--                        &lt;#&ndash;                        <div class="select">&ndash;&gt;-->
<#--                        <select name="deptCode" id="deptCode" class="form-control">-->
<#--                            <option value=""${pTeamCode?has_content?then('', ' selected')}>ALL</option>-->
<#--                            <#list deptList as dept>-->
<#--                                <option value="${dept['deptCode']}"${pDeptCode?has_content?then((pDeptCode == dept['deptCode'])?then(' selected', ''), '')}>${dept['deptName']}</option>-->
<#--                            </#list>-->
<#--                        </select>-->
<#--                        &lt;#&ndash;                        </div>&ndash;&gt;-->
<#--                    </div>-->
<#--                    <script>-->
<#--                        $("#deptCode").change(function() {-->
<#--                            $("#teamCode").val("");-->

<#--                            $("#f").attr("method", "get").submit();-->
<#--                        });-->
<#--                    </script>-->
<#--                </#if>-->
<#--                <#if teamList?has_content>-->
<#--                    <#assign pTeamCode = RequestParameters['teamCode']?default('')/>-->
<#--                    <div class="box-inline mar-btm">-->
<#--                        &lt;#&ndash;                        Team :&ndash;&gt;-->
<#--                        &lt;#&ndash;                        <div class="select">&ndash;&gt;-->
<#--                        <select name="teamCode" id="teamCode" class="form-control">-->
<#--                            <option value=""${pTeamCode?has_content?then('', ' selected')}>ALL</option>-->
<#--                            <#list teamList as team>-->
<#--                                <option value="${team['teamCode']}"${pTeamCode?has_content?then((pTeamCode == team['teamCode'])?then(' selected', ''), '')}>${team['teamName']}</option>-->
<#--                            </#list>-->
<#--                        </select>-->
<#--                        &lt;#&ndash;                        </div>&ndash;&gt;-->
<#--                    </div>-->
<#--                    <script>-->
<#--                        $("#teamCode").change(function() {-->
<#--                            $("#userList").val("");-->

<#--                            $("#f").attr("method", "get").submit();-->
<#--                        });-->
<#--                    </script>-->
<#--                </#if>-->
                <div class="box-inline mar-btm">
                    <input type="text" name="name" value="${RequestParameters['name']?default('')}" class="form-control" placeholder="사용자 명">
                </div>
                <div class="box-inline mar-btm">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                </div>
            </form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:80px">No.</th>
                    <th class="text-center">ID</th>
                    <th class="text-center">부서</th>
                    <th class="text-center">팀</th>
                    <th class="text-center">이름</th>
                    <th class="text-center">생년월일</th>
                    <th class="text-center">연락처</th>
                    <th class="text-center">E-mail</th>
                    <th class="text-center">교육 진행</th>
                    <th class="text-center">이메일 수신</th>
                    <th class="text-center">#Actions</th>
                </tr>
                </thead>
                <tbody>
                <#if users.content?has_content>
                    <#list users.content as t>
                        <tr>
                            <td class="text-center" style="vertical-align: middle;">${(users.totalElements - (users.size * users.number)) - t?index}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.username}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.deptName!''}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.teamName!''}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.name}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.birthDate?string('yyyy-MM-dd')}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.phone}</td>
                            <td class="text-center" style="vertical-align: middle;">${t.email}</td>
                            <td class="text-center">
                                <#if t.userStatus?has_content && t.userStatus == 'ACTIVE'>
                                    <button class="btn btn-icon btn-${t.training?then('success', 'default')} btn-circle" data-id="${t.id}" name="trainingBtn" id="trainingBtn${t.id}"><i class="fa fa-check"></i></button>
                                <#else>
                                    -
                                </#if>
                            </td>
                            <td class="text-center">
                                <#if t.userStatus?has_content && t.userStatus == 'ACTIVE'>
                                    <button class="btn btn-icon btn-${t.receiveEmail?then('success', 'default')} btn-circle" data-id="${t.id}" name="receiveEmailBtn" id="receiveEmailBtn${t.id}"><i class="fa fa-check"></i></button>
                                <#else>
                                    -
                                </#if>
                            </td>
                            <td class="text-center">
                                <#if t.userStatus?has_content && t.userStatus == 'ACTIVE'>
                                    <form action="pwReset" method="POST">
                                        <a href="/admin/authority/users/${t.id}" class="btn btn-icon btn-info">Edit</a>
                                        <button class="btn btn-icon btn-danger">Password Reset</button>
                                    </form>
                                <#elseif t.userStatus?has_content && t.userStatus == 'SIGNUP_REQUEST'>
                                    <form class="form-horizontal" method="POST">
                                        <input name="id" type="hidden" value="${t.id}" />
                                        <button class="btn btn-icon btn-info" type="button" id="acceptBtn" data-toggle="modal" data-value="${t.username}" data-jds="${t.commaJobIds}" data-pdeptid="${t.department.parentDepartment.id!''}" data-deptid="${t.department.id}" data-target="#accept-modal">Accept</button>
                                        <button class="btn btn-icon btn-danger" id="rejectBtn" data-value="${t.username}" name="result" value="reject">Reject</button>
                                    </form>
                                <#elseif t.userStatus?has_content && t.userStatus == 'RETIREE'>
                                    <span class="badge badge-info text-2x">퇴사자</span>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="11" class="text-center">등록된 데이터가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

    <!-- Final Accept Modal -->
    <div class="modal fade" id="accept-modal" role="dialog" tabindex="-1" aria-labelledby="accept-modal"
         aria-hidden="true">
        <div class="modal-dialog modal-fullsize">
            <div class="modal-content">
                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title">Accept Check</h4>
                </div>
                <form class="form-horizontal" method="POST">
                    <input name="id" id="userId" type="hidden" />
                    <!--Modal body-->
                    <div class="modal-body">
                        <div class="panel panel-bordered-primary panel-primary">
                            <div class="panel-heading">
                                <h4 class="text-light userTitle" style="margin:0 0; padding:7px 0 0 7px;"></h4>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <@spring.formHiddenInput "account.department" "class='form-control'"/>
                                    <label class="control-label">Department / Team* : </label>
                                    <div class="form-group">
                                        <div class="col-sm-6" style="padding-left: 0px">
                                            <select id="deptSelect" class="form-control">
                                                <#list departments as d>
                                                    <option value="${d.id}">${d.name}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        <div class="col-sm-6" style="padding-right: 0px">
                                            <select id="teamSelect" class="form-control" disabled></select>
                                        </div>
                                    </div>
                                    <label class="control-label">Job Descriptions</label>
                                    <div class="form-group">
                                    <@spring.formMultiSelect "account.jdIds" jobDescriptionMap "class='form-control'" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Modal footer-->
                    <div class="modal-footer">
                        <button class="btn btn-icon btn-info" name="result" value="accept">Accept</button>
                        <button data-dismiss="modal" class="btn btn-default" id="closeBtn" type="button">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- End Final Accept Modal-->

</div><!-- end panel-->
</body>
</html>
