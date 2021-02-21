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
            <th class="text-center">Title</th>
            <th class="text-center">Content</th>
            <th class="text-center">isoType</th>
            <th class="text-center">Attach Files</th>
            <th class="text-center">Training</th>
            <th class="text-center">Training Period</th>
            <th class="text-center">Quiz</th>
            <th class="text-center">Correct Count</th>
            <th class="text-center">Active</th>
            <th class="text-center">Training Matrix</th>
            <th class="text-center">topView End Data</th>
            <th class="text-center">Deleted</th>
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
                    <td class="text-center ${new}" style="${audit.contains('title')?then('color:red;', '')}">${audit.entity.title!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('content')?then('color:red;', '')}">
                        <#if audit.entity.content?has_content>
                            <button type="button" class="btn btn-xs ${new?has_content?then('btn-info', audit.contains('content')?then('btn-danger', 'bg-gray-dark'))}"
                                    data-content="${audit.entity.content}" data-toggle="modal" data-target="#content-modal">
                                <i class="fa fa-comments" style="font-size: 16px;"></i>
                            </button>
                        </#if>
                    </td>
                    <td class="text-center ${new}">
                        <span class="label ${new?has_content?then('label-info', audit.contains('isoType')?then('label-danger;', 'bg-gray-dark'))}">
                            ${audit.entity.isoType}
                        </span>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">
                        <#if audit.entity.attachFiles?has_content>
                            <#list audit.entity.attachFiles as file>
                                <i class="fa fa-file"></i>&nbsp;${file.originalFileName!'N/A'}<br />
                            </#list>
                        <#else>
                            N/A
                        </#if>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('training')?then('color:red;', '')}">${audit.entity.training?then('Y', 'N')}</td>
                    <td class="text-center ${new}" style="${audit.contains('isoTrainingPeriods')?then('color:red;', '')}">${audit.entity.isoTrainingPeriods?has_content?then(audit.entity.trainingDate, 'N/A')}</td>
                    <td class="text-center ${new}">
                        <#if audit.entity.quiz?has_content>
                            <button type="button" class="btn btn-xs ${new?has_content?then('btn-info', audit.contains('quiz')?then('btn-danger', 'bg-gray-dark'))}" data-quiz="${audit.entity.quiz}" data-toggle="modal" data-target="#quiz-modal">
                                <i class="pli-question-circle" style="font-size: 16px;"></i>
                            </button>
                        <#else>
                            N/A
                        </#if>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('correctCount')?then('color:red;', '')}">${audit.entity.correctCount!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('active')?then('color:red;', '')}">${audit.entity.active?then('Y', 'N')}</td>
                    <td class="text-center ${new}" ${audit.contains('isoTrainingMatrix')?then('color:red;', '')}>
                        <#if audit.entity.isoTrainingMatrix?has_content>
                            <#if audit.entity.attendee=='ALL'>
                                ${audit.entity.attendee}
                            <#else>
                                <span class="label ${audit.contains('isoTrainingMatrix')?then('label-danger', 'label-default')} add-popover"
                                      data-toggle="popover" data-container="body" data-placement="top"
                                      data-trigger="hover" data-html="true"
                                      data-content="<div><#list audit.entity.isoTrainingMatrix as m><i class='fa fa-check'> </i>${m.user.name}&nbsp;</#list></div>"
                                      style="max-width: 160px; cursor: pointer;">
                                    ${audit.entity.attendee}명
                                </span>
                            </#if>
                        </#if>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('topViewEndDate')?then('color:red;', '')}">${audit.entity.topViewEndDate?has_content?then(audit.entity.topViewEndDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('deleted')?then('color:red;', '')}">${audit.entity.deleted?then('Y', 'N')}</td>
<#--                    <td class="text-center ${new}">${audit.entity.document.id}</td>-->
<#--                    <td class="text-center ${new}">-->
<#--                        <span class="label ${new?has_content?then('label-info', audit.contains('status')?then('label-danger;', 'label-gray'))}">-->
<#--                            ${audit.entity.status}-->
<#--                        </span>-->
<#--                    </td>-->
<#--                    <td class="text-center ${new}" style="${audit.contains('version')?then('color:red;', '')}">${audit.entity.version!'N/A'}</td>-->
<#--                    <td class="text-center ${new}">-->
<#--                        <#if audit.entity.quiz?has_content>-->
<#--                            <button type="button" class="btn btn-sm ${audit.contains('quiz')?then('btn-danger', 'bg-gray-dark')}" data-quiz="${audit.entity.quiz}" data-toggle="modal" data-target="#quiz-modal">-->
<#--                                <i class="pli-question-circle" style="font-size: 16px;"></i>-->
<#--                            </button>-->
<#--                        </#if>-->
<#--                    </td>-->
<#--                    <td class="text-center ${new}" style="${audit.contains('effectiveDate')?then('color:red;', '')}">${audit.entity.effectiveDate?has_content?then(audit.entity.effectiveDate?string('yyyy-MM-dd'), 'N/A')}</td>-->
<#--                    <td class="text-center ${new}" style="${audit.contains('retirementDate')?then('color:red;', '')}">${audit.entity.retirementDate?has_content?then(audit.entity.retirementDate?string('yyyy-MM-dd'), 'N/A')}</td>-->

                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="12" class="text-center">Change History가 없습니다.</td>
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