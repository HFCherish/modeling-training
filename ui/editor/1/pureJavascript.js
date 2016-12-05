function render() {
    var showButtons = document.getElementsByClassName("showButton");
    for (var i = 0; i < showButtons.length; i++) {
        var showButton = showButtons[i];
        showButton.onclick = function (evt) {
            var parentNode = this.parentNode;
            var titleNode = parentNode.querySelector('li.title');
            var textNode = parentNode.querySelector('li.text');
            var previousTitle = titleNode.firstElementChild.value;
            previousTitle = previousTitle == undefined ? "title" : previousTitle;
            var previousText = textNode.firstElementChild.value;
            previousText = previousText == undefined ? "" : previousText;
            titleNode.innerHTML = "<h1>" + previousTitle + "</h1>";
            textNode.innerHTML = "<textarea readonly>" + previousText +"</textarea>";
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
            var previousTitle = titleNode.firstElementChild.textContent;
            var previousText = textNode.firstElementChild.textContent;
            titleNode.innerHTML = "<input type='text' value='" + previousTitle + "'/>";
            textNode.innerHTML = "<textarea>" + previousText +"</textarea>";
        }
    }
}

render();
edit();