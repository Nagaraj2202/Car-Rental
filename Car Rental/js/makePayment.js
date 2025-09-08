function makePayment() {
  const amount = document.getElementById("amount").value;

  if (!selectedMethod) {
    alert("Please select a payment method.");
    return;
  }

  if (!amount || amount <= 0) {
    alert("Please enter a valid amount.");
    return;
  }

  // Save selected method and amount to session storage
  sessionStorage.setItem("paymentAmount", amount);
  sessionStorage.setItem("paymentMethod", selectedMethod);

  // Redirect to confirmation page
  window.location.href = "payment_done.html";
}
