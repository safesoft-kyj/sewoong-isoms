<#import "/spring.ftl" as spring />

    <input type="hidden" name="trainingLogId" id="testTrainingLogId">
<#list quiz.quizQuestions as q>
    <#if q?is_first == false>
        <hr/>
    </#if>
    <div class="panel">
        <!-- //c * 1024 //-->
<#--        <div class="panel-heading" data-pos="${q.hashCode()},${q.index}<#list q.correct as c>,${c * 1024}</#list>">-->
<#--            <p class="panel-title">${q?index + 1}. ${q.text}</p>-->
<#--        </div>-->
        <div class="panel-body pad-no" id="answerDiv${q?index}">
            <!--Paragraph-->
            <!--===================================================-->
            <p class="text-main text-semibold">${q?index + 1}. ${q.text}</p>
            <div style="margin-left: 20px;">
            <#list q.answers as a>
            <p><label class="mar-no">
                    <@spring.formCheckbox "quiz.quizQuestions[" + q?index +"].answers["+a?index+"].correct"/> ${a.text}
                </label></p>
                <@spring.formHiddenInput "quiz.quizQuestions[" + q?index +"].answers["+a?index+"].index"/>
            </#list>
            <!--===================================================-->
            </div>
        </div>
    </div>
</#list>
<#--</form>-->