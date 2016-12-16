$(document).ready(function () {
    render();
    edit();
});

function render() {
    $('.showButton').each(function () {
        $(this).click(function () {
            $('.title').innerHTML = "<h1>my test title</h1>";
            $('.text').innerHTML = "<textarea readonly>my test content</textarea>";
            // $(this).parent().children('li.title').replaceWith("<li class='title'><h1></h1></li>");
            // $(this).parent().children('li.title').children('h1').text("my test title");

            // $(this).parent().children('li.text').replaceWith("<li class='title'><textarea readonly></textarea></li>");
            // $(this).parent().children('li.text').children('textarea').text("my test content");
        });
    });
}

function edit() {
    $('.editButton').each(function () {
        $(this).click(function () {
            $('.title').innerHTML = "<h1>my test title</h1>";
            $('.text').innerHTML = "<textarea></textarea>";
        });
    });


    // var editButtons = document.getElementsByClassName("editButton");
    // // Array.from(editButtons).forEach(function (editButton) {
    // for(var i=0; i<editButtons.length; i++) {
    //     var editButton = editButtons[i];
    //     editButton.addEventListener('click', function (event) {
    //         var titleNode = editButton.parentNode.childNodes[2];
    //         titleNode.innerHTML = "<input type='text'>";
    //
    //         var textNode = editButton.parentNode.childNodes.item[3];
    //         textNode.innerHTML = "<textarea></textarea>";
    //     })
    // };
}
