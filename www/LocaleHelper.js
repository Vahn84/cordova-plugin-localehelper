

var localehelper =
        {
            execute: function (localehelperSuccess, localehelperError, action, args)
            {
                cordova.exec(
                        localehelperSuccess,
                        localehelperError,
                        'LocaleHelper',
                        action,
                        [args]
                            );
            }
        };

        module.exports = localehelper;
