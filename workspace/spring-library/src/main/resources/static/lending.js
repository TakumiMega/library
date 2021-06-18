'use strict';

function openWin(formName){
	let form = document.forms[formName];
	if(!form){
		alert('指定したフォームが所得できませんでした');
		return;
	}
	let win = window.open('about:blank', formName, "width=600,height=400,scrollbars=yes,resizable=yes");
	form.target = formName;
	form.submit();
	win.focus();
}

function addBooks(){
	let parForm = window.opener.document.closeWin;
	if(!parForm){
		alert('指定したフォームが所得できませんでした');
		return;
	}
	var booksId = document.getElementById('booksId').value;
	//Formのaction設定
	parForm.action = "/library/lending/"+booksId;
	//submit実行
	parForm.submit();
	//後処理
	window.opener.focus();
	//閉じる
	window.close();
}