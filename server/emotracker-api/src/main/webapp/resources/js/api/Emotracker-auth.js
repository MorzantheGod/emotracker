/**
 * Created by petrpopov on 27.04.14.
 */

$( document ).ready(function() {

    enableValidation();
    enableAuth();

    function enableValidation() {
        $('#loginform').validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            }
        });

        $('#signupform').validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
                fullName: {required: true},
                userName: {required: true},
                password: {required: true}
            }
        });
    }

    function enableAuth() {

        $('#logoutLink').click(function() {
            $('#logoutForm').submit();
        });
    }
});