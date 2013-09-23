YUI.add('DataSearchApp', function(Y){
		
	Y.DataSearchPaginatorModel = Y.Base.create('pager-model', Y.Model, [Y.Paginator.Core]);

    Y.DataSearchPaginatorPages = Y.Base.create('pager-pages', Y.View, [], {

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

    Y.DataSearchPaginator = Y.Base.create('pager', Y.View, [], {

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
                pages = this.pages = new Y.DataSearchPaginatorPages({
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
            firstControls = this._makeControl('first','第一页');
            firstControls += this._makeControl('prev', '上一页');

            // build next and last controls
            lastControls = this._makeControl('next','下一页');
            lastControls += this._makeControl('last','最后页');

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

    Y.DataSearchPageView = Y.Base.create('page-view', Y.View, [], {

        // We will be displaying the images in list elements, so we need to
        // ensure the parent element is semantically correct
        containerTemplate: '',

        // Our image template is dictated from the  API. Here we set up
        // placeholders that will be substitued by the photo's API data in
        // order to display the correct photo
        dataItemTemplate: '',
        
        dataItemTemplateTMall: '',
        
        dataItemTemplateJingDong: '',

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
            	var sply = photos[i].shangPinLaiYuan;
            	if(sply==1)
            	{
            		photoItems += Y.Lang.sub(this.dataItemTemplateTMall, photos[i]);
            	}else if(sply==2)
            	{
            		photoItems += Y.Lang.sub(this.dataItemTemplate, photos[i]);
            	}else if(sply==3)
            	{
            		photoItems += Y.Lang.sub(this.dataItemTemplateJingDong, photos[i]);
            	}else
            	{
            		photoItems += Y.Lang.sub(this.dataItemTemplate, photos[i]);
            	}
            }

            this.get('container').append(photoItems)
        }
    });

    
    Y.DataSearchKeywordTagView = Y.Base.create('search-keyword-tag-view', Y.View, [], {

    	//this is the url for generate common search key word tags
    	url:'',
    	
    	containerTemplate: '',
    	
    	dataItemTemplate: '',
    	
    	page: 1,
    	
    	pageSize: 10,
        	    	      
        // When we request a new photo, there are a few things that happen.
        //
        // * First we alert our app that something is loading
        // * Then we construct our url based on the API configuration and the
        //   page requested
        // * Finally we request the API response through JSON-P
        //
        // If that request fails, we let the user know with a message
        // If the requst succeeds, we process the response data
        requestSearchKeywordTags: function() {
        	var url = this.url + 'page=' + this.page;
            Y.io(url, {
                method: 'GET',
                on: {
                	failure: Y.bind(function (transactionid, response, arguments) {
                        this.setLoading(false);
                        var respJsonObj = Y.JSON.parse(response.responseText);
                        var errMsg = respJsonObj.message; 
                        if(errMsg=='')
                        {
                        	errMsg = systemErrorMessageBusy;
                        }
                        this.setMessage(errMsg);
                    }, this),

                    success: Y.bind(function (transactionid, response, arguments) {
                    	var respJsonObj = Y.JSON.parse(response.responseText);
                    	if(respJsonObj.success)
                    	{
                    		this._processSearchKeywordTagResults(respJsonObj, this);     		
                    	}else
                    	{
                    	
                   		 	var errMsg = respJsonObj.message; 
                   		 	if(errMsg=='')
                   		 	{
                   		 		errMsg = systemErrorMessageBusy;
                   		 	}
                   		 	this.setMessage(errMsg);
                   		 	                   		
                    	}
                    }, this)
                }                
            });
        	
        },
        
        // Our app needs a way to display a message to the user where there's
        // an error or remove an error message if there is no longer a
        // message that needs to be displayed
        setMessage: function (msg) {
            var container = Y.one('.results'),
                msgNode = container.one('.msg');

            if (msg) {
            	container.setHTML('<div class="msg">' + msg + '</div>');                
                this.setLoading(false);
            } else {
                if (msgNode) {
                    msgNode.remove();
                }
            }
        },               
        
        _processSearchKeywordTagResults: function(resp, context){
        	if(!resp.exist)
        	{
        		if(this.page>1)
        		{
        			this.page--;
        		}
        		return;
        	}
        	this.get('container').empty();
        	var searchkeywordTags = '';
        	var keywordList = resp.JSON_SEARCH_KEYWORD_LIST;
        	
        	if(keywordList)
        	{
        		this.dataItemTemplate = '<li><a href="#" title="{searchKeyword}">{searchKeywordSummary}</a>&nbsp;&nbsp;</li>';
        		var len = keywordList.length;
        		for(var i=0;i<len;i++)
        		{
        			searchkeywordTags += Y.Lang.sub(this.dataItemTemplate, keywordList[i]); 
        		}
        	}        	
        	
        	this.get('container').append(searchkeywordTags);
        	if(resp.JSON_TOTAL_RESULT_COUNT > this.pageSize)
        	{
        		this.get('container').append(this._makeControl(this.page, resp.JSON_TOTAL_RESULT_COUNT, this.pageSize));	
        	}       	
        	
            Y.all('#searchKeywordTag a').on('click',function(ev){
            	ev.preventDefault();
            	var currTarget = ev.currentTarget._node;
            	if(currTarget.id == 'searchKeywordPrev')
            	{
            		if(context.page>1)
            		{
            			context.page--;
            		}
            		context.requestSearchKeywordTags();
            	}else if(currTarget.id == 'searchKeywordNext')
            	{
            		context.page++;
            		context.requestSearchKeywordTags();
            	}else
            	{
            		var searchKeyword = ev.currentTarget.getAttribute('title');    	
            		Y.one('#searchKeyword').set('value',searchKeyword);
            		Y.one('#searchBtn').simulate('click');
            	}
            });
        },
        
        
        _makeControl: function(currPage, totalResultCount, pageSize){
        	var control = '';
        	if(currPage > 1)
        	{
        		control += '<li><a id="searchKeywordPrev" href="#" title="Previous" alt="Previous"><&nbsp;</a></li>';
        	}
        	
        	if(currPage*pageSize < totalResultCount)
        	{
        		control += '<li><a id="searchKeywordNext" href="#" title="Next" alt="Next">&nbsp;></a></li>';
        	}
        	return control;
        }
    });
    
    Y.one('#searchKeyword').on('keydown', function(ev){  
    	if(ev.charCode==13)
    	{
    		Y.one('#searchBtn').focus();
    	}
    });           

    
    Y.one('#resetBtn').on('click', function(ev){  
    	Y.one('#resetBtn').hide();
    });

    Y.one('#searchKeyword').on('focus', function(ev){  
    	showResetBtn(ev);
    });
    
    Y.one('#searchKeyword').on('mouseover', function(ev){  
    	showResetBtn(ev);
    });
    
    Y.one('#searchKeyword').on('keyup', function(ev){  
    	showResetBtn(ev);
    }); 
    
    function showResetBtn(ev)
    {
    	if(Y.one('#searchKeyword').get('value').length>0)
    	{
    		Y.one('#resetBtn').show();
    	}else
    	{
    		Y.one('#resetBtn').hide();
    	}
    }

    
    Y.DataSearchApp = Y.Base.create('search', Y.View, [], {

    	// This is a flag that will determin if we are performing a new query
        // or simply going to a new page of the previous query
        _isNewQuery: false,

        // An array of pages that are being displayed. This will adjust as
        // the paginator is being interacted with, but should result in only
        // one item being persistent
        _pages: [],

        //the paginator of the search result
        paginator: null,

        //the searchKeywordTag view of the search result
        searchKeywordTagView: null,
        
        searchNoResultInfoPrefix: '',
        
        searchNoResultInfoSuffix: '',
        
        systemErrorMessageBusy: '',
        
        // When our form is submitted, we run a new query on it
        events: {
            '#searchBtn': {
                'click': '_afterFormSubmit'
            }
        },

        // When we first initialize our application, we set up our paginator
        // We also listen to it's page change event to get that page's images
        initializer: function () {
            this._api = this.get('apiConfig');

            this.paginator = new Y.DataSearchPaginator({
                model:  new Y.DataSearchPaginatorModel({
                    itemsPerPage: this._api.limit
                })
            });

            this.paginator.get('model').after('pageChange', this._afterPageChange, this);
            
            this.searchKeywordTagView = new Y.DataSearchKeywordTagView({container: '#searchKeywordTag'});
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
            api.start = api.limit*(page-1);

            //url += encodeURI(encodeURI(getParameterByProps(api)));
            url += encodeURI(Y.QueryString.stringify(api));
            
            Y.io(url, {
                method: 'GET',
                on: {
                	failure: Y.bind(function (transactionid, response, arguments) {
                        this.setLoading(false);
                        var respJsonObj = Y.JSON.parse(response.responseText);
                        var errMsg = respJsonObj.message; 
                        if(errMsg=='')
                        {
                        	errMsg = systemErrorMessageBusy;
                        }
                        this.setMessage(errMsg);
                    }, self),

                    success: Y.bind(function (transactionid, response, arguments) {
                    	var respJsonObj = Y.JSON.parse(response.responseText);
                    	if(respJsonObj.success)
                    	{
                    		this._processResults(respJsonObj);
                    		this._isNewQuery = false;
                    		this.searchKeywordTagView.requestSearchKeywordTags(1);
                    	}else
                    	{
                   		 	var errMsg = respJsonObj.message; 
                   		 	if(errMsg=='')
                   		 	{
                   		 		errMsg = systemErrorMessageBusy;
                   		 	}
                   		 	this.setMessage(errMsg);                    		
                    	}
                    }, self)
                }                
            });
            
            /*
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
            */
        },
        
        
        /*
         * Construct search params by usr input and selected search condition
         * */
        _constructSearchParams: function()
        {
        	this._api.searchKeyword = Y.one('#searchKeyword').get("value");
        	
        	var orderBy1SelIdx = Y.one('#orderByWithDierction1').get('selectedIndex');        	
        	this._api.orderByWithDierction1 = Y.one('#orderByWithDierction1').get('options')._nodes[orderBy1SelIdx].value
        	
        	var searchWebSite = '';
        	var searchWebSiteNodeList = Y.all('#searchIn input');
        	
        	if(searchWebSiteNodeList)
        	{
        		searchWebSiteNodeList.each(function(node){
        			var value = node.get('value');
        			var checked = node.get('checked');
        			if(checked)
        			{
        				searchWebSite += value;
        				searchWebSite += ",";
        			}
        		});
        	}
        	
        	this._api.searchWebSite = searchWebSite;
        },        

        // When our form is submitted, we assume it's a new request. As a new
        // request we remove our old pages, ensure our paginator is set to
        // page 1 and request our new set of photos
        _afterFormSubmit: function (e) {
            e.preventDefault();    
            
            this._api.logSearchKeyword = true;
            
            while (this._pages.length) {
                this._pages.shift().destroy({ remove: true });
            }

            //construct user input to search params.
            this._constructSearchParams();

            this._isNewQuery = true;

            if (this.paginator.get('page') !== 1) {
                this.paginator.set('page', 1);
            } else {
                this.requestPhotos();
            }
        },

        // After we receive a response from the  API, we check if we
        // have any pages to process and send the
        _processResults: function (resp) {
        	if(!resp.exist || resp.JSON_TOTAL_RESULT_COUNT==0)
        	{
        		var errMsg = this.searchNoResultInfoPrefix;
        		if(this._api.searchKeyword!='')
        		{
        			errMsg += " '";
        			errMsg += this._api.searchKeyword;
        			errMsg += "' ";
        		}
        		errMsg += this.searchNoResultInfoSuffix;
        		this.setMessage(errMsg);	
        	}else
        	{
        		this.setMessage(false);
        	}
            
            if (this._isNewQuery) {
                this.paginator.set('totalItems', parseInt(resp.JSON_TOTAL_RESULT_COUNT, 10));
            }

            this._createNewPage(resp.JSON_ROOT_FOOD_LIST);
        },

        // Once our data have been received by the Flicker API and the
        // results have been processed, we create a new page containing the
        // new photos
        _createNewPage: function (photos) {
            var page = new Y.DataSearchPageView(),
                resultsNode = this.get('container').one('.results'),
                pageContainer;
            
            page.containerTemplate = this.containerTemplate;
            page.dataItemTemplate = this.dataItemTemplate;
            page.dataItemTemplateTMall = this.dataItemTemplateTMall;
            page.dataItemTemplateJingDong = this.dataItemTemplateJingDong;
            
            // Add our photos to the new page
            page.addPhotos(photos);

            pageContainer = page.get('container');

            // append our new page to the results node
            resultsNode.append(pageContainer);

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
                    
                    //set height here can make the paginator div under the results div after all images loaded
                    resultsNode.setStyle('height', pageContainer.get('offsetHeight'));

                }else
                {
                	resultsNode.setStyle('height', pageContainer.get('offsetHeight'));
                }
            }, this);

            // We now have a page to display, so let's show the paginator
            this.get('container').removeClass('hide-pg');
            if(this._pages.length>0)
            {
            	this._pages.shift().destroy({ remove: true });
            }
            this._pages.push(page);
        },

        // After our page changes, we request that page's photos
        _afterPageChange: function (e) {
        	if(this._isNewQuery)
        	{
        		this._api.logSearchKeyword = true;
        	}else
        	{
        		this._api.logSearchKeyword = false;
        	}
            this.requestPhotos(e.newVal);
        },
        
        url:'',
        
        containerTemplate: '',
        
        dataItemTemplate: '',
        
        dataItemTemplateTMall: '',
        
        dataItemTemplateJingDong: ''

    }, {
        ATTRS: {
            // This will contain our settings for the  API
            apiConfig: {
                value: {
        			searchKeyword: '',
        			logSearchKeyword: true,
        			orderByWithDierction1: '',
        			orderByWithDierction1: '',
        			page: 1,
        			start: 0,
                    limit: 16,
                    searchWebSite: 0
                }
            }
        }
    });
},'1.0',{requires:['paginator-core', 'model', 'view', 'transition', 'Escape','jsonp','io-base','json', 'querystring-stringify-simple', 'cssbutton', 'node-event-simulate']});