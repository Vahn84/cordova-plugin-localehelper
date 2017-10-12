

var mpbx_nav =
        {
            navigator: function (mpbxNavigatorSuccess, mpbxNavigatorError, action, args)
            {
                cordova.exec(
                        mpbxNavigatorSuccess,
                        mpbxNavigatorError,
                        'MPBXNavigator',
                        action,
                        [args]
                            );
            }
        };

        module.exports = mpbx_nav;
