$(document).ready(function () {
    $("#btnSubmit").click(function () {
        $("#resultTable").html("Đang lấy dữ liệu...");
        $.ajax({
            url: "SimilarityQuestion",
            type: 'post',
            dataType: 'json',
            data: {
                action: encodeURI("getCommonQuestions"),
                question: encodeURI($("#question").val()),
                mea: encodeURI($("#mea").val()),
                abs2Use: encodeURI('[{"abs": 1}]')
            },
            success: function (res) {

                var html = "<thead class='thead-default'><tr>" +
                        "<td style='width: 3%;'>STT</td>" +
                        "<td style='width: 46%;'>Câu hỏi</td>" +
                        "<td style='width: 18%; text-align: right;'>Dựa trên</td>" +
                        "<td style='width: 23%; text-align: right;'>Trả lời</td>" +
                        "<td style='width: 10%; text-align: right;'>Độ tương tự</td>" +
                        "</tr></thead>";
                $("#resultTable").html(html);
                var count = 1;
                $.each(res.tests, function (key, item) {
//                    console.log(parseFloat(item.same));
                    $table = $('#resultTable');
                    $row = $('<tr>');
                    $row.append($('<td style="width: 3%;">').html(count++));
                    $row.append($('<td style="width: 46%;">').html(item.question));
                    $row.append($('<td style="width: 18%; text-align: right;">').html(item.base));
                    $row.append($('<td style="width: 23%; text-align: right;">').html(item.answer));
                    $row.append($('<td style="width: 10%; text-align: right;">').html(item.same.toFixed(2)));
                    
                    $row.appendTo($table).hide().fadeIn().slideDown();
                });
            },
            error: function (ts) {
                alert("Có lỗi đã xảy ra! Hãy thử lại!");
                console.log(ts.responseText);
                $("#resultTable").html("");
            }
        });
    });
});