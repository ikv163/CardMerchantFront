$(function () {
    validateKickout();
    $('#btnSubmit').click(login);
});


function login() {
    var username = $("input[name='username']").val()
    var password = $("input[name='password']").val();
    var googlecodetop = $("input[name='googlecodetop']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');
    if (!username) {
        return $.modal.msg(' 用户名不能为空');
    }
    if (!password) {
        return $.modal.msg(' password不能为空');
    }
    if (!googlecodetop) {
        return $.modal.msg(' googlecode不能为空');
    }
    $.modal.loading($("#btnSubmit").data("loading"));

    $.ajax({
        type: "POST",
        url: "login",
        data: {
            "username": username,
            "password": password,
            "googlecodetop": googlecodetop,
            "rememberMe": rememberMe
        },
        beforeSend: function (request) {
            request.setRequestHeader("newDate", new Date().getTime());
        },
        success: function (r) {
            if (r.code == 0) {
                    location.href = 'index';
            } else {
                $.modal.closeLoading();
                $(".code").val("");
                $.modal.msg(r.msg);
            }
        }
    });
    return false;
}


function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function validateKickout() {
    if (getParam("kickout") == 1) {
        if (top != self) {
            top.location = self.location;
        } else {
            var url = location.search;
            if (url) {
                var oldUrl = window.location.href;
                var newUrl = oldUrl.substring(0, oldUrl.indexOf('?'));
                self.location = newUrl;
            }
        }
    }
}


