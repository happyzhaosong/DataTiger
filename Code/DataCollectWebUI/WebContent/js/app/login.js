   
Ext.onReady(function ()
{
		var loginForm = Ext.create('Ext.form.Panel', {
		    	title: '',
		    	bodyPadding: 10,
		    	border: false,
		        url: doLoginUrl,
		        defaultType: 'textfield',
		        items: [{
		            fieldLabel: 'User Name',
		            name: 'userName',
		            maxLength: 30,
		            allowBlank: false
		        },{
		            fieldLabel: 'Password',
		            name: 'passWord',
		            inputType: 'password',
		            minLength: 5,
		            maxLength: 30,
		            allowBlank: false
		        }, {
		            xtype: 'hiddenfield',
		            name: actionFieldName,
		            value: actionLogin
		        }],
		
		        // Reset and Submit buttons
		        buttons: [{
		            text: 'Login',
		            formBind: true, //only enabled once the form is valid
		            disabled: true,
		            handler: function() {
		                var form = this.up('form').getForm();
		                formToolGlobal.submitFormCall(form,this,null,null);
		            },
		            formSuccessFN: function(form, action, optionObjArr)
		            {
		            	window.location.href = webSiteUrl;
		            },
		            formFailFN: function(form, action, optionObjArr)
		            {
		           	    Ext.Msg.alert('Login Failure', formToolGlobal.getFormResponseJson(action).message);                            	   
		            }
		        },{
		            text: 'Cancel',
		            handler: function() {
		                this.up('form').getForm().reset();
		            }
		        }]           
		});
	
       Ext.create('Ext.window.Window', {
			title: 'Data Collect Login',
   			height: 130,
   			width: 300,
   			modal: true,
   			closable: false,
   			resizable: false,
   			layout: 'fit',
   			items: [loginForm]
   		}).show();
});