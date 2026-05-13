// ===== LOGIN PAGE =====
const loginForm = document.getElementById("loginForm");
if(loginForm) {
    const showBtn = document.getElementById("showBtn");
    if(showBtn) {
        showBtn.addEventListener("click", function() {
            const passField = document.getElementById("password");
            if(passField.type === "password") {
                passField.type = "text";
                showBtn.innerText = "Hide";
            } else {
                passField.type = "password";
                showBtn.innerText = "Show";
            }
        });
    }

    loginForm.addEventListener("submit", function(e) {
        e.preventDefault();
        const user = document.getElementById("username").value;
        const pass = document.getElementById("password").value;
        if(user == "") { alert("Please enter username!"); return; }
        if(pass == "") { alert("Please enter password!"); return; }
        if(user.length < 3) { alert("Username must be at least 3 characters!"); return; }
        if(pass.length < 3) { alert("Password must be at least 3 characters!"); return; }
        const btn = document.getElementById("loginBtn");
        btn.innerText = "Signing in...";
        btn.disabled = true;
        loginForm.submit();
    });
}

// ===== DELETE CONFIRMATION =====
const deleteBtns = document.querySelectorAll(".btn-delete");
if(deleteBtns.length > 0) {
    deleteBtns.forEach(function(btn) {
        btn.addEventListener("click", function(e) {
            const confirmed = window.confirm("Are you sure you want to delete this record?");
            if(!confirmed) { e.preventDefault(); }
        });
    });
}

// ===== PATIENT ADD PAGE =====
const patientForm = document.getElementById("patientForm");
if(patientForm) {
    patientForm.addEventListener("submit", function(e) {
        e.preventDefault();
        const name = document.getElementById("fullName").value;
        const age = document.getElementById("age").value;
        const phone = document.getElementById("phone").value;
        const disease = document.getElementById("disease").value;
        const gender = document.getElementById("gender").value;
        if(name == "") { alert("Please enter patient name!"); return; }
        if(age < 1 || age > 120) { alert("Please enter valid age (1-120)!"); return; }
        if(phone.length != 10) { alert("Phone must be 10 digits!"); return; }
        if(disease == "") { alert("Please enter disease!"); return; }
        if(gender == "") { alert("Please select gender!"); return; }
        const btn = document.getElementById("patientBtn");
        if(btn) { btn.innerText = "Saving..."; btn.disabled = true; }
        patientForm.submit();
    });
}

// ===== DOCTOR ADD PAGE =====
const doctorForm = document.getElementById("doctorForm");
if(doctorForm) {
    doctorForm.addEventListener("submit", function(e) {
        e.preventDefault();
        const name = document.getElementById("fullname").value;
        const spec = document.getElementById("specialization").value;
        const phone = document.getElementById("phone").value;
        const exp = document.getElementById("experience").value;
        const fee = document.getElementById("fee").value;
        if(name == "") { alert("Please enter doctor name!"); return; }
        if(spec == "") { alert("Please select specialization!"); return; }
        if(phone.length != 10) { alert("Phone must be 10 digits!"); return; }
        if(exp < 0 || exp > 50) { alert("Experience must be between 0 and 50!"); return; }
        if(fee <= 0) { alert("Please enter valid fee!"); return; }
        const btn = document.getElementById("doctorBtn");
        if(btn) { btn.innerText = "Saving..."; btn.disabled = true; }
        doctorForm.submit();
    });
}

// ===== APPOINTMENT PAGE =====
const appointmentForm = document.getElementById("appointmentForm");
if(appointmentForm) {
    const dateInput = document.getElementById("date");
    if(dateInput) {
        const today = new Date().toISOString().split("T")[0];
        dateInput.setAttribute("min", today);
    }
    appointmentForm.addEventListener("submit", function(e) {
        e.preventDefault();
        const patient = document.getElementById("patient").value;
        const doctor = document.getElementById("doctor").value;
        const date = document.getElementById("date").value;
        const time = document.getElementById("time").value;
        const reason = document.getElementById("reason").value;
        if(patient == "") { alert("Please select a patient!"); return; }
        if(doctor == "") { alert("Please select a doctor!"); return; }
        if(date == "") { alert("Please select a date!"); return; }
        const today = new Date().toISOString().split("T")[0];
        if(date < today) { alert("Cannot select past date!"); return; }
        if(time == "") { alert("Please select a time!"); return; }
        if(reason == "") { alert("Please enter reason for visit!"); return; }
        const btn = document.getElementById("appointmentBtn");
        if(btn) { btn.innerText = "Booking..."; btn.disabled = true; }
        appointmentForm.submit();
    });
}

// ===== AI CHATBOT =====
function openChat() { document.getElementById("chatModal").style.display = "flex"; }
function closeChat() { document.getElementById("chatModal").style.display = "none"; }

async function sendChat() {
    const input = document.getElementById("chatInput");
    const question = input.value.trim();
    if(question == "") { alert("Please type a question!"); return; }
    const messages = document.getElementById("chatMessages");
    messages.innerHTML += `<div class="user-message">${question}</div>`;
    input.value = "";
    messages.innerHTML += `<div class="ai-message" id="loadingMsg">🤔 Thinking...</div>`;
    messages.scrollTop = messages.scrollHeight;
    try {
        const response = await fetch("/ai/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ question: question })
        });
        const data = await response.json();
        document.getElementById("loadingMsg").remove();
        messages.innerHTML += `<div class="ai-message">${data.answer}</div>`;
        messages.scrollTop = messages.scrollHeight;
    } catch(error) {
        document.getElementById("loadingMsg").remove();
        messages.innerHTML += `<div class="ai-message">Sorry, something went wrong!</div>`;
    }
}

// ===== DOCTOR SUGGESTER =====
function openSuggest() { document.getElementById("suggestModal").style.display = "flex"; }
function closeSuggest() {
    document.getElementById("suggestModal").style.display = "none";
    document.getElementById("suggestResult").innerHTML = "";
    document.getElementById("diseaseInput").value = "";
}

async function suggestDoctor() {
    const disease = document.getElementById("diseaseInput").value.trim();
    if(disease == "") { alert("Please enter a disease or symptom!"); return; }
    const result = document.getElementById("suggestResult");
    result.innerHTML = "🔍 Finding best doctor for you...";
    try {
        const response = await fetch("/ai/suggest-doctor", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ disease: disease })
        });
        const data = await response.json();
        result.innerHTML = data.suggestion;
    } catch(error) {
        result.innerHTML = "Sorry, something went wrong!";
    }
}

// ===== REPORT SUMMARIZER =====
function openSummarize() { document.getElementById("summarizeModal").style.display = "flex"; }
function closeSummarize() {
    document.getElementById("summarizeModal").style.display = "none";
    document.getElementById("summarizeResult").innerHTML = "";
    document.getElementById("reportInput").value = "";
}

async function summarizeReport() {
    const details = document.getElementById("reportInput").value.trim();
    if(details == "") { alert("Please enter patient details!"); return; }
    const result = document.getElementById("summarizeResult");
    result.innerHTML = "📋 Generating summary...";
    try {
        const response = await fetch("/ai/summarize", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ details: details })
        });
        const data = await response.json();
        result.innerHTML = data.summary;
    } catch(error) {
        result.innerHTML = "Sorry, something went wrong!";
    }
}

// Close modal when clicking outside
window.addEventListener("click", function(e) {
    if(e.target.classList.contains("ai-modal")) {
        e.target.style.display = "none";
    }
});
