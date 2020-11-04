<#--<#import "/common/pagination.ftl" as pagination />-->
<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->
<#--<div class="panel">-->
<#--    <div class="panel-heading">-->
<#--        <h3 class="panel-title">My Training(트레이닝이 필요한 SOP만 보여주기)-->
<#--            <@security.authentication property="principal.inDate" var="inDate"/>-->
<#--        </h3>-->
<#--    </div>-->

<#--    <!--Data Table&ndash;&gt;-->
<#--    <!--===================================================&ndash;&gt;-->
<#--    <@pagination.bind trainingMatrix/>-->
<#--    <div class="panel-body">-->
<#--        <div class="table-responsive">-->
<#--            <table class="table table-striped table-bordered">-->
<#--                <thead>-->
<#--                <tr>-->
<#--                    <th class="text-center">No.</th>-->
<#--                    <th>Document ID</th>-->
<#--                    <th>Title</th>-->
<#--                    <th>Version</th>-->
<#--                    <th>Effective Date</th>-->
<#--                    <th>Training period</th>-->
<#--                    <th>Result</th>-->
<#--                    <th>Complete date</th>-->
<#--                    <th>#Actions</th>-->
<#--                </tr>-->
<#--                </thead>-->
<#--                <tbody>-->
<#--                <#if trainingMatrix.content?has_content>-->
<#--                    <#list trainingMatrix.content as t>-->
<#--                        <tr>-->
<#--                            <td class="text-center">${(trainingMatrix.totalElements - (trainingMatrix.size * trainingMatrix.number)) - t?index}</td>-->
<#--                            <td>${t.document.docId}</td>-->
<#--                            <td>${t.document.title}</td>-->
<#--                            <td>${t.documentVersion.version}</td>-->
<#--                            <td>${t.documentVersion.effectiveDate?string('yyyy.MM.dd')}</td>-->
<#--                            <td>-->
<#--                                ${t.startDate?string('yyyy.MM.dd')}~${t.endDate?string('yyyy.MM.dd')}-->

<#--                            </td>-->
<#--                            <td>-->
<#--                                <#if t.trainingPeriod.endDate?datetime lt baseDate?datetime>-->
<#--                                    <#if t.trainingLog?has_content>-->
<#--                                    <#else>-->
<#--                                        보고서 작성 하세요!-->
<#--                                    </#if>-->
<#--                                </#if>-->
<#--                            </td>-->
<#--                            <td>-</td>-->
<#--                            <td>-->
<#--                                &lt;#&ndash;                            <#if diff == -1>&ndash;&gt;-->
<#--                                &lt;#&ndash;                                <button class="btn btn-warning btn-xs" type="button"><i class="fa fa-pencil"></i> Deviation Report 작성</button>&ndash;&gt;-->
<#--                                &lt;#&ndash;                            <#else>&ndash;&gt;-->
<#--                                <button class="btn btn-info btn-xs" type="button" href="#training-modal" data-toggle="modal" data-title="${t.document.title} v${t.documentVersion.version}" data-id="${t.documentVersion.id}" data-total="${(t.documentVersion.totalPage?has_content)?then(t.documentVersion.totalPage, 0)}"><i class="fa fa-search"></i> Training</button>-->
<#--                                &lt;#&ndash;                            </#if>&ndash;&gt;-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                    </#list>-->
<#--                <#else>-->
<#--                    <tr>-->
<#--                        <td colspan="9" class="text-center">모든 교육을 완료 하였습니다.</td>-->
<#--                    </tr>-->
<#--                </#if>-->
<#--                </tbody>-->
<#--            </table>-->
<#--        </div>-->
<#--    </div>-->
<#--    <!--===================================================&ndash;&gt;-->
<#--    <!--End Data Table&ndash;&gt;-->

<#--</div>-->

<#--<div class="modal fade" id="training-modal" role="dialog" tabindex="-1" aria-labelledby="training-modal" aria-hidden="true">-->
<#--    <div class="modal-dialog modal-lg">-->
<#--        <div class="modal-content">-->

<#--            <!--Modal header&ndash;&gt;-->
<#--            <div class="modal-header">-->
<#--                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>-->
<#--                <h4 class="modal-title">SOP Self Training : <span id="sopInfo"></span> </h4>-->
<#--            </div>-->

<#--            <!--Modal body&ndash;&gt;-->
<#--            <div class="modal-body">-->
<#--                <ul id="document-files">-->
<#--                </ul>-->
<#--            </div>-->

<#--            <!--Modal footer&ndash;&gt;-->
<#--            <div class="modal-footer">-->
<#--                <div class="row">-->
<#--                    <div class="col-xs-6 text-left">-->
<#--                        <i class="fa fa-clock-o"></i> 경과 시간 : <span id="timer"></span>-->
<#--                        진행율:<span id="progressPercent"></span>-->
<#--                    </div>-->
<#--                    <div class="col-xs-6">-->

<#--                        <button class="btn btn-primary" id="prevBtn"><i class="fa fa-caret-left"></i> Prev</button>-->
<#--                        <span id="pageInfo" class="text-bold"></span>-->
<#--                        <button class="btn btn-primary" id="nextBtn">Next <i class="fa fa-caret-right"></i></button>-->
<#--                        <button class="btn btn-info" id="testBtn"><i class="fa fa-check-square-o"></i> Test</button>-->
<#--                        <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->

<#--<script>-->
<#--    var elapsedTime = 0;-->
<#--    var time = {h:0,m:0,s:0,total:0};-->
<#--    var intervalId;-->
<#--    var total = 0;-->
<#--    var current = 1;-->
<#--    var percent = 0;-->
<#--    $(document).ready(function() {-->
<#--        var modal;-->
<#--        var documentId;-->
<#--        $('#training-modal').on('show.bs.modal', function (event) {-->
<#--            current = 1;-->
<#--            time.h = 0;-->
<#--            time.m = 0;-->
<#--            time.s = 0;-->
<#--            percent = 0;-->

<#--            var button = $(event.relatedTarget); // Button that triggered the modal-->
<#--            documentId = button.data('id'); // Extract info from data-* attributes-->
<#--            total = parseInt(button.data('total')); // Extract info from data-* attributes-->
<#--            modal = $(this);-->
<#--            modal.find('.modal-body').html("<img src='/sop/CONFIDENTIAL/viewer/" + documentId + "?page="+current+"' class='img-responsive'/>");-->

<#--            $("#sopInfo").text(button.data('title'));-->
<#--            intervalId = setInterval(trainingTime, 1000);-->
<#--            progress();-->
<#--            updatePageInfo();-->
<#--            changeButtonStatus();-->
<#--        });-->

<#--        $('#training-modal').on('hide.bs.modal', function(e) {-->
<#--            if(intervalId) {-->
<#--                if (!confirm('교육을 종료 하시겠습니까?')) {-->
<#--                    e.preventDefault();-->
<#--                } else {-->
<#--                    alert('교육시간 업데이트! 교육시간 : ' + time.total + "초");-->
<#--                    clearInterval(intervalId);-->
<#--                    intervalId = null;-->
<#--                }-->
<#--            }-->
<#--        });-->

<#--        $("#nextBtn").click(function() {-->
<#--            if(total > current) {-->
<#--                current ++;-->
<#--                modal.find('.modal-body').html("<img src='/sop/CONFIDENTIAL/viewer/" + documentId + "?page="+current+"' class='img-responsive'/>");-->
<#--                progress();-->

<#--                updatePageInfo();-->
<#--                changeButtonStatus();-->
<#--            }-->
<#--        });-->
<#--        $("#prevBtn").click(function() {-->
<#--            if(current > 1) {-->
<#--                current --;-->
<#--                modal.find('.modal-body').html("<img src='/sop/CONFIDENTIAL/viewer/" + documentId + "?page="+current+"' class='img-responsive'/>");-->

<#--                updatePageInfo();-->
<#--                changeButtonStatus();-->
<#--            }-->
<#--        });-->

<#--        $("#testBtn").click(function() {-->
<#--            if(intervalId) {-->
<#--                clearInterval(intervalId);-->
<#--                intervalId = null;-->
<#--            }-->

<#--            alert('문제를 풀어 볼까요?');-->
<#--        });-->
<#--    });-->

<#--    function trainingTime() {-->
<#--        time.s ++;-->
<#--        time.total ++;-->
<#--        if(time.s >= 60) {-->
<#--            time.m ++;-->
<#--            time.s = 0;-->
<#--        }-->

<#--        if(time.m >= 60) {-->
<#--            time.h ++;-->
<#--            time.m = 0;-->
<#--        }-->

<#--        var viewTime = "";-->
<#--        if(time.h > 0) {-->
<#--            viewTime = time.h + "시간 ";-->
<#--        }-->

<#--        if(time.h != 0 || time.m > 0) {-->
<#--            viewTime += time.m + "분 ";-->
<#--        }-->
<#--        viewTime += time.s + "초";-->

<#--        $("#timer").text(viewTime);-->
<#--    }-->

<#--    function progress() {-->
<#--        var per = (current / total) * 100;-->
<#--        if(percent < per) {-->
<#--            percent = per;-->
<#--        }-->
<#--        $("#progressPercent").text(percent + "%");-->

<#--        if(per == 100) {-->
<#--            $("#testBtn").show();-->
<#--        } else {-->
<#--            $("#testBtn").hide();-->
<#--        }-->
<#--    }-->

<#--    function updatePageInfo() {-->
<#--        $("#pageInfo").text(current + "/"+total);-->
<#--    }-->

<#--    function changeButtonStatus() {-->
<#--        if(total > current) {-->
<#--            $("#nextBtn").removeAttr("disabled");-->
<#--        } else {-->
<#--            $("#nextBtn").attr("disabled", "disabled");-->
<#--        }-->

<#--        if(current > 1) {-->
<#--            $("#prevBtn").removeAttr("disabled");-->
<#--        } else {-->
<#--            $("#prevBtn").attr("disabled", "disabled");-->
<#--        }-->
<#--    }-->
<#--</script>-->