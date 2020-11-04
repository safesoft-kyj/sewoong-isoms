<#import "/spring.ftl" as spring />
<#list trainingTestLogs as test>
<#list test.quiz.quizQuestions as q>
    <br/>
    <div class="panel panel-default panel-bordered">
        <!-- //c * 1024 //-->
        <div class="panel-heading" data-pos="${q.hashCode()},${q.index}<#list q.correct as c>,${c * 1024}</#list>">
            <h6 class="panel-title" style="font-size: 1.1em !important;">${q?index + 1}. ${q.text}</h6>
        </div>
        <div class="panel-body pad-hor" style="padding-bottom: 0px;" id="answerDiv${q?index}">
            <!--Paragraph-->
            <!--===================================================-->
            <#--            <p class="text-main text-semibold">${q?index + 1}. ${q.text}</p>-->
            <#--            <hr/>-->
            <#list q.answers as a>
                <p><label class="mar-no">
                        ${a.text}
                    </label></p>
            </#list>
            <!--===================================================-->
        </div>
    </div>
</#list>
<#--</form>-->
</#list>