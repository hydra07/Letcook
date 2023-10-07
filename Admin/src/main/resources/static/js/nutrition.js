$('document').ready(function() {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (nutrition, status) { //Day la mot phuong thuc AJAX của JQUERY được sử
            // dụng để gửi một yêu cầu GET đến URL được chỉ định trong href. Khi yêu cầu hoàn tất,
            // hàm callback sẽ được gọi với hai tham số: category (dữ liệu trả về từ máy chủ) và status
            // (trạng thái của yêu cầu). category se duoc o controller getById tra ve 1 json
            // ,val la gan gia tri cho cai form update bang cai category vua duoc gui toi sau do hien thi modal
            $('#idEdit').val(nutrition.id);
            $('#nameEdit').val(nutrition.name);
        });
        $('#editModal').modal(); //hien thi cai modal
    });
});