function checkBillingAddress(){
    if($("#theSameAsShippingAddress").is(":checked")){
        $(".billingAddress").prop("disabled",true);
    }else {
        $(".billingAddress").prop("disabled",false);
    }
}
function checkPasswordMatch() {
    var password = $("#txtNewPassword").val();
    var confirmPassword = $("#txtConfirmPassword").val();

    if(password == "" && confirmPassword =="") {
        $("#checkPasswordMatch").html("");
        $("#updateUserInfoButton").prop('disabled', false);
    } else {
        if(password != confirmPassword) {
            $("#checkPasswordMatch").html("Passwords do not match!");
            $("#updateUserInfoButton").prop('disabled', true);
        } else {
            $("#updateUserInfoButton").prop('disabled', false);
        }
    }
}
$(document).ready(function (){
    $("#updateQty").on('change',function (){
        var id = this.id;
        console.log("changed")
        $('#update-item-').css('display', 'inline');
    })
    $("#theSameAsShippingAddress").on('click',checkBillingAddress);
    $("#txtConfirmPassword").keyup(checkPasswordMatch);
    $("#txtNewPassword").keyup(checkPasswordMatch);
});
