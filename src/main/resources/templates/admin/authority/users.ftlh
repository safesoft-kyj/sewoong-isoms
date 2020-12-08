<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > Authority Management > 사용자 관리</title>
    <script>
        //Training 진행 여부
        $(document).ready(function() {
           $("button[name='trainingBtn']").click(function() {
               $.ajax({
                   url:'/admin/authority/users/'+$(this).data("id")+'/training',
                   method:'post',
                   data:{"_method":"put",r:Math.random()},
                   success:function(res) {
                       if(res.result == 'success') {
                           $("#trainingBtn" + res.id).removeClass('btn-' + (res.className == 'default' ? 'success' : 'default'));
                           $("#trainingBtn" + res.id).addClass('btn-' + res.className);

                           $.niftyNoty({
                               type: 'info',
                               container: 'floating',
                               message:'['+res.name+'] 교육 진행 상태가 변경 되었습니다.',
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
           $("button[name='receiveEmailBtn']").click(function() {
               $.ajax({
                   url:'/admin/authority/users/'+$(this).data("id")+'/receiveEmail',
                   method:'post',
                   data:{"_method":"put",r:Math.random()},
                   success:function(res) {
                       if(res.result == 'success') {
                           $("#receiveEmailBtn" + res.id).removeClass('btn-' + (res.className == 'default' ? 'success' : 'default'));
                           $("#receiveEmailBtn" + res.id).addClass('btn-' + res.className);

                           $.niftyNoty({
                               type: 'info',
                               container: 'floating',
                               message:'['+res.name+'] 이메일 수신 상태가 변경 되었습니다.',
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
        });
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
                    <th class="text-center"></th>
                </tr>
                </thead>
                <tbody>
                <#if users.content?has_content>
                    <#list users.content as t>
                        <tr>
                            <td class="text-center">${(users.totalElements - (users.size * users.number)) - t?index}</td>
                            <td class="text-center">${t.username}</td>
                            <td class="text-center">${t.orgDepart!''}</td>
                            <td class="text-center">${t.orgTeam!''}</td>
                            <td class="text-center">${t.name}</td>
                            <td class="text-center">${t.birthDate?string('yyyy-MM-dd')}</td>
                            <td class="text-center">${t.phone}</td>
                            <td class="text-center">${t.email}</td>
                            <td class="text-center"><button class="btn btn-icon btn-${t.training?then('success', 'default')} btn-circle" data-id="${t.id}" name="trainingBtn" id="trainingBtn${t.id}"><i class="fa fa-check"></i></button></td>
                            <td class="text-center"><button class="btn btn-icon btn-${t.receiveEmail?then('success', 'default')} btn-circle" data-id="${t.id}" name="receiveEmailBtn" id="receiveEmailBtn${t.id}"><i class="fa fa-check"></i></button></td>
                            <td class="text-center">
                                <#if t.userStatus?has_content && t.userStatus == 'ACTIVE'>
                                    <form action="pwReset" method="POST">
                                        <a href="/admin/authority/users/${t.id}" class="btn btn-icon btn-info">Edit</a>
                                        <button class="btn btn-icon btn-danger">Password Reset</button>
                                    </form>
                                <#elseif t.userStatus?has_content && t.userStatus == 'SIGNUP_REQUEST'>
                                    <form class="form-horizontal" method="POST">
                                        <input name="id" type="hidden" value="${t.id}" />
                                        <button class="btn btn-icon btn-info" name="result" value="accept">Accept</button>
                                        <button class="btn btn-icon btn-danger" name="result" value="reject">Reject</button>
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
</div><!-- end panel-->
</body>
</html>
