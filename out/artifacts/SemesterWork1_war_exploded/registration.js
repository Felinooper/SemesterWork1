function checkUsername() {
    const username = document.getElementById("username")
    const xml = new XMLHttpRequest()
    if (!/^[a-z0-9_-]{3,16}$/i.test(username.value))  {
        document.getElementById("username-size-error").style.display = "block"
        document.getElementById("username-error").style.display = "none"
        return false
    } else {
        document.getElementById("username-size-error").style.display = "none"
        document.getElementById("lgn").disabled = false
    }

    xml.open("GET", "http://localhost:8080/logInCheck?username=" + username.value, false)
    xml.send()

    const result = xml.responseText;

    if (result === "true") {
        document.getElementById("username-error").style.display = "none"
        return true
    }

    if (xml.status !== 200) {
        document.getElementById("username-error").style.display = "block"
    } else {
        if (result === "false") {
            document.getElementById("username-error").style.display = "block"
        }
        else {
            document.getElementById("username-error").style.display = "none"
            return true
        }
    }
}

function checkPasswords() {
    const password = document.getElementById("password")
    const secondPassword = document.getElementById("secondPass")

    if (password.value !== secondPassword.value || !/(?=.*[0-9])[a-zA-Z0-9]{6,64}/.test(password.value)) {
        document.getElementById("password-error").style.display = "block"
    } else {
        document.getElementById("password-error").style.display = "none"
        return true
    }
}

function checkAll() {
    console.log(checkUsername(), checkPasswords())
    if (checkPasswords() && checkUsername()) {
        document.getElementById("username-error").style.display = "none"
        document.getElementById("password-error").style.display = "none"
        document.getElementById("lgn").disabled = false
    } else {
        document.getElementById("lgn").disabled = true
    }
}