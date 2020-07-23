Ext.Ajax.on('requestcomplete', function(conn, response, options) {
	try {
		var data = Ext.decode(response.responseText)
		if (data.success == false && data.message == '<spring:message code="errors.loginRequired" />') {
			Ext.MessageBox.alert('错误', '<spring:message code="errors.loginRequired" />', Ext.MessageBox.ERROR);
		}
	} catch (e) {
	}
});

Toast = function() {
	var toastContainer;

	function createMessageBar(title, msg) {
		return '<div class="x-message-box" style="text-align: center; color: #666;"><div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div><div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc" style="font: bold 15px Microsoft YaHei;">' + title + ' : ' + msg + '</div></div></div><div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div></div>';
	}

	return {
		alert : function(title, msg, delay) {
			if (!toastContainer) {
				toastContainer = Ext.DomHelper.insertFirst(document.body, {
					id : 'toastContainer',
					style : 'position: absolute; left: 0; right: 0; margin: auto; width: 360px; z-index: 20000; background: #87CEFA; '
				}, true);
			}

			var message = Ext.DomHelper.append(toastContainer, createMessageBar(title, msg), true);
			message.hide();
			message.slideIn('t').ghost("t", {
				delay : delay,
				remove : true
			});
		}
	};
}();