<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">

<@pagination.bind auditList/>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center" style="width:80px">Rev No.</th>
            <th class="text-center">Revision Date</th>
            <th class="text-center">Revision Type</th>
            <th class="text-center">ID</th>
            <th class="text-center">ISO Id</th>
            <th class="text-center">User Id</th>
            <th class="text-center">Status</th>
            <th class="text-center">Training Type</th>
            <th class="text-center">Offline</th>
            <th class="text-center">Organization</th>
            <th class="text-center">Complete Date</th>
            <th class="text-center">Percent (%)</th>
            <th class="text-center">Training Time (Sec)</th>
            <th class="text-center">Last Page No.</th>
            <th class="text-center">Score</th>
        </tr>
        </thead>
        <tbody>
        <#if auditList.content?has_content>
            <#list auditList.content as audit>
                <tr>
                    <#assign new = ''>
                    <#if audit.revisionType == 'ADD'>
                        <#assign revType = 'info' />
                        <#assign new = 'text-info text-bold bg-trans-dark'>
                    <#elseif audit.revisionType == 'MOD'>
                        <#assign revType = 'warning' />
                    <#elseif audit.revisionType == 'DEL'>
                        <#assign revType = 'danger' />
                    </#if>
                    <td class="text-center ${new}">${audit.revisionEntity.rev!''}</td>
                    <td class="text-center ${new}">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td class="text-center ${new}"><span class="label label-${revType}">${audit.revisionType}</span></td>
                    <td class="text-center ${new}">${audit.entity.id}</td>
                    <td class="text-center ${new}" style="${audit.contains('documentVersion')?then('color:red;', '')}">${audit.entity.iso.id!''}</td>
                    <td class="text-center ${new}" style="${audit.contains('user')?then('color:red;', '')}">${audit.entity.user.username!''}</td>
                    <td class="text-center ${new}" style="${audit.contains('status')?then('color:red;', '')}">
                        <span class="label ${new?has_content?then('label-info', audit.contains('status')?then('label-danger', 'bg-gray-dark'))}">
                            ${audit.entity.status}
                        </span>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('type')?then('color:red;', '')}">${audit.entity.type.label!''}</td>
                    <td class="text-center ${new}" style="${audit.contains('offlineTraining')?then('color:red;', '')}">${audit.entity.offlineTraining?has_content?then('Y', 'N')}</td>
                    <td class="text-center ${new}" style="${audit.contains('offlineTraining')?then('color:red;', '')}">${audit.entity.organization!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('completeDate')?then('color:red;', '')}">${audit.entity.completeDate?has_content?then(audit.entity.completeDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('progressPercent')?then('color:red;', '')}">${audit.entity.progressPercent!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('trainingTime')?then('color:red;', '')}">${audit.entity.trainingTime!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('lastPageNo')?then('color:red;', '')}">${audit.entity.lastPageNo!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('score')?then('color:red;', '')}">${audit.entity.score!'N/A'}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="15" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <div class="modal fade" id="content-modal" role="dialog" tabindex="-1" aria-labelledby="training-modal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title">Content Details</h4>
                </div>
                <!--Modal body-->
                <div class="modal-body">
                    <div id="content-html" style="max-height: 700px; overflow: auto;"></div>
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <div class="row">
                        <div class="col-xs-8 text-left">
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-info" data-dismiss="modal" type="button"><i class="fa fa-close"></i>Close</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        $("#content-modal").on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Button that triggered the modal
            let content = button.data('content');

            $("#content-html").html(content);
        });
    </script>

    <div class="modal fade" id="quiz-modal" role="dialog" tabindex="-1" aria-labelledby="training-modal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title">Quiz Details</h4>
                </div>
                <!--Modal body-->
                <div class="modal-body">
                    <div id="quiz-content" style="max-height: 700px; overflow: auto;"></div>
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <div class="row">
                        <div class="col-xs-8 text-left">
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-info" data-dismiss="modal" type="button"><i class="fa fa-close"></i>Close</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script>
        $("#quiz-modal").on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Button that triggered the modal
            let quiz = button.data('quiz');

            let divStart = "<div>";
            let quizTable = "";

            $.each(quiz, function(idx, questions){
                $.each(questions, function(idx, question){
                    quizTable += "<table class='table table-striped'><thead>";
                    quizTable += "<tr><th colspan='2'>Q" + (idx+1)+ ".&nbsp;" + question.text + "</th></tr>";
                    quizTable += "</thead><tbody>";
                    $.each(question.answers, function(idx, answer){
                        quizTable += "<tr><td class='text-right' style='width: 50px;'>"
                        if(answer.correct) {
                            quizTable += "<i class='fa fa-check-circle'></i>&nbsp;";
                        }
                        quizTable += answer.index + ". </td><td>";
                        quizTable += answer.text;
                        quizTable += "</td></tr></tbody>";
                    })

                    quizTable += "</table>";
                });
            });
            let divEnd = "</div>";
            let quizHtml = divStart + quizTable + divEnd;

            $("#quiz-content").html(quizHtml);
        });

    </script>
</div>
<@pagination.default />