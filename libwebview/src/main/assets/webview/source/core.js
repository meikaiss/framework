
!function (window, undefined) {

    var core_version = "core_1.0";
    document.write(core_version + "<br>");


    window.webview = window.webview || {};
    window.webview.debug = false;

    window.webview.core = {
        execute: function (group, methods, params) {
            return execute(group, methods, params, false);
        }
    }


    var execute = function (group, methods, userParams, async) {

        var url = buildUrl(group, methods, userParams);
        var params = parseParams(userParams);
        var callback = getCallback(userParams);
        var callbackName = getCallbackName(userParams);

            switch (getSystem()) {
                case 'iphone':
                    return iosExecute(url, params, callback, async, callbackName);
                    break;
                case 'android':
                    return androidExecute(url, params, callback, async, callbackName);
                    break;

                default:
                    break;
            }

        if (window.webview.debug) {
            log(group, methods, params, url);
        }
    }

    var buildUrl = function (group, methods) {
        if (getSystem() === 'iphone') {
            return '/!mk-web/' + group + '/' + methods;
        } else if (getSystem() === 'android'){
            return 'mk-web://' + group + '/' + methods;
        }
    }

    var parseParams = function (params) {
        var paramString = [];

        if (params) {
            for (var e in params) {
                switch (e) {
                    case 'config':
                        paramString = paramString.concat(objectToParams(params[e]));
                        break;
                    case 'callback':
                    case 'callbackName':
                        break;
                    default:
                        if (typeof params[e] === 'object') {
                            paramString.push(e + '=' + encodeURIComponent(JSON.stringify(params[e])));
                        } else {
                            paramString.push(e + '=' + encodeURIComponent(params[e]));
                        }
                    }
                }
                return paramString.join('&');
            } else {
                return '';
            }
        }

    var getCallback = function (params) {
        if (params) {
            for (var e in params) {
                if (e === 'callback') {
                    return params[e];
                }
            }
        }
        return null;
    }

    var getCallbackName = function (params) {
        if (params) {
            for (var e in params) {
                if (e === 'callbackName') {
                    return params[e];
                }
            }
        }
        return null;
    }

    var androidExecute = function (url, params, callback, async, callbackName) {

        if (async) {
            return window.mikeAndroidWebViewInterface.getMikeWebViewData(url + '?' + params, buildCallback(callback, callbackName));
        } else {
            return window.mikeAndroidWebViewInterface.getMikeWebViewData(url + '?' + params);
        }
    }


    var getSystem = function () {
        var ua = window.navigator.userAgent.toLocaleLowerCase();
        var system = ['iphone', 'android', 'windows', 'mac'];

        for (var i = 0; i < system.length; i++) {
            if (ua.indexOf(system[i]) > -1) {
                return system[i];
            }
        }

        return 'other';
    }


    // 监听获取数据
        !function () {

            if (getSystem() === 'android') {

                var handle = function () {
                    var value = JSON.parse(window.msAndroidWebview2.getSSWebViewCallbackData());
                    window[value.callback] && window[value.callback](value.data);
                }

                window.addEventListener('online', handle, false);

                window.addEventListener('offline', handle, false);

            }

        }();


}(window, undefined);