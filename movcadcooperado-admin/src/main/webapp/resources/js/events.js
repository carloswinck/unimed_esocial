// ver exemplos em http://jsfromhell.com

addEvent = function(o, e, f, s){
    var r = o[r = "_" + (e = "on" + e)] = o[r] || (o[e] ? [[o[e], o]] : []), a, c, d;
    r[r.length] = [f, s || o], o[e] = function(e){
        try{
            (e = e || event).preventDefault || (e.preventDefault = function(){e.returnValue = false;});
            e.stopPropagation || (e.stopPropagation = function(){e.cancelBubble = true;});
            e.target || (e.target = e.srcElement || null);
            e.key = (e.which + 1 || e.keyCode + 1) - 1 || 0;
        }catch(f){}
        for(d = 1, f = r.length; f; r[--f] && (a = r[f][0], o = r[f][1], a.call ? c = a.call(o, e) : (o._ = a, c = o._(e), o._ = null), d &= c !== false));
        return e = null, !!d;
    };
};

removeEvent = function(o, e, f, s){
    for(var i = (e = o["_on" + e] || []).length; i;)
        if(e[--i] && e[i][0] == f && (s || o) == e[i][1])
            return delete e[i];
    return false;
};

enterAsTab = function(f, a){
    addEvent(f, "keypress", function(e){
        var l, i, f, j, o = e.target;
        if(e.keyCode == 13 && (!/button|submit|textarea/i.test(o.type))){
        	//alert(o.type);
            for(i = l = (f = o.form.elements).length; f[--i] != o;);
            for(j = i; (j = (j + 1) % l) != i && (!f[j].type || f[j].disabled || f[j].readOnly || f[j].type.toLowerCase() == "hidden" || f[j].style.visibility == "hidden"););
            e.preventDefault(), j != i && f[j].focus();
        }
    });
};

autoTab = function(f){
    var c = 0;
    addEvent(f, "keyup", function(e){
        var i, j, f = (e = e.target).form.elements, l = e.value.length, m = e.maxLength;
        if(c && m > -1 && l >= m){
            for(i = l = f.length; f[--i] != e;);
            for(j = i; (j = (j + 1) % l) != i && (!f[j].type || f[j].disabled || f[j].readOnly || f[j].type.toLowerCase() == "hidden" || f[j].style.visibility == "hidden"););
            j != i && f[j].focus();
        }
    });
    addEvent(f, "keypress", function(e){c = e.key;});
};

function pressEnter(e){
	var c = (e.which) ? e.which : e.keyCode;	
	if(c == 13){return true;}
	return false;
}

function applyEnterAsTab() {
	if(document.forms != null){
		var i;
		for(i = 0; i < document.forms.length; i++){
			enterAsTab(document.forms[i], true);
		}
	}	
}