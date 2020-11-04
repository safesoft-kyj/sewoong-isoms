<div class="panel">
    <div class="panel-body">
        <div class="fixed-fluid">
            <div class="fixed-sm-200 pull-sm-left fixed-right-border">

<#--                <div class="pad-btm bord-btm">-->
<#--                    <a href="mailbox-compose.html" class="btn btn-block btn-success">Compose Mail</a>-->
<#--                </div>-->

                <p class="pad-hor mar-top text-main text-bold text-sm text-uppercase">Category</p>
                <div class="list-group bg-trans pad-btm bord-btm">
                    <a href="#" class="list-group-item mail-nav-unread">
                        <i class="pli-mail-unread icon-lg icon-fw"></i> 전체 (73)
                    </a>
                    <a href="#" class="list-group-item">
                        <i class="pli-pen-5 icon-lg icon-fw"></i> SOP
                    </a>
                    <a href="#" class="list-group-item">
                        <i class="pli-mail-send icon-lg icon-fw"></i> 전자결재
                    </a>
                </div>

<#--                <div class="list-group bg-trans">-->
<#--                    <a href="#" class="list-group-item"><i class="pli-male-female icon-lg icon-fw"></i> Address Book</a>-->
<#--                    <a href="#" class="list-group-item"><i class="pli-folder-with-document icon-lg icon-fw"></i> User Folders</a>-->
<#--                </div>-->

                <!-- Friends -->
<#--                <div class="list-group bg-trans pad-ver bord-ver">-->
<#--                    <p class="pad-hor mar-top text-main text-bold text-sm text-uppercase">Friends</p>-->

<#--                    <!-- Menu list item &ndash;&gt;-->
<#--                    <a href="#" class="list-group-item list-item-sm">-->
<#--                        <span class="badge badge-purple badge-icon badge-fw pull-left"></span>-->
<#--                        Joey K. Greyson-->
<#--                    </a>-->
<#--                    <a href="#" class="list-group-item list-item-sm">-->
<#--                        <span class="badge badge-info badge-icon badge-fw pull-left"></span>-->
<#--                        Andrea Branden-->
<#--                    </a>-->
<#--                    <a href="#" class="list-group-item list-item-sm">-->
<#--                        <span class="badge badge-pink badge-icon badge-fw pull-left"></span>-->
<#--                        Lucy Moon-->
<#--                    </a>-->
<#--                    <a href="#" class="list-group-item list-item-sm">-->
<#--                        <span class="badge badge-success badge-icon badge-fw pull-left"></span>-->
<#--                        Johny Juan-->
<#--                    </a>-->
<#--                    <a href="#" class="list-group-item list-item-sm">-->
<#--                        <span class="badge badge-danger badge-icon badge-fw pull-left"></span>-->
<#--                        Susan Sun-->
<#--                    </a>-->
<#--                </div>-->

<#--                <p class="pad-hor mar-top text-main text-bold text-sm text-uppercase">Labels</p>-->
<#--                <ul class="list-inline mar-hor">-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Family</a>-->
<#--                    </li>-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Home</a>-->
<#--                    </li>-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Work</a>-->
<#--                    </li>-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Film</a>-->
<#--                    </li>-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Music</a>-->
<#--                    </li>-->
<#--                    <li class="tag tag-xs">-->
<#--                        <a href="#"><i class="pli-tag"></i> Photography</a>-->
<#--                    </li>-->
<#--                </ul>-->

            </div>
            <div class="fluid">
                <div id="email-list">
                    <div class="row">
                        <div class="col-sm-7 toolbar-left">

                            <!-- Mail toolbar -->
                            <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

                            <!--Split button dropdowns-->
                            <div class="btn-group">
                                <label id="checked-all-mail" for="select-all-mail" class="btn btn-default">
                                    <input id="select-all-mail" class="magic-checkbox" type="checkbox">
                                    <label for="select-all-mail"></label>
                                </label>
                                <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"><i class="dropdown-caret"></i></button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" id="select-all-list">All</a></li>
                                    <li><a href="#" id="select-none-list">None</a></li>
                                    <li><a href="#" id="select-toggle-list">Toggle</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#" id="select-read-list">Read</a></li>
                                    <li><a href="#" id="select-unread-list">Unread</a></li>
                                    <li><a href="#" id="select-starred-list">Starred</a></li>
                                </ul>
                            </div>

                            <!--Refresh button-->
                            <button id="mail-ref-btn" data-toggle="panel-overlay" data-target="#email-list" class="btn btn-default" type="button">
                                <i class="psi-repeat-2"></i>
                            </button>

                            <!--Dropdown button (More Action)-->
                            <div class="btn-group dropdown">
                                <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
                                    More <i class="dropdown-caret"></i>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Mark as read</a></li>
                                    <li><a href="#">Mark as unread</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#">Star</a></li>
                                    <li><a href="#">Clear Star</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-5 toolbar-right">
                            <!--Pager buttons-->
                            <span class="text-main">
					                                <strong>1-50</strong>
					                                of
					                                <strong>160</strong>
					                            </span>
                            <div class="btn-group btn-group">
                                <button class="btn btn-default" type="button">
                                    <i class="psi-arrow-left"></i>
                                </button>
                                <button class="btn btn-default" type="button">
                                    <i class="psi-arrow-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>

                    <!--Mail list group-->
                    <ul id="mail-list" class="mail-list pad-top bord-top">

                        <!--Mail list item-->
                        <li class="mail-list-unread mail-attach">
                            <div class="mail-control">
                                <input id="email-list-1" class="magic-checkbox" type="checkbox">
                                <label for="email-list-1"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Michael Robert</a></div>
                            <div class="mail-time">05:55 PM</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">This is an example if there is a really really long text. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, </a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-starred">
                            <div class="mail-control">
                                <input id="email-list-2" class="magic-checkbox" type="checkbox">
                                <label for="email-list-2"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Shopping Mall</a></div>
                            <div class="mail-time">10:45 AM</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">Tracking Your Order - Shoes Store Online</a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-list-unread mail-starred mail-attach">
                            <div class="mail-control">
                                <input id="email-list-3" class="magic-checkbox" type="checkbox">
                                <label for="email-list-3"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Dropbox</a></div>
                            <div class="mail-time">07:18 AM</div>
                            <div class="mail-attach-icon"><i class="psi-paperclip"></i></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">Reset your account password</a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-list-unread">
                            <div class="mail-control">
                                <input id="email-list-4" class="magic-checkbox" type="checkbox">
                                <label for="email-list-4"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Server Host</a></div>
                            <div class="mail-time">01:51 PM</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">
					                                <span class="label label-danger">
					                                Bussines
					                                </span>
                                    Regarding to your website issues.
                                </a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li>
                            <div class="mail-control">
                                <input id="email-list-5" class="magic-checkbox" type="checkbox">
                                <label for="email-list-5"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Lisa D. Smith</a></div>
                            <div class="mail-time">Yesterday</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">Hi John! How are you?</a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-starred">
                            <div class="mail-control">
                                <input id="email-list-6" class="magic-checkbox" type="checkbox">
                                <label for="email-list-6"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Johny Juan</a></div>
                            <div class="mail-time">Yesterday</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">
					                                <span class="label label-info">
					                                Partner
					                                </span>
                                    Repair Status Unregistered User
                                </a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-attach">
                            <div class="mail-control">
                                <input id="email-list-7" class="magic-checkbox" type="checkbox">
                                <label for="email-list-7"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Bobby Marz</a></div>
                            <div class="mail-time">Oct 10</div>
                            <div class="mail-attach-icon"><i class="psi-paperclip"></i></div>
                            <div class="mail-subject"><a href="mailbox-message.html">Bugs in your system.</a></div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-list-unread">
                            <div class="mail-control">
                                <input id="email-list-8" class="magic-checkbox" type="checkbox">
                                <label for="email-list-8"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Michael Robert</a></div>
                            <div class="mail-time">Oct 10</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject"><a href="mailbox-message.html">This is an example if there is a really really long text. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, </a></div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-starred mail-attach">
                            <div class="mail-control">
                                <input id="email-list-9" class="magic-checkbox" type="checkbox">
                                <label for="email-list-9"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Shopping Mall</a></div>
                            <div class="mail-time">Oct 9</div>
                            <div class="mail-attach-icon"><i class="psi-paperclip"></i></div>
                            <div class="mail-subject"><a href="mailbox-message.html">Tracking Your Order - Shoes Store Online</a></div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-list-unread mail-starred">
                            <div class="mail-control">
                                <input id="email-list-10" class="magic-checkbox" type="checkbox">
                                <label for="email-list-10"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Dropbox</a></div>
                            <div class="mail-time">Oct 8</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject"><a href="mailbox-message.html">Reset your account password</a></div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-list-unread">
                            <div class="mail-control">
                                <input id="email-list-11" class="magic-checkbox" type="checkbox">
                                <label for="email-list-11"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Server Host</a></div>
                            <div class="mail-time">Oct 7</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">
					                                <span class="label label-danger">
					                                    Bussines
					                                </span>
                                    Regarding to your website issues.
                                </a>
                            </div>
                        </li>

                        <!--Mail list item-->
                        <li class="mail-starred">
                            <div class="mail-control">
                                <input id="email-list-12" class="magic-checkbox" type="checkbox">
                                <label for="email-list-12"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Lisa D. Smith</a></div>
                            <div class="mail-time">Oct 5</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject"><a href="mailbox-message.html">Hi John! How are you?</a></div>
                        </li>


                        <!--Mail list item-->
                        <li class="mail-starred">
                            <div class="mail-control">
                                <input id="email-list-13" class="magic-checkbox" type="checkbox">
                                <label for="email-list-13"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Johny Juan</a></div>
                            <div class="mail-time">Oct 5</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject">
                                <a href="mailbox-message.html">
					                                <span class="label label-info">
					                                    Partner
					                                </span>
                                    Repair Status Unregistered User
                                </a>
                            </div>
                        </li>


                        <!--Mail list item-->
                        <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
                        <li class="mail-attach">
                            <div class="mail-control">
                                <input id="email-list-14" class="magic-checkbox" type="checkbox">
                                <label for="email-list-14"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Bobby Marz</a></div>
                            <div class="mail-time">Oct 3</div>
                            <div class="mail-attach-icon"><i class="psi-paperclip"></i></div>
                            <div class="mail-subject"><a href="mailbox-message.html">Bugs in your system.</a></div>
                        </li>


                        <!--Mail list item-->
                        <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
                        <li class="mail-list-unread mail-starred">
                            <div class="mail-control">
                                <input id="email-list-15" class="magic-checkbox" type="checkbox">
                                <label for="email-list-15"></label>
                            </div>
                            <div class="mail-star"><a href="#"><i class="psi-star"></i></a></div>
                            <div class="mail-from"><a href="#">Lucy Moon</a></div>
                            <div class="mail-time">Oct 1</div>
                            <div class="mail-attach-icon"></div>
                            <div class="mail-subject"><a href="mailbox-message.html">We need to meet up soon</a></div>
                        </li>
                    </ul>
                </div>


                <!--Mail footer-->
                <div class="panel-footer clearfix">
                    <div class="pull-right">
                        <span class="text-main"><strong>1-50</strong> of <strong>160</strong></span>
                        <div class="btn-group btn-group">
                            <button type="button" class="btn btn-default">
                                <i class="psi-arrow-left"></i>
                            </button>
                            <button type="button" class="btn btn-default">
                                <i class="psi-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>