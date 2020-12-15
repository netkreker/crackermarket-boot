function registerUser() {
    var password        = document.getElementById("password").value;
    var passwordConfirm = document.getElementById("passwordConfirm").value;

    if (password !== passwordConfirm) {
        alert("Passwords must match!");
        return false;
    }
    else {

        // Converting HTML-form to JSON
        let object = {};
        let formData = new FormData(document.forms["registerForm"]);

        formData.forEach(function (value, key) {
            object[key] = value;
        });

        let json = JSON.stringify(object);

        // Creating request

        $.ajax({
            type: "POST",
            url: "/users/register",
            data: json,
            contentType: "application/json",
            dataType: 'json'
        });


    }
}
/////////////////////////////////
// Creating request

/*let xhr = new XMLHttpRequest(); // у конструктора нет аргументов

xhr.open("POST", "/users/register", false);
xhr.setRequestHeader("Content-type", "application/json");

xhr.onload = function() {
    if (xhr.status === 201)
    alert(`Loaded: ${xhr.status} ${xhr.response}`);
};
//xhr.send(json);*/
/////////////////////////////////