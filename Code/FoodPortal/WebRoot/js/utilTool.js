function getParameterByProps(obj) { 
    var props = "";
    for(var p in obj){ 
        if(typeof(obj[p])!="function"){ 
        	props+= (p + "=" + obj[p] + "&");
        } 
    } 
    return props;
}