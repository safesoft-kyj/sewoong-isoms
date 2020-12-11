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
            <sec:authentication property="principal.deptName" var="deptName"/>
            <sec:authentication property="principal.teamName" var="teamName"/>
            <sec:authentication property="principal.commaJobTitle" var="authorities"/>
            <p class="mnp-name"><sec:authentication property="principal.name"/></p>
            <c:if test="${not empty deptName or not empty teamName}">
                <p class="text-sm">
                    (<c:if test="${not empty teamName}">${teamName}/</c:if>
                    <c:if test="${not empty deptName}">${deptName}</c:if>)
                </p>
            </c:if>
            <c:if test="${not empty authorities}"><p><span class="label label-primary">${authorities}</span></p></c:if>
<%--            <c:if test="${not empty jobTitle}"><p><span class="label label-primary">${jobTitle}</span></p></c:if>--%>
                <%--                <p class="mnp-desc"><sec:authentication property="principal.name"/></p>--%>
            <sec:authentication property="principal.loginDate" var="loginDate"/>
            <span class="mnp-desc">Login date : <fmt:formatDate value="${loginDate}" pattern="MM/dd hh:mm:ss"/></span>
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
