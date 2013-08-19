<script>var FLICKR_API_KEY = '0c13dc70aa7eb3df87b3fee5caf37080';</script>
<style scoped>
/** DEMO BOX **/
#demo {
    position: relative;
    border: 1px solid #cbcbcb;
    border-radius: 6px;
    padding: 8px;
    background: #fafafa;
}

/** SEARCH FORM **/
#demo form {
    border: 1px solid #cbcbcb;
    border-radius: 3px;
    padding: 8px;
    background: #fefefe;
}
#demo form input {
    font-size: 180%;
    padding: 3px 7px;
}

/** SEARCH RESULTS **/
#demo .results {
    position: relative;
    margin: 8px 0;
}
#demo.hide-pg .results {
    margin: 0;
}
#demo .results ul {
    margin: 0;
    padding: 0;
    text-align: center;
    position: absolute;
    opacity: 0;
}
#demo .results li {
    list-style: none;
    float: left;
    padding: 3px;
    position: relative;
    width: 150px;
    height: 150px;
}
#demo .results li img {
    background: #efefef;
    width: 150px;
    height: 150px;
}
#demo .results li:hover {
    z-index: 10;
}
#demo .results li:hover img {
    position: absolute;
    left: -5px;
    top: -5px;
    width: 160px;
    height: 160px;
    z-index: 10;
    border: 3px solid #fff;
    -webkit-box-shadow: 3px 3px 5px hsla(250, 40%, 30%, 0.5);
    -moz-box-shadow: 3px 3px 5px hsla(250, 40%, 30%, 0.5);
    box-shadow: 3px 3px 5px hsla(250, 40%, 30%, 0.5);
}

/** LOADING INDICATOR **/
#demo.loading .loading {
    display: block;
}
#demo .loading {
    background: url('/food/yui_3.11.0/build/assets/paginator/images/loading.gif') left center repeat-x;
    height: 15px;
    width: 150px;
    border: 1px solid #3b4f93;
    border-radius: 4px;
    box-shadow: 3px 3px 3px hsla(40, 40%, 30%, 0.5);
    position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -75px;
    display: none;
    z-index: 100;
    -webkit-box-shadow: 0 0 0 6px hsla(226, 10%, 40%, 0.4);
    -moz-box-shadow: 0 0 0 6px hsla(226, 10%, 40%, 0.4);
    box-shadow: 0 0 0 6px hsla(226, 10%, 40%, 0.4);
}

/** PAGINATOR CSS **/
#demo.hide-pg .paginator {
    display: none;
}

#demo .paginator {
    margin-top: 8px;
    text-align: center;
    border-top: 1px solid #eee;
    padding-top: 10px;
    margin: 0 10px;
}

#demo .paginator .control {
    display: block;
    padding: 0 .5em;
    text-align: center;
    text-decoration: none;
}
#demo .paginator ul {
    margin: 0;
    padding: 0;
    display: inline-block;
    zoom: 1; *display: inline;
}
#demo .paginator ul li {
    display: inline-block;
    zoom: 1; *display: inline;
    list-style: none;
}
#demo .paginator .control {
    border: solid 1px #CBCBCB;
    display: inline-block;
    zoom: 1; *display: inline;
    margin: 0 3px;
    padding: 0 .5em;
    text-align: center;
    text-decoration: none;
    line-height: 1.7em;
    color: #4A4A4A;
    font-family: arial,san-serif;
    background-color: #E6E6E6;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    -ie-border-radius: 3px;
    -o-border-radius: 3px;
    border-radius: 3px;
    cursor: pointer;
}
#demo .paginator .control:hover {
    background-color: #d6d6d6;
    color: #3a3a3a;
}
#demo .paginator .control.selected,
#demo .paginator .control.selected:hover {
    cursor: default;
    font-weight: bold;
    font-weight: bold;
    background-color: #2647a0;
    color: #fff;
}
#demo .paginator .control.disabled,
#demo .paginator .control.disabled:hover {
    background-color: #E6E6E6;
    cursor: default;
    opacity: 0.55;
    filter: alpha(opacity=55);
}
#demo .paginator .page:hover {
    background-color: #bfdaff;
    color: #000;
}
</style>
<div id="demo" class="yui3-skin-sam hide-pg">
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="kitten">
        <input type="submit" value="Search" class="yui3-button">
    </form>
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>

<script>
YUI().use('paginator-core', 'model', 'view', 'transition', 'jsonp', 'querystring-stringify-simple', 'cssbutton', 'node-event-simulate', function (Y) {

    var FlickrSearch = {};

    FlickrSearch.PaginatorModel = Y.Base.create('pager-model', Y.Model, [Y.Paginator.Core]);

    FlickrSearch.PaginatorPages = Y.Base.create('pager-pages', Y.View, [], {

        // We modify the the containerTemplate so our page items are contained in
        // their proper parent
        containerTemplate: '<ul/>',

        // Our page numbers are in a list element (<li>) and contain data about
        // the page (`data-page`) and control type (`data-type`)
        pageTemplate: '<li class="control page page-{page}{selected}"' +
                      ' data-type="page" data-page="{page}">{page}</li>',

        // We delegate our .page element clicks
        events: {
            '.page': {
                'click': '_pageClick'
            }
        },

        // We listen to our model's `change` event to know what page controls to
        // display and which page item to activate
        initializer: function () {
            this.get('model').after('change', this._afterChange, this);
        },

        // When we render, we simply call _buildPageList to render the page items
        render: function () {
            this.get('container').setHTML(this._buildPageList());

            return this;
        },

        // Generates a string of page items based on the current page number and
        // the number of items we can display at one time. The current page of the
        // model will be given the "selected" class name.
        //
        // For example, if our current page is 20, we have a total of 50 pages
        // and we can only display 10, our paginator should display pages
        // 15 thru 24
        //
        // Given the same scenario, if we have a total of 20 pages, our paginator
        // should display 11 thru 20
        _buildPageList: function () {
            var data = this.get('model').toJSON(),
                pages = data.totalPages,
                pagesNode = '',
                display = this.get('display'),
                min = Math.max(1, data.page - Math.floor(display / 2)),
                max = min + display - 1;

            if (max > pages) {
                min -= max - pages;
            }

            min = Math.max(1, min);
            max = Math.min(pages, max);

            for (; min <= max; min++) {
                pagesNode += Y.Lang.sub(this.pageTemplate, {
                    page: min,
                    selected: (data.page === min) ? ' selected' : ''
                });
            }

            return pagesNode;
        },

        // When a page is clicked, we need to push that data to the model
        _pageClick: function (e) {
            var page = e.currentTarget.getData('page');

            this.get('model').set('page', parseInt(page, 10));
        },

        // When the model's `page` or `totalItems` changes, we need to re-render
        // all the page nodes to ensure we are displaying the correct number
        _afterChange: function (e) {
            this.render();
        }

    }, {
        ATTRS: {

            // Maximum number of items to display at one time
            display: {
                value: 10
            }
        }
    });

    FlickrSearch.Paginator = Y.Base.create('pager', Y.View, [], {

        // Our paginator will consist of controls as list elements so we need to
        // ensure our container is the proper parent element type
        containerTemplate: '<ul/>',

        // Our controls are list items we bind click events to. Based on the given
        // control type - stored in `data-type` - we will be able to determine the
        // course of action we need to take
        controlTemplate: '<li class="control {type}" data-type="{type}">' +
                            '{label}' +
                         '</li>',

        // We delegate click events for our controls
        events: {
            '.control': {
                'click': '_controlClick'
            }
        },

        // Our paginator will consist of a few controls as well as a list of pages
        // To get the list of pages, we need to instantiate a `PaginatorPages`
        // view. We also pass our model to the pages view to keep our controls and
        // page items in sync with each other
        initializer: function () {
            var model = this.get('model'),
                pages = this.pages = new FlickrSearch.PaginatorPages({
                    model: model
                });

            model.after('change', this._afterModelChange, this);
        },

        // We can generate our controls as strings, but our pages controls are
        // generated through another module, so we need to append the pages
        // container node between our two sets of controls.
        render: function () {
            var container = this.get('container'),
                firstControls,
                lastControls;

            // build first and prev controls
            firstControls = this._makeControl('first');
            firstControls += this._makeControl('prev', 'Previous');

            // build next and last controls
            lastControls = this._makeControl('next');
            lastControls += this._makeControl('last');

            // add the controls and page items to the container
            container.append(firstControls);
            container.append(this.pages.render().get('container'));
            container.append(lastControls);

            return this;
        },

        // We use this method to simplify the building of our controls. This will
        // give us a Title Cased label from the `type` if we do not provide one
        _makeControl: function (type, label, control) {
            return Y.Lang.sub(this.controlTemplate, {
                type: type,
                label: label || (type.charAt(0).toUpperCase() + type.substr(1))
            });
        },

        // After our model is changed, we only need to update our controls
        _afterModelChange: function (e) {
            this._updateControls();
        },

        // We can know whether our controls should be disabled or not by checking
        // if there is a previous page (for first and previous controls) and a
        // next page (for the next and last controls)
        _updateControls: function () {
            var model = this.get('model'),
                hasPrev = model.hasPrevPage(),
                hasNext = model.hasNextPage(),
                disabled = 'disabled',
                container = this.get('container');

            container.one('.first').toggleClass(disabled, !hasPrev);
            container.one('.prev').toggleClass(disabled, !hasPrev);
            container.one('.next').toggleClass(disabled, !hasNext);
            container.one('.last').toggleClass(disabled, !hasNext);
        },

        // When one of our controls is clicked, we want to update the model as
        // expected, unless the control has been disabled.
        _controlClick: function (e) {
            e.preventDefault();

            var control = e.currentTarget.getData('type'),
                model = this.get('model');

            if (e.currentTarget.hasClass('disabled')) {
                return;
            }

            switch (control) {
                case 'first':
                    model.set('page', 1);
                    break;
                case 'prev':
                    model.prevPage();
                    break;
                case 'next':
                    model.nextPage();
                    break;
                case 'last':
                    model.set('page', model.get('totalPages'));
                    break;
                default:
                    return;
            }
        },


        // ATTRS passthrough to our internal model for external gets and sets
        _setPageFn: function (val) {
            this.get('model').set('page', parseInt(val, 10));
            return val;
        },

        _getPageFn: function () {
            return this.get('model').get('page');
        },

        _setTotalItemsFn: function (val) {
            this.get('model').set('totalItems', parseInt(val, 10));
            return val;
        },

        _getTotalItemsFn: function () {
            return this.get('model').get('totalItems');
        }


    }, {
        ATTRS: {
            // Relays changes to our model's page and retrives our model's page
            // when requested
            page: {
                setter: '_setPageFn',
                getter: '_getPageFn'
            },

            // Relays changes to our model's totalItems and retrieves our model's
            // totalItems when requested
            totalItems: {
                setter: '_setTotalItemsFn',
                getter: '_getTotalItemsFn'
            }
        }
    });

    FlickrSearch.PageView = Y.Base.create('page-view', Y.View, [], {

        // We will be displaying the images in list elements, so we need to
        // ensure the parent element is semantically correct
        containerTemplate: '<ul></ul>',

        // Our image template is dictated from the Flickr API. Here we set up
        // placeholders that will be substitued by the photo's API data in
        // order to display the correct photo
        imageTemplate: '<img src="http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_q.jpg">',

        // In order to add photos to the container, they are expected to be in
        // the format from the API, this would mean an Array of photos.
        //
        // We loop through each of these photo data objects and generate an
        // image element. Then we append all the image elements to our
        // container
        addPhotos: function (photos) {
            var container = this.get('container'),
                photoItems = '',
                i,
                len;

            for (i = 0, len = photos.length; i < len; i++) {
                photoItems += Y.Lang.sub('<li>' + this.imageTemplate + '</li>', photos[i]);
            }

            this.get('container').append(photoItems)
        }

    });


    FlickrSearch.App = Y.Base.create('search', Y.View, [], {

        // This is the Flickr API URL
        url: 'http://api.flickr.com/services/rest/?',

        // This is a flag that will determin if we are performing a new query
        // or simply going to a new page of the previous query
        _isNewQuery: false,

        // An array of pages that are being displayed. This will adjust as
        // the paginator is being interacted with, but should result in only
        // one item being persistent
        _pages: [],

        paginator: null,

        // When our form is submitted, we run a new query on it
        events: {
            'form .yui3-button': {
                'click': '_afterFormSubmit'
            }
        },

        // When we first initialize our application, we set up our paginator
        // We also listen to it's page change event to get that page's images
        initializer: function () {
            this._api = this.get('apiConfig');

            this.paginator = new FlickrSearch.Paginator({
                model:  new FlickrSearch.PaginatorModel({
                    itemsPerPage: this._api.per_page
                })
            });

            this.paginator.get('model').after('pageChange', this._afterPageChange, this);
        },

        // We append our paginator to the paginator placeholder in our
        // container
        render: function () {
            this.get('container').one('.paginator').append(
                this.paginator.render().get('container')
            );
        },

        // We update the app based on our loading process
        //
        // If status is true, we add the "loading" class
        // If status is false, we remove the "loading" class
        setLoading: function (status) {
            this.get('container').toggleClass('loading', status);
        },

        // Our app needs a way to display a message to the user where there's
        // an error or remove an error message if there is no longer a
        // message that needs to be displayed
        setMessage: function (msg) {
            var container = this.get('container'),
                msgNode = container.one('.results .msg');

            if (msg) {
                container.one('.results').setHTML('<div class="msg">' + msg + '</div>');
                container.removeClass('hide-pg');
                this.setLoading(false);
            } else {
                if (msgNode) {
                    msgNode.remove();
                }
            }
        },

        // When we request a new photo, there are a few things that happen.
        //
        // * First we alert our app that something is loading
        // * Then we construct our url based on the API configuration and the
        //   page requested
        // * Finally we request the API response through JSON-P
        //
        // If that request fails, we let the user know with a message
        // If the requst succeeds, we process the response data
        requestPhotos: function (page) {
            this.setLoading(true);

            var self = this,
                api = this._api,
                url = this.url;

            api.page = page || 1;

            url += Y.QueryString.stringify(api);

            Y.jsonp(url, {
                format: function (url, proxy) {
                    return url + '&jsoncallback=' + proxy;
                },
                on: {
                    failure: Y.bind(function () {
                        this.setLoading(false);
                        this.setMessage('Oops!! something broke :(');
                    }, self),

                    success: Y.bind(function (resp) {
                        this._processResults(resp.photos);
                        this._isNewQuery = false;
                    }, self)
                }
            });
        },

        // When our form is submitted, we assume it's a new request. As a new
        // request we remove our old pages, ensure our paginator is set to
        // page 1 and request our new set of photos
        _afterFormSubmit: function (e) {
            e.preventDefault();

            while (this._pages.length) {
                this._pages.shift().destroy({ remove: true });
            }

            this._api.text = this.get('container').one('form input').get('value');

            this._isNewQuery = true;

            if (this.paginator.get('page') !== 1) {
                this.paginator.set('page', 1);
            } else {
                this.requestPhotos();
            }
        },

        // After we receive a response from the Flickr API, we check if we
        // have any pages to process and send the
        _processResults: function (resp) {
            this.setMessage( !resp.pages ?
                'There are no images for "' + this._api.text + '"' :
                ''
            );

            if (this._isNewQuery) {
                this.paginator.set('totalItems', parseInt(resp.total, 10));
            }

            this._createNewPage(resp.photo);
        },

        // Once our data have been received by the Flicker API and the
        // results have been processed, we create a new page containing the
        // new photos
        _createNewPage: function (photos) {
            var page = new FlickrSearch.PageView(),
                resultsNode = this.get('container').one('.results'),
                pageContainer;

            // Add our photos to the new page
            page.addPhotos(photos);

            pageContainer = page.get('container');

            // append our new page to the results node
            resultsNode.append(pageContainer);
            resultsNode.setStyle('height', pageContainer.get('offsetHeight'));


            // We do not want to display the new page before all the images
            // requested have had a chance to process.
            var images = pageContainer.all('img'),
                imagesLeft = images.size();

            images.after(['load', 'error', 'abort'], function (e) {

                if (!(--imagesLeft)) {

                    // If there is more than one page, we have a previous page
                    var prevPage = (this._pages.length > 1) ? this._pages.shift() : null;

                    // Turn off the loading indicator
                    this.setLoading(false);

                    // Transition the new page in
                    // If there is not a previous page, just display the new page
                    pageContainer.transition({
                        opacity: 1,
                        duration: 1,
                        delay: (prevPage) ? 0.5 : 0
                    });

                    // Transition the previous page out
                    // Once the transition is complete, remove the previous page
                    if (prevPage) {
                        prevPage.get('container').transition({
                            opacity: 0,
                            duration: 1
                        }, function (e) {
                            prevPage.destroy({
                                remove: true
                            });
                        });
                    }
                }
            }, this);

            // We now have a page to display, so let's show the paginator
            this.get('container').removeClass('hide-pg');
            this._pages.push(page);
        },

        // After our page changes, we request that page's photos
        _afterPageChange: function (e) {
            this.requestPhotos(e.newVal);
        }

    }, {
        ATTRS: {
            // This will contain our settings for the Flickr API
            apiConfig: {
                value: {
                    api_key: FLICKR_API_KEY,
                    method: 'flickr.photos.search',
                    safe_search: 1,
                    sort: 'relevance',
                    format: 'json',
                    license: 4,
                    per_page: 20
                }
            }
        }
    });

    var flickrSearch = new FlickrSearch.App({
        container: '#demo'
    });

    flickrSearch.render();      
    
    Y.one('form .yui3-button').simulate('click');
    
    Y.one('#searchKeyword').on('keydown', function(ev){
    	ev.currentTarget;
    	//alert("ev.charCode = " + ev.charCode);
    	if(ev.charCode==13)
    	{
    		Y.one('form .yui3-button').focus();
    	}
    });     
});
</script>