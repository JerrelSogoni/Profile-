$(document).ready(function() {

    $('#settings_form').bootstrapValidator({

        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function (validator, form, submitButton) {
            $('#success_message').slideDown({opacity: "show"}, "slow"); // Do something ...
            $('#contact_form').data('bootstrapValidator').resetForm();

            var bv = form.data('bootstrapValidator');
            // Use Ajax to submit form data
            $.post(form.attr('action'), form.serialize(), function (result) {
                console.log(result);
            }, 'json');
        },
        fields: {
            canseeprofile: {
                validators: {
                    notEmpty: {
                        message: 'Must select a policy'
                    }
                }
            },
            canpostprofile: {
                validators: {
                    notEmpty: {
                        message: 'Must select a policy'
                    }
                }
            }
        }
    })

});