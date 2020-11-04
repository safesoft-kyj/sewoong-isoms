<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오전 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="isAuthenticated()">
    <div id="mainnav-profile" class="mainnav-profile">
        <div class="profile-wrap text-center">
                <%--            <div class="pad-btm">--%>
                <%--                <img class="img-circle img-md" src="/static/img/profile-photos/1.png" alt="Profile Picture">--%>
                <%--            </div>--%>
                <%--            <a href="#profile-nav" class="box-block" data-toggle="collapse" aria-expanded="false">--%>
                <%--                                        <span class="pull-right dropdown-toggle">--%>
                <%--                                            <i class="dropdown-caret"></i>--%>
                <%--                                        </span>--%>
            <sec:authentication property="principal.orgDepart" var="deptName"/>
            <sec:authentication property="principal.orgTeam" var="teamName"/>
            <p class="mnp-name"><sec:authentication property="principal.name"/></p>
                <p class="text-sm">
                    (${teamName})
                </p>
            <sec:authentication property="principal.disclosureStartDate" var="disclosureStartDate"/>
            <sec:authentication property="principal.disclosureEndDate" var="disclosureEndDate"/>
                    <fmt:setLocale value="en_US"/>
            <span class="mnp-desc"><i class="fa fa-unlock-alt"></i> <fmt:formatDate value="${disclosureStartDate}" pattern="dd/MMM/yyy" timeZone=""/>~<fmt:formatDate value="${disclosureEndDate}" pattern="dd/MMM/yyy"/></span>
            <hr/>
                <%--            </a>--%>
        </div>
            <%--        <div id="profile-nav" class="collapse list-group bg-trans">--%>
            <%--                &lt;%&ndash;<#--<a href="#" class="list-group-item">-->&ndash;%&gt;--%>
            <%--                &lt;%&ndash;<#--<i class="pli-male icon-lg icon-fw"></i> View Profile-->&ndash;%&gt;--%>
            <%--                &lt;%&ndash;<#--</a>-->&ndash;%&gt;--%>
            <%--                &lt;%&ndash;<#--<a href="#" class="list-group-item">-->&ndash;%&gt;--%>
            <%--                &lt;%&ndash;<#--<i class="pli-gear icon-lg icon-fw"></i> Settings-->&ndash;%&gt;--%>
            <%--                &lt;%&ndash;<#--</a>-->&ndash;%&gt;--%>
            <%--            <a href="#" class="list-group-item">--%>
            <%--                <i class="pli-information icon-lg icon-fw"></i> Help--%>
            <%--            </a>--%>
            <%--            <a href="/logout" class="list-group-item">--%>
            <%--                <i class="pli-unlock icon-lg icon-fw"></i> Logout--%>
            <%--            </a>--%>
            <%--        </div>--%>
    </div>
</sec:authorize>
