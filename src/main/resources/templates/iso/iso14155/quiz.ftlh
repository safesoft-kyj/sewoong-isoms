<#import "/spring.ftl" as spring />
<form method="post">
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-question-circle-o"></i> ${iso.title} 퀴즈 등록</h3>
        </div>
        <div class="panel-body">
            <div class="pad-btm form-inline">
                <button class="btn btn-primary" style="width:100%;" name="add" value="${quiz.quizQuestions?size}"><i class="pli-add"></i> Add</button>
            </div>
            <!--Validation States-->
            <!--===================================================-->
            <#list quiz.quizQuestions as q>
                <div class="form-group">
                    <div class="input-group">
                        <label class="control-label text-semibold">Q. ${q?index + 1}</label>
                        <div class="input-group-addon" style="border: 0px;">
                            <button class="btn btn-danger btn-xs mar-lft" type="submit" name="removeQuiz"
                                    value="${q?index}"<#if q?index lt 1 || !q?is_last> disabled</#if>><i
                                        class="fa fa-times"></i></button>
                        </div>
                    </div>
                    <@spring.formHiddenInput "quiz.quizQuestions[" + q?index + "].index"/>
                    <@spring.formTextarea "quiz.quizQuestions[" + q?index + "].text" "class='form-control'"/>
                    <@spring.showErrors "<br/>" "color:red;"/>
                </div>
                <div class="form-group">
                    <#list q.answers as answer>
                        <div class="input-group mar-lft">
                            <div class="input-group-addon">
                                ${answer?index + 1}.
                                <@spring.formHiddenInput "quiz.quizQuestions[" + q?index +"].answers["+answer?index+"].index"/>
                                <@spring.formCheckbox "quiz.quizQuestions[" + q?index +"].answers["+answer?index+"].correct"/>
                            </div>

                            <@spring.formInput "quiz.quizQuestions[" + q?index + "].answers["+answer?index+"].text" "class='form-control'"/>
                            <div class="input-group-addon">
                                <button class="btn btn-warning btn-xs" type="submit" name="remove"
                                        value="${q?index}.${answer?index}"<#if answer?index lt 2 || !answer?is_last> disabled</#if>>
                                    <i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <@spring.showErrors "" "color:red;"/>
                    </#list>
                </div>
                <!--===================================================-->
                <!--End Validation States-->
            </#list>
            <button class="btn btn-primary" style="width:100%;" name="add" value="${quiz.quizQuestions?size}"><i class="pli-add"></i> Add</button>
        </div>
        <div class="panel-footer text-right">
            <a class="btn btn-link" href="..">취소</a>
            <button class="btn btn-primary" type="submit">퀴즈 저장</button>
        </div>
    </div>
</form>