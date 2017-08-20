var tags, query, answer;
var needInfo = false;

function showAddedInfo(message) {
    $("#answer").html("");
    $('.added-info p').first().text(message);
    $('.added-info').slideDown(300);
    needInfo = true;
}

function refreshPage() {
    $('#question').val('');
    $('#answer').html('');
    $('#base').slideUp(300);
    $('#confirm').slideUp(300);
    $('.added-info').slideUp(300);
    needInfo = false;
}

function saveResult(confirm) {
    $.ajax({
        url: 'Answer',
        type: 'post',
        dataType: 'json',
        data: {
            action: 'saveTest',
            question: $('#question').val(),
            answer: answer,
            query: query,
            'tags': JSON.stringify(tags),
            satisfied: confirm
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
    needInfo = false;
    $('.added-info').slideUp(300);

    if (res.answer.indexOf('_') !== -1) {
        var answers = res.answer.split('_');
        html = 'Trả lời: <ul>';

        for (var i = 0; i < answers.length; i++) {
            var ans = answers[i];
            html += `<li>${ans}</li>`;
        }

        html += '</ul>';

        if (res.base != null) {
            html += 'Tham chiếu: <ul>';
            var bases = res.base.split('_');
            for (var i = 0; i < bases.length; i++) {
                var base = bases[i];
                html += `<li><a>${base}</a></li>`;
            }
        }
        html += '</ul>';
    } else {
        html = `Trả lời: ${res.answer.indexOf("đồng") !== -1 ? 'bị phạt từ ' : ''}${res.answer} <br>${res.base != null ? `Tham chiếu: <a>${res.base}</a>` : ''}`;
    }

    $('#answer').html(html);

    $('#answer a').click(function () {
        var rdiem = /điểm \S+/gim;
        var rkhoan = /khoản \S+/gim;
        var rdieu = /điều \S+/gim;
        var rnd = /nghị định \d+/gim;
        var diem, dieu, khoan, nd;
        var text = $(this).text();

        var match = rdiem.exec(text);
        if (match != null)
            diem = text.substring(rdiem.lastIndex - match[0].length, rdiem.lastIndex).split(' ')[1];

        match = rkhoan.exec(text);
        khoan = text.substring(rkhoan.lastIndex - match[0].length, rkhoan.lastIndex).split(' ')[1];

        match = rdieu.exec(text);
        dieu = text.substring(rdieu.lastIndex - match[0].length, rdieu.lastIndex).split(' ')[1];

        match = rnd.exec(text);
        if (match == null) {
            rnd = /thông tư số \d+/gim;
            match = rnd.exec(text);
            nd = text.substring(rnd.lastIndex - match[0].length, rnd.lastIndex).split(' ')[3];
        }

        if (match == null) {
            rnd = /theo Luật số: \d+/gim;
            match = rnd.exec(text);
            nd = text.substring(rnd.lastIndex - match[0].length, rnd.lastIndex).split(' ')[3];
        }
        nd = text.substring(rnd.lastIndex - match[0].length, rnd.lastIndex).split(' ')[2];

        console.log(`${nd} ${dieu} ${khoan} ${diem}`);

        $.ajax({
            url: 'Base',
            type: 'post',
            dataType: 'json',
            data: {
                'nd': nd,
                'dieu': `d${dieu}`,
                'khoan': `k${khoan}`,
                'diem': diem
            },
            success: function (data) {
                var content = `${data.dieu}\r\n${data.khoan}\r\n${data.diem}`;
                $('#base textarea').first().text(content);
                $('#base').slideDown(300);
            },
            error: function (err) {
                console.log(err.message);
            }
        });
    });
    $('#confirm').slideDown(300);
}

$(document).ready(function () {
    $("#btnSubmit").click(function () {
        $('#base').slideUp(300);
        $('.added-info').slideUp(300);

        // $('#question').prop('disabled', true);

        if ($('#question').val() === '') {
            if (needInfo) {
                alert('Hãy nhập thông tin thêm để tìm kiếm tiếp hoặc nhấn làm mới để bắt đầu lại từ đầu!');
            } else
                alert('Bạn chưa nhập câu hỏi!');
            return;
        }
        $('#thanks').slideUp(300);

        $("#answer").html("Đang lấy câu trả lời...");

        var dat = {
            action: 'getAnswer',
            question: $('#question').val()
        };

        if (needInfo) {
            dat.tags = JSON.stringify(tags);
            needInfo = false;
        }

        $.ajax({
            url: "Answer",
            type: 'post',
            dataType: 'json',
            data: dat,
            success: function (res) {
                console.log(res);
                tags = res.tags;
                answer = res.answer;
                query = res.query;

                if (res.error === 1) {
                    alert('Hãy hỏi những câu liên quan tới giao thông!');
                    refreshPage();
                } else if (res.has_answer === true) {
                    showAnswer(res);
                } else
                    showAddedInfo(res.message);
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

    $('#refresh').click(function () {
        refreshPage();
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