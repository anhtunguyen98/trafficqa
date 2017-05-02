$(document).ready(function () {
    $("#btnSubmit").click(function () {
        $("#resultTable").html("Đang lấy dữ liệu...");
        console.log($("#question").val());
        $.ajax({
            url: "Answer",
            type: 'post',
            dataType: 'json',
            data: {
                action: encodeURI("getAnswer"),
                question: encodeURI($("#question").val())
            },
            success: function (res) {
                console.log(res);
                $('#answer').html(res.answer);
            },
            error: function (ts) {
                alert("Có lỗi đã xảy ra! Hãy thử lại!");
                console.log(ts.responseText);
            }
        });
    });
});