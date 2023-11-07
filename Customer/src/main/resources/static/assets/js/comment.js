//
//post options

async function postReaction(data){
    const url = 'http://localhost:8020/shop/api/add-reaction';
    const postOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    }

    fetch(url, postOptions)
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
async function reaction (commentId, userId, type) {
    console.log(commentId);
    console.log(userId);
    console.log(type);
    const data = {"commentId": commentId,"customerId": userId,"type": type};
    const jsonData = JSON.stringify(data);
    console.log(typeof jsonData);
    // await postReaction(jsonData);
    console.log(typeof data);
    await postReaction(data);
    window.location.reload();
}

//get number for reaction
async function getNumberReaction(commentId, type) {
    const url =
        'http://localhost:8020/shop/api/show-reaction?commentId=' +
        commentId +
        '&type=' +
        type;
    const getOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const response = await fetch(url, getOptions);
    const data = await response.json();
    console.log(data);
    return data;
}

async function updateReaction(commentId, type) {
    const result = await getNumberReaction(commentId, type);
    const pElement = document.getElementById(type + commentId);
    pElement.innerText = result; // Cập nhật nội dung với kết quả từ hàm getNumberReaction
}

var likeElements = document.querySelectorAll('[name="like"]');
likeElements.forEach(function (element) {
    var like_commentId = element.getAttribute('id').replace('like', '');

    updateReaction(like_commentId, 'like');

});

var dislikeElements = document.querySelectorAll('[name="dislike"]');
dislikeElements.forEach(function (element) {
    var dislike_commentId = element.getAttribute('id').replace('dislike', '');
    updateReaction(dislike_commentId, 'dislike');
});
//
// setInterval(() => {
//     updateReaction(commentId, type); // Thay commentId và type bằng giá trị thực tế
// }, 5000);