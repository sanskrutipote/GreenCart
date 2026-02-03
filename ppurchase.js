document
  .getElementById("purchaseForm")
  .addEventListener("submit", function (event) {
    // Simple validation example
    const item = document.getElementById("item").value;
    const qty = document.getElementById("right-page--qty").value;
    const cid = document.getElementById("cid").value;

    if (!item || !qty || !cid) {
      alert("Please fill out all fields.");
      event.preventDefault();
    } else {
      alert("Form submitted successfully!");
    }
  });
