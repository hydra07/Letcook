$(document).ready(function () {
    let stepCount = 1;
    let ingredientCount = 1;

    // Xử lý sự kiện khi nút "Thêm Nguyên liệu" được bấm
    $("#addIngredient").click(function () {
        ingredientCount++;
        // Tạo mới dòng nguyên liệu và thêm nó vào div chứa nguyên liệu
        const newIngredientRow = `
            <div class="ingredient-row form-group">
                <label>Nguyên liệu ${ingredientCount}:</label>
                <input
                    type="text"
                    class="form-control"
                    id="ingredient${ingredientCount}"
                    placeholder="Nguyên liệu ${ingredientCount}"
                    required
                />
                <button
                    type="button"
                    class="btn btn-danger"
                    data-bs-toggle="tooltip"
                    data-bs-placement="top"
                    title="Xoá Nguyên liệu"
                    onclick="removeIngredient(this)"
                >
                    Xoá
                </button>
            </div>
        `;
        $(".list-group").append(newIngredientRow);
        $("#ingredientCount").val(ingredientCount);
    });

    // Xử lý sự kiện khi nút "Thêm Bước" được bấm
    $("#addStep").click(function () {
        stepCount++;
        // Tạo mới dòng bước thực hiện và thêm nó vào div chứa bước
        const newStepRow = `
            <div class="step-row form-group">
                <label for="step">Bước ${stepCount}:</label>
                <input
                    type="text"
                    class="form-control"
                    id="step${stepCount}"
                    placeholder="Bước ${stepCount}"
                    required
                />
                <input
                    type="file"
                    multiple
                    class="form-control-file"
                    id="step${stepCount}Image"
                    accept="image/*"
                    required
                />
                <button
                    type="button"
                    class="btn btn-danger"
                    data-bs-toggle="tooltip"
                    data-bs-placement="top"
                    title="Xoá Bước"
                    onclick="removeStep(this)"
                >
                    Xoá
                </button>
            </div>
        `;
        $(".list-group").append(newStepRow);
        $("#stepCount").val(stepCount);
    });
});
