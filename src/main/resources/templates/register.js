document.getElementById("registerForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Prevent default form submission

    const fullName = document.getElementById("fullName").value;
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const responseMessage = document.getElementById("responseMessage");

    if (password !== confirmPassword) {
        responseMessage.innerHTML = "Passwords do not match!";
        responseMessage.style.color = "red";
        return;
    }

    const userData = {
        fullName: fullName,
        username: username,
        email: email,
        password: password,
        confirmPassword: confirmPassword
    };

    try {
        const response = await fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userData)
        });

        const result = await response.text();
        responseMessage.innerHTML = result;

        if (result.includes("successfully")) {
            responseMessage.style.color = "green";
            setTimeout(() => {
                window.location.href = "login.html"; // Redirect to login page after successful registration
            }, 2000);
        } else {
            responseMessage.style.color = "red";
        }

    } catch (error) {
        console.error("Error:", error);
        responseMessage.innerHTML = "Something went wrong. Please try again!";
        responseMessage.style.color = "red";
    }
});
