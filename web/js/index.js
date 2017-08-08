var tags, query, answer;

function showAddedInfo(tv, qt, a) {
    $("#answer").html("");
    $('.added-info').slideDown(300);
    if (!tv) {
        $('#tv').css({'display': 'block'});
    }

    if (!qt) {
        $('#qt').css({'display': 'block'});
    }

    if (!a) {
        $('#a').css({'display': 'block'});
    }
}

function refreshPage() {
    $('#question').prop('disabled', false);
    $('#btnSubmit').val("Tìm kiếm");
    $('#answer').text('');
    $('#confirm').slideUp(300);
    $('.added-info').slideUp(300);
    $('#tv').css({'display': 'none'});
    $('#qt').css({'display': 'none'});
    $('#a').css({'display': 'none'});
}

function saveResult(confirm) {
    $.ajax({
        url: 'Answer',
        type: 'post',
        dataType: 'json',
        data: {
            action: encodeURI('saveTest'),
            question: encodeURI($('#question').val()),
            answer: encodeURI(answer),
            query: encodeURI(query),
            'tags': encodeURI(JSON.stringify(tags)),
            satisfied: encodeURI(confirm)
        },
        success: function (data) {
            console.log(data);
            $('#thanks').slideDown(300);
        },
        error: function (ts) {
            alert("Có lỗi trong việc lưu kết quả");
            console.log(ts.responseText);
        }
    });

    refreshPage();
}

function showAnswer(res) {
    var html = '';

    if (res.answer.indexOf('_') !== -1) {
        var answers = res.answer.split('_');
        html = 'Trả lời: <ul>';

        for (var i = 0; i < answers.length; i++) {
            var ans = answers[i];
            html += `<li>${ans}</li>`;
        }

        html += '</ul>';

        html += 'Tham chiếu: <ul>';
        var bases = res.base.split('_');
        for (var i = 0; i < bases.length; i++) {
            var base = bases[i];
            html += `<li>${base}</li>`;
        }
        html += '</ul>';
    } else {
        html = `Trả lời: ${res.answer} <br>Tham chiếu: ${res.base}`;
    }

    $('#answer').html(html);
    $('#btnSubmit').val('Tiếp tục');
    $('#confirm').slideDown(300);
}

$(document).ready(function () {
    $("#btnSubmit").click(function () {
        if ($('#question').prop('disabled')) {
            refreshPage();
            return;
        }

        $('#question').prop('disabled', true);

        if ($('#question').val() === '') {
            alert('Bạn chưa nhập câu hỏi!');
            $('#question').prop('disabled', false);
            return;
        }
        $('#thanks').slideUp(300);

        $("#answer").html("Đang lấy câu trả lời...");
        //        console.log($("#question").val());
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
                tags = res.tags;
                answer = res.answer;
                query = res.query;

                if (res.qt === null) {
                    showAnswer(res);
                } else showAddedInfo(res.tv, res.qt, res.a);
            },
            error: function (ts) {
                alert("Có lỗi đã xảy ra! Hãy thử lại!");
                $("#answer").html("Có lỗi đã xảy ra! Hãy thử lại!");
                $('#btnSubmit').text('Tìm kiếm');
                $('#question').prop('disabled', false);
                console.log(ts.responseText);
            }
        });
    });

    $('#re-submit').click(function () {
        $.ajax({
            url: "Answer",
            type: 'post',
            dataType: 'json',
            data: {
                'tags': encodeURI(JSON.stringify(tags)),
                action: encodeURI('reGetAnswer'),
                tv: encodeURI($('#tv').val()),
                qt: encodeURI($('#qt').val()),
                a: encodeURI($('#a').val())
            },
            success: function (res) {
                console.log(res);
                showAnswer(res);
            },
            error: function (err) {
                console.log(err.message);
            }
        })
    });

    $('#btn-yes').click(function () {
        saveResult('yes');
    });

    $('#btn-ok').click(function () {
        saveResult('ok');
    });

    $('#btn-no').click(function () {
        saveResult('no');
    });
});