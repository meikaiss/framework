

!function (self, undefined) {

    var router_version = "router_1.0";
    document.write(router_version);


    self.webview = self.webview || {};


    self.webview.system = {

        "version": function () {
            return self.webview.core.execute('system', 'version');
        },

        "toast": function (message) {
            return self.webview.core.execute('system', 'toast', {
                message: message
            });
        },

        "log": function (message) {
            return self.webview.core.execute('system', 'log', {
                message: message
            });
        }
   }

   self.webview.jump = {

        "action":function(message){
            return self.webview.core.execute('jump', 'action', {
                message:message
            });
        }
   }

}(self, undefined);