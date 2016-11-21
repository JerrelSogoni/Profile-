$(document).ready(function() {

    $('#contact_form').bootstrapValidator({

        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton) {
            $('#success_message').slideDown({ opacity: "show" }, "slow"); // Do something ...
            $('#contact_form').data('bootstrapValidator').resetForm();

            var bv = form.data('bootstrapValidator');
            // Use Ajax to submit form data
            $.post(form.attr('action'), form.serialize(), function(result) {
                console.log(result);
            }, 'json');
        },
        fields: {
            first_name: {
                validators: {
                    stringLength: {
                        min: 2
                    },
                    notEmpty: {
                        message: 'Please supply your first name'
                    }
                }
            },
            last_name: {
                validators: {
                    stringLength: {
                        min: 2
                    },
                    notEmpty: {
                        message: 'Please supply your last name'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your email address'
                    },
                    emailAddress: {
                        message: 'Please supply a valid email address'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your phone number'
                    },
                    phone: {
                        country: 'US',
                        message: 'Please supply a vaild phone number with area code'
                    },
                    stringLength: {
                        min: 10,
                        max: 10
                    }

                }
            },
            address: {
                validators: {
                    stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Please supply your street address'
                    }
                }
            },
            city: {
                validators: {
                    stringLength: {
                        min: 4
                    },
                    notEmpty: {
                        message: 'Please supply your city'
                    }
                }
            },
            state: {
                validators: {
                    notEmpty: {
                        message: 'Please select your state'
                    }
                }
            },
            zip: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your zip code'
                    },
                    zipCode: {
                        country: 'US',
                        message: 'Please supply a vaild zip code'
                    }
                }
            },
            dob: {
                validators: {
                    date: {
                        message: 'The value is not a valid date'
                    },
                    stringLength: {
                        min: 10,
                        max: 10

                    },
                    notEmpty: {
                        message: 'Please supply your DOB'
                    }

                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: "Please Pick Gender"
                    }
                }
            }


        }
    })


});