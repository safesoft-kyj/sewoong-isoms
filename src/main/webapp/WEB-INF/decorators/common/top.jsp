<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오전 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authentication property="principal" var="user"/>
<%--<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>--%>
<!--NAVBAR-->
<!--===================================================-->
<header id="navbar">
    <div id="navbar-container" class="boxed">
        <!--Brand logo & name-->
        <!--================================-->

        <div class="navbar-header">
            <a href="/" class="navbar-brand">
<%--                <img src="/static/img/logo.png" alt="Nifty Logo" class="brand-icon">--%>
<%--                <img src="/static/img/cau_logo.png" alt="Nifty Logo" class="brand-icon">--%>
                <div class="brand-title">
                    <span class="brand-text">ISO MS</span>
<%--                    <span class="brand-text">--%>
<%--                        <sec:authorize access="isAuthenticated()">--%>
<%--                            <sec:authentication var="companyId" property="principal.companyId" />--%>
<%--                            <c:if test="${empty companyId}">--%>
<%--                                &lt;%&ndash;                                ${company.name}&ndash;%&gt;--%>
<%--                                Dt&SanoMedics--%>
<%--                            </c:if>--%>
<%--                            <c:if test="${not empty companyId}">--%>
<%--                                Logo?--%>
<%--                            </c:if>--%>
<%--                        </sec:authorize>--%>
<%--                        <sec:authorize access="!isAuthenticated()">--%>
<%--                            Dt&SanoMedics--%>
<%--                        </sec:authorize>--%>
<%--                    </span>--%>
                </div>
            </a>
        </div>
        <!--================================-->
        <!--End brand logo & name-->


        <!--Navbar Dropdown-->
        <!--================================-->
        <div class="navbar-content clearfix">
            <ul class="nav navbar-top-links">

                <!--Navigation toogle button-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <li class="tgl-menu-btn">
                    <a class="mainnav-toggle" href="#">
                        <i class="pli-list-view icon-lg"></i>
                    </a>
                </li>
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--End Navigation toogle button-->


                <!--Search-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <li>
<%--                    <div class="custom-search-form">--%>
<%--                        <label class="btn btn-trans" for="search-input" data-toggle="collapse" data-target="#nav-searchbox">--%>
<%--                            <i class="pli-magnifi-glass"></i>--%>
<%--                        </label>--%>
<%--                        <form method="get" action="/studies">--%>
<%--                            <div class="search-container collapse" id="nav-searchbox">--%>
<%--                                <input id="search-input" name="q" type="text" class="form-control" placeholder="과제명 검색...">--%>
<%--                            </div>--%>
<%--                        </form>--%>
<%--                    </div>--%>
                </li>
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--End Search-->


            </ul>
            <ul class="nav navbar-top-links">


                <!--Mega dropdown-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--End mega dropdown-->


                <!--Notification dropdown-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <c:if test="${user.userType == 'U'}">
<%--                <li class="dropdown">--%>
<%--                    <a href="#" data-toggle="dropdown" class="dropdown-toggle">--%>
<%--                        <i class="pli-bell icon-lg"></i>--%>
<%--                        <span class="badge badge-header badge-danger"></span>--%>
<%--                    </a>--%>

<%--                    <!--Notification dropdown menu-->--%>

<%--                    <div class="dropdown-menu dropdown-menu-md dropdown-menu-right">--%>
<%--                        <div class="nano scrollable">--%>
<%--                            <div class="nano-content">--%>
<%--                                <ul class="head-list">--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#">--%>
<%--                                            <div class="clearfix">--%>
<%--                                                <p class="pull-left">Progressbar</p>--%>
<%--                                                <p class="pull-right">70%</p>--%>
<%--                                            </div>--%>
<%--                                            <div class="progress progress-sm">--%>
<%--                                                <div style="width: 70%;" class="progress-bar">--%>
<%--                                                    <span class="sr-only">70% Complete</span>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <div class="media-left">--%>
<%--                                                <i class="pli-hd icon-2x icon-lg"></i>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">With Icon</div>--%>
<%--                                                <small class="text-muted">15 minutes ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <div class="media-left">--%>
<%--                                                <i class="pli-power-cable icon-2x icon-lg"></i>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">With Icon</div>--%>
<%--                                                <small class="text-muted">15 minutes ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <div class="media-left">--%>

<%--                                                    <span class="icon-wrap icon-circle bg-primary">--%>
<%--                                                    <i class="pli-disk icon-lg icon-lg"></i>--%>
<%--                                                    </span>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">Circle Icon</div>--%>
<%--                                                <small class="text-muted">15 minutes ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <span class="badge badge-success pull-right">90%</span>--%>
<%--                                            <div class="media-left">--%>

<%--                                                    <span class="icon-wrap icon-circle bg-danger">--%>
<%--                                                        <i class="pli-mail-open icon-lg icon-lg"></i>--%>
<%--                                                    </span>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">Circle icon with badge</div>--%>
<%--                                                <small class="text-muted">50 minutes ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <div class="media-left">--%>

<%--                                                    <span class="icon-wrap bg-info">--%>
<%--                                                    <i class="pli-monitor-3 icon-lg icon-lg"></i>--%>
<%--                                                    </span>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">Square icon</div>--%>
<%--                                                <small class="text-muted">Last Update 8 hours ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>

<%--                                    <!-- Dropdown list-->--%>
<%--                                    <li>--%>
<%--                                        <a href="#" class="media">--%>
<%--                                            <span class="label label-danger pull-right">New</span>--%>
<%--                                            <div class="media-left">--%>

<%--                                                    <span class="icon-wrap bg-purple">--%>
<%--                                                    <i class="pli-paintbrush icon-lg icon-lg"></i>--%>
<%--                                                    </span>--%>
<%--                                            </div>--%>
<%--                                            <div class="media-body">--%>
<%--                                                <div class="text-nowrap">Square icon with label</div>--%>
<%--                                                <small class="text-muted">Last Update 8 hours ago</small>--%>
<%--                                            </div>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>
<%--                                </ul>--%>
<%--                            </div>--%>
<%--                        </div>--%>

<%--                        <!--Dropdown footer-->--%>
<%--                        <div class="pad-all bord-top">--%>
<%--                            <a href="#" class="btn-link text-main box-block">--%>
<%--                                <i class="pci-chevron chevron-right pull-right"></i>Show All Notifications--%>
<%--                            </a>--%>
<%--                        </div>--%>
<%--                    </div>--%>

<%--                </li>--%>
                </c:if>
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--End notifications dropdown-->

                <li class="dropdown">
                    <a href="#" data-toggle="dropdown" class="dropdown-toggle text-right">
                            <span class="ic-user pull-right">
                                <i class="fa fa-clock-o"></i>
                                <small class="text-xs text-semibold"><span id="time-min">10</span>:<span id="time-sec">00</span></small>
                            </span>
                    </a>


                    <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right panel-default">
                        <ul class="head-list">
                            <li>
                                <a href="#" onclick="resetTimer();"><i class="pli-clock-back icon-lg icon-fw"></i> Reset</a>
                            </li>
                        </ul>
                    </div>
                    <script>
                        dailyMissionTimer();
                    </script>
                </li>

                <!--User dropdown-->
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <li id="dropdown-user" class="dropdown">
                    <a href="#" data-toggle="dropdown" class="dropdown-toggle text-right">
                            <span class="ic-user pull-right">
                            <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                                <!--You can use an image instead of an icon.-->
                                <!--<img class="img-circle img-user media-object" src="/static/img/profile-photos/1.png" alt="Profile
                                Picture">-->
                                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                            <i class="pli-male"></i>
                            </span>
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                        <!--You can also display a user name in the navbar.-->
                        <!--<div class="username hidden-xs">Aaron Chavez</div>-->
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                    </a>
                    <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right panel-default">
                        <ul class="head-list">
                            <c:if test="${user.userType eq 'U'}">
                            <li>
                                <a href="/user/profile" data-toggle="modal"><i class="pli-male icon-lg icon-fw"></i> Profile</a>
                            </li>
                            </c:if>
                            <li>
                                <a href="/logout"><i class="fa fa-sign-out icon-lg icon-fw"></i> Sign-out</a>
                            </li>
                        </ul>
                    </div>
                </li>
                <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                <!--End user dropdown-->
                <sec:authorize access="hasAnyAuthority('ADMIN')">
                <li>
                    <a href="/admin/dashboard">
                        <i class="pli-gears-2 icon-lg"></i>
                    </a>
                </li>
                </sec:authorize>
            </ul>
        </div>
        <!--================================-->
        <!--End Navbar Dropdown-->

    </div>
</header>
<!--===================================================-->
<!--END NAVBAR-->
