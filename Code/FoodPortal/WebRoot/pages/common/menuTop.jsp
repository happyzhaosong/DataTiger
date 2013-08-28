<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="yui3-g" id="bd">
    <div class="yui3-u-1" id="main">
       <div id="productsandservices" class="yui3-menu yui3-menu-horizontal">
            <div class="yui3-menu-content">
                <ul class="first-of-type">
                    <li class="yui3-menuitem">
                    	<a class="yui3-menuitem-content" href="http://www.yahoo.com/">Home</a>
                    </li>
                    
                    <li>
                    	<a class="yui3-menu-label" href="#connect">Connect</a>
                    </li>
                    
                    <li>
                    	<a class="yui3-menu-label" href="#find-info">Find Info</a>
                    </li>
                    
                    <li>
                        <a class="yui3-menu-label" href="#lifestyles">Lifestyles</a>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#new-finance">News &#38; Finance</a>
                    </li>

                    <li>
                        <a class="yui3-menu-label" href="#your-yahoo">Your Yahoo!</a>
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
        
        Y.all('.first-of-type a').on('mouseover',function(ev){
        	ev.currentTarget.setStyle('cursor', 'hand');
        });

    });

</script>
