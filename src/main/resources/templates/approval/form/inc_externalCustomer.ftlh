<#import "/spring.ftl" as spring />
<#if approval.sopDisclosureRequestForm.externalCustomers?has_content>
    <#list approval.sopDisclosureRequestForm.externalCustomers as c>
        <tr>
            <td>${c?index + 1}</td>
            <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[${c?index}].name" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[${c?index}].email" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[${c?index}].jobTitle" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td>
                <button type="button" class="btn btn-default" name="removeExternalCustomer"<#if c?index == 0> disabled="disabled"</#if> data-id="${c?index}"><i class="fa fa-times"></i></button>
            </td>
        </tr>
    </#list>
<#else>
    <tr>
        <td>1</td>
        <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[0].name" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[0].email" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopDisclosureRequestForm.externalCustomers[0].jobTitle" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><button type="button" class="btn btn-default" name="removeExternalCustomer" disabled="disabled" data-id="0"><i class="fa fa-times"></i></button></td>
    </tr>
</#if>
<tr>
    <td colspan="5"><button class="btn btn-block btn-primary" name="addExternalCustomer" type="button"><i class="fa fa-plus"></i> Add</button></td>
</tr>