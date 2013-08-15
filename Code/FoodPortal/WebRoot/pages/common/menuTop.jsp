<div class="yui3-g" id="bd">
    <div class="yui3-u-1" id="main">
       <div id="productsandservices" class="yui3-menu yui3-menu-horizontal">
            <div class="yui3-menu-content">
                <ul class="first-of-type">
                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://www.yahoo.com/">Home</a></li>
                    <li>
                        <a class="yui3-menu-label" href="#connect">Connect</a>
                        <div id="connect" class="yui3-menu">
                            <div class="yui3-menu-content">
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://developer.yahoo.com/">Developer Network</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://www.flickr.com">Flickr</a></li>
                                    <li>
                                        <a class="yui3-menu-label" href="#pim">PIM</a>
                                        <div id="pim" class="yui3-menu">
                                            <div class="yui3-menu-content">

                                                <ul>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://mail.yahoo.com">Mail</a></li>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://www.zimbra.com">Zimbra</a></li>
                                                </ul>

                                                <ul>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://addressbook.yahoo.com">Address Book</a></li>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://calendar.yahoo.com">Calendar</a></li>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://notepad.yahoo.com">Notepad</a></li>
                                                </ul>

                                                <ul>
                                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://messenger.yahoo.com">Messenger</a></li>
                                                </ul>

                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#find-info">Find Info</a>
                        <div id="find-info" class="yui3-menu">
                            <div class="yui3-menu-content">
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://answers.yahoo.com">Answers</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://babelfish.yahoo.com/">Babel Fish Translations</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://info.yahoo.com/">Company Info</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#lifestyles">Lifestyles</a>
                        <div id="lifestyles" class="yui3-menu">
                            <div class="yui3-menu-content">
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://autos.yahoo.com">Autos</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://food.yahoo.com">Food</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://green.yahoo.com">Green</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#new-finance">News &#38; Finance</a>
                        <div id="new-finance" class="yui3-menu">
                            <div class="yui3-menu-content">
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://buzz.yahoo.com/">Buzz</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://finance.yahoo.com">Finance</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://news.yahoo.com">News</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#your-yahoo">Your Yahoo!</a>
                        <div id="your-yahoo" class="yui3-menu">
                            <div class="yui3-menu-content">
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://edit.yahoo.com/config/eval_profile?.done">Account Information</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://alerts.yahoo.com/">Alerts</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://smallbusiness.yahoo.com/domains">Domains</a></li>
                                </ul>
                                <ul>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://bookmarks.yahoo.com/">Bookmarks</a></li>
                                    <li class="yui3-menuitem"><a class="yui3-menuitem-content" href="http://delicious.com/">delicious</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</div>


<script>

    //  Call the "use" method, passing in "node-menunav".  This will load the
    //  script and CSS for the MenuNav Node Plugin and all of the required
    //  dependencies.

    YUI().use('node-menunav', function(Y) {

        //  Retrieve the Node instance representing the root menu
        //  (<div id="productsandservices">) and call the "plug" method
        //  passing in a reference to the MenuNav Node Plugin.

        var menu = Y.one("#productsandservices");

        menu.plug(Y.Plugin.NodeMenuNav);

        //  Show the menu now that it is ready

        menu.get("ownerDocument").get("documentElement").removeClass("yui3-loading");

    });

</script>
