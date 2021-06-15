'use strict';

function openWin(formName){
	let form = document.forms[formName];
	if(!form){
		alert('指定したフォームが所得できませんでした');
		return;
	}
	let win = window.open('about:blank', formName);
	form.target = formName;
	form.submit();
	win.focus();
}