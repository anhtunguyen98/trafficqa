$(document).ready(function () {
    $("#btnSubmit").click(function () {
        $("#answer").html("Đang lấy câu trả lời...");
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
                console.log(res.tags);
                $('#answer').html(res.answer);
            },
            error: function (ts) {
                alert("Có lỗi đã xảy ra! Hãy thử lại!");
                console.log(ts.responseText);
            }
        });
    });
});