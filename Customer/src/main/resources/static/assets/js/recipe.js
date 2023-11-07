let ingredientCount = 0; // Biến đếm nguyên liệu
let stepCount = 0; // Biến đếm bước nấu ăn

let measurements = [];

$.ajax({
    url: 'http://localhost:8020/shop/api/measurements',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        measurements = data;
        console.log(measurements);
    },
    error: function (error) {
        console.log('Error:', error);
    }
});

// Sau đó, sử dụng danh sách lưu trữ cục bộ để điền vào dropdown khi thêm hàng mới
function createMeasurementDropdown(ingredientCount) {
    const select = $('<select>')

        .attr('name', 'measurements' + ingredientCount)
        .addClass('form-control')
        .attr('required', true);

    if (measurements.length > 0) {
        measurements.forEach(function (measurement) {
            select.append($('<option>')
                .attr('value', measurement)
                .text(measurement));
        });
    } else {
        select.append($('<option>').attr('value', '').text('No measurements available'));
    }

    return select;
}

$('#addIngredient').click(function () {
    ingredientCount++;
    $('#ingredientCount').val(ingredientCount);

    const ingredientRow = $('<div>').addClass('ingredient-row form-group');
    ingredientRow.append(
        $('<label>')
            .attr('for', 'ingredient' + ingredientCount)
            .text('Nguyên liệu ' + ingredientCount + ':'),
    );
    ingredientRow.append(
        $('<input>')
            .attr('type', 'text')
            .addClass('form-control')
            .attr('id', 'ingredient' + ingredientCount)
            .attr('placeholder', 'Nguyên liệu ' + ingredientCount)
            .attr('name', 'ingredient' + ingredientCount)
            .attr('required', true)
            .attr('oninput', 'fetchSuggestionsIngredient(this.id)'),
    );
    ingredientRow.append(
        $('<input>')
            .attr('type', 'number')
            .addClass('form-control')
            .attr('id', 'amount' + ingredientCount)
            .attr('placeholder', 'Số lượng ' + ingredientCount)
            .attr('name', 'amount' + ingredientCount)
            .attr('required', true),
    );


    // ingredientRow.append(
    //     $('<button>')
    //         .attr('type', 'button')
    //         .addClass('btn btn-danger')
    //         .text('Xoá')
    //         .data('bs-toggle', 'tooltip')
    //         .data('bs-placement', 'top')
    //         .attr('title', 'Xoá Nguyên liệu')
    //         .click(function () {
    //             ingredientRow.remove();
    //         }),
    // );
    console.log(createMeasurementDropdown(ingredientCount));
    ingredientRow.append(createMeasurementDropdown(ingredientCount));
    const suggestRowId = 'suggest' + 'ingredient' + ingredientCount;
    ingredientRow.append(
        $('<div>')
            .addClass('suggestion-row row d-block')
            .attr('id', suggestRowId)
    );
    $('#ingredient').append(ingredientRow);
});

$('#addStep').click(function () {
    stepCount++;
    $('#stepCount').val(stepCount);

    const stepRow = $('<div>').addClass('step-row form-group');
    stepRow.append(
        $('<label>')
            .attr('for', 'step' + stepCount)
            .text('Bước ' + stepCount + ':'),
    );
    stepRow.append(
        $('<input>')
            .attr('type', 'text')
            .addClass('form-control')
            .attr('id', 'step' + stepCount)
            .attr('placeholder', 'Bước ' + stepCount)
            .attr('required', true)
            .attr('name' , 'step' + stepCount),
    );

    // Thêm trường chọn tệp hình ảnh
    stepRow.append(
        $('<input>')
            .attr('type', 'file')
            .addClass('form-control-file')
            .attr('id', 'step' + stepCount + 'Image')
            .attr('accept', 'image/*')
            .attr('multiple', true) // Cho phép chọn nhiều tệp hình ảnh
            .attr('required', false)
            .attr('name' , 'step' + stepCount + 'Image'),
    );

    // stepRow.append(
    //     $('<button>')
    //         .attr('type', 'button')
    //         .addClass('btn btn-danger')
    //         .text('Xoá')
    //         .data('bs-toggle', 'tooltip')
    //         .data('bs-placement', 'top')
    //         .attr('title', 'Xoá Bước')
    //         .click(function () {
    //             stepRow.remove();
    //         }),
    // );

    $('#step').append(stepRow);
});

// function removeIngredient(button) {
//     $(button).parent('.ingredient-row').remove();
// }
//
// function removeStep(button) {
//     $(button).parent('.step-row').remove();
// }

$('#removeLastIngredient').click(function (e) {
    e.preventDefault();
    const ingredientRows = $('.ingredient-row');
    if (ingredientRows.length > 0) {
        ingredientRows.last().remove();
        ingredientCount--;
        $('#ingredientCount').val(ingredientCount);

    }
});

$('#removeLastStep').click(function (e) {
    e.preventDefault();
    const stepRows = $('.step-row');
    if (stepRows.length > 0) {
        stepRows.last().remove();
        stepCount--;
        $('#stepCount').val(stepCount);
    }
});

// // Đối với mỗi lựa chọn đơn vị đo trong dropdown
// $('select[name^="measurements"]').on('change', function () {
//     const selectedMeasurement = $(this).val();
//     const $amountInput = $(this).closest('.ingredient-row').find('input[type="number"]');
//
//     // Kiểm tra nếu đơn vị đo không phải là "gram" hoặc "kilogram"
//     if (selectedMeasurement !== 'gam' && selectedMeasurement !== 'kilogam') {
//         // Tắt khả năng nhập số lượng
//         $amountInput.attr('disabled', true);
//     } else {
//         // Cho phép nhập số lượng
//         $amountInput.attr('disabled', false);
//     }
// });





