function handleRejectButtonClick(buttonValue) {
    var hiddenInput = document.querySelector('input[name="orderId"]');
    hiddenInput.value = buttonValue;
}