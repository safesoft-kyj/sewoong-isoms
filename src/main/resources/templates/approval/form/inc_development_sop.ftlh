<#import "/spring.ftl" as spring />
<#if approval.sopRfRequestForm.sopDevelopmentDocs?has_content>
    <#list approval.sopRfRequestForm.sopDevelopmentDocs as c>
        <tr>
            <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[${c?index}].docId" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[${c?index}].title" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[${c?index}].version" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td>
                <button type="button" class="btn btn-default" data-type="sop" name="removeDevelopment"<#if c?index == 0> disabled="disabled"</#if> data-id="${c?index}"><i class="fa fa-times"></i></button>
            </td>
        </tr>
    </#list>
<#else>
    <tr>
        <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[0].docId" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[0].title" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopRfRequestForm.sopDevelopmentDocs[0].version" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><button type="button" class="btn btn-default" data-type="sop" name="removeDevelopment" disabled="disabled" data-id="0"><i class="fa fa-times"></i></button></td>
    </tr>
</#if>
<tr>
    <td colspan="5"><button class="btn btn-block btn-primary" data-type="sop" name="addDevelopment" type="button"><i class="fa fa-plus"></i> Add</button></td>
</tr>