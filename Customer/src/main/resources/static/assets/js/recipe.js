let ingredientCount = 1; // Biến đếm nguyên liệu
let stepCount = 1; // Biến đếm bước nấu ăn

$('#addIngredient').click(function () {
    ingredientCount++;

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
            .attr('required', true),
    );
    ingredientRow.append(
        $('<button>')
            .attr('type', 'button')
            .addClass('btn btn-danger')
            .text('Xoá')
            .data('bs-toggle', 'tooltip')
            .data('bs-placement', 'top')
            .attr('title', 'Xoá Nguyên liệu')
            .click(function () {
                ingredientRow.remove();
            }),
    );

    $('#ingredient').append(ingredientRow);
});

$('#addStep').click(function () {
    stepCount++;

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
            .attr('required', true),
    );

    // Thêm trường chọn tệp hình ảnh
    stepRow.append(
        $('<input>')
            .attr('type', 'file')
            .addClass('form-control-file')
            .attr('id', 'step' + stepCount + 'Image')
            .attr('accept', 'image/*')
            .attr('multiple', true) // Cho phép chọn nhiều tệp hình ảnh
            .attr('required', true),
    );

    stepRow.append(
        $('<button>')
            .attr('type', 'button')
            .addClass('btn btn-danger')
            .text('Xoá')
            .data('bs-toggle', 'tooltip')
            .data('bs-placement', 'top')
            .attr('title', 'Xoá Bước')
            .click(function () {
                stepRow.remove();
            }),
    );

    $('#step').append(stepRow);
});

function removeIngredient(button) {
    $(button).parent('.ingredient-row').remove();
}

function removeStep(button) {
    $(button).parent('.step-row').remove();
}
