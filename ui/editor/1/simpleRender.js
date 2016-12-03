function render() {
    var showButtons = document.getElementsByClassName("showButton");
    for (var i = 0; i < showButtons.length; i++) {
        var showButton = showButtons[i];
        showButton.onclick = function () {
            var parentNode = this.parentNode;
            var titleNode = parentNode.querySelector('li.title');
            var textNode = parentNode.querySelector('li.text');
            titleNode.innerHTML = "<h1> my test title </h1>";
            textNode.innerHTML = "<textarea readonly>my test area</textarea>";
        };
    }
}

function edit() {
    var editButtons = document.getElementsByClassName("editButton");
    for (var i = 0; i < editButtons.length; i++) {
        editButtons[i].onclick = function () {
            var parentNode = this.parentNode;
            var titleNode = parentNode.querySelector('li.title');
            var textNode = parentNode.querySelector('li.text');
            titleNode.innerHTML = "<input type='text' />"
            textNode.innerHTML = "<textarea></textarea>";
        }
    }
}

render();
edit();

// function render() {
//     var viewButtons = document.getElementsByClassName("showButton");
//     for(var i=0; i<viewButtons.length; i++) {
//         var viewButton = viewButtons[i];
//         viewButton.addEventListener('click', function (event) {
//             var titleNode = viewButton.parentNode.childNodes.item(2);
//             console.log(titleNode.innerHTML + "******");
//             titleNode.innerHTML = "<h1>my test title</h1>";
//
//             var textNode = viewButton.parentNode.childNodes.item(3);
//             textNode.innerHTML = "<textarea readonly>my test textarea</textarea>";
//         })
//
//     };
//     // Array.from(viewButtons).forEach(function (viewButton) {
//     //     viewButton.addEventListener('click', function (event) {
//     //         var titleNode = viewButton.parentNode.childNodes.item(2);
//     //         titleNode.innerHTML = "<h1>my test title</h1>";
//     //
//     //         var textNode = viewButton.parentNode.childNodes.item(3);
//     //         textNode.innerHTML = "<textarea readonly>my test textarea</textarea>";
//     //     })
//     // });
// }
//
// function edit() {
//     var editButtons = document.getElementsByClassName("editButton");
//     // Array.from(editButtons).forEach(function (editButton) {
//     for(var i=0; i<editButtons.length; i++) {
//         var editButton = editButtons[i];
//         editButton.addEventListener('click', function (event) {
//             var titleNode = editButton.parentNode.childNodes.item(2);
//             titleNode.innerHTML = "<input type='text'>";
//
//             var textNode = editButton.parentNode.childNodes.item(3);
//             textNode.innerHTML = "<textarea></textarea>";
//         })
//     };
// }
//
// render();
// edit();